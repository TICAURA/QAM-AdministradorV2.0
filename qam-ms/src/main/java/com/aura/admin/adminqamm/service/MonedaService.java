package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Moneda;
import com.aura.admin.adminqamm.repository.MonedaRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonedaService {
    @Autowired
    private MonedaRepository monedaRepository;

    @Autowired
    private PermissionService permissionService;

    public List<Moneda> getMonedas(int loggedUser) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),
                PermitionENUM.COLABORADOR_ADMINISTRADOR.getId(),PermitionENUM.COLABORADOR_VER.getId(),PermitionENUM.SERVICIOS_CATALOGOS_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return  monedaRepository.findAll();
    }
    public Moneda getMoneda(int loggedUser, int id) throws BusinessException{
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),
                PermitionENUM.COLABORADOR_ADMINISTRADOR.getId(),PermitionENUM.COLABORADOR_VER.getId(),PermitionENUM.SERVICIOS_CATALOGOS_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return monedaRepository.findById(id).orElseThrow(() -> new BusinessException("DISPERSOR NOT FOUND",404));

    }
}
