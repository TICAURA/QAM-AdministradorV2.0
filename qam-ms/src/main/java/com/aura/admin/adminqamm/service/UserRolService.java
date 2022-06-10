package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.Relation;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.*;
import com.aura.admin.adminqamm.repository.RolRepository;
import com.aura.admin.adminqamm.repository.UserRepository;
import com.aura.admin.adminqamm.repository.UsuarioRolRepositorio;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserRolService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UsuarioRolRepositorio usuarioRolRepositorio;
    @Autowired
    private RolRepository rolRepository;

    public List<Relation> viewUserRolesLink(int loggedUser, int requestUserId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_SEGURIDAD_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_SEGURIDAD_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        Usuario usuario = userRepository.findById(requestUserId).orElseThrow(() -> new BusinessException("USER NOT FOUND",404));
        Set<UsuarioRol> usuarioRolSet = usuario.getRoles();
        List<Relation> relations = new ArrayList<>();
        for (UsuarioRol usuarioRol: usuarioRolSet) {
            Relation rel = new Relation();
            rel.setFather(usuarioRol.getId().getIdUsuario());
            rel.setChild(usuarioRol.getId().getIdRol());
            relations.add(rel);
        }
        return relations;
    }

    public void createUserRolesLink(int loggedUser, Relation relation) throws BusinessException{
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_SEGURIDAD_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setId(new UsuarioRolKey(relation.getFather(),relation.getChild()));
        usuarioRol.setRegistro(new Date());
        Usuario usuario = userRepository.findById(relation.getFather()).orElseThrow(()-> new BusinessException("USER NOT FOUND",404));
        Rol rol = rolRepository.findById(relation.getChild()).orElseThrow(() -> new BusinessException("ROLES NOT FOUND",404));
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);
        usuarioRolRepositorio.save(usuarioRol);
    }

    public void deleteUserRolesLink(int loggedUser, Relation relation) throws BusinessException{
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_SEGURIDAD_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        usuarioRolRepositorio.deleteById(new UsuarioRolKey(relation.getFather(),relation.getChild()));

    }
}
