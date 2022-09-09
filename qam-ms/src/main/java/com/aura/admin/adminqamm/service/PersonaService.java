/**
 * 
 */
package com.aura.admin.adminqamm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.dto.PersonaDto;
import com.aura.admin.adminqamm.model.Persona;
import com.aura.admin.adminqamm.repository.PersonaRepository;

/**
 * @author Cesar Agustin Soto
 *
 */
@Service
public class PersonaService {
	
	@Autowired
	private PersonaRepository personaRepository;
	
	public PersonaDto crearPersona(PersonaDto personaDTO, Boolean esRepesentanteLegal) {
		
		Persona personaBD = new Persona();		
		cargarPersonaBD(personaDTO, personaBD);
		
		if (esRepesentanteLegal) {
			personaBD.setRepresentanteLegal(Boolean.TRUE);
		}
		
		personaRepository.save(personaBD);		
		personaDTO.setId(personaBD.getPersonaId());
		
		return personaDTO;
	}
	
	private void cargarPersonaBD(PersonaDto personaDTO, Persona personaBD) {
		
		personaBD.setNombre(personaDTO.getNombre());
		personaBD.setRfc(personaDTO.getRfc());
		personaBD.setCurp(personaDTO.getCurp());
		personaBD.setApellidoPat(personaDTO.getApellidoPat());
		personaBD.setApellidoMat(personaDTO.getApellidoMat());
		personaBD.setNss(personaDTO.getNss());
		personaBD.setFechaNacimiento(personaDTO.getFechaDeNacimiento());
		personaBD.setGenero(personaDTO.getGenero());		
		personaBD.setEmailCorporativo(personaDTO.getEmailCorporativo());
		personaBD.setFechaAltaQuincena(personaDTO.getFchAltaQuincenam());
				
	}

}
