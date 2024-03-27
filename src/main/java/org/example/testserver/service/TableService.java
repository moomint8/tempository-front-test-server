package org.example.testserver.service;

import org.example.testserver.aggregate.entity.Table;
import org.example.testserver.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class TableService {
    private final TableRepository tableRepository;

    @Autowired
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public Map<Integer,String> findAllTableInfoByProjectId(int projectId) {
        return tableRepository.selectAllTableInfoByProjectId(projectId);
    }

    public ArrayList<Table> findAllTableDetailByProjectIdAndTableNo(int projectId, int tableNo) {
        return tableRepository.selectAllTableDetailByProjectIdAndTableNo(projectId, tableNo);
    }

    public Table addTableDetail(int projectId, int tableNo, String propertyName, boolean primaryKey,
                                String foreignKey, boolean nullAble, String columnName, String defaultValue,
                                String dataType, String note) {
        return tableRepository.insertTableDetail(projectId, tableNo, propertyName, primaryKey, foreignKey,
                                                 nullAble, columnName, defaultValue, dataType, note);
    }

    public Table modifyTableDetail(int projectId, int tableNo, int propertyNo, String propertyName, boolean primaryKey,
                                   String foreignKey, boolean nullAble, String columnName, String defaultValue,
                                   String dataType, String note) throws Exception {
        return tableRepository.updateTableDetail(projectId, tableNo, propertyNo, propertyName, primaryKey, foreignKey,
                nullAble, columnName, defaultValue, dataType, note);
    }
}
