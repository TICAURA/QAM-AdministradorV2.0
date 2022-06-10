package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.controller.PermissionController;
import com.aura.admin.adminqamm.dao.AdminDao;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Interfaz;
import com.aura.admin.adminqamm.model.Permiso;
import com.aura.admin.adminqamm.model.Usuario;
import com.aura.admin.adminqamm.repository.InterfaceRepository;
import com.aura.admin.adminqamm.repository.PermissionRepository;
import com.aura.admin.adminqamm.repository.UserRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private PermissionRepository permissionRepository;

    public boolean validatePermition(int userId, int ... permitions ) throws BusinessException{

        for(int permition : permitions){
            if(adminDao.validatePermition(userId,permition)){return true;}
        }
        return false;
    }

    @Autowired
    private InterfaceRepository interfaceRepository;

    public List<Permiso> getPermission(int loggedUser) throws BusinessException {

        if(!validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_SEGURIDAD_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_SEGURIDAD_VER.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        List<Permiso> permisos = permissionRepository.findAll();
        return permisos;
    }

    public Permiso getPermission(int loggedUser,int permissionId) throws BusinessException {
        if(!validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_SEGURIDAD_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_SEGURIDAD_VER.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return permissionRepository.findById(permissionId).orElseThrow(() -> new BusinessException("PERMISSION NOT FOUND",404));
    }
}
