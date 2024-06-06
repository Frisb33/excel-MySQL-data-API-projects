package com.example.restservice;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelDemoService {

    private List<ExcelDemo> excelDemoData;
    @PostConstruct
    public void initData() {
        GetContentFromExcelSheets reader = new GetContentFromExcelSheets();
        try {
            excelDemoData = reader.readDataFromExcelFile("ReadExcelTask.xlsx");
            reader.insertDataToDatabase(excelDemoData); // Optionally insert to DB
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ExcelDemo> getAllExcelDemoData() {
        return excelDemoData;
    }

    public void setExcelDemoData(List<ExcelDemo> data) {
        this.excelDemoData = data;
    }
}
