package org.example.testserver.aggregate.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Project {
    private int id;
    private String name;
    private Status status;
    private String content;
    private List<Integer> memberIds;

    public enum Status {
        COMPLETED,
        IN_PROGRESS
    }
}
