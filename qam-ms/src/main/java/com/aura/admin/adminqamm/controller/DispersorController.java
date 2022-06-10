package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Dispersor;
import com.aura.admin.adminqamm.service.DispersorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dispersor")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class DispersorController {
    @Autowired
    private DispersorService dispersorService;

    Logger logger = LoggerFactory.getLogger(DispersorController.class);
    @GetMapping("")
    public ResponseEntity<Object> getDispersores(@RequestAttribute("username") int loggedIdUser){
        try {
            List<Dispersor> dispersores = dispersorService.getDispersores(loggedIdUser);
            return new ResponseEntity<Object>(dispersores, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{dispersorId}")
    public ResponseEntity<Object> getDispersor(@RequestAttribute("username") int loggedIdUser,@PathVariable("dispersorId") int dispersorId ){
        try {
            Dispersor dispersor = dispersorService.getDispersor(loggedIdUser,dispersorId);
            return new ResponseEntity<Object>(dispersor, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
