package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.Role;
import com.trainingmanagementserver.entity.UserCredentialsEntity;
import com.trainingmanagementserver.entity.UserMerged;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserCredentialsService {
    // save user
    UserCredentialsEntity userCredentialsSave(UserCredentialsEntity user);

    // add trainees in bulk
    List<UserCredentialsEntity> addBulkTrainees(List<UserCredentialsEntity> user);

    // show trainees enrolled by a trainer
    List<UserMerged> showTraineesEnrolled(int id);

    // save role
    Role roleSave(Role role);

    // fetch all users
    List<UserCredentialsEntity> fetchUsersCredentials();

    // add role to user
    void addRoleToUser(String roleName, String username );

    // get a particular user
    UserCredentialsEntity getUser(String username);
 
    // delete user
    void deleteUser(int userId);
}
