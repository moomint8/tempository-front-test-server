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
}
