package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.UserCredentialsEntity;
import com.trainingmanagementserver.repository.UserCredentialsRepository;

import java.util.List;

public class UserCredentialsServiceImp implements UserCredentialsService{

    private UserCredentialsRepository userCredentialsRepository;

    public UserCredentialsServiceImp(final UserCredentialsRepository userCredentialsRepository){
        this.userCredentialsRepository=userCredentialsRepository;
    }

    @Override
    public UserCredentialsEntity userCredentialsSave(UserCredentialsEntity user) {
        return null;
    }

    @Override
    public List<UserCredentialsEntity> fetchUsersCredentials() {
        return null;
    }

    @Override
    public void deleteUser(int userId) {

    }
}
