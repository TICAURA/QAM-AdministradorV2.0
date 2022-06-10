/**
 * 
 */
package com.aura.admin.adminqamm.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aura.admin.adminqamm.service.ReporteIncidenciaService;

/**
 * @author Cesar Agustin Soto
 *
 */

@RestController
@RequestMapping("/repincidencia")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
		RequestMethod.PUT })
public class RepIncidenciasController {
	
	@Autowired
	private ReporteIncidenciaService repIncidenciaService;

	private static final Logger logger = LoggerFactory.getLogger(RepIncidenciasController.class);
	
	@GetMapping(value= "",produces = "application/vnd.ms-excel")
    public ResponseEntity  getPorCentroCosto(@RequestAttribute("username") int loggedIdUser,
    												final String centroCosto,
    												final String cliente,
    												final String clienteSubcontrato,
    												final String fechaInicio,
    												final String fechaFin, HttpServletResponse response){
		
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
		Date fchTransferenciaInicio = null;
		Date fchTransferenciaFin = null;
		try {
			if (StringUtils.isNotBlank(fechaInicio)) {
				fchTransferenciaInicio = dateFormatter.parse(fechaInicio);
			}
			if (StringUtils.isNotBlank(fechaFin)) {
				fchTransferenciaFin = dateFormatter.parse(fechaFin);
			}
		
			HSSFWorkbook wb = repIncidenciaService.mostrarVRepIncidencias(centroCosto, cliente, clienteSubcontrato, fchTransferenciaFin, fchTransferenciaInicio);

	        writeToOutputStream(response,wb);

	        response.setHeader("Content-Disposition","attachment; filename=\"reporte.xlsx\"");


			
            return ResponseEntity.ok().build();
            
        } catch (Exception e){
            logger.error("error al construir reporte incidencias:"+e.getMessage(),e);
            return ResponseEntity.badRequest().build();
        }
	}
	
	public void writeToOutputStream(HttpServletResponse response,HSSFWorkbook wb){

	    ServletOutputStream out ;
	    try {
	        out = response.getOutputStream();
	        wb.write(out);
	        wb.close();
	        out.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}
	
}
