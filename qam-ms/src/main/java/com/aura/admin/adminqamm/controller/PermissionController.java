package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Permiso;
import com.aura.admin.adminqamm.model.Rol;
import com.aura.admin.adminqamm.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("permission")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    Logger logger = LoggerFactory.getLogger(PermissionController.class);
    @GetMapping("")
    public ResponseEntity<Object> getPermittions(@RequestAttribute("username") int loggedIdUser){
        try {
            List<Permiso> permisos = permissionService.getPermission(loggedIdUser);
            return new ResponseEntity<Object>(permisos, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{permisoId}")
    public ResponseEntity<Object> getPermition(@RequestAttribute("username") int loggedIdUser,@PathVariable("permisoId") int permisoId ){
        try {
            Permiso permiso = permissionService.getPermission(loggedIdUser,permisoId);
            return new ResponseEntity<Object>(permiso, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
