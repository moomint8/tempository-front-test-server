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
                "", false, "ID", "", "INT", "회원 테이블의 고유 인조키", 1));

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


        // 상품 테이블 더미값
        tableList.add(new Table(3, "tbl_product", 1, "id", true,
                "", false, "ID", "", "INT", "상품 테이블의 고유 인조키", 1));

        tableList.add(new Table(3, "tbl_product", 2, "상품명", false,
                "", false, "NAME", "", "VARCHAR(255)", "상품의 이름", 1));

        tableList.add(new Table(3, "tbl_product", 3, "가격", false,
                "", false, "PRICE", "", "INT", "상품의 가격", 1));

        tableList.add(new Table(3, "tbl_product", 4, "설명", false,
                "", false, "DESCRIPTION", "", "TEXT", "상품에 대한 설명", 1));

        tableList.add(new Table(3, "tbl_product", 5, "이미지", false,
                "", false, "IMAGE", "", "VARCHAR(255)", "상품 이미지 파일명", 1));

        // 주문 테이블 더미값
        tableList.add(new Table(4, "tbl_order", 1, "id", true,
                "", false, "ID", "", "INT", "주문 테이블의 고유 인조키", 1));

        tableList.add(new Table(4, "tbl_order", 2, "회원ID", false,
                "tbl_user.ID", false, "USER_ID", "", "INT", "주문한 회원의 ID", 1));

        tableList.add(new Table(4, "tbl_order", 3, "주문날짜", false,
                "", false, "ORDER_DATE", "", "DATETIME", "주문한 날짜", 1));

        tableList.add(new Table(4, "tbl_order", 4, "총액", false,
                "", false, "TOTAL_AMOUNT", "", "INT", "주문 총액", 1));

        tableList.add(new Table(4, "tbl_order", 5, "배송상태", false,
                "", false, "SHIPPING_STATUS", "", "VARCHAR(50)", "주문 배송 상태", 1));

        // 리뷰 테이블 더미값
        tableList.add(new Table(5, "tbl_review", 1, "id", true,
                "", false, "ID", "", "INT", "리뷰 테이블의 고유 인조키", 1));

        tableList.add(new Table(5, "tbl_review", 2, "회원ID", false,
                "tbl_user.ID", false, "USER_ID", "", "INT", "리뷰 작성한 회원의 ID", 1));

        tableList.add(new Table(5, "tbl_review", 3, "상품ID", false,
                "tbl_product.ID", false, "PRODUCT_ID", "", "INT", "리뷰 대상 상품의 ID", 1));

        tableList.add(new Table(5, "tbl_review", 4, "내용", false,
                "", false, "CONTENT", "", "TEXT", "리뷰 내용", 1));

        tableList.add(new Table(5, "tbl_review", 5, "평점", false,
                "", false, "RATING", "", "INT", "리뷰 평점 (1-5)", 1));
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
