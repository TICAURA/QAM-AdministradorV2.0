package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.dto.Relation;
import com.aura.admin.adminqamm.dto.request.CatalogueRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Rol;
import com.aura.admin.adminqamm.model.RolPermiso;
import com.aura.admin.adminqamm.model.Usuario;
import com.aura.admin.adminqamm.model.UsuarioCliente;
import com.aura.admin.adminqamm.service.RolPermissionService;
import com.aura.admin.adminqamm.service.RolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rol")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class RolController {
    @Autowired
    private RolService rolService;
    @Autowired
    private RolPermissionService rolPermissionService;

    Logger logger = LoggerFactory.getLogger(RolController.class);
    @GetMapping("")
    public ResponseEntity<Object> getRols(@RequestAttribute("username") int loggedIdUser){
        try {
            List<Rol> roles = rolService.getRoles(loggedIdUser);
            return new ResponseEntity<Object>(roles, HttpStatus.OK);
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
            boolean found = rolService.existName(nombre);
            return new ResponseEntity<Object>("{\"found\":" + found + "}", HttpStatus.OK);
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{rolId}")
    public ResponseEntity<Object> getRol(@RequestAttribute("username") int loggedIdUser,@PathVariable("rolId") int rollId ){
        try {
            Rol rol = rolService.getRol(loggedIdUser,rollId);
            return new ResponseEntity<Object>(rol, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("")
    public ResponseEntity<Object> insertRol(@RequestAttribute("username") int loggedIdUser, @RequestBody Rol rol){
        try {
            int id = rolService.createRol(loggedIdUser,rol);
            return new ResponseEntity<Object>("{\"id\":\""+id+"\"}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("")
    public ResponseEntity<Object> updateRol(@RequestAttribute("username") int loggedIdUser, @RequestBody CatalogueRequestDto rol){
        try {
            rolService.modifyRol(loggedIdUser,rol);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{rolId}")
    public ResponseEntity<Object> deleteRol(@RequestAttribute("username") int loggedIdUser,@PathVariable("rolId") int rollId ){
        try {
            rolService.deleteRol(loggedIdUser,rollId);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/permission/{rolId}")
    public ResponseEntity<Object> viewRolPermission(@RequestAttribute("username") int loggedIdUser,@PathVariable("rolId") int rollId){
        try {
            List<Relation>  usuariospermisos = rolPermissionService.viewRolPermissionLink(loggedIdUser,rollId);
            return new ResponseEntity<Object>(usuariospermisos, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/permission")
    public ResponseEntity<Object> linkRolPermission(@RequestAttribute("username") int loggedIdUser, @RequestBody Relation relation){
        try {
            rolPermissionService.createRolPermissionLink(loggedIdUser,relation);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/permission")
    public ResponseEntity<Object> breakRolPermission(@RequestAttribute("username") int loggedIdUser, @RequestBody Relation relation ){
        try {
            rolPermissionService.deleteRolPermissionLink(loggedIdUser,relation);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
