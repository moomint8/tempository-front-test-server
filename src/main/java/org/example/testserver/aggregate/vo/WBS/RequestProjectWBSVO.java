package org.example.testserver.aggregate.vo.WBS;

import lombok.*;
import org.example.testserver.aggregate.entity.User;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestProjectWBSVO {
    private int id;
    private int no;
    private String content;
    private String status;
    private String startDate;
    private String endDate;
    private int managerId;
    private int projectId;
}
