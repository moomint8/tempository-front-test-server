package org.example.testserver.aggregate.vo.projectTestcase;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestTestcaseListVO {
    ArrayList<RequestProjectTestcaseVO> Target;
}
