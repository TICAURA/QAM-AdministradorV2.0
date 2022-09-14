package com.aura.admin.adminqamm.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aura.admin.adminqamm.dto.request.CargaRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.service.CargaColombiaService;

@RestController
@RequestMapping("/cargacolombia")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class CargaColombiaController {

	@Autowired
	private CargaColombiaService cargaColombiaService;
	
	Logger logger = LoggerFactory.getLogger(CargaColombiaController.class);
	
	@PostMapping("")
    public ResponseEntity<Object> insertCargaColombia(@RequestAttribute("username") int loggedIdUser, @ModelAttribute CargaRequestDto cargaColombia){
        try {
        	//logger.info(cargaColombia.getArchivo().getName());
        	cargaColombiaService.insertCargaColombia(loggedIdUser, cargaColombia);
            return new ResponseEntity<Object>("", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al realizar la carga colombia :"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible para carga.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping(value= "",produces = "application/vnd.ms-excel")
    public ResponseEntity  descargaLayout(@RequestAttribute("username") int loggedIdUser, HttpServletResponse response){
	
		try {
			HSSFWorkbook wb = cargaColombiaService.obtenerWorkBook(loggedIdUser);

	        writeToOutputStream(response,wb);

	        response.setHeader("Content-Disposition","attachment; filename=\"Layout.xlsx\"");
			
            return ResponseEntity.ok().build();
            
        } catch (Exception e){
            logger.error("error al descargar layout:"+e.getMessage(),e);
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
