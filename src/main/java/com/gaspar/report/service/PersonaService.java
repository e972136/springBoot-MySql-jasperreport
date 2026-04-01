package com.gaspar.report.service;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PersonaService {
    private final DataSource dataSource;


    public PersonaService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public byte[] generarReporte(
            String nombreReporte
    ) throws JRException, SQLException {
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
                dataSource.getConnection()
        );
        System.out.println("servicio3");
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
