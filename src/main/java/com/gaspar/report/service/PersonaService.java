package com.gaspar.report.service;

import com.gaspar.report.entity.Personas;
import com.gaspar.report.repository.PersonasRepository;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PersonaService {
    private final DataSource dataSource;

    private final PersonasRepository personasRepository;

    public PersonaService(DataSource dataSource, PersonasRepository personasRepository) {
        this.dataSource = dataSource;
        this.personasRepository = personasRepository;
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

    public byte[] generarReporteListado(String fileName) throws FileNotFoundException, JRException {
        List<Personas> all = personasRepository.findAll().stream().map(e->new Personas(e.getId(),e.getNombre().toUpperCase(),e.getDireccion().toUpperCase(),e.getTelefono())).toList();
        File file = ResourceUtils.getFile("classpath:reports/persona.jrxml");
        JasperReport jr = JasperCompileManager.compileReport(file.getAbsolutePath());
        Map<String, Object> p = new HashMap<>();
        p.put("x","x");
        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(all);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jr, p, ds);
//        JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath + "factura.pdf");
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
