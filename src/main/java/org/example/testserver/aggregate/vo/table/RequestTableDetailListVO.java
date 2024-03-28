package org.example.testserver.aggregate.vo.table;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RequestTableDetailListVO {
    ArrayList<RequestTableDetailVO> target;
}
