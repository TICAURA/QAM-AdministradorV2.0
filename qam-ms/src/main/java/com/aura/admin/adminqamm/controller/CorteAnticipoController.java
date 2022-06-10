/**
 * 
 */
package com.aura.admin.adminqamm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aura.admin.adminqamm.dto.CorteAnticipoDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.service.CorteAnticipoService;

import lombok.AllArgsConstructor;

/**
 * @author Cesar Agustin Soto
 *
 */
@AllArgsConstructor
@RestController
@RequestMapping("/corte")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class CorteAnticipoController {
	
	private final CorteAnticipoService corteAnticipoService;
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
	
	@GetMapping("")
    public ResponseEntity<Object> getPorCentroCosto(@RequestAttribute("username") int loggedIdUser,
    												final Integer centroCosto,
    												final String periodicidad){
		try {
			List<CorteAnticipoDto> corteAnticipoList = corteAnticipoService.getPorCentroCosto(centroCosto, periodicidad, loggedIdUser);
			
            return new ResponseEntity<Object>(corteAnticipoList, HttpStatus.OK);
        } catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        } catch (Exception e){
            logger.error("error al consultar corte anticipo:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@GetMapping("/{corteAntId}")
	public ResponseEntity<Object> getPorId(@RequestAttribute("username") int loggedIdUser, @PathVariable("corteAntId") Integer corteAntId ){
		try {
            CorteAnticipoDto corteAnticipo = corteAnticipoService.getPorId(loggedIdUser,corteAntId);
            return new ResponseEntity<Object>(corteAnticipo, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al consultar corte anticipo por id:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@PutMapping("")
	public ResponseEntity<Object> update(@RequestAttribute("username") int loggedIdUser, @RequestBody CorteAnticipoDto corteAnticipoDto){
		try {
			corteAnticipoService.actualizarCorteAnticipo(loggedIdUser,corteAnticipoDto);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al actualizar el corte anticipo:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

}
