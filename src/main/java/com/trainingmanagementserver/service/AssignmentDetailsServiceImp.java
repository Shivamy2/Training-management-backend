package com.trainingmanagementserver.service;

import com.trainingmanagementserver.entity.AssignmentDetail;
import com.trainingmanagementserver.repository.AssignmentDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentDetailsServiceImp implements AssignmentDetailsService{
    private final AssignmentDetailRepository assignmentDetailRepository;

    @Override
    public List<AssignmentDetail> getAssignmentDetail(int trainer_id) {
        return assignmentDetailRepository.findByTrainerId(trainer_id);
    }

    @Override
    public AssignmentDetail save(AssignmentDetail assignmentDetail) {
        return assignmentDetailRepository.save(assignmentDetail);
    }
}
