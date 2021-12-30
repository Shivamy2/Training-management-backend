package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.UserCredentialsEntity;

import java.util.List;

public interface UserCredentialsService {
    // save user
    UserCredentialsEntity userCredentialsSave(UserCredentialsEntity user);

    // fetch all users
    List<UserCredentialsEntity> fetchUsersCredentials();

    // delete user
    void deleteUser(int userId);
}
