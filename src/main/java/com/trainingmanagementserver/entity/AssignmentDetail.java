package com.trainingmanagementserver.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "assignment")
@Getter
@Setter
@NoArgsConstructor
public class AssignmentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int trainerId;
    private String title;
    private String description;
    private int totalCredit;

    private int scoredCredit;

    private String dueDate;

    private boolean isAvailable;

    public AssignmentDetail(int trainerId, String title, String description, int totalCredit, String dueDate, boolean isAvailable) {
        this.title = title;
        this.trainerId = trainerId;
        this.description = description;
        this.totalCredit = totalCredit;
        this.dueDate = dueDate;
        this.isAvailable = isAvailable;
    }
}
