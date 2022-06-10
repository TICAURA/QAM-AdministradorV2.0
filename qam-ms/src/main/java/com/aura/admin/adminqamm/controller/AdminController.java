package com.aura.admin.adminqamm.controller;

import com.aura.admin.adminqamm.dao.AdminDao;
import com.aura.admin.adminqamm.dto.RecuperarContra;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/recuperar-contra")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AdminDao adminDao;

    @GetMapping("checar-token/{jwt}")
    public ResponseEntity<String> checarToken(@PathVariable("jwt") String jwt){
        try {
            //checar expiracion
            if (jwtTokenUtil.isTokenExpired(jwt) == true) {
                return new ResponseEntity<String>("{\"error\":\"Token expirado, porfavor solicitar el cambio de contraseña otra vez.\"}", HttpStatus.UNAUTHORIZED);
            }

            String email = jwtTokenUtil.getUsernameFromToken(jwt);

            logger.info("email:" + email);

            return new ResponseEntity<String>("{\"email\":\"" + email + "\"}", HttpStatus.OK);

        }catch (Exception e){
            logger.error("error al validar token:"+e.getMessage(),e);
            return new ResponseEntity<String>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("cambiar-contra/{jwt}")
    public ResponseEntity<String> recuperarContra(@RequestBody RecuperarContra recuperarContra, @PathVariable("jwt") String jwt ){
        try {
            if (jwtTokenUtil.isTokenExpired(jwt) == true) {
                return new ResponseEntity<String>("{\"error\":\"Token expirado.\"}", HttpStatus.UNAUTHORIZED);
            }
            if (!recuperarContra.getEmail().equals(jwtTokenUtil.getUsernameFromToken(jwt)) || !adminDao.validarEmail(recuperarContra.getEmail())) {
                return new ResponseEntity<String>("{\"error\":\"Eamil invalido.\"}", HttpStatus.UNAUTHORIZED);
            }

            adminDao.actualizarContra(recuperarContra.getEmail(),recuperarContra.getPassword());

            return new ResponseEntity<String>("", HttpStatus.OK);
        }catch (BusinessException e){
            return new ResponseEntity<String>("{\"error\":\""+e.getError()+"\"}",HttpStatus.valueOf(e.getCode()));
        }catch (Exception e){
            logger.error("error al cambiar la contraseña:"+e.getMessage(),e);
            return new ResponseEntity<String>("{\"error\":\"Servicio no disponible.\"}",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
