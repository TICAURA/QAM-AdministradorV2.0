package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.CargaMasivaDto;
import com.aura.admin.adminqamm.dto.ClienteDto;
import com.aura.admin.adminqamm.dto.ColaboradorDto;
import com.aura.admin.adminqamm.dto.PersonaDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Colaborador;
import com.aura.admin.adminqamm.model.ColaboradorId;
import com.aura.admin.adminqamm.repository.ColaboradorRepository;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ColaboradorService {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private MedioContactoService medioContactoService;
	
	@Autowired
	private BancoService bancoService;
	
	@Autowired
	private ColaboradorRepository colaboradorRepository;
	
	/**
	 * 
	 */
	private final static Logger logger = LoggerFactory.getLogger(ColaboradorService.class);

    public List<ColaboradorDto> getColaboradores(int loggedUser) throws BusinessException {
        return new ArrayList<ColaboradorDto>();
    }

    public ColaboradorDto getColaborador(int loggedUser,int colaboradorId) throws BusinessException{
    return new ColaboradorDto();
    }

    public int postColaborador(int loggedUser, ColaboradorDto colaboradorDto) throws BusinessException{
        return 1;
    }

    public void putColaborador(int loggedUser,ColaboradorDto colaboradorDto) throws BusinessException{

    }
    public void deleteColaborador(int loggedUser,int colaboradorId) throws BusinessException{

    }
    
    public List<CargaMasivaDto> cargaMasiva(List<CargaMasivaDto> infoArchivo) {

        for (CargaMasivaDto itemArchivo : infoArchivo) {
        	logger.info("*************************************");
        	logger.info("/**** FILA :: " + itemArchivo.getFila() + " :PROCESAR: " + itemArchivo.getProcesar() + " ****/");

            try {

            	validaRequeridos(itemArchivo);
            	
                if (itemArchivo.getProcesar()) {
                    ClienteDto clienteDTO = clientService.buscarClienteCarga(
                            itemArchivo.getColaboradorDTO().getClienteDto().getRfc(),
                            itemArchivo.getColaboradorDTO().getClienteDto().getRegistroPatronal(),
                    		itemArchivo.getColaboradorDTO().getClienteDto().getSubRfc());
                    		
                    itemArchivo.getColaboradorDTO().setClienteDto(clienteDTO);

                    Colaborador colaborador = buscarColaboradorPorClave(itemArchivo.getColaboradorDTO().getClaveColaborador());

                    if (colaborador == null) {
                        PersonaDto personaDTO = personaService.crearPersona(itemArchivo.getColaboradorDTO().getPersonaDto(),
                                Boolean.FALSE);
                        ColaboradorId colaboradorId = cargarColaboradorId(itemArchivo.getColaboradorDTO(), personaDTO);

                        Colaborador colaboradorBD = new Colaborador();
                        colaboradorBD.setColaboradorId(colaboradorId);
                        cargarColaboradorBD(itemArchivo.getColaboradorDTO(), colaboradorBD, Boolean.TRUE);

                        medioContactoService.creaContacto(itemArchivo.getColaboradorDTO().getDatosContactoDto(), personaDTO.getId());

                        itemArchivo.getColaboradorDTO().getCuentaBancoDto().setPersonaDto(personaDTO);
                        bancoService.crearCuentaBanco(itemArchivo.getColaboradorDTO().getCuentaBancoDto(), Boolean.TRUE);
                        colaboradorRepository.save(colaboradorBD);
                    }
                } else { 
                	for (String error : itemArchivo.getErrores()) {
                		logger.info("***** ERROR  : " + error);
                	}
                }
            } catch (BusinessException e) {
                itemArchivo.setProcesar(Boolean.FALSE);
                itemArchivo.getErrores().add("ERROR No se logró cargar colaborador :: " + e.getMessage());
                logger.error("ERROR No se logró cargar colaborador ****/" + e.getMessage());
            }

            logger.info("/**** Finaliza carga colaborador  ****/");
            logger.info("*************************************");

        }

        return infoArchivo;
    }
    
    private void validaRequeridos(CargaMasivaDto itemArchivo) throws BusinessException {
		if (StringUtils.isBlank(itemArchivo.getColaboradorDTO().getCuentaBancoDto().getCuenta())) {
			throw new BusinessException("No de Cuenta Bancaria, valor requerido ",  401);
		}
	}
    
    private Colaborador buscarColaboradorPorClave(Long claveColaborador) throws BusinessException {
        Colaborador colaborador = colaboradorRepository.findByClaveColaborador(claveColaborador);

        if (colaborador != null) {
        	throw new BusinessException("Ya existe un colaborador con la clave", 401);
        }

        return colaborador;
    }
    
    private ColaboradorId cargarColaboradorId(ColaboradorDto colaboradorDTO, PersonaDto personaDTO) {
        ColaboradorId colaboradorId = new ColaboradorId();
        colaboradorId.setPersonaId(personaDTO.getId());
        colaboradorId.setFechaIngreso(colaboradorDTO.getFechaIngreso());
        colaboradorId.setClienteId(colaboradorDTO.getClienteDto().getIdClient());

        return colaboradorId;
    }
    
    private Colaborador cargarColaboradorBD(ColaboradorDto colaboradorDTO, Colaborador colaboradorBD, Boolean esCrear) {
        colaboradorBD.setClaveColaborador(colaboradorDTO.getClaveColaborador());
        colaboradorBD.setSalarioDiarioReal(colaboradorDTO.getSalarioDiarioReal());
        colaboradorBD.setSalarioNetoPeriodo(colaboradorDTO.getSalarioNetoPeriodo());
        colaboradorBD.setSalarioDiarioIntegrado(colaboradorDTO.getSalarioDiarioIntegrado());
        colaboradorBD.setDistribucionImssComplemento(colaboradorDTO.getDistribucionImssComplemento());
        colaboradorBD.setPeriodicidad(colaboradorDTO.getPeriodicidad());
        colaboradorBD.setFchInicio(new Date());
        colaboradorBD.setFchFin(colaboradorDTO.getFchFin());
        colaboradorBD.setFchPago(colaboradorDTO.getFchPago());
        colaboradorBD.setFchCorteIncidencias(colaboradorDTO.getFchCorteIncidencias());
        colaboradorBD.setMailRegistro(colaboradorDTO.getPersonaDto().getEmailCorporativo());
        colaboradorBD.setSoyGuest(Boolean.FALSE);

        if (esCrear) {
        	colaboradorBD.setEsActivo(Boolean.TRUE);
        } else {
        	colaboradorBD.setEsActivo(colaboradorDTO.isActivo());
        }

        return colaboradorBD;
    }
}
