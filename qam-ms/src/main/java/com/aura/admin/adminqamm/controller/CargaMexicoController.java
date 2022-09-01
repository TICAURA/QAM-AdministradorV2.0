package com.aura.admin.adminqamm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aura.admin.adminqamm.dto.request.CargaRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.service.CargaMexicoService;

@RestController
@RequestMapping("/cargamexico")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class CargaMexicoController {

	@Autowired
	private CargaMexicoService cargaMexicoService;
	
	Logger logger = LoggerFactory.getLogger(CargaMexicoController.class);
	
	@PostMapping("")
    public ResponseEntity<Object> insertCargaMexico(@RequestAttribute("username") int loggedIdUser, @ModelAttribute CargaRequestDto cargaMexico){
        try {
        	logger.info(cargaMexico.getArchivo().getName());
        	cargaMexicoService.insertCargaMexico(loggedIdUser, cargaMexico);
            return new ResponseEntity<Object>("", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al realizar la carga mexico ::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible para carga.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
