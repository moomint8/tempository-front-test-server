package org.example.testserver.aggregate.vo.issue;

import lombok.*;
import org.example.testserver.aggregate.entity.Issue;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseIssueListVO {
    private String message;
    private ArrayList<ResponseIssueVO> issueList;
}
