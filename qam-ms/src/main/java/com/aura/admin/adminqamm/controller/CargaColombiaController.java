package com.aura.admin.adminqamm.controller;

import java.io.IOException;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aura.admin.adminqamm.dto.ColaboradorDto;
import com.aura.admin.adminqamm.dto.ResponseCargaColombiaDto;
import com.aura.admin.adminqamm.dto.ResponseCargaNequiDto;
import com.aura.admin.adminqamm.dto.UsuarioDto;
import com.aura.admin.adminqamm.dto.request.CargaRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.DetCarga;
import com.aura.admin.adminqamm.service.CargaColombiaService;

@RestController
@RequestMapping("/cargacolombia")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class CargaColombiaController {

	@Autowired
	private CargaColombiaService cargaColombiaService;
	
	Logger logger = LoggerFactory.getLogger(CargaColombiaController.class);
	
	@PostMapping("/cargaColaborador")
    public ResponseEntity<Object> insertCargaColombia(@RequestAttribute("username") int loggedIdUser, @ModelAttribute CargaRequestDto cargaColombia){
		
		try {
        	ResponseCargaColombiaDto responseCargaColombiaDto = cargaColombiaService.insertCargaColombia(loggedIdUser, cargaColombia);
        	
        	logger.info("/**** Procesados :: "+responseCargaColombiaDto.getProcesados());
       
            return new ResponseEntity<Object>(responseCargaColombiaDto, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al realizar la carga colombia :"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible para carga.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PostMapping("/cargaNequi")
    public ResponseEntity<Object> insertCargaNequi(@RequestAttribute("username") int loggedIdUser, @ModelAttribute CargaRequestDto cargaColombia){
        	
		logger.info("/**** Entrando a insertaNequi *****/ ");
		
		try {
			ResponseCargaNequiDto responseCargaNequiDto = cargaColombiaService.insertCargaNequi(loggedIdUser, cargaColombia);
        	
        	logger.info("/**** Procesados :: "+responseCargaNequiDto.getProcesados());
       
            return new ResponseEntity<Object>(responseCargaNequiDto, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al realizar la carga colombia :"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible para carga.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/procesados/{idCargaMasiva}")
    public ResponseEntity<Object> procesadosCargaColombia(@RequestAttribute("username") int loggedIdUser, @PathVariable("idCargaMasiva") int idCargaMasiva){
        try {
        	ResponseCargaColombiaDto responseCargaColombiaDto = new ResponseCargaColombiaDto();
        	
        	List<ColaboradorDto> procesados = cargaColombiaService.obetenerResgistrosProcesados(idCargaMasiva);
        	
        	responseCargaColombiaDto.setColaboradores(procesados);
       
            return new ResponseEntity<Object>(responseCargaColombiaDto, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al obtener la carga colombia :"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible para carga.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping("/procesados-nequi/{idCargaMasiva}")
    public ResponseEntity<Object> procesadosCargaNequi(@RequestAttribute("username") int loggedIdUser, @PathVariable("idCargaMasiva") int idCargaMasiva){
        try {
        	ResponseCargaNequiDto responseCargaNequiDto = new ResponseCargaNequiDto();
        	
        	List<UsuarioDto> procesados = cargaColombiaService.obetenerResgistrosNequiProcesados(idCargaMasiva);
        	
        	responseCargaNequiDto.setUsuarioDto(procesados);
       
            return new ResponseEntity<Object>(responseCargaNequiDto, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al obtener la carga colombia :"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible para carga.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	
	@GetMapping("")
    public ResponseEntity<Object> getUsers(@RequestAttribute("username") int loggedIdUser){
        try {
            List<DetCarga> usuarios = cargaColombiaService.getUsers(loggedIdUser);
            return new ResponseEntity<Object>(usuarios, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al ingresar a carga masiva:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
