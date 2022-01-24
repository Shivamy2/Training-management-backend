package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.AssignmentSubmitDetails;

import java.util.List;

public interface AssignmentSubmitDetailsService {
    AssignmentSubmitDetails save(AssignmentSubmitDetails assignmentSubmitDetails);
    List<AssignmentSubmitDetails> getSubmitAssignments(int id);
}
