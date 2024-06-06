package com.example.restservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExcelDemoController {

    @Autowired
    private ExcelDemoService service;

    @GetMapping("/excel-demo")
    public List<ExcelDemo> fetchData() {
        return service.getAllExcelDemoData();
    }

    @PostMapping("/excel-demo")
    public List<ExcelDemo> echoInput(@RequestBody List<ExcelDemo> input) {
        service.setExcelDemoData(input);
        return input;
    }
}
