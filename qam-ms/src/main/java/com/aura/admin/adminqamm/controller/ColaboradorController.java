package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.dto.ColaboradorDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.service.ColaboradorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("colaborador")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class ColaboradorController {
    @Autowired
    private ColaboradorService colaboradorService;

    Logger logger = LoggerFactory.getLogger(ServiceController.class);
    @GetMapping("")
    public ResponseEntity<Object> getColaboradores(@RequestAttribute("username") int loggedIdUser){
        try {
            List<ColaboradorDto> servicios = colaboradorService.getColaboradores(loggedIdUser);
            return new ResponseEntity<Object>(servicios, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{colaboradorId}")
    public ResponseEntity<Object> getServicio(@RequestAttribute("username") int loggedIdUser,@PathVariable("colaboradorId") int colaboradorId ){
        try {
            ColaboradorDto servicio = colaboradorService.getColaborador(loggedIdUser,colaboradorId);
            return new ResponseEntity<Object>(servicio, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("")
    public ResponseEntity<Object> insertServicio(@RequestAttribute("username") int loggedIdUser, @RequestBody ColaboradorDto colaboradorDto){
        try {
            int id = colaboradorService.postColaborador(loggedIdUser,colaboradorDto);
            return new ResponseEntity<Object>("{\"id\":\""+id+"\"}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("")
    public ResponseEntity<Object> updateServicio(@RequestAttribute("username") int loggedIdUser, @RequestBody ColaboradorDto colaboradorDto){
        try {
            colaboradorService.putColaborador(loggedIdUser,colaboradorDto);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{colaboradorId}")
    public ResponseEntity<Object> deleteServicio(@RequestAttribute("username") int loggedIdUser,@PathVariable("colaboradorId") int colaboradorId ){
        try {
            colaboradorService.deleteColaborador(loggedIdUser,colaboradorId);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
