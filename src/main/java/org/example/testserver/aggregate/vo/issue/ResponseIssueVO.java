package org.example.testserver.aggregate.vo.issue;

import lombok.*;
import org.example.testserver.aggregate.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseIssueVO {
    private String message;
    private int id;
    private int no;
    private String name;
    private String status;
    private String content;
    private User manager;
    private User writer;
    private int projectId;
}
