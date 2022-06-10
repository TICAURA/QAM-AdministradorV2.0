package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.dto.ArchivoLogo;
import com.aura.admin.adminqamm.dto.request.InterfazRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Interfaz;

import com.aura.admin.adminqamm.service.InterfaceService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/interfaz")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class InterfaceController {

    @Autowired
    private InterfaceService interfaceService;

    Logger logger = LoggerFactory.getLogger(InterfaceController.class);
    @GetMapping("")
    public ResponseEntity<Object> getInterfaces(@RequestAttribute("username") int loggedIdUser){
        try {
            List<Interfaz> interfaces = interfaceService.getInterfaces(loggedIdUser);
            return new ResponseEntity<Object>(interfaces, HttpStatus.OK);
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
            boolean found = interfaceService.existName(nombre);
            return new ResponseEntity<Object>("{\"found\":" + found + "}", HttpStatus.OK);
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{interfazId}")
    public ResponseEntity<Object> getInterfaz(@RequestAttribute("username") int loggedIdUser,@PathVariable("interfazId") int interfaceId ){
        try {
            Interfaz interfaz = interfaceService.getInterfaz(loggedIdUser,interfaceId);
            return new ResponseEntity<Object>(interfaz, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("")
    public ResponseEntity<Object> insertInterfaz(
                                                 @RequestAttribute("username") int loggedIdUser,
                                                 @ModelAttribute InterfazRequestDto interfaz){
        try {
            int id = interfaceService.createInterface(loggedIdUser,interfaz);
            return new ResponseEntity<Object>("{\"id\":\""+id+"\"}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("")
    public ResponseEntity<Object> updateInterfaz(@RequestAttribute("username") int loggedIdUser, @ModelAttribute InterfazRequestDto interfaz){
        try {
            interfaceService.modifyInterface(loggedIdUser,interfaz);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{interfazId}")
    public ResponseEntity<Object> deleteInterfaz(@RequestAttribute("username") int loggedIdUser,@PathVariable("interfazId") int interfaceId ){
        try {
            interfaceService.deleteInterface(loggedIdUser,interfaceId);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/archivo/{archivoId}",produces = MediaType.IMAGE_PNG_VALUE)
    @CrossOrigin(origins = "*")
    public @ResponseBody ResponseEntity<Object> descargarArchivo(@PathVariable("archivoId") int archivoId){
        try{

            ArchivoLogo logo = interfaceService.getArchivo(archivoId);
            byte[] decodedBytes = Base64.decodeBase64(logo.getContent());
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + logo.getName())
                    .body(decodedBytes);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
