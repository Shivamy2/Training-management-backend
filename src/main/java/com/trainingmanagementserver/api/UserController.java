package com.trainingmanagementserver.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainingmanagementserver.entity.*;
import com.trainingmanagementserver.exception.ApiRequestException;
import com.trainingmanagementserver.repository.UserCredentialsRepository;
import com.trainingmanagementserver.service.UserCredentialsService;
import com.trainingmanagementserver.service.UserDetailService;
import com.trainingmanagementserver.utility.Utility;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
//@Validated
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    private final UserCredentialsService userCredentialsService;
    private final UserDetailService userDetailService;
    private final UserCredentialsRepository userCredentialsRepository;

    @PostMapping("/auth/signup")
    public ResponseEntity<UserCredentialsEntity> saveUser(@RequestBody UserCredentialsEntity userCredentialsEntity) throws ApiRequestException{
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/signup").toUriString());
        var username = userCredentialsRepository.findByUsername(userCredentialsEntity.getUsername());
        var email = userCredentialsRepository.findByEmail(userCredentialsEntity.getEmail());
        if(username == null  && email == null) {
            return ResponseEntity.created(uri).body(userCredentialsService.userCredentialsSave(userCredentialsEntity));
        } else if(username != null && email != null) {
            throw new ApiRequestException("Username and Email already exist");
        }
        else if(username != null){
            throw new ApiRequestException("Username already exist");
        }
        throw new ApiRequestException("Email already exist");
    }

    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role").toUriString());
        return ResponseEntity.created(uri).body(userCredentialsService.roleSave(role));
    }

    @GetMapping("/register/check")
    public ResponseEntity<?> checkRegister(HttpServletRequest request) throws ApiRequestException{
        try {
            String username = (new Utility()).getUsernameFromToken(request);
            int userId = userCredentialsRepository.findByUsername(username).getId();
            Map<String, Boolean> response = new HashMap<>();
            if (userDetailService.fetchUserDetails(userId).isPresent()) {
                response.put("isPresent", true);
            } else {
                response.put("isPresent", false);
            }
            return ResponseEntity.ok().body(response);
        } catch (ApiRequestException exception) {
            throw new ApiRequestException("Token is mismatch");
        }
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm roleForm) throws ApiRequestException {
        try {
            userCredentialsService.addRoleToUser(roleForm.getRoleName(), roleForm.getUsername());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new ApiRequestException("Not able to add role to the user");
        }
    }

    @PostMapping("/me")
    public ResponseEntity<?> saveUser(@Valid @RequestBody UserDetailsEntity userDetailsEntity, HttpServletRequest request) {
//        System.out.println("Printing result "+result);
//        if(result.hasErrors()) {
//            Map<String, BindingResult> errors = new HashMap<>();
//            errors.put("error", result);
//            return ResponseEntity.badRequest().body(errors);
//        }
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/detail").toUriString());
        String username = (new Utility()).getUsernameFromToken(request);
        int userId = userCredentialsRepository.findByUsername(username).getId();
        userDetailsEntity.setId(userId);
        return ResponseEntity.created(uri).body(userDetailService.save(userDetailsEntity));
    }

    @GetMapping("/me")
    public ResponseEntity<UserMerged> fetchUserDetails(HttpServletRequest request) throws ApiRequestException {
        try {
            String username = (new Utility()).getUsernameFromToken(request);
            int userId = userCredentialsRepository.findByUsername(username).getId();
            if (userDetailService.fetchUserDetails(userId).isPresent()) {
                UserDetailsEntity userDetailsEntity = userDetailService.fetchUserDetails(userId).get();
                UserCredentialsEntity userCredentialsEntity = userCredentialsRepository.findByUsername(username);
                UserMerged userMerged = new UserMerged();
                userMerged.setUser_detail(userDetailsEntity);
                userMerged.setUser_credential(userCredentialsEntity);
                return ResponseEntity.ok().body(userMerged);
            }
        } catch (ApiRequestException exception) {
            throw new ApiRequestException("Token mismatch");
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        log.info(authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                log.info(refresh_token);
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                UserCredentialsEntity userCredentialsEntity = userCredentialsService.getUser(username);
                String access_token = JWT.create()
                        .withSubject(userCredentialsEntity.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", userCredentialsEntity.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token required!");
        }
    }

    @GetMapping("/user/all")
    public List<UserCredentialsEntity> fetchUsers() {
        return userCredentialsService.fetchUsersCredentials();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int userId) {
        userCredentialsService.deleteUser(userId);
        return ResponseEntity.ok("User is deleted Successfully!!");
    }
}

@Data
class RoleToUserForm {
    private String username;
    private String roleName;
}