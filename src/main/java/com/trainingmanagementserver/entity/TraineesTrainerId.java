package com.trainingmanagementserver.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class TraineesTrainerId implements Serializable {
    private int trainerId;
    private int traineeId;
}
