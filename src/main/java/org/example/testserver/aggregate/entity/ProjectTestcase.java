package org.example.testserver.aggregate.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProjectTestcase {
    private int no;
    private String separate;
    private String content;
    private String expectedValue;
    private String result;
    private String note;
    private int projectId;
}
