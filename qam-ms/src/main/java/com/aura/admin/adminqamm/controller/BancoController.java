package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Banco;
import com.aura.admin.adminqamm.service.BancoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("bancos")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class BancoController {
    @Autowired
    private BancoService bancoService;

    Logger logger = LoggerFactory.getLogger(BancoController.class);
    @GetMapping("")
    public ResponseEntity<Object> getBancos(@RequestAttribute("username") int loggedIdUser){
        try {
            List<Banco> bancos = bancoService.getBancos(loggedIdUser);
            return new ResponseEntity<Object>(bancos, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{dispersorId}")
    public ResponseEntity<Object> getBanco(@RequestAttribute("username") int loggedIdUser,@PathVariable("dispersorId") int bancoId ){
        try {
            Banco banco = bancoService.getBanco(loggedIdUser,bancoId);
            return new ResponseEntity<Object>(banco, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
