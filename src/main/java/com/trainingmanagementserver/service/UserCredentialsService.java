package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.Role;
import com.trainingmanagementserver.entity.UserCredentialsEntity;

import java.util.List;

public interface UserCredentialsService {
    // save user
    UserCredentialsEntity userCredentialsSave(UserCredentialsEntity user);

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
