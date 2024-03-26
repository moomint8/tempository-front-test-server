package org.example.testserver.aggregate.vo.requirement;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseRequirementVO {
    private String message;
    private int id;
    private int no;
    private String separate;
    private String content;
    private String note;
    private int projectId;
}
