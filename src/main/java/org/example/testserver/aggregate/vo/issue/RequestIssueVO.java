package org.example.testserver.aggregate.vo.issue;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestIssueVO {
    private String message;
    private int no;
    private String name;
    private String status;
    private String content;
    private int managerId;
    private int writerId;
}
