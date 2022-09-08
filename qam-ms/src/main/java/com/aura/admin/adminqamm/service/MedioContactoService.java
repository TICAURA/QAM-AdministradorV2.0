/**
 * 
 */
package com.aura.admin.adminqamm.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.dto.DatosContactoDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.MedioContacto;
import com.aura.admin.adminqamm.model.MedioContactoId;
import com.aura.admin.adminqamm.model.TipoContacto;
import com.aura.admin.adminqamm.repository.MedioContactoRepository;
import com.aura.admin.adminqamm.repository.TipoContactoRepository;

/**
 * @author Cesar Agustin Soto
 *
 */
@Service
public class MedioContactoService {
	
	@Autowired
	private MedioContactoRepository medioContactoRepository;
	
	@Autowired
	private TipoContactoRepository tipoContactoRepository;
	
	Logger logger = LoggerFactory.getLogger(CargaMexicoService.class);
	
	/**
	 * 
	 * @param listContactoDTOs
	 * @param idPersona
	 * @throws QamException
	 */
	public void  creaContacto(List<DatosContactoDto> listContactoDTOs, Integer idPersona) throws BusinessException {
		
		for (DatosContactoDto doContactoDTO : listContactoDTOs) {
			
			MedioContacto validaCntacto = medioContactoRepository.buscarContactoPorId(idPersona, doContactoDTO.getNumClave());
			
			if (validaCntacto!=null) {
				logger.error("Contacto duplicado");
				throw new BusinessException("El contacto con valor "+doContactoDTO.getNumClave().toString()+" esta duplicado", 401);
			}
			
			MedioContacto contactoBD = cargarContactoBD(doContactoDTO, idPersona);
			
			if (doContactoDTO.getIdTctk().intValue()==1 && doContactoDTO.getEsCelularEnrolamiento()) {	
				List<MedioContacto> contactosColaborador = medioContactoRepository.buscarContactoCelularColaborador(idPersona);
				
				for (MedioContacto medioContacto : contactosColaborador) {
					if (medioContacto.getEsCelularEnrolamiento()) {
						medioContacto.setEsCelularEnrolamiento(Boolean.FALSE);
						medioContactoRepository.save(medioContacto);
					}
				}
			}
			
			medioContactoRepository.save(contactoBD);
		}
	}
	
	private MedioContacto cargarContactoBD(DatosContactoDto contacto, Integer idPersona) throws BusinessException {
		MedioContacto contactoBD = new MedioContacto();
		
		MedioContactoId medioContactoId = new MedioContactoId();
		medioContactoId.setPersId(idPersona);
		medioContactoId.setNumClave(contacto.getNumClave());
		
		TipoContacto tipoContacto = tipoContactoRepository.findById(contacto.getIdTctk())
				.orElseThrow(() -> new BusinessException("No se encontro tipo contacto indicado ",401));
		
		contactoBD.setMediContactoId(medioContactoId);
				
		if (contacto.getEsPrincipal()==null) {
			contactoBD.setEsPrincipal(Boolean.FALSE);
		}else {
			contactoBD.setEsPrincipal(contacto.getEsPrincipal());	
		}
		
		if (contacto.getEsCelularEnrolamiento()==null) {
			contactoBD.setEsCelularEnrolamiento(Boolean.FALSE);
		}else {
			contactoBD.setEsCelularEnrolamiento(contacto.getEsCelularEnrolamiento());	
		}
		
		contactoBD.setFchAlta(new Date());
		contactoBD.setIdTctk(tipoContacto);		
		
		contactoBD.setEsActivo(Boolean.TRUE);	
		
		return contactoBD;
	}

}
