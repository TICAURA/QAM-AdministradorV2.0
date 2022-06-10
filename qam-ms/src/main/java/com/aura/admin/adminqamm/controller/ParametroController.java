package com.aura.admin.adminqamm.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.aura.admin.adminqamm.dto.UpdateParametroDto;
import com.aura.admin.adminqamm.dto.response.ParametroDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.service.ParametroSistemaService;

@RestController
@RequestMapping("/parametro")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
		RequestMethod.PUT })
public class ParametroController {

	@Autowired
	private ParametroSistemaService parametrosService;
	Logger logger = LoggerFactory.getLogger(ServiceController.class);

	@GetMapping("")
	public ResponseEntity<Object> getParametros(@RequestAttribute("username") int loggedIdUser) {
		try {
			List<ParametroDto> parametros = parametrosService.getParametrosSistma(loggedIdUser);
			return new ResponseEntity<Object>(parametros, HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Object>("{\"error\":\"" + e.getError() + "\"}", HttpStatus.valueOf(e.getCode()));
		} catch (Exception e) {
			logger.error("error al obtener parametros:" + e.getMessage(), e);
			return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 @GetMapping("/{idParametro}")
	    public ResponseEntity<Object> getParametro(@RequestAttribute("username") int loggedIdUser,@PathVariable("idParametro") int idParametro ){
	        try {
				ParametroDto parametro = parametrosService.obtieneParametro(loggedIdUser,idParametro);
	            return new ResponseEntity<Object>(parametro, HttpStatus.OK);
	        }catch (BusinessException e){
	            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
	        }catch (Exception e){
	            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
	            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	@PutMapping("")
	public ResponseEntity<Object> updateValor(@RequestAttribute("username") int loggedIdUser,
			@RequestBody UpdateParametroDto parametroA) {
		try {
			parametrosService.actualizaValor(loggedIdUser, parametroA);
            logger.info("Se ha actualizado el valor");
			return new ResponseEntity<Object>(parametroA, HttpStatus.OK);
		} catch (BusinessException e) {
			return new ResponseEntity<Object>("{\"error\":\"" + e.getError() + "\"}", HttpStatus.valueOf(e.getCode()));
		} catch (Exception e) {
			logger.error("error al actualizar parametro valor" + e.getMessage(), e);
			return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
