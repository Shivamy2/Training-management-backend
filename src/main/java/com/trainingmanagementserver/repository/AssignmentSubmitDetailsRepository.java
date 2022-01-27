package com.trainingmanagementserver.repository;

import com.trainingmanagementserver.entity.AssignmentSubmitDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentSubmitDetailsRepository extends JpaRepository<AssignmentSubmitDetails, Integer> {
    List<AssignmentSubmitDetails> findByAssignmentId(int id);
    List<AssignmentSubmitDetails> findByTraineeId(int id);
}
