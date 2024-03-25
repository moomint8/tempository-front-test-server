package org.example.testserver.aggregate.vo.project;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseProjectVO {
    private String message;
    private int id;
    private String name;
    private String status;
    private String content;
    private List<Integer> memberId;
}
