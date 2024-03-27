package org.example.testserver.controller;

import org.example.testserver.aggregate.vo.table.ResponseTableInfoVO;
import org.example.testserver.aggregate.vo.table.TableInfo;
import org.example.testserver.service.SessionService;
import org.example.testserver.service.TableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/table")
public class TableController {

    private final TableService tableService;
    private final SessionService sessionService;

    public TableController(TableService tableService, SessionService sessionService) {
        this.tableService = tableService;
        this.sessionService = sessionService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseTableInfoVO> findTableInfoByProjectId(@PathVariable("projectId") String projectId) {

        ResponseTableInfoVO response = new ResponseTableInfoVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Map<Integer, String> tableInfoMap = tableService.findAllTableInfoByProjectId(Integer.parseInt(projectId));
        ArrayList<TableInfo> tableInfoList = new ArrayList<>();

        for (int i = 1; i <= tableInfoMap.size(); i++) {
            tableInfoList.add(new TableInfo(i, tableInfoMap.get(i)));
        }

        response.setMessage("조회 성공");
        response.setTableInfoList(tableInfoList);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
