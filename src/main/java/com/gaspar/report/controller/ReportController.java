package com.gaspar.report.controller;

import com.gaspar.report.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report")
    public ResponseEntity<byte[]> generarReporte(){
        System.out.println("hola");
        try {
            byte[] report = reportService.generarReporte("patito");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition","inline; filename=patito.pdf");

            return new ResponseEntity<>(report,headers,HttpStatus.OK);
        } catch (JRException e) {
            System.out.println("e:"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
