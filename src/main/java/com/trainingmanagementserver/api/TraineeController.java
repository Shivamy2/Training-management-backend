package com.trainingmanagementserver.api;

import com.trainingmanagementserver.entity.TraineesTrainer;
import com.trainingmanagementserver.entity.UserCredentialsEntity;
import com.trainingmanagementserver.entity.UserMerged;
import com.trainingmanagementserver.exception.ApiRequestException;
import com.trainingmanagementserver.repository.TraineesTrainerRepository;
import com.trainingmanagementserver.repository.UserCredentialsRepository;
import com.trainingmanagementserver.service.UserCredentialsService;
import com.trainingmanagementserver.utility.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
//@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TraineeController {
    private final UserCredentialsService userCredentialsService;
    private final UserCredentialsRepository userCredentialsRepository;
    private final TraineesTrainerRepository traineesTrainerRepository;

    @PostMapping("/trainee/addBulk")
    public ResponseEntity<List<UserCredentialsEntity>> addBulkTrainees(@RequestBody List<UserCredentialsEntity> bulkUserCredentialsEntity, HttpServletRequest request) throws ApiRequestException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role").toUriString());
        String username = (new Utility()).getUsernameFromToken(request);
        int trainerId = userCredentialsRepository.findByUsername(username).getId();
        bulkUserCredentialsEntity.forEach(trainee -> {
            var checkUsername = userCredentialsRepository.findByUsername(trainee.getUsername());
            var checkEmail = userCredentialsRepository.findByEmail(trainee.getEmail());
            if(checkUsername != null || checkEmail != null) {
                throw new ApiRequestException("Invalid Entry");
            }
        });
        var savedTrainees = userCredentialsService.addBulkTrainees(bulkUserCredentialsEntity);
        savedTrainees.forEach(trainees -> {
            int traineeId = trainees.getId();
            TraineesTrainer traineesTrainer = new TraineesTrainer();
            traineesTrainer.setTraineeId(traineeId);
            traineesTrainer.setTrainerId(trainerId);
            traineesTrainerRepository.save(traineesTrainer);
            userCredentialsService.addRoleToUser("ROLE_TRAINEE", trainees.getUsername());
        });
        return ResponseEntity.created(uri).body(savedTrainees);
    }

    @GetMapping("/trainee/all")
    public ResponseEntity<List<UserMerged>> getAllTrainees(HttpServletRequest request) {
        String username = (new Utility().getUsernameFromToken(request));
        if(username.isEmpty()) throw new ApiRequestException("Username not present");
        var trainerId = userCredentialsRepository.findByUsername(username).getId();
        return ResponseEntity.ok().body(userCredentialsService.showTraineesEnrolled(trainerId));
    }

}
