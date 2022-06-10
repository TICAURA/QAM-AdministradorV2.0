/**
 * 
 */
package com.aura.admin.adminqamm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.dto.SegmentoDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Segmento;
import com.aura.admin.adminqamm.repository.SegmentoRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;

import lombok.AllArgsConstructor;

/**
 * @author Cesar Agustin Soto
 *
 */
@Service
@AllArgsConstructor
public class SegmentoService {
	
	/**
	 * 
	 */
	private final PermissionService permissionService;
	
	/**
	 * 
	 */
	private final SegmentoRepository segmentoRepository;

	/**
	 * Obtiene todos los segmentos registrados
	 * @param loggedIdUser
	 * @return
	 */
	public List<SegmentoDto> getAll(int loggedIdUser) throws BusinessException {
		
		if (!permissionService.validatePermition(loggedIdUser, PermitionENUM.SUDO.getId(),
				PermitionENUM.PARAMETROS_ADMINISTRADOR.getId(), PermitionENUM.PARAMETROS_VER.getId())) {
			throw new BusinessException("El usuario no tiene acceso a esta información.", 401);
		}
		
		List<SegmentoDto> segmegtosResponse = new ArrayList<SegmentoDto>();
		
		for (Segmento segmento : segmentoRepository.findByEsActivo(Boolean.TRUE)) {
			segmegtosResponse.add(mapperToDTO(segmento));
		}
		
		return segmegtosResponse;
	}

	/**
	 * Obtiene el segmento correspondiente al identificador enviado
	 * @param loggedIdUser
	 * @param corteAntId
	 * @return
	 * @throws BusinessException
	 */
	public SegmentoDto getPorId(int loggedIdUser, Integer segmentoId) throws BusinessException {
		
		if (!permissionService.validatePermition(loggedIdUser, PermitionENUM.SUDO.getId(),
				PermitionENUM.PARAMETROS_ADMINISTRADOR.getId(), PermitionENUM.PARAMETROS_VER.getId())) {
			throw new BusinessException("El usuario no tiene acceso a esta información.", 401);
		}
		
		Segmento segmentoBD = segmentoRepository.findBySgmnId(segmentoId);
		
		if (segmentoBD==null) {
			throw new BusinessException("No existe segmento con el ID proporcionado", 401);
		}
		
		return mapperToDTO(segmentoBD);
	}
	
	/**
	 * Crea en la BD un registro para la tabla c_segmento
	 * @param loggedIdUser
	 * @param segmento
	 * @return 
	 * @throws BusinessException
	 */
	public Integer crear(int loggedIdUser, SegmentoDto segmentoDto) throws BusinessException {
		
		if (!permissionService.validatePermition(loggedIdUser, PermitionENUM.SUDO.getId(),
				PermitionENUM.PARAMETROS_ADMINISTRADOR.getId(), PermitionENUM.PARAMETROS_VER.getId())) {
			throw new BusinessException("El usuario no tiene acceso a esta información.", 401);
		}
		
		Segmento segmento = mapperToModel(segmentoDto);
		
		return segmentoRepository.saveAndFlush(segmento).getSgmnId();
	}
	
	public void actualizar(int loggedIdUser, SegmentoDto segmentoDto) throws BusinessException {
		if (!permissionService.validatePermition(loggedIdUser, PermitionENUM.SUDO.getId(),
				PermitionENUM.PARAMETROS_ADMINISTRADOR.getId(), PermitionENUM.PARAMETROS_VER.getId())) {
			throw new BusinessException("El usuario no tiene acceso a esta información.", 401);
		}
		
		Segmento segmentoBD = segmentoRepository.findBySgmnId(segmentoDto.getSegmentoId());
		
		if (segmentoBD==null) {
			throw new BusinessException("No existe segmento con el ID proporcionado", 401);
		}
		
		segmentoBD.setDescripcion(segmentoDto.getDescripcion());
		segmentoBD.setNomCorto(segmentoDto.getNombreCorto());
		segmentoBD.setEsActivo(segmentoDto.getEsActivo());
		
		segmentoRepository.save(segmentoBD);
	}
	
	private SegmentoDto mapperToDTO(Segmento segmento) {
		SegmentoDto segmentoDto = new SegmentoDto();
		
		segmentoDto.setSegmentoId(segmento.getSgmnId());
		segmentoDto.setNombreCorto(segmento.getNomCorto());
		segmentoDto.setEsActivo(segmento.getEsActivo());
		segmentoDto.setFchAlta(segmento.getFchAlta());
		segmentoDto.setDescripcion(segmento.getDescripcion());
		
		return segmentoDto;
	}
	
	private Segmento mapperToModel(SegmentoDto segmentoDto) {
		Segmento segmento = new Segmento();
		
		segmento.setNomCorto(segmentoDto.getNombreCorto());
		segmento.setEsActivo(Boolean.TRUE);
		segmento.setFchAlta(new Date());
		segmento.setDescripcion(segmentoDto.getDescripcion());
		
		return segmento;
	}

}
