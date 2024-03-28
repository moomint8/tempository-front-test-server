package org.example.testserver.aggregate.vo.WBS;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestProjectWBSListVO {
    ArrayList<RequestProjectWBSVO> target;
}
