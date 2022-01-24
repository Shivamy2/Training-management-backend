package com.trainingmanagementserver.api;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.trainingmanagementserver.entity.AssignmentDetail;
import com.trainingmanagementserver.entity.AssignmentSubmitDetails;
import com.trainingmanagementserver.entity.FileDB;
import com.trainingmanagementserver.exception.ApiRequestException;
import com.trainingmanagementserver.message.ResponseFile;
import com.trainingmanagementserver.message.ResponseMessage;
import com.trainingmanagementserver.repository.*;
import com.trainingmanagementserver.service.AssignmentDetailsService;
import com.trainingmanagementserver.service.AssignmentSubmitDetailsService;
import com.trainingmanagementserver.service.FileDBService;
import com.trainingmanagementserver.utility.Utility;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
//@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AssignmentController {
    private final UserCredentialsRepository userCredentialsRepository;
    private final AssignmentDetailsService assignmentDetailsService;
    private final AssignmentDetailRepository assignmentDetailRepository;
    private final FileDBService fileDBService;
    private final FileDBRepository fileDBRepository;
    private final AssignmentSubmitDetailsRepository assignmentSubmitDetailsRepository;
    private final AssignmentSubmitDetailsService assignmentSubmitDetailsService;
    private final TraineesTrainerRepository traineesTrainerRepository;

    @PostMapping("/assignment/upload")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam("title") String title, @RequestParam("description") String description, @RequestParam("total_credit") int total_credit, @RequestParam("due_date") String due_date, HttpServletRequest request) {
        String senderUsername = (new Utility()).getUsernameFromToken(request);
        int trainerId = userCredentialsRepository.findByUsername(senderUsername).getId();
        var savedAssignment = assignmentDetailsService.save(new AssignmentDetail(trainerId, title, description, total_credit, due_date, true));
        if(file != null) {
            try {
                var savedFile = fileDBService.store(file, savedAssignment.getId());
                var responseFile = (new Utility()).getResponseFile(savedFile);
                return ResponseEntity.ok(new AssignmentFileMerged(savedAssignment, responseFile));
            } catch(IOException exception) {
                String message = "File cannot be uploaded";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }
        return ResponseEntity.ok(new AssignmentFileMerged(savedAssignment, new ResponseFile()));
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<ResponseFile> getFile(@PathVariable("id") int id) {
        var file = fileDBService.getFileByFileId(id);
        var responseFile = (new Utility()).getResponseFile(file);
        return ResponseEntity.ok().body(responseFile);
    }

    @GetMapping("/assignment/all")
    public ResponseEntity<List<AssignmentFileMerged>> getAllAssignments(HttpServletRequest request) {
        String username = (new Utility()).getUsernameFromToken(request);
        int trainerId = userCredentialsRepository.findByUsername(username).getId();
        var assignments = assignmentDetailRepository.findByTrainerId(trainerId);
        List<AssignmentFileMerged> assignmentFileMerged = new ArrayList<>();
        if(assignments != null) {
            assignments.forEach(assignmentDetail -> {
                var assignmentFile = new AssignmentFileMerged();
                var fileDetails = fileDBRepository.findByAssignmentId(assignmentDetail.getId());
                assignmentFile.setResponseFile(new ResponseFile());
                if(fileDetails != null) {
                    var responseFile = (new Utility()).getResponseFile(fileDetails);
                    assignmentFile.setResponseFile(responseFile);
                }
                assignmentFile.setAssignmentDetail(assignmentDetail);
                assignmentFileMerged.add(assignmentFile);
            });
        } else {
            throw new ApiRequestException("No assignments are present");
        }
        return ResponseEntity.ok().body(assignmentFileMerged);
    }

    @GetMapping("assignment/trainee/all")
    public ResponseEntity<List<AssignmentFileMerged>> getAllAssignmentsVisible(HttpServletRequest request) {
        String username = (new Utility()).getUsernameFromToken(request);
        int id = userCredentialsRepository.findByUsername(username).getId();
        var trainerTrainee = traineesTrainerRepository.findByTraineeId(id);
        log.info(String.valueOf(trainerTrainee));
        if(trainerTrainee != null) {
            int trainerId = trainerTrainee.getTrainerId();
            var assignments = assignmentDetailRepository.findByTrainerId(trainerId);
            List<AssignmentFileMerged> assignmentFileMerged = new ArrayList<>();
            if (assignments != null) {
                assignments.forEach(assignmentDetail -> {
                    var isAssignmentSubmitted = assignmentSubmitDetailsRepository.findByAssignmentId(assignmentDetail.getId());
                    if(assignmentDetail.isAvailable() && isAssignmentSubmitted == null) {
                        var assignmentFile = new AssignmentFileMerged();
                        var fileDetails = fileDBRepository.findByAssignmentId(assignmentDetail.getId());
                    assignmentFile.setResponseFile(new ResponseFile());
                        if(fileDetails != null) {
                            var responseFile = (new Utility()).getResponseFile(fileDetails);
                            assignmentFile.setResponseFile(responseFile);
                        }
                        assignmentFile.setAssignmentDetail(assignmentDetail);
                        assignmentFileMerged.add(assignmentFile);
                        log.error(String.valueOf(assignmentFileMerged));
                    }
                });
            } else {
                throw new ApiRequestException("You have no assignments till now");
            }
            return ResponseEntity.ok().body(assignmentFileMerged);
        } else {
            throw new ApiRequestException("Trainee is not enrolled in any training");
        }
    }

    @PostMapping("assignment/trainee/submit")
    public ResponseEntity<AssignmentSubmitDetailsMerged> submitAssignmentSolution(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file , @RequestParam("assignment_id") int assignmentId, @RequestParam(value = "link", required = false) String link, @RequestParam("description") String description) throws IOException, ApiRequestException, ParseException {
        String username = new Utility().getUsernameFromToken(request);
        int traineeId = userCredentialsRepository.findByUsername(username).getId();
        var redundantAssgId = assignmentSubmitDetailsRepository.findByAssignmentId(assignmentId);
        var assignment = assignmentDetailRepository.findById(assignmentId);
        if(assignment.isPresent()) {
            var date = assignment.get().getDueDate();
            String choppedDate = date.substring(1, date.length()-1);
            DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            var date1 = simpleDateFormat.parse(choppedDate);
            var today = new Date();
            var current = simpleDateFormat.parse(simpleDateFormat.format(today));
            var checkDate = date1.compareTo(current);
            if(checkDate >= 0) {
                if(redundantAssgId == null) {
                    var savedAssignmentSubmitDetail = assignmentSubmitDetailsService.save(new AssignmentSubmitDetails(assignmentId, traineeId, link, description));
                    var responseFile = new ResponseFile();
                    if (file != null) {
                        var savedFile = fileDBService.store(file, savedAssignmentSubmitDetail.getId());
                        responseFile = (new Utility()).getResponseFile(savedFile);
                    }
                    return ResponseEntity.ok().body(new AssignmentSubmitDetailsMerged(savedAssignmentSubmitDetail, responseFile));
                } else {
                    throw new ApiRequestException("Assignment already submitted");
                }
            } else {
                throw new ApiRequestException("Assignment is expired");
            }
        } else {
            throw new ApiRequestException("Assignment does not exist");
        }
    }

    @GetMapping("/assignment/trainee/submit/all")
    public ResponseEntity<List<AssignmentDetailSubmitDetailsMerged>> getSubmittedAssignments(HttpServletRequest request) {
        String username = (new Utility()).getUsernameFromToken(request);
        int traineeId = userCredentialsRepository.findByUsername(username).getId();
        var submittedAssignments = assignmentSubmitDetailsService.getSubmitAssignments(traineeId);
        log.info(String.valueOf(submittedAssignments));
        List<AssignmentDetailSubmitDetailsMerged> assignmentDetailSubmitDetailsMerged = new ArrayList<>();
        submittedAssignments.forEach(assignment -> {
            var merge = new AssignmentDetailSubmitDetailsMerged();
            var assignmentDetail = assignmentDetailRepository.findById(assignment.getAssignmentId());
            if (assignmentDetail.isPresent()) {
                merge.setAssignmentDetail(assignmentDetail.get());
            } else {
                merge.setAssignmentDetail(new AssignmentDetail());
            }
            merge.setLink(assignment.getLink());
            merge.setSolution(assignment.getDescription());
            var file = fileDBRepository.findByAssignmentId(assignment.getId());
            merge.setResponseFile(new ResponseFile());
            if(file != null) {
                var responseFile = new Utility().getResponseFile(file);
                merge.setResponseFile(responseFile);
            }
            assignmentDetailSubmitDetailsMerged.add(merge);
        });
        return ResponseEntity.ok().body(assignmentDetailSubmitDetailsMerged);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class AssignmentFileMerged {
    @JsonUnwrapped
    private AssignmentDetail assignmentDetail;
    @JsonUnwrapped
    private ResponseFile responseFile;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class AssignmentSubmitDetailsMerged {
    @JsonUnwrapped
    private AssignmentSubmitDetails assignmentSubmitDetails;
    @JsonUnwrapped
    private ResponseFile responseFile;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class AssignmentDetailSubmitDetailsMerged {
    @JsonUnwrapped
    private AssignmentDetail assignmentDetail;
    private String link;
    private String solution;
    @JsonUnwrapped
    private ResponseFile responseFile;
}