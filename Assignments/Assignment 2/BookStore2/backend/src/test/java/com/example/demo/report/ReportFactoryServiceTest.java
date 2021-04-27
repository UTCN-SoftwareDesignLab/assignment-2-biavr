package com.example.demo.report;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.demo.report.ReportType.CSV;
import static com.example.demo.report.ReportType.PDF;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportFactoryServiceTest {

    @Autowired
    private ReportFactoryService reportFactoryService;
    
    @Test
    void getReportService() {
        ReportService csvService = reportFactoryService.getReportService(CSV);
        String actualCsvResult = csvService.export();
        Assertions.assertEquals("This is a csv report",actualCsvResult);

        ReportService pdfService = reportFactoryService.getReportService(PDF);
        String actualPdfResult = csvService.export();
        Assertions.assertEquals("This is a csv report",actualPdfResult);
    }
}