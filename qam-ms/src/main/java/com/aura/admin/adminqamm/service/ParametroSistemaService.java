package com.aura.admin.adminqamm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.dto.UpdateParametroDto;
import com.aura.admin.adminqamm.dto.response.ParametroDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.ParametroSistema;
import com.aura.admin.adminqamm.repository.ParametroSistemaRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ParametroSistemaService {

	private final ParametroSistemaRepository parametrSistemaRepository;
	
	private final PermissionService permissionService;


	public List<ParametroDto> getParametrosSistma(int loggedUser) throws BusinessException {

		if (!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),
				PermitionENUM.PARAMETROS_ADMINISTRADOR.getId(), PermitionENUM.PARAMETROS_VER.getId())) {
			throw new BusinessException("El usuario no tiene acceso a esta información.", 401);
		}
		List<ParametroSistema> parametrosSistema = parametrSistemaRepository.buscarParametrosAll();

		List<ParametroDto> parametros = new ArrayList<>();

		parametrosSistema.forEach(parametro -> {
			parametros.add(llenaparametro(parametro));

		});
		return parametros;

	}
	
	public ParametroDto obtieneParametro(int loggedUser,Integer idParametro)throws BusinessException {
		if (!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),
				PermitionENUM.PARAMETROS_ADMINISTRADOR.getId(), PermitionENUM.PROMOCIONES_VER.getId())) {
			throw new BusinessException("El usuario no tiene acceso a esta información.", 401);
		}
		
		ParametroDto parametro=new ParametroDto();
		ParametroSistema parametrosSistema = parametrSistemaRepository.findByConsecutivo(idParametro);
		
		parametro=llenaparametro(parametrosSistema);
		
		return parametro;
		
	}

	public void actualizaValor(int loggedUser, UpdateParametroDto parametroA) throws BusinessException{
		if (!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),
				PermitionENUM.PARAMETROS_ADMINISTRADOR.getId())) {
			throw new BusinessException("El usuario no tiene acceso a esta información.", 401);
		}

		try {

			ParametroSistema parametroActual = parametrSistemaRepository.findByConsecutivo(parametroA.getConsecutivo());
			parametroActual.setEsActivo(Boolean.FALSE);
			
			ParametroSistema parametroNuevo = new ParametroSistema();
			parametroNuevo.setFchAlta(new Date());
			parametroNuevo.setIdParametro(parametroActual.getIdParametro());	
			parametroNuevo.setDescripcion(parametroActual.getDescripcion());
			parametroNuevo.setValor(parametroA.getValor());
			parametroNuevo.setEsActivo(Boolean.TRUE);
			parametroNuevo.setEsGlobal(parametroActual.getEsGlobal());
			parametroNuevo.setSgmnId(parametroActual.getSgmnId());
			
			parametrSistemaRepository.save(parametroActual);
			parametroA.setConsecutivo(parametrSistemaRepository.saveAndFlush(parametroNuevo).getConsecutivo());
			parametroA.setActualizado(Boolean.TRUE);

		} catch (Exception e) {
			e.printStackTrace();
			
			new Throwable("Error Al actualizar valor");
		}
	}

	public ParametroDto llenaparametro(ParametroSistema param) {

		return new ParametroDto().build(param);
	}

}
