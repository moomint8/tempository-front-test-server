package org.example.testserver.aggregate.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Issue {
    private int id;
    private int no;
    private String name;
    private String status;
    private String content;
    private int managerId;
    private int writerId;
    private int projectId;
}
