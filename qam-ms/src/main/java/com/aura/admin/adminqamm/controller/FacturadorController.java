package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Facturador;
import com.aura.admin.adminqamm.service.FacturadorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("facturador")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class FacturadorController {
    @Autowired
    private FacturadorService facturadorService;

    Logger logger = LoggerFactory.getLogger(FacturadorController.class);
    @GetMapping("")
    public ResponseEntity<Object> getFacturadores(@RequestAttribute("username") int loggedIdUser){
        try {
            List<Facturador> facturadores = facturadorService.getFacturadores(loggedIdUser);
            return new ResponseEntity<Object>(facturadores, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{facturadorId}")
    public ResponseEntity<Object> getFacturador(@RequestAttribute("username") int loggedIdUser,@PathVariable("facturadorId") int facturadorId ){
        try {
            Facturador facturador = facturadorService.getFacturador(loggedIdUser,facturadorId);
            return new ResponseEntity<Object>(facturador, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
