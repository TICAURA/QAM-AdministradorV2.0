package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.Relation;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.*;
import com.aura.admin.adminqamm.repository.PermissionRepository;
import com.aura.admin.adminqamm.repository.UserRepository;
import com.aura.admin.adminqamm.repository.UsuarioPermisoRepositorio;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserPermissionService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UsuarioPermisoRepositorio usuarioPermisoRepositorio;

    @Autowired
    private PermissionRepository permissionRepository;

    public List<Relation> viewUserPermissionLink(int loggedUser, int requestUserId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_SEGURIDAD_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_SEGURIDAD_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        Usuario usuario = userRepository.findById(requestUserId).orElseThrow(() -> new BusinessException("USER NOT FOUND",404));

        Set<UsuarioPermiso> usuarioPermisoSet = usuario.getPermisos();
        List<Relation> relations = new ArrayList<>();
        for (UsuarioPermiso usuarioPermiso: usuarioPermisoSet) {
            Relation rel = new Relation();
            rel.setFather(usuarioPermiso.getId().getIdUsuario());
            rel.setChild(usuarioPermiso.getId().getIdPermiso());
            relations.add(rel);
        }
        return relations;
    }

    public void createUserPermissionLink(int loggedUser, Relation relation) throws BusinessException{
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_SEGURIDAD_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        UsuarioPermiso usuarioPermiso = new UsuarioPermiso();
        usuarioPermiso.setId(new UsuarioPermisoKey(relation.getFather(),relation.getChild()));
        usuarioPermiso.setRegistro(new Date());
        Usuario usuario = userRepository.findById(relation.getFather()).orElseThrow(()-> new BusinessException("USER NOT FOUND",404));
        Permiso permiso = permissionRepository.findById(relation.getChild()).orElseThrow(() -> new BusinessException("PERMISSION NOT FOUND",404));
        usuarioPermiso.setUsuario(usuario);
        usuarioPermiso.setPermiso(permiso);

        usuarioPermisoRepositorio.save(usuarioPermiso);
    }

    public void deleteUserPermissionLink(int loggedUser, Relation relation) throws BusinessException{
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_SEGURIDAD_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        usuarioPermisoRepositorio.deleteById(new UsuarioPermisoKey(relation.getFather(),relation.getChild()));

    }

}
