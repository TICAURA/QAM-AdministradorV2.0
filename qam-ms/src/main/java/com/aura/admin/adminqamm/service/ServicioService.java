package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.request.ServiceRequestDto;
import com.aura.admin.adminqamm.dto.response.ServiceResponseDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Servicio;
import com.aura.admin.adminqamm.model.TipoAmbiente;
import com.aura.admin.adminqamm.model.TipoServicio;
import com.aura.admin.adminqamm.model.Usuario;
import com.aura.admin.adminqamm.repository.ServiceRepository;
import com.aura.admin.adminqamm.repository.TipoAmbienteRepositorio;
import com.aura.admin.adminqamm.repository.TipoServicioRepositorio;
import com.aura.admin.adminqamm.repository.UserRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ServicioService {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TipoAmbienteRepositorio tipoAmbienteRepositorio;

    @Autowired
    private TipoServicioRepositorio tipoServicioRepositorio;

    @Autowired
    private UserRepository userRepository;

    public List<ServiceResponseDto> getServicios(int loggedUser) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.INTERFACES_ADMINISTRADOR.getId(),PermitionENUM.INTERFACES_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        List<Servicio> servicios = serviceRepository.findAll();
        List<ServiceResponseDto> serviceDtos = new ArrayList<>();
        for (Servicio servicio: servicios) {
            ServiceResponseDto serviceDto = new ServiceResponseDto();
            serviceDto.setIdServicio(servicio.getIdServicio());
            serviceDto.setNombre(servicio.getNombre());
            serviceDto.setEndpoint(servicio.getEndpoint());
            serviceDto.setTipoAmbienteId(servicio.getAmbiente().getId());
            serviceDto.setTipoAmbienteNombre(servicio.getAmbiente().getNombre());
            serviceDto.setTipoServicioId(servicio.getServicio().getId());
            serviceDto.setTipoServicioNombre(servicio.getServicio().getNombre());
            serviceDto.setFechaRegistro(servicio.getFechaRegistro());
            serviceDto.setFechaModificacion(servicio.getFechaModificacion());
            serviceDto.setActivo(servicio.isActivo());
            serviceDtos.add(serviceDto);
        }
        return serviceDtos;
    }

    public ServiceResponseDto getServicio(int loggedUser,int serviceId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.INTERFACES_ADMINISTRADOR.getId(),PermitionENUM.INTERFACES_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        Servicio servicio = serviceRepository.findById(serviceId).orElseThrow(() -> new BusinessException("SERVICE NOT FOUND",404));
        ServiceResponseDto serviceDto = new ServiceResponseDto();
        serviceDto.setIdServicio(servicio.getIdServicio());
        serviceDto.setNombre(servicio.getNombre());
        serviceDto.setEndpoint(servicio.getEndpoint());
        serviceDto.setTipoAmbienteId(servicio.getAmbiente().getId());
        serviceDto.setTipoAmbienteNombre(servicio.getAmbiente().getNombre());
        serviceDto.setTipoServicioId(servicio.getServicio().getId());
        serviceDto.setTipoServicioNombre(servicio.getServicio().getNombre());
        serviceDto.setFechaRegistro(servicio.getFechaRegistro());
        serviceDto.setFechaModificacion(servicio.getFechaModificacion());
        serviceDto.setActivo(servicio.isActivo());
        return serviceDto;
    }

    public int createService(int loggedUser, ServiceRequestDto serviceDto) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.INTERFACES_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        Servicio servicio = new Servicio();

        servicio.setNombre(serviceDto.getNombre());
        servicio.setEndpoint(serviceDto.getEndpoint());

        TipoAmbiente tipoAmbiente = tipoAmbienteRepositorio.findById(serviceDto.getTipoAmbienteId()).orElseThrow(() -> new BusinessException("TIPO DE AMBIENTE NOT FOUND",404));
        TipoServicio tipoServicio = tipoServicioRepositorio.findById(serviceDto.getTipoServicioId()).orElseThrow(() -> new BusinessException("TIPO DE SERVICIO NOT FOUND",404));

        Usuario usuario = userRepository.findById(loggedUser).orElseThrow(() -> new BusinessException("USER NOT FOUND",404));
        servicio.setUsuario(usuario);

        servicio.setAmbiente(tipoAmbiente);
        servicio.setServicio(tipoServicio);

        servicio.setFechaRegistro(new Date());
        servicio.setFechaModificacion(new Date());
        servicio.setActivo(serviceDto.isActivo());

        validateServicio(servicio);

        return serviceRepository.saveAndFlush(servicio).getIdServicio();

    }

    public void modifyService(int loggedUser, ServiceRequestDto serviceDto) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.INTERFACES_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        Servicio servicio = serviceRepository.findById(serviceDto.getIdServicio()).orElseThrow(() -> new BusinessException("SERVICE NOT FOUND",404));

        if(serviceDto.getNombre() != null && !serviceDto.getNombre().isEmpty() && !serviceDto.getNombre().equals(servicio.getNombre())) {

            if(existName(serviceDto.getNombre())){
                throw new BusinessException("Servicio invalido, porfavor ingrese otro nombre.",406);
            }
            servicio.setNombre(serviceDto.getNombre());
        }
        servicio.setEndpoint(serviceDto.getEndpoint());

        TipoAmbiente tipoAmbiente = tipoAmbienteRepositorio.findById(serviceDto.getTipoAmbienteId()).orElseThrow(() -> new BusinessException("TIPO DE AMBIENTE NOT FOUND",404));
        TipoServicio tipoServicio = tipoServicioRepositorio.findById(serviceDto.getTipoServicioId()).orElseThrow(() -> new BusinessException("TIPO DE SERVICIO NOT FOUND",404));

        servicio.setAmbiente(tipoAmbiente);
        servicio.setServicio(tipoServicio);

        servicio.setFechaModificacion(new Date());
        servicio.setActivo(serviceDto.isActivo());
        serviceRepository.save(servicio);
    }
    public void deleteService(int loggedUser,int serviceId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.INTERFACES_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        serviceRepository.deleteById(serviceId);

    }

    public boolean existName(String name){
        return serviceRepository.findByNombre(name)!=null;
    }

    private void validateServicio(Servicio servicio)throws BusinessException{

        if(existName(servicio.getNombre())){
            throw new BusinessException("Servicio invalido, porfavor ingrese otro nombre.",406);
        }
        if(servicio.getNombre() == null||servicio.getNombre().isEmpty()  ){
            throw new BusinessException("Servicio invalido, porfavor ingrese un nombre valido.",406);
        }
        if(servicio.getEndpoint() == null||servicio.getEndpoint().isEmpty()  ){
            throw new BusinessException("Servicio invalido, porfavor ingrese un endpoint valido.",406);
        }
    }
}
