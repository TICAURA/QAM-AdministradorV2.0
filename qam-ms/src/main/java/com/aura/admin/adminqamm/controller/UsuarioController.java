package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.dto.LoginDto;
import com.aura.admin.adminqamm.dto.Relation;
import com.aura.admin.adminqamm.dto.request.UsuarioRequestDto;
import com.aura.admin.adminqamm.dto.response.UsuarioResponseDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Usuario;
import com.aura.admin.adminqamm.service.UserClientService;
import com.aura.admin.adminqamm.service.UserPermissionService;
import com.aura.admin.adminqamm.service.UserRolService;
import com.aura.admin.adminqamm.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class UsuarioController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserClientService userClientService;
    @Autowired
    private UserRolService userRolService;
    @Autowired
    private UserPermissionService userPermissionService;

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("")
    public ResponseEntity<Object> getUsers(@RequestAttribute("username") int loggedIdUser){
        try {
            List<UsuarioResponseDto> usuarios = userService.getUsers(loggedIdUser);
            return new ResponseEntity<Object>(usuarios, HttpStatus.OK);
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
            boolean found = userService.existName(nombre);
            return new ResponseEntity<Object>("{\"found\":" + found + "}", HttpStatus.OK);
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{userid}")
    public ResponseEntity<Object> getUser(@RequestAttribute("username") int loggedIdUser,@PathVariable("userid") int requestedIdUser ){
        try {
            UsuarioResponseDto usuario = userService.getUser(loggedIdUser,requestedIdUser);
            return new ResponseEntity<Object>(usuario, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> insertUser(@RequestAttribute("username") int loggedIdUser, @RequestBody Usuario usuario){
        try {
            int id = userService.createUser(loggedIdUser,usuario);
            return new ResponseEntity<Object>("{\"id\":\""+id+"\"}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login( @RequestBody LoginDto loginDto){
        try {
             String token = userService.login(loginDto);
            return new ResponseEntity<Object>("{\"token\":\""+token+"\"}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Object> updateUser(@RequestAttribute("username") int loggedIdUser, @RequestBody UsuarioRequestDto usuario){
        try {
            userService.modificarUser(loggedIdUser,usuario);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userid}")
    public ResponseEntity<Object> deleteUser(@RequestAttribute("username") int loggedIdUser,@PathVariable("userid") int requestedIdUser ){
        try {
            userService.borrarUser(loggedIdUser,requestedIdUser);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/client/{userid}")
    public ResponseEntity<Object> viewUserClient(@RequestAttribute("username") int loggedIdUser,@PathVariable("userid") int requestedIdUser){
        try {
            List<Relation> usuariosclientes = userClientService.viewUserClientLinks(loggedIdUser,requestedIdUser);
            return new ResponseEntity<Object>(usuariosclientes, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/client")
    public ResponseEntity<Object> linkUserClient(@RequestAttribute("username") int loggedIdUser, @RequestBody Relation relation){
        try {
            userClientService.createUserClientLink(loggedIdUser,relation);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/client")
    public ResponseEntity<Object> breakUserClient(@RequestAttribute("username") int loggedIdUser,@RequestBody Relation relation ){
        try {
            userClientService.deleteUserClientLink(loggedIdUser,relation);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rol/{userid}")
    public ResponseEntity<Object> viewUserRol(@RequestAttribute("username") int loggedIdUser,@PathVariable("userid") int requestedIdUser){
        try {
            List<Relation>  roles = userRolService.viewUserRolesLink(loggedIdUser,requestedIdUser);
            return new ResponseEntity<Object>(roles, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/rol")
    public ResponseEntity<Object> linkUserRol(@RequestAttribute("username") int loggedIdUser, @RequestBody Relation relation){
        try {
            userRolService.createUserRolesLink(loggedIdUser,relation);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/rol")
    public ResponseEntity<Object> breakUserRol(@RequestAttribute("username") int loggedIdUser,@RequestBody Relation relation ){
        try {
            userRolService.deleteUserRolesLink(loggedIdUser,relation);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/permiso/{userid}")
    public ResponseEntity<Object> viewUserPermission(@RequestAttribute("username") int loggedIdUser,@PathVariable("userid") int requestedIdUser){
        try {
            List<Relation>  permisos = userPermissionService.viewUserPermissionLink(loggedIdUser,requestedIdUser);
            return new ResponseEntity<Object>(permisos, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/permiso")
    public ResponseEntity<Object> linkUserPermission(@RequestAttribute("username") int loggedIdUser, @RequestBody Relation relation){
        try {
            userPermissionService.createUserPermissionLink(loggedIdUser,relation);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/permiso")
    public ResponseEntity<Object> breakUserPermission(@RequestAttribute("username") int loggedIdUser,@RequestBody Relation relation ){
        try {
            userPermissionService.deleteUserPermissionLink(loggedIdUser,relation);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}
