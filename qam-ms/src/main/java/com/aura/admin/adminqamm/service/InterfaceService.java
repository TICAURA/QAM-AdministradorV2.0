package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.ArchivoLogo;
import com.aura.admin.adminqamm.dto.request.InterfazRequestDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Interfaz;
import com.aura.admin.adminqamm.model.Usuario;
import com.aura.admin.adminqamm.repository.InterfaceRepository;
import com.aura.admin.adminqamm.repository.UserRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class InterfaceService {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private InterfaceRepository interfaceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArchivoService archivoService;

    public List<Interfaz> getInterfaces(int loggedUser) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.INTERFACES_ADMINISTRADOR.getId(),PermitionENUM.INTERFACES_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        List<Interfaz> interfaces = interfaceRepository.findAll();
        return interfaces;
    }

    public Interfaz getInterfaz(int loggedUser,int interfazId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.INTERFACES_ADMINISTRADOR.getId(),PermitionENUM.INTERFACES_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return interfaceRepository.findById(interfazId).orElseThrow(() -> new BusinessException("INTERFACE NOT FOUND",404));
    }

    public int createInterface(int loggedUser,InterfazRequestDto interfazDto) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.INTERFACES_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        Interfaz interfaz = new Interfaz();

        interfaz.setFechaModificacion(new Date());
        interfaz.setFechaRegistro(new Date());

        if(interfazDto.getNombre() == null || interfazDto.getNombre().isEmpty()) {
            throw new BusinessException("Interfaz invalido, porfavor ingrese un nombre.",406);
        }

        if(existName(interfazDto.getNombre())){
            throw new BusinessException("Interfaz invalido, porfavor ingrese otro nombre.",406);
        }

        interfaz.setNombre(interfazDto.getNombre());

        try{
            //TODO validar tipo de archivo .jpg .png etc etc...
            if(interfazDto.getUrlLogoIcono()!=null) {
                String archivoLogo = Base64.getEncoder().encodeToString(interfazDto.getUrlLogoIcono().getBytes());
                interfaz.setUrlLogoIcono(archivoService.guardarArchivo(UUID.randomUUID().toString(), archivoLogo) + "");
            }

        }catch(IOException e){
            throw new BusinessException("Error al guardar el archivo.",401);
        }

        interfaz.setTitulo(interfazDto.getTitulo());
        interfaz.setColorGlobalPrimario(interfazDto.getColorGlobalPrimario());
        interfaz.setColorGlobalSecundario(interfazDto.getColorGlobalSecundario());
        interfaz.setColorHomePrimario(interfazDto.getColorHomePrimario());
        interfaz.setColorHomeSecundario(interfazDto.getColorHomeSecundario());
        interfaz.setColorTexto(interfazDto.getColorTexto());
        interfaz.setColorError(interfazDto.getColorError());
        interfaz.setColorBackground(interfazDto.getColorBackground());

        interfaz.setActivo(interfazDto.isActivo());

        Usuario usuario = userRepository.findById(loggedUser).orElseThrow(() -> new BusinessException("USER NOT FOUND",404));
        interfaz.setUsuario(usuario);

        validateInterfaz(interfaz);

        return interfaceRepository.saveAndFlush(interfaz).getIdInterfaz();

    }

    public void modifyInterface(int loggedUser, InterfazRequestDto interfaz) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.INTERFACES_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        Interfaz interfazOriginal = interfaceRepository.findById(interfaz.getIdInterfaz()).orElseThrow(() -> new BusinessException("INTERFACE NOT FOUND",404));

        if(interfaz.getNombre() != null && !interfaz.getNombre().isEmpty() && !interfaz.getNombre().equals(interfazOriginal.getNombre())) {

            if(existName(interfaz.getNombre())){
                throw new BusinessException("Interfaz invalido, porfavor ingrese otro nombre.",406);
            }
            interfazOriginal.setNombre(interfaz.getNombre());
        }

        interfazOriginal.setTitulo(interfaz.getTitulo());
        interfazOriginal.setColorGlobalPrimario(interfaz.getColorGlobalPrimario());
        interfazOriginal.setColorGlobalSecundario(interfaz.getColorGlobalSecundario());
        interfazOriginal.setColorHomePrimario(interfaz.getColorHomePrimario());
        interfazOriginal.setColorHomeSecundario(interfaz.getColorHomeSecundario());
        interfazOriginal.setColorTexto(interfaz.getColorTexto());
        interfazOriginal.setColorError(interfaz.getColorError());
        interfazOriginal.setColorBackground(interfaz.getColorBackground());

        try{
            //TODO validar tipo de archivo .jpg .png etc etc...
            if(interfaz.getUrlLogoIcono()!=null) {
                String archivoLogo = Base64.getEncoder().encodeToString(interfaz.getUrlLogoIcono().getBytes());
                interfazOriginal.setUrlLogoIcono(archivoService.guardarArchivo(UUID.randomUUID().toString(), archivoLogo) + "");
            }
        }catch(IOException e){
            throw new BusinessException("Error al guardar el archivo.",401);
        }

        interfazOriginal.setActivo(interfaz.isActivo());

        interfazOriginal.setFechaModificacion(new Date());

        interfaceRepository.save(interfazOriginal);
    }
    public void deleteInterface(int loggedUser,int interfaceId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.INTERFACES_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        interfaceRepository.deleteById(interfaceId);

    }

    public boolean existName(String name){
        return interfaceRepository.findByNombre(name)!=null;
    }

    private void validateInterfaz(Interfaz interfaz)throws BusinessException{

        if(existName(interfaz.getNombre())){
            throw new BusinessException("Interfaz invalida, porfavor ingrese otro nombre.",406);
        }
        if(interfaz.getNombre() == null||interfaz.getNombre().isEmpty()  ){
            throw new BusinessException("Interfaz invalida, porfavor ingrese un nombre valido.",406);
        }
    }

    public ArchivoLogo getArchivo(int archivoId) throws BusinessException{
        return archivoService.getArchivo(archivoId);
    }

}
