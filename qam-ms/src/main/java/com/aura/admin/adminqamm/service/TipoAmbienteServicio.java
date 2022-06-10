package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.request.CatalogueRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.TipoAmbiente;
import com.aura.admin.adminqamm.repository.TipoAmbienteRepositorio;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TipoAmbienteServicio {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private TipoAmbienteRepositorio tipoAmbienteRepositorio;

    public List<TipoAmbiente> getTipoAmbientes(int loggedUser) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.SERVICIOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_VER.getId(),PermitionENUM.SERVICIOS_CATALOGOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_CATALOGOS_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        List<TipoAmbiente> tipoAmbientes = tipoAmbienteRepositorio.findAll();
        return tipoAmbientes;
    }

    public TipoAmbiente getTipoAmbiente(int loggedUser, int tipoAmbienteId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.SERVICIOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_VER.getId(),PermitionENUM.SERVICIOS_CATALOGOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_CATALOGOS_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return tipoAmbienteRepositorio.findById(tipoAmbienteId).orElseThrow(() -> new BusinessException("TIPO AMBIENTE NOT FOUND",404));
    }

    public int createTipoAmbiente(int loggedUser,TipoAmbiente tipoAmbiente) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.SERVICIOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_CATALOGOS_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        tipoAmbiente.setFechaModificacion(new Date());
        tipoAmbiente.setFechaRegistro(new Date());

        validateTipoAmbiente(tipoAmbiente);

        return tipoAmbienteRepositorio.saveAndFlush(tipoAmbiente).getId();

    }

    public void modifyTipoAmbiente(int loggedUser, CatalogueRequestDto tipoAmbiente) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.SERVICIOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_CATALOGOS_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        TipoAmbiente tipoAmbienteOriginal = tipoAmbienteRepositorio.findById(tipoAmbiente.getId()).orElseThrow(() -> new BusinessException("TIPO AMBIENTE NOT FOUND",404));

        if(tipoAmbiente.getNombre() != null && !tipoAmbiente.getNombre().isEmpty() && !tipoAmbiente.getNombre().equals(tipoAmbienteOriginal.getNombre())) {

            if(existName(tipoAmbiente.getNombre())){
                throw new BusinessException("Tipo de ambiente invalido, porfavor ingrese otro nombre.",406);
            }
            tipoAmbienteOriginal.setNombre(tipoAmbiente.getNombre());
        }
        tipoAmbienteOriginal.setDescripcion(tipoAmbiente.getDescripcion());
        tipoAmbienteOriginal.setActivo(tipoAmbiente.isActivo());

        tipoAmbienteOriginal.setFechaModificacion(new Date());

        tipoAmbienteRepositorio.save(tipoAmbienteOriginal);
    }
    public void deleteTipoAmbiente(int loggedUser,int tipoAmbienteId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.SERVICIOS_ADMINISTRADOR.getId(),PermitionENUM.SERVICIOS_CATALOGOS_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        tipoAmbienteRepositorio.deleteById(tipoAmbienteId);

    }

    public boolean existName(String name){
        return tipoAmbienteRepositorio.findByNombre(name)!=null;
    }

    private void validateTipoAmbiente(TipoAmbiente tipoAmbiente)throws BusinessException{

        if(existName(tipoAmbiente.getNombre())){
            throw new BusinessException("Tipo de ambiente invalido, porfavor ingrese otro nombre.",406);
        }
        if(tipoAmbiente.getNombre() == null||tipoAmbiente.getNombre().isEmpty()  ){
            throw new BusinessException("Tipo de ambiente invalido, porfavor ingrese un nombre valido.",406);
        }
    }
}
