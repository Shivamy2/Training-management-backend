package com.trainingmanagementserver.repository;

import com.trainingmanagementserver.entity.TraineesTrainer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TraineesTrainerRepository extends JpaRepository<TraineesTrainer, Integer> {
    List<TraineesTrainer> findByTrainerId(int id);
    TraineesTrainer findByTraineeId(int id);
}
