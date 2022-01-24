package com.trainingmanagementserver.repository;

import com.trainingmanagementserver.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileDBRepository extends JpaRepository<FileDB, Integer> {
    FileDB findByAssignmentId(int id);
}
