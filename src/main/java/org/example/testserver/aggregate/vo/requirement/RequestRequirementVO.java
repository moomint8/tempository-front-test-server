package org.example.testserver.aggregate.vo.requirement;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestRequirementVO {
    private int id;
    private int no;
    private String separate;
    private String name;
    private String content;
    private String note;
}
