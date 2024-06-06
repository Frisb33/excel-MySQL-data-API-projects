package com.example.restservice;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.sql.SQLException;


public class GetContentFromExcelSheets {
    private Object getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case STRING:
                        return cell.getRichStringCellValue().getString();
                    case NUMERIC:
                        return cell.getNumericCellValue();
                }
            default:
                return null;
        }
    }

    public List<ExcelDemo> readDataFromExcelFile(String excelFilePath) throws IOException {
        List<ExcelDemo> listExcelDemo = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        boolean firstRow = true;  // Flag to skip the first row

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();

            // Skip the first row
            if (firstRow) {
                firstRow = false;
                continue;
            }

            Iterator<Cell> cellIterator = nextRow.cellIterator();
            int stt = 0;
            String fields = null, path = null, description = null, objectType = null, vcbDescription = null, fisDescription = null, code = null;
            int length = 0;

            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex();

                switch (columnIndex) {
                    case 0:
                        if (getCellValue(nextCell) != null) {
                            stt = (int) (double) getCellValue(nextCell);
                        }
                        break;
                    case 1:
                        fields = (String) getCellValue(nextCell);
                        break;
                    case 2:
                        path = (String) getCellValue(nextCell);
                        break;
                    case 3:
                        description = (String) getCellValue(nextCell);
                        break;
                    case 4:
                        String codeString = (String) getCellValue(nextCell);
                        if (codeString != null && !codeString.isEmpty()) {
                            code = codeString;
                        }
                        break;
                    case 5:
                        objectType = (String) getCellValue(nextCell);
                        break;
                    case 6:
                        if (getCellValue(nextCell) != null) {
                            length = (int) (double) getCellValue(nextCell);
                        }
                        break;
                    case 7:
                        vcbDescription = (String) getCellValue(nextCell);
                        break;
                    case 8:
                        fisDescription = (String) getCellValue(nextCell);
                        break;
                }
            }
            ExcelDemo excelDemo = new ExcelDemo(
                    stt != 0 ? stt : 0,
                    fields != null ? fields : "",
                    path != null ? path : "",
                    description != null ? description : "",
                    code != null ? code : "",
                    objectType != null ? objectType : "",
                    length != 0 ? length : 0,
                    vcbDescription != null ? vcbDescription : "",
                    fisDescription != null ? fisDescription : ""
            );
            listExcelDemo.add(excelDemo);
        }

        workbook.close();
        inputStream.close();

        return listExcelDemo;
    }

    public void insertDataToDatabase(List<ExcelDemo> dataList) {
        String jdbcURL = "jdbc:mysql://localhost:3306/excel";
        String username = "root";
        String password = "Qj329032003!";

        String sql = "INSERT INTO excel_demo (STT, Fields, Path, Description, Code, ObjectType, Length, VCBDescription, FISDescription) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (ExcelDemo data : dataList) {
                    statement.setInt(1, data.getSTT());
                    statement.setString(2, data.getFields());
                    statement.setString(3, data.getPath());
                    statement.setString(4, data.getDescription());
                    statement.setString(5, data.getCode());
                    statement.setString(6, data.getObjectType());
                    statement.setInt(7, data.getLength());
                    statement.setString(8, data.getVCBDescription());
                    statement.setString(9, data.getFISDescription());
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
