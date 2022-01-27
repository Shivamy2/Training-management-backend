package com.trainingmanagementserver.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "assignment_submit")
public class AssignmentSubmitDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int assignmentId;
    private int traineeId;
    private String link;
    private String description;
    private int scoredCredit;

    public AssignmentSubmitDetails(int assignmentId, int traineeId, String link, String description) {
        this.assignmentId = assignmentId;
        this.traineeId = traineeId;
        this.link = link;
        this.description = description;
    }
}
