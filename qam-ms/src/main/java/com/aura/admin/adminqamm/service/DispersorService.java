package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Dispersor;
import com.aura.admin.adminqamm.repository.DispersorRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DispersorService {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private DispersorRepository dispersorRepository;

    public List<Dispersor> getDispersores(int loggedUser) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        List<Dispersor> dispersores = dispersorRepository.findAll();
        return dispersores;
    }

    public Dispersor getDispersor(int loggedUser, int dispersorId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return dispersorRepository.findById(dispersorId).orElseThrow(() -> new BusinessException("DISPERSOR NOT FOUND",404));
    }
}
