package com.trainingmanagementserver.repository;

import com.trainingmanagementserver.entity.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity, Integer> {
    UserCredentialsEntity findByEmail(String email);
    UserCredentialsEntity findByUsername(String username);
}
