/**
 * 
 */
package com.aura.admin.adminqamm.controller;

import java.io.IOException;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aura.admin.adminqamm.service.ReporteFacturaService;

import lombok.AllArgsConstructor;

/**
 * @author Cesar Agustin Soto
 *
 */
@AllArgsConstructor
@RestController
@RequestMapping("/repfacturacion")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
		RequestMethod.PUT })
public class RepFacturacionController {

	private final ReporteFacturaService repFacturaService;
	
	private static final Logger logger = LoggerFactory.getLogger(RepFacturacionController.class);

	@GetMapping(value= "",produces = "application/vnd.ms-excel")
    public ResponseEntity  getPorCentroCosto(@RequestAttribute("username") int loggedIdUser,
    												final int mes,
    												HttpServletResponse response){
		
		try {
			HSSFWorkbook wb = repFacturaService.obtenerWorkBook(mes);

	        writeToOutputStream(response,wb);

	        response.setHeader("Content-Disposition","attachment; filename=\"ReporteFactura.xlsx\"");
			
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
