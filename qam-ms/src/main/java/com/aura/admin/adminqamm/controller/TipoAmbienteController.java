package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.dto.request.CatalogueRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.TipoAmbiente;
import com.aura.admin.adminqamm.service.TipoAmbienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-ambiente")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class TipoAmbienteController {
    @Autowired
    private TipoAmbienteServicio tipoAmbienteServicio;

    Logger logger = LoggerFactory.getLogger(InterfaceController.class);
    @GetMapping("")
    public ResponseEntity<Object> getTipoAmbiente(@RequestAttribute("username") int loggedIdUser){
        try {
            List<TipoAmbiente> tipoAmbientes = tipoAmbienteServicio.getTipoAmbientes(loggedIdUser);
            return new ResponseEntity<Object>(tipoAmbientes, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/validate/{nombre}")
    public ResponseEntity<Object> validateNombre(@RequestAttribute("username") int loggedIdUser,@PathVariable("nombre") String nombre){
        try {
            boolean found = tipoAmbienteServicio.existName(nombre);
            return new ResponseEntity<Object>("{\"found\":" + found + "}", HttpStatus.OK);
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{tipoAmbienteId}")
    public ResponseEntity<Object> getTipoAmbiente(@RequestAttribute("username") int loggedIdUser,@PathVariable("tipoAmbienteId") int tipoAmbienteId ){
        try {
            TipoAmbiente tipoAmbiente = tipoAmbienteServicio.getTipoAmbiente(loggedIdUser,tipoAmbienteId);
            return new ResponseEntity<Object>(tipoAmbiente, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("")
    public ResponseEntity<Object> insertTipoAmbiente(@RequestAttribute("username") int loggedIdUser, @RequestBody TipoAmbiente tipoAmbiente){
        try {
            int id  = tipoAmbienteServicio.createTipoAmbiente(loggedIdUser,tipoAmbiente);
            return new ResponseEntity<Object>("{\"id\":\""+id+"\"}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("")
    public ResponseEntity<Object> updateTipoAmbiente(@RequestAttribute("username") int loggedIdUser, @RequestBody CatalogueRequestDto tipoAmbiente){
        try {
            tipoAmbienteServicio.modifyTipoAmbiente(loggedIdUser,tipoAmbiente);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{tipoAmbienteId}")
    public ResponseEntity<Object> deleteTipoAmbiente(@RequestAttribute("username") int loggedIdUser,@PathVariable("tipoAmbienteId") int tipoAmbienteId ){
        try {
            tipoAmbienteServicio.deleteTipoAmbiente(loggedIdUser,tipoAmbienteId);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
