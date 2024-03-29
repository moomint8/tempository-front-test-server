package org.example.testserver.service;

import org.example.testserver.aggregate.entity.Table;
import org.example.testserver.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

@Service
public class TableService {
    private final TableRepository tableRepository;

    @Autowired
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public Map<Integer, String> findAllTableInfoByProjectId(int projectId) {
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

    public boolean removeTableDetail(int projectId, int tableNo, int propertyNo) {
        return tableRepository.deleteTableDetail(projectId, tableNo, propertyNo);
    }

    public String createDDL(int projectId, int tableNo) {
        ArrayList<Table> tableStruct = findAllTableDetailByProjectIdAndTableNo(projectId, tableNo);

        String tableName = tableStruct.get(0).getTableName();

        String ddlHeader = "CREATE TABLE " + tableName + " ";
        String ddlColumnBody = "";
        String ddlForeignKeyBody = "";

        for (Table table : tableStruct) {
            if (table.getForeignKey() == null || table.getForeignKey().isEmpty()) {
                String column = table.getColumnName() + " " + table.getDataType();

                if (table.isPrimaryKey()) {
                    column += " AUTO_INCREMENT PRIMARY KEY";
                } else {
                    if (!table.isNullAble()) {
                        column += " NOT NULL";
                    }
                    if (table.getDefaultValue() != null && !table.getDefaultValue().isEmpty()) {
                        column += " DEFAULT '" + table.getDefaultValue() + "'";
                    }
                }

                ddlColumnBody += "  " + column + ", \n";
            } else {
                StringTokenizer st = new StringTokenizer(table.getForeignKey(), ".");

                String column = "FOREIGN KEY (" + table.getColumnName() + ") REFERENCES " + st.nextToken() + " (" +
                        st.nextToken() + "), \n";

                ddlForeignKeyBody += "  " + column;
            }
        }

        String totalDDL = ddlHeader + "\n( \n" + ddlColumnBody + ddlForeignKeyBody;

        int lastIndex = totalDDL.lastIndexOf(", \n");
        if (lastIndex != -1) {
            totalDDL = totalDDL.substring(0, lastIndex) + totalDDL.substring(lastIndex + 2);
        }

        totalDDL += ");";

        return totalDDL;
    }
}
