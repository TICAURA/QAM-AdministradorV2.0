package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.dto.ClienteDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class ClientController {

    @Autowired
    private ClientService clientService;

    Logger logger = LoggerFactory.getLogger(ClientController.class);
    
    @GetMapping("")
    public ResponseEntity<Object> getClients(@RequestAttribute("username") int loggedIdUser){
        try {
            List<ClienteDto> clients = clientService.getClients(loggedIdUser);
            return new ResponseEntity<Object>(clients, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{clientId}")
    public ResponseEntity<Object> getClient(@RequestAttribute("username") int loggedIdUser,@PathVariable("clientId") int clientId ){
        try {
            ClienteDto client = clientService.getClient(loggedIdUser,clientId);
            return new ResponseEntity<Object>(client, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("")
    public ResponseEntity<Object> updateClient(@RequestAttribute("username") int loggedIdUser, @RequestBody ClienteDto clienteDto){
        try {
            clientService.modifyClient(loggedIdUser,clienteDto);
            return new ResponseEntity<Object>("{}", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/centroCosto")
    public ResponseEntity<Object> getCentroCostoAll(@RequestAttribute("username") int loggedIdUser){
        try {
            List<ClienteDto> clients = clientService.getCentroCostoAll(loggedIdUser);
            return new ResponseEntity<Object>(clients, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al consultar getCentroCostoAll : "+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/subcontrato")
    public ResponseEntity<Object> getSubcontrato(@RequestAttribute("username") int loggedIdUser) {
        try {
            List<ClienteDto> clients = clientService.getSubcontrato(loggedIdUser);
            return new ResponseEntity<Object>(clients, HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al consultar getSubcontrato : "+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
