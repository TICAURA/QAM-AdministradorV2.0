/**
 * 
 */
package com.aura.admin.adminqamm.service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.aura.admin.adminqamm.controller.ServiceController;
import com.aura.admin.adminqamm.dto.CorteAnticipoDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.CorteAnticipo;
import com.aura.admin.adminqamm.repository.CorteAnticipoRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;

import lombok.AllArgsConstructor;

/**
 * @author Cesar Agustin Soto
 *
 */
@Service
@AllArgsConstructor
public class CorteAnticipoService {
	
	private final CorteAnticipoRepository corteAnticipoRepository;
	
	private final PermissionService permissionService;
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
	
	public List<CorteAnticipoDto> getPorCentroCosto(Integer idCentroCosto, String periodicidad, int loggedIdUser) throws BusinessException {
		
		if(!permissionService.validatePermition(loggedIdUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
		
		if (idCentroCosto==null) {
			throw new BusinessException("El centro de costos es requirido",401);
		}
		
		List<CorteAnticipo> corteAnticipoList = null;
		
		if (StringUtils.isNotBlank(periodicidad)) {
			corteAnticipoList = corteAnticipoRepository.findAllByCentroCostosIdAndPeriodicidadAndEsActivo(idCentroCosto, periodicidad, Boolean.TRUE);
		} else {
			corteAnticipoList = corteAnticipoRepository.findAllByCentroCostosIdAndEsActivo(idCentroCosto, Boolean.TRUE);
		}
		
		return Optional.ofNullable(corteAnticipoList.stream().map(this::mapperToDTO)
				.collect(Collectors.toList()))
				.orElseGet(Collections::emptyList);
	}

	public CorteAnticipoDto getPorId(int loggedIdUser, Integer corteAntId) throws BusinessException {
		
		if(!permissionService.validatePermition(loggedIdUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
		
		CorteAnticipo corteAntBD = corteAnticipoRepository.findById(corteAntId)
				.orElseThrow(() -> new BusinessException("No se encontro el corte anticipo indicado ",401));
		
		CorteAnticipoDto corteAntDto = Optional.ofNullable(corteAntBD)
				.map(this::mapperToDTO)
				.orElse(null);
		
		return corteAntDto;
	}
	
	public void actualizarCorteAnticipo(int loggedIdUser, CorteAnticipoDto corteAnticipoEdit) throws BusinessException {
		
		if(!permissionService.validatePermition(loggedIdUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
		
		CorteAnticipo corteAntBD = corteAnticipoRepository.findById(corteAnticipoEdit.getCorteId())
				.orElseThrow(() -> new BusinessException("No se encontro el corte anticipo indicado ",401));
		
		int inicio = corteAntBD.getFchInicio().compareTo(corteAnticipoEdit.getFchInicio());
		int fin = corteAntBD.getFchFin().compareTo(corteAnticipoEdit.getFchFin());
		
		if (inicio!=0) {
			validaFechaEdit(corteAntBD, corteAnticipoEdit.getFchInicio());
		}
				
		if (fin!=0) {
			validaFechaEdit(corteAntBD, corteAnticipoEdit.getFchFin());
		}
		
//		corteAntBD.setEsActivo(corteAnticipoEdit.getEsActivo());
		corteAntBD.setFchCorteIncidencias(corteAnticipoEdit.getFchCorteIncidencias());
		corteAntBD.setFchFin(corteAnticipoEdit.getFchFin());
		corteAntBD.setFchInicio(corteAnticipoEdit.getFchInicio());
		corteAntBD.setFchPago(corteAnticipoEdit.getFchPago());
//		corteAntBD.setPeriodicidad(corteAnticipoEdit.getPeriodicidad());
		
		corteAnticipoRepository.save(corteAntBD);
	}
	
	private CorteAnticipoDto mapperToDTO(CorteAnticipo corteAnticipo) {
		CorteAnticipoDto corteAntDto = new CorteAnticipoDto();
		
		corteAntDto.setCentroCostosId(corteAnticipo.getCentroCostosId());
		corteAntDto.setCorteId(corteAnticipo.getCorteId());
		corteAntDto.setEsActivo(corteAnticipo.getEsActivo());
		corteAntDto.setFchCorteIncidencias(corteAnticipo.getFchCorteIncidencias());
		corteAntDto.setFchFin(corteAnticipo.getFchFin());
		corteAntDto.setFchInicio(corteAnticipo.getFchInicio());
		corteAntDto.setFchPago(corteAnticipo.getFchPago());
		corteAntDto.setPeriodicidad(corteAnticipo.getPeriodicidad());
		
		return corteAntDto;
	}
	
	private void validaFechaEdit(CorteAnticipo corteBD, Date fchInicio) throws BusinessException {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);
		if (!CollectionUtils.isEmpty(corteAnticipoRepository.consultarCortePorFechas(corteBD.getCentroCostosId(), corteBD.getCorteId(), corteBD.getPeriodicidad(), fchInicio))) {
			throw new BusinessException("Existen cortes anticipo con fechas entre las de inicio y fin que se quieren actualizar",401);
		}
	}

}
