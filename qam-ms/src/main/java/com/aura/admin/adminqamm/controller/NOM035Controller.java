package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.dto.NOM035CuestionarioDto;
import com.aura.admin.adminqamm.dto.NOM035ListDto;
import com.aura.admin.adminqamm.dto.NOM035ReportDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.service.Nomina035Service;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("nom035")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class NOM035Controller {
    @Autowired
    private Nomina035Service nomina035Service;

    Logger logger = LoggerFactory.getLogger(BancoController.class);
    @GetMapping("")
    public ResponseEntity<Object> getListaNomina(@RequestAttribute("username") int loggedIdUser){
        try {
            List<NOM035ListDto> lista = nomina035Service.lista(loggedIdUser);
            return new ResponseEntity<Object>(lista, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cargar lista de nomina 035.:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cuestionarios")
    public ResponseEntity<Object> getCuestionarios(@RequestAttribute("username") int loggedIdUser){
        try {
            List<NOM035CuestionarioDto> cuestionarioDtos = nomina035Service.cuestionarios(loggedIdUser);
            return new ResponseEntity<Object>(cuestionarioDtos, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cargar lista de nomina 035.:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reporte/{idColaborador}/{idCuestionario}")
    public ResponseEntity<Object> getCuestionarios(@RequestAttribute("username") int loggedIdUser,
                                                   @PathVariable("idColaborador") int idColaborador,
                                                   @PathVariable("idCuestionario") int idCuestionario){
        try {
            NOM035ReportDto reporte = nomina035Service.getReporte(loggedIdUser,idColaborador,idCuestionario);
            return new ResponseEntity<Object>(reporte, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cargar lista de nomina 035.:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value= "/reportes/respuestas/{idColaborador}/{idCuestionario}",produces = "application/vnd.ms-excel")
    public ResponseEntity  getReporteRespuestas(@RequestAttribute("username") int loggedIdUser,
                                             @PathVariable("idColaborador") int idColaborador,
                                             @PathVariable("idCuestionario") int idCuestionario,
                                             HttpServletResponse response){

        try {
            XSSFWorkbook wb = nomina035Service.generarReporteRepuestas(loggedIdUser,idColaborador,idCuestionario);

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\"ReporteRespuestas.xlsx\"");
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.close();
            wb.close();


            response.flushBuffer();
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cargar lista de nomina 035.:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @GetMapping(value= "/reportes/general/{idCliente}",produces = "application/vnd.ms-excel")
    public ResponseEntity  getReporteGeneral(@RequestAttribute("username") int loggedIdUser,
                                             @PathVariable("idCliente") int idCliente,
                                             HttpServletResponse response){

        try {
            XSSFWorkbook wb = nomina035Service.generarReporteGeneral(loggedIdUser,idCliente);

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\"ReporteGeneral.xlsx\"");
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.close();
            wb.close();


            response.flushBuffer();
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cargar lista de nomina 035.:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }



    @GetMapping(value= "/reportes/individual/{idColaborador}/{idCuestionario}",produces = "application/vnd.ms-excel")
    public ResponseEntity  getReporteIndividual(@RequestAttribute("username") int loggedIdUser,
                                             @PathVariable("idColaborador") int idColaborador,
                                             @PathVariable("idCuestionario") int idCuestionario,
                                             HttpServletResponse response){

        try {
            XSSFWorkbook wb = nomina035Service.generarReporteIndividual(loggedIdUser,idColaborador,idCuestionario);

            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\"ReporteIndividual.xlsx\"");
            OutputStream out = response.getOutputStream();
            wb.write(out);
            out.close();
            wb.close();


            response.flushBuffer();
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cargar lista de nomina 035.:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }



}
