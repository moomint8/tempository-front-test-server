package org.example.testserver.aggregate.vo.projectTestcase;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseProjectTestcaseListVO {
    String message;
    private ArrayList<ResponseProjectTestcaseVO> projectTestcases;
}
