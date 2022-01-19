package com.trainingmanagementserver.repository;

import com.trainingmanagementserver.entity.AssignmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentDetailRepository extends JpaRepository<AssignmentDetail, Integer> {
    List<AssignmentDetail> findByTrainerId(int trainer_id);
}
