package org.example.testserver.aggregate.vo.requirement;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestRequirementListVO {
    ArrayList<RequestRequirementVO> target;
}
