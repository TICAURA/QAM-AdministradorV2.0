package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.Relation;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.*;
import com.aura.admin.adminqamm.repository.PermissionRepository;
import com.aura.admin.adminqamm.repository.RolPermissionRepository;
import com.aura.admin.adminqamm.repository.RolRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class RolPermissionService {
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolPermissionRepository rolPermissionRepository;

    @Autowired
    private PermissionRepository permissionRepository;



    public List<Relation> viewRolPermissionLink(int loggedUser, int requestRolId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        Rol rol = rolRepository.findById(requestRolId).orElseThrow(() -> new BusinessException("ROL NOT FOUND",404));
        Set<RolPermiso> rolPermisos = rol.getRolPermisos();
        List<Relation> relations = new ArrayList<>();
        for (RolPermiso rolp: rolPermisos) {
            Relation rel = new Relation();
            rel.setFather(rolp.getId().getIdRol());
            rel.setChild(rolp.getId().getIdPermiso());
            relations.add(rel);
        }
        return relations;
    }

    public void createRolPermissionLink(int loggedUser, Relation relation) throws BusinessException{
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        RolPermiso rolPermiso = new RolPermiso();
        rolPermiso.setId(new RolPermisoKey(relation.getFather(),relation.getChild()));
        rolPermiso.setRegistro(new Date());
        Rol rol = rolRepository.findById(relation.getFather()).orElseThrow(() -> new BusinessException("ROLES NOT FOUND",404));
        Permiso permiso = permissionRepository.findById(relation.getChild()).orElseThrow(() -> new BusinessException("PERMISSION NOT FOUND",404));
        rolPermiso.setRol(rol);
        rolPermiso.setPermiso(permiso);
        rolPermissionRepository.save(rolPermiso);
    }

    public void deleteRolPermissionLink(int loggedUser, Relation relation) throws BusinessException{
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        rolPermissionRepository.deleteById(new RolPermisoKey(relation.getFather(),relation.getChild()));

    }
}
