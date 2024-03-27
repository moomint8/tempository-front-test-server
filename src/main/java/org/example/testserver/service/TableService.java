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
}
