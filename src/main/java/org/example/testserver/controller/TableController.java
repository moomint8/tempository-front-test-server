package org.example.testserver.controller;

import org.example.testserver.aggregate.entity.Table;
import org.example.testserver.aggregate.vo.table.*;
import org.example.testserver.service.SessionService;
import org.example.testserver.service.TableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("{projectId}/{tableNo}")
    public ResponseEntity<ResponseTableDetailListVO> findTableDetailByProjectIdAndTableNo(
            @PathVariable("projectId") String projectId, @PathVariable("tableNo") String tableNo) {

        ResponseTableDetailListVO response = new ResponseTableDetailListVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ArrayList<Table> tableList = tableService.findAllTableDetailByProjectIdAndTableNo(
                Integer.parseInt(projectId), Integer.parseInt(tableNo));
        ArrayList<ResponseTableDetailVO> tableDetailVOS = new ArrayList<>();

        for (Table table : tableList) {
            tableDetailVOS.add(changeTableToResponseTableDetailVO(table));
        }

        response.setMessage("조회 성공");
        response.setTableDetailVOList(tableDetailVOS);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/add/{projectId}/{tableNo}")
    public ResponseEntity<ResponseTableDetailVO> addTableDetail(@PathVariable("projectId") String projectId,
                                                                @PathVariable("tableNo") String tableNo,
                                                                @RequestBody RequestTableDetailVO request) {
        ResponseTableDetailVO response = new ResponseTableDetailVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response = changeTableToResponseTableDetailVO(tableService.addTableDetail(Integer.parseInt(projectId),
                Integer.parseInt(tableNo), request.getPropertyName(), request.isPrimaryKey(), request.getForeignKey(),
                request.isNullAble(), request.getColumnName(), request.getDefaultValue(), request.getDataType(), request.getNote()));

        response.setMessage("추가 성공");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/modify/{projectId}/{tableNo}")
    public ResponseEntity<ResponseTableDetailVO> modifyTableDetail(@PathVariable("projectId") String projectId,
                                                                   @PathVariable("tableNo") String tableNo,
                                                                   @RequestBody RequestTableDetailVO request) {
        ResponseTableDetailVO response = new ResponseTableDetailVO();

        if (sessionService.whoAmI() == null) {
            response.setMessage("로그인 이후 이용해주세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        try {
            response = changeTableToResponseTableDetailVO(tableService.modifyTableDetail(Integer.parseInt(projectId),
                    Integer.parseInt(tableNo), request.getPropertyNo(), request.getPropertyName(), request.isPrimaryKey(), request.getForeignKey(),
                    request.isNullAble(), request.getColumnName(), request.getDefaultValue(), request.getDataType(), request.getNote()));

            response.setMessage("수정 성공");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception e) {
            response.setMessage("수정 실패");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private ResponseTableDetailVO changeTableToResponseTableDetailVO(Table table) {
        ResponseTableDetailVO detailVO = new ResponseTableDetailVO();
        detailVO.setTableNo(table.getTableNo());
        detailVO.setTableName(table.getTableName());
        detailVO.setPropertyNo(table.getPropertyNo());
        detailVO.setPropertyName(table.getPropertyName());
        detailVO.setPrimaryKey(table.isPrimaryKey());
        detailVO.setForeignKey(table.getForeignKey());
        detailVO.setNullAble(table.isNullAble());
        detailVO.setColumnName(table.getColumnName());
        detailVO.setDefaultValue(table.getDefaultValue());
        detailVO.setDataType(table.getDataType());
        detailVO.setNote(table.getNote());
        detailVO.setProjectId(table.getProjectId());

        return detailVO;
    }
}
