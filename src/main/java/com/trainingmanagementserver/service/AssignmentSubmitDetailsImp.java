package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.AssignmentSubmitDetails;
import com.trainingmanagementserver.repository.AssignmentSubmitDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AssignmentSubmitDetailsImp implements AssignmentSubmitDetailsService{
    private final AssignmentSubmitDetailsRepository assignmentSubmitDetailsRepository;
    @Override
    public AssignmentSubmitDetails save(AssignmentSubmitDetails assignmentSubmitDetails) {
        return assignmentSubmitDetailsRepository.save(assignmentSubmitDetails);
    }

    @Override
    public List<AssignmentSubmitDetails> getSubmitAssignments(int id) {
        return assignmentSubmitDetailsRepository.findByTraineeId(id);
    }
}
