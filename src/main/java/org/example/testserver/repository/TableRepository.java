package org.example.testserver.repository;

import org.example.testserver.aggregate.entity.Table;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TableRepository {
    private final ArrayList<Table> tableList = new ArrayList<>();

    public TableRepository() {
        // 회원 테이블 더미값
        tableList.add(new Table(1, "tbl_user", 1, "ID", true,
                "", false, "id", "", "INT", "회원 테이블의 고유 인조키", 1));

        tableList.add(new Table(1, "tbl_user", 2, "닉네임", false,
                "", false, "NICKNAME", "", "VARCHAR(255)", "회원의 닉네임", 1));

        tableList.add(new Table(1, "tbl_user", 3, "이메일", false,
                "", false, "EMAIL", "", "VARCHAR(255)", "회원의 이메일", 1));

        tableList.add(new Table(1, "tbl_user", 4, "비밀번호", false,
                "", false, "PASSWORD", "", "VARCHAR(255)", "회원의 비밀번호", 1));

        // 팔로우 테이블 더미값
        tableList.add(new Table(2, "tbl_follow", 1, "id", true,
                "", false, "ID", "", "INT", "팔로우 테이블의 고유 인조키", 1));

        tableList.add(new Table(2, "tbl_follow", 2, "팔로잉 id", false,
                "tbl_user.ID", false, "FOLLOWING_ID", "", "INT", "팔로우 된 회원의 id", 1));

        tableList.add(new Table(2, "tbl_follow", 3, "팔로워 id", false,
                "tbl_user.ID", false, "FOLLOWER_ID", "", "INT", "팔로우 한 회원의 id", 1));
    }


    public Map<Integer, String> selectAllTableInfoByProjectId(int projectId) {
        Map<Integer, String> tableInfo = new HashMap<>();

        for (Table table : tableList) {
            if (table.getProjectId() == projectId && !tableInfo.containsKey(table.getTableNo())) {
                tableInfo.put(table.getTableNo(), table.getTableName());
            }
        }

        return tableInfo;
    }

    public ArrayList<Table> selectAllTableDetailByProjectIdAndTableNo(int projectId, int tableNo) {
        ArrayList<Table> tables = new ArrayList<>();

        for (Table table : tableList) {
            if (table.getProjectId() == projectId && table.getTableNo() == tableNo) {
                tables.add(table);
            }
        }

        return tables;
    }

    public Table insertTableDetail(int projectId, int tableNo, String propertyName, boolean primaryKey,
                                   String foreignKey, boolean nullAble, String columnName, String defaultValue,
                                   String dataType, String note) {
        String tableName = null;
        int propertyNo = 1;

        for (Table table : tableList) {
            if (table.getProjectId() == projectId && table.getTableNo() == tableNo) {
                tableName = table.getTableName();
                propertyNo++;
            }
        }

        Table newTable = new Table(tableNo, tableName, propertyNo, propertyName, primaryKey, foreignKey, nullAble,
                columnName, defaultValue, dataType, note, projectId);

        tableList.add(newTable);

        return newTable;
    }

    public Table updateTableDetail(int projectId, int tableNo, int propertyNo, String propertyName, boolean primaryKey,
                                   String foreignKey, boolean nullAble, String columnName, String defaultValue,
                                   String dataType, String note) throws Exception {


        for (Table table : tableList) {
            if (table.getProjectId() == projectId && table.getTableNo() == tableNo && table.getPropertyNo() == propertyNo) {
                table.setPropertyName(propertyName);
                table.setPrimaryKey(primaryKey);
                table.setForeignKey(foreignKey);
                table.setNullAble(nullAble);
                table.setColumnName(columnName);
                table.setDefaultValue(defaultValue);
                table.setDataType(dataType);
                table.setNote(note);

                return table;
            }
        }

        throw new Exception();
    }

    public boolean deleteTableDetail(int projectId, int tableNo, int propertyNo) {

        boolean flag = false;
        Table target = null;

        for (Table table : tableList) {
            if (table.getProjectId() == projectId && table.getTableNo() == tableNo) {
                if (flag) {
                    table.setPropertyNo(table.getPropertyNo() - 1);
                } else if (table.getPropertyNo() == propertyNo) {
                    flag = true;
                    target = table;
                }
            }
        }

        tableList.remove(target);

        return flag;
    }
}
