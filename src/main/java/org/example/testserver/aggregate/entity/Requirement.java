package org.example.testserver.aggregate.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Requirement {
    private int id;
    private int no;
    private String separate;
    private String name;
    private String content;
    private String note;
    private int projectId;
}
