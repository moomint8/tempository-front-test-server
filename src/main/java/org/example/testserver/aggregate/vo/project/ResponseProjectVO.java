package org.example.testserver.aggregate.vo.project;

import lombok.*;
import org.example.testserver.aggregate.entity.Project;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseProjectVO {
    private int id;
    private String name;
    private String status;
    private String content;
    private List<Integer> memberId;
}
