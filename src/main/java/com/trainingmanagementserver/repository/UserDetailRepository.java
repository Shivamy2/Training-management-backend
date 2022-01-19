package com.trainingmanagementserver.repository;

import com.trainingmanagementserver.entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetailsEntity, Integer> {
}
