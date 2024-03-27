package org.example.testserver.aggregate.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Table {

    // 테이블에 대한 값
    private int tableNo;
    private String tableName;

    // 실제 테이블을 구성하는 값
    private int propertyNo;
    private String propertyName;
    private boolean primaryKey;
    private String foreignKey;
    private boolean nullAble;
    private String columnName;
    private String defaultValue;
    private String dataType;
    private String note;

    // 어느 프로젝트에 포함되는지
    private int projectId;
}
