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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aura.admin.adminqamm.dto.SegmentoDto;
import com.aura.admin.adminqamm.dto.UpdateParametroDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.service.SegmentoService;

import lombok.AllArgsConstructor;

/**
 * @author Cesar Agustin Soto
 *
 */
@AllArgsConstructor
@RestController
@RequestMapping("/segmento")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class SegmentoController {
	
	/**
	 * 
	 */
	private final SegmentoService segmentoService;
	
	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory.getLogger(SegmentoController.class);

	@GetMapping("")
	public ResponseEntity<Object> getAll(@RequestAttribute("username") int loggedIdUser){
		try {
            List<SegmentoDto> segmentos = segmentoService.getAll(loggedIdUser);
            return new ResponseEntity<Object>(segmentos, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al consultar los segentos:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@GetMapping("/{segmentoId}")
	public ResponseEntity<Object> getPorId(@RequestAttribute("username") int loggedIdUser, @PathVariable("segmentoId") Integer segmentoId ){
		try {
			SegmentoDto segmento = segmentoService.getPorId(loggedIdUser,segmentoId);
            return new ResponseEntity<Object>(segmento, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al consultar el segmento por id:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	 @PostMapping("")
	 public ResponseEntity<Object> crear(@RequestAttribute("username") int loggedIdUser, @RequestBody SegmentoDto segmentoDto){
		 try {
			 Integer id = segmentoService.crear(loggedIdUser,segmentoDto);
	         return new ResponseEntity<Object>("{\"id\":\""+id+"\"}", HttpStatus.OK);
	     }catch (BusinessException e){
	         return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
	     }catch (Exception e){
	         logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
	         return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
	     }
	 }
	 
	 @PutMapping("")
	 public ResponseEntity<Object> updateValor(@RequestAttribute("username") int loggedIdUser,
			@RequestBody SegmentoDto segmentoDto) {
		try {
			segmentoService.actualizar(loggedIdUser, segmentoDto);
	        logger.info("Se ha actualizado el segemento");
			return new ResponseEntity<Object>("{}", HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Object>("{\"error\":\"" + e.getError() + "\"}", HttpStatus.valueOf(e.getCode()));
		} catch (Exception e) {
			logger.error("error al actualizar parametro valor" + e.getMessage(), e);
			return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
