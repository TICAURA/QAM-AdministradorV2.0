package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Facturador;
import com.aura.admin.adminqamm.model.TipoAmbiente;
import com.aura.admin.adminqamm.repository.FacturadorRepository;
import com.aura.admin.adminqamm.repository.TipoAmbienteRepositorio;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturadorService {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private FacturadorRepository facturadorRepository;

    public List<Facturador> getFacturadores(int loggedUser) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        List<Facturador> facturadores = facturadorRepository.findAll();
        return facturadores;
    }

    public Facturador getFacturador(int loggedUser, int facturadorId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return facturadorRepository.findById(facturadorId).orElseThrow(() -> new BusinessException("FACTURADOR NOT FOUND",404));
    }
}
