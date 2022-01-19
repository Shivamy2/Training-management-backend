package com.trainingmanagementserver.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "files")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDB {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int assignmentId;

    private String name;
    private String type;
    @Lob
    private byte[] data;

    public FileDB(int assignmentId, String name, String type, byte[] data) {
        this.name = name;
        this.assignmentId = assignmentId;
        this.type = type;
        this.data = data;
    }
}
