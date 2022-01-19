package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.AssignmentDetail;

import java.util.List;

public interface AssignmentDetailsService {
    List<AssignmentDetail> getAssignmentDetail(int trainer_id);
    AssignmentDetail save(AssignmentDetail assignmentDetail);
}
