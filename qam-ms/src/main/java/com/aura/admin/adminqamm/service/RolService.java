package com.aura.admin.adminqamm.service;


import com.aura.admin.adminqamm.dto.request.CatalogueRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Rol;
import com.aura.admin.adminqamm.repository.RolRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RolService {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolRepository rolRepository;

    public List<Rol> getRoles(int loggedUser) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        List<Rol> roles = rolRepository.findAll();
        return roles;
    }

    public Rol getRol(int loggedUser,int rolId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return rolRepository.findById(rolId).orElseThrow(() -> new BusinessException("ROL NOT FOUND",404));
    }

    public int createRol(int loggedUser,Rol rol) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        rol.setFechaModificacion(new Date());
        rol.setFechaRegistro(new Date());

        validateRol(rol);
        return rolRepository.saveAndFlush(rol).getId();

    }

    public void modifyRol(int loggedUser, CatalogueRequestDto rol) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        Rol rolOriginal = rolRepository.findById(rol.getId()).orElseThrow(() -> new BusinessException("ROL NOT FOUND",404));

        if(rol.getNombre() != null && !rol.getNombre().isEmpty() && !rol.getNombre().equals(rolOriginal.getNombre())) {

            if(existName(rol.getNombre())){
                throw new BusinessException("Rol invalido, porfavor ingrese otro nombre.",406);
            }
            rolOriginal.setNombre(rol.getNombre());
        }

        rolOriginal.setDescripcion(rol.getDescripcion());
        rolOriginal.setActivo(rol.isActivo());
        rolOriginal.setFechaModificacion(new Date());
        rolRepository.save(rolOriginal);
    }
    public void deleteRol(int loggedUser,int rolId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.ADMINISTRADOR_DE_ROLES.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        rolRepository.deleteById(rolId);

    }
    public boolean existName(String name){
        return rolRepository.findByNombre(name)!=null;
    }

    private void validateRol(Rol rol)throws BusinessException{

        if(existName(rol.getNombre())){
            throw new BusinessException("Rol invalido, porfavor ingrese otro nombre.",406);
        }
        if(rol.getNombre() == null||rol.getNombre().isEmpty()  ){
            throw new BusinessException("Rol invalido, porfavor ingrese un nombre valido.",406);
        }
    }
}
