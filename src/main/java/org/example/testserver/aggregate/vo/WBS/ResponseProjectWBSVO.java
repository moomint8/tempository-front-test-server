package org.example.testserver.aggregate.vo.WBS;

import lombok.*;
import org.example.testserver.aggregate.entity.User;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseProjectWBSVO {
    private String message;
    private int id;
    private int no;
    private String content;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private User manager;
    private int projectId;
}
