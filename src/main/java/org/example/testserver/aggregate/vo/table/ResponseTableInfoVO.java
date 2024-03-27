package org.example.testserver.aggregate.vo.table;

import lombok.*;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseTableInfoVO {
    private String message;
    private ArrayList<TableInfo> tableInfoList;


}
