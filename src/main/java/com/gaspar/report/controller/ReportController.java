package com.gaspar.report.controller;

import com.gaspar.report.service.PersonaService;
import com.gaspar.report.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class ReportController {
    private final ReportService reportService;
    private final PersonaService personaService;

    public ReportController(ReportService reportService, PersonaService personaService) {
        this.reportService = reportService;
        this.personaService = personaService;
    }

    @GetMapping(value ="/listado", produces = "application/json")
    public ResponseEntity<Listado> obtenerListado() throws InterruptedException {
        Thread.sleep(2000);
        return ResponseEntity.ok(new Listado(List.of("1","2","3")));
    }

//    http://localhost:8080/api/report
    @GetMapping("/report")
    public ResponseEntity<byte[]> generarReporte(){
        log.info("hola");
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

//    http://localhost:8080/api/persona
    @GetMapping("/persona")
    public ResponseEntity<byte[]> generarReportePersona(){
        log.info("Persona");
        try {
            byte[] report = personaService.generarReporte("Personas");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition","inline; filename=persona.pdf");

            return new ResponseEntity<>(report,headers,HttpStatus.OK);
        } catch (JRException e) {
            System.out.println("e:"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//    http://localhost:8080/api/persona-lista
    @GetMapping("/persona-lista")
    public ResponseEntity<byte[]> generarReportePersonaLista() throws FileNotFoundException {
        log.info("Persona-lista");
        try {
            byte[] report = personaService.generarReporteListado("persona");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add("Content-Disposition","inline; filename=persona.pdf");

            return new ResponseEntity<>(report,headers,HttpStatus.OK);
        } catch (JRException e) {
            System.out.println("e:"+e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
