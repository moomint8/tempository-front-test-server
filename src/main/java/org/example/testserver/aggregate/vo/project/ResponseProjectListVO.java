package org.example.testserver.aggregate.vo.project;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseProjectListVO {
    private String message;
    private ArrayList<ResponseProjectVO> projects;
}
