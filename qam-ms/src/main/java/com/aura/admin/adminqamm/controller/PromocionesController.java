package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.dto.Promocion;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.service.PromocionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/promocion")
//@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class PromocionesController {

    @Autowired
    private PromocionService promocionService;

    Logger logger = LoggerFactory.getLogger(PromocionesController.class);

  //  @GetMapping("")
    public ResponseEntity<Object> getNotifications(@RequestAttribute("username") int loggedUser){
        try{
            List<Promocion> promociones = promocionService.getPromociones(loggedUser);
            return new ResponseEntity<>(promociones, HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@GetMapping("/{idPromocion}")
    public ResponseEntity<Object> getNotification(@RequestAttribute("username") int loggedUser,@PathVariable("idPromocion") int idPromocion){
        try{
            Promocion promo = promocionService.getPromocion(loggedUser,idPromocion);
            return new ResponseEntity<>(promo, HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PostMapping("")
    public ResponseEntity<Object> postNotification(@RequestAttribute("username") int loggedUser, @RequestBody Promocion promocion){
        try{
            Promocion promo = promocionService.crearPromocion(loggedUser,promocion);
            return new ResponseEntity<>("{\"id\":\""+promo.getIdPromocion()+"\"}", HttpStatus.OK);
        }catch(BusinessException e){
            logger.error("ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@PutMapping("")
    public ResponseEntity<Object> updateNotification(@RequestAttribute("username") int loggedUser, @RequestBody Promocion promocion){
        try{
            promocionService.updatePromocion(loggedUser,promocion);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //@DeleteMapping("/{idPromocion}")
    public ResponseEntity<Object> deleteNotification(@RequestAttribute("username") int loggedUser,@PathVariable("idPromocion") int idPromocion){
        try{
            promocionService.deletePromocion(loggedUser,idPromocion);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        }catch(BusinessException e){
            return new ResponseEntity<Object>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("UNEXPECTED ERROR::::::"+e.getMessage(),e);
            return new ResponseEntity<Object>("{\"error\":\"Unexpected error\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
