package com.aura.admin.adminqamm.service;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.dto.RecuperarContra;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Colaborador;
import com.aura.admin.adminqamm.repository.ColaboradorRepository;

import lombok.AllArgsConstructor;

/**
 * @author Cesar Agustin Soto
 *
 */
@Service
@AllArgsConstructor
public class ResetPasswordService {
	
	private final ColaboradorRepository colaboradorRepository;

	private static final Logger logger = LoggerFactory.getLogger(ResetPasswordService.class);
	
	private final static String EXITO_RESPONSE = "El password se actualizo correctamente";
	
	public String resetPassword(RecuperarContra resetPassword) throws BusinessException {
		
		if (StringUtils.isBlank(resetPassword.getEncoded())) {
			throw new BusinessException("Parametro incompletos",401);
		}
		
		byte[] valueDecoded = Base64.decodeBase64(resetPassword.getEncoded().getBytes());
		String email = new String(valueDecoded);				
		logger.info("/****Email ::"+email);
		
		if(!resetPassword.getPassword().equals(resetPassword.getPasswordConfirma())) {		
			throw new BusinessException("Los pasword no son iguales",401);				
		}
		
		Colaborador colaborador = colaboradorRepository.findAllByMailRegistro(email);
		
		if (colaborador==null) {
			throw new BusinessException("No se encontro el colaborador con el email proporcionado",401);
		}
		
		colaborador.setPassword(resetPassword.getEmail());
		
		colaboradorRepository.save(colaborador);
		
		return EXITO_RESPONSE;
	}
}
