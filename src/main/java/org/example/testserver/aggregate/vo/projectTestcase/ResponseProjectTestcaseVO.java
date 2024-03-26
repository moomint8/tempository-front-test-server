package org.example.testserver.aggregate.vo.projectTestcase;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseProjectTestcaseVO {
    String message;
    private int no;
    private String separate;
    private String content;
    private String expectedValue;
    private String result;
    private String note;
    private int projectId;
}
