package com.trainingmanagementserver.repository;

import com.trainingmanagementserver.entity.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileDBRepository extends JpaRepository<FileDB, Integer> {
}
