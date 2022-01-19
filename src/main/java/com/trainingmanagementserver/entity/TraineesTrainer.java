package com.trainingmanagementserver.entity;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@Setter
@Service
@AllArgsConstructor
@NoArgsConstructor
@ToString
@IdClass(TraineesTrainerId.class)
public class TraineesTrainer {
    @Id
    private int traineeId;
    @Id
    private int trainerId;
}
