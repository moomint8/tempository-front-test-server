package org.example.testserver.aggregate.vo.project;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestProjectVO {
    private String message;
    private String name;
    private String status;
    private String content;
    private List<Integer> memberId;
}
