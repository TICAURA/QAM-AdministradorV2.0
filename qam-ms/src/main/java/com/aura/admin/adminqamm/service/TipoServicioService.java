package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.request.CatalogueRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.TipoServicio;
import com.aura.admin.adminqamm.repository.TipoServicioRepositorio;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TipoServicioService {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private TipoServicioRepositorio tipoServicioRepositorio;

    public List<TipoServicio> getTipoServicios(int loggedUser) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.SERVICIOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_VER.getId(),PermitionENUM.SERVICIOS_CATALOGOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_CATALOGOS_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        List<TipoServicio> tipoServicios = tipoServicioRepositorio.findAll();
        return tipoServicios;
    }

    public TipoServicio getTipoServicio(int loggedUser, int tipoServicioId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.SERVICIOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_VER.getId(),PermitionENUM.SERVICIOS_CATALOGOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_CATALOGOS_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return tipoServicioRepositorio.findById(tipoServicioId).orElseThrow(() -> new BusinessException("TIPO SERVICIO NOT FOUND",404));
    }

    public int createTipoServicio(int loggedUser,TipoServicio tipoServicio) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.SERVICIOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_CATALOGOS_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        tipoServicio.setFechaModificacion(new Date());
        tipoServicio.setFechaRegistro(new Date());

        validateTipoServicio(tipoServicio);

        return tipoServicioRepositorio.saveAndFlush(tipoServicio).getId();

    }

    public void modifyTipoServicio(int loggedUser, CatalogueRequestDto tipoServicio) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.SERVICIOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_CATALOGOS_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        TipoServicio tipoServicioOriginal = tipoServicioRepositorio.findById(tipoServicio.getId()).orElseThrow(() -> new BusinessException("TIPO SERVICIO NOT FOUND",404));


        if(tipoServicio.getNombre() != null && !tipoServicio.getNombre().isEmpty() && !tipoServicio.getNombre().equals(tipoServicioOriginal.getNombre())) {

            if(existName(tipoServicio.getNombre())){
                throw new BusinessException("Tipo de servicio invalido, porfavor ingrese otro nombre.",406);
            }

            tipoServicioOriginal.setNombre(tipoServicio.getNombre());
        }
        tipoServicioOriginal.setDescripcion(tipoServicio.getDescripcion());
        tipoServicioOriginal.setActivo(tipoServicio.isActivo());

        tipoServicioOriginal.setFechaModificacion(new Date());

        tipoServicioRepositorio.save(tipoServicioOriginal);
    }
    public void deleteTipoServicio(int loggedUser,int tipoServicioId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.SERVICIOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_CATALOGOS_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        tipoServicioRepositorio.deleteById(tipoServicioId);

    }

    public boolean existName(String name){
        return tipoServicioRepositorio.findByNombre(name)!=null;
    }

    private void validateTipoServicio(TipoServicio tipoServicio)throws BusinessException{

        if(existName(tipoServicio.getNombre())){
            throw new BusinessException("Tipo de servicio invalido, porfavor ingrese otro nombre.",406);
        }
        if(tipoServicio.getNombre() == null||tipoServicio.getNombre().isEmpty()  ){
            throw new BusinessException("Tipo de servicio invalido, porfavor ingrese un nombre valido.",406);
        }
    }
}
