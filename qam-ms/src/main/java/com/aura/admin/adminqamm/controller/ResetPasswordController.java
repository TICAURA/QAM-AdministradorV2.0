/**
 * 
 */
package com.aura.admin.adminqamm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aura.admin.adminqamm.dto.RecuperarContra;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.service.ResetPasswordService;

import lombok.AllArgsConstructor;

/**
 * @author Cesar Agustin Soto
 *
 */
@AllArgsConstructor
@RestController
@RequestMapping("/resetPassword")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class ResetPasswordController {

	private final ResetPasswordService resetPasswordService;
	
	private static final Logger logger = LoggerFactory.getLogger(ResetPasswordController.class);
	
	@PostMapping("")
	public ResponseEntity<Object> updateClient(@RequestBody RecuperarContra resetPassword) {
		try {
             String token = resetPasswordService.resetPassword(resetPassword);
            return new ResponseEntity<Object>("{\"token\":\""+token+"\"}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar el password:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
	 }
	
}
