package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.UserDetailsEntity;

import java.util.Optional;

public interface UserDetailService {
    UserDetailsEntity save(UserDetailsEntity userDetailsEntity);
    Optional<UserDetailsEntity> fetchUserDetails(int userId);
}
