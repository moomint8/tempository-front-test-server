package org.example.testserver.aggregate.entity;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WBS {
    private int id;
    private int no;
    private String content;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private int managerId;
    private int projectId;
}
