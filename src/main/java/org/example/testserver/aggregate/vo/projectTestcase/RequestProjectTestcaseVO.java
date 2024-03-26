package org.example.testserver.aggregate.vo.projectTestcase;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestProjectTestcaseVO {
    private int no;
    private String separate;
    private String content;
    private String expectedValue;
    private String result;
    private String note;
}
