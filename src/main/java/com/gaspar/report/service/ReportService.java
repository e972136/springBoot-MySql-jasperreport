package com.gaspar.report.service;


import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {
    public byte[] generarReporte(
            String nombreReporte
    ) throws JRException {
//        InputStream reportStream  = this.getClass().getResourceAsStream("/reports/"+nombreReporte+".jasper");
        InputStream reportStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("reports/" + nombreReporte + ".jasper");

        if (reportStream == null) {
            System.out.println("xxx");
            throw new RuntimeException("No se encontró el reporte en /reports/" + nombreReporte + ".jasper");
        }

        Map<String,Object> params = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                reportStream,
                params,
                new JREmptyDataSource()
        );
        System.out.println("servicio3");
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
