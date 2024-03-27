package org.example.testserver.aggregate.vo.table;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseTableDetailVO {
    private String message;

    private int tableNo;
    private String tableName;

    private int propertyNo;
    private String propertyName;
    private boolean primaryKey;
    private String foreignKey;
    private boolean nullAble;
    private String columnName;
    private String defaultValue;
    private String dataType;
    private String note;

    private int projectId;
}
