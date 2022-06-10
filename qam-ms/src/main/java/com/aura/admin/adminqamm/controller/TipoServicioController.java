package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.dto.request.CatalogueRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.TipoServicio;
import com.aura.admin.adminqamm.service.TipoServicioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tipo-servicio")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class TipoServicioController {
    @Autowired
    private TipoServicioService tipoServicioService;

    Logger logger = LoggerFactory.getLogger(TipoServicioController.class);
    @GetMapping("")
    public ResponseEntity<Object> getTipoServicios(@RequestAttribute("username") int loggedIdUser){
        try {
            List<TipoServicio> tipoServicios = tipoServicioService.getTipoServicios(loggedIdUser);
            return new ResponseEntity<Object>(tipoServicios, HttpStatus.OK);
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
            boolean found = tipoServicioService.existName(nombre);
            return new ResponseEntity<Object>("{\"found\":" + found + "}", HttpStatus.OK);
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{tipoServicioId}")
    public ResponseEntity<Object> getTipoServicio(@RequestAttribute("username") int loggedIdUser,@PathVariable("tipoServicioId") int tipoServicioId ){
        try {
            TipoServicio tipoServicio = tipoServicioService.getTipoServicio(loggedIdUser,tipoServicioId);
            return new ResponseEntity<Object>(tipoServicio, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("")
    public ResponseEntity<Object> insertTipoServicio(@RequestAttribute("username") int loggedIdUser, @RequestBody TipoServicio tipoServicio){
        try {
            int id = tipoServicioService.createTipoServicio(loggedIdUser,tipoServicio);
            return new ResponseEntity<Object>("{\"id\":\""+id+"\"}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("")
    public ResponseEntity<Object> updateTipoServicio(@RequestAttribute("username") int loggedIdUser, @RequestBody CatalogueRequestDto tipoServicio){
        try {
            tipoServicioService.modifyTipoServicio(loggedIdUser,tipoServicio);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{tipoServicioId}")
    public ResponseEntity<Object> deleteTipoServicio(@RequestAttribute("username") int loggedIdUser,@PathVariable("tipoServicioId") int tipoServicioId ){
        try {
            tipoServicioService.deleteTipoServicio(loggedIdUser,tipoServicioId);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
