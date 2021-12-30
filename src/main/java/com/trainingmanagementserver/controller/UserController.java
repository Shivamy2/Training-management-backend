package com.trainingmanagementserver.controller;

import com.trainingmanagementserver.entity.UserCredentialsEntity;
import com.trainingmanagementserver.repository.UserCredentialsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserCredentialsRepository userCredentialsRepository;

    public UserController(final UserCredentialsRepository userCredentialsRepository){
        this.userCredentialsRepository=userCredentialsRepository;
    }

    @PostMapping("/user")
    public UserCredentialsEntity saveUser(@RequestBody UserCredentialsEntity userCredentialsEntity) {
        return userCredentialsRepository.save(userCredentialsEntity);
    }

    @GetMapping("/user")
    public List<UserCredentialsEntity> fetchUsers() {
        return userCredentialsRepository.findAll();
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") int userId) {
        userCredentialsRepository.deleteById(userId);
        return "User is deleted Successfully!!";
    }
}
