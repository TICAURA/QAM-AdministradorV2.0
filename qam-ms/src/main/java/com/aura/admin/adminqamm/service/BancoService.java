package com.aura.admin.adminqamm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.dto.CuentaBancoDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Banco;
import com.aura.admin.adminqamm.model.CuentaBanco;
import com.aura.admin.adminqamm.model.Persona;
import com.aura.admin.adminqamm.repository.BancoRepository;
import com.aura.admin.adminqamm.repository.CuentaBancoRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;

@Service
public class BancoService {
	
    @Autowired
    private BancoRepository bancoRepository;
    
    @Autowired
    private CuentaBancoRepository cuentaBancoRepository;

    @Autowired
    private PermissionService permissionService;
    
    /**
	 * 
	 */
	private final static Logger logger = LoggerFactory.getLogger(ColaboradorService.class);

    public List<Banco> getBancos(int loggedUser) throws BusinessException{
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),
                PermitionENUM.COLABORADOR_ADMINISTRADOR.getId(),PermitionENUM.COLABORADOR_VER.getId(),PermitionENUM.SERVICIOS_CATALOGOS_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return  bancoRepository.findAll();
    }
    
    public Banco getBanco(int loggedUser, int id) throws BusinessException{
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),
                PermitionENUM.COLABORADOR_ADMINISTRADOR.getId(),PermitionENUM.COLABORADOR_VER.getId(),PermitionENUM.SERVICIOS_CATALOGOS_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return bancoRepository.findById(id).orElseThrow(() -> new BusinessException("DISPERSOR NOT FOUND",404));

    }

	public List<CuentaBancoDto> crearCuentaBanco(CuentaBancoDto cuentaBancoDto, Boolean esCarga) throws BusinessException {
		
		if (cuentaBancoDto.getClabe()==null) {
			throw new BusinessException("La clabe es requerida para crear la cuenta", 401);
		}
		
		CuentaBanco cuentaBancoAct = cuentaBancoRepository.buscarCuentaPorId(cuentaBancoDto.getClabe());
		
		if (cuentaBancoAct!=null) {
			throw new BusinessException(" Ya existe un registro con esa clabe bancaria", 401);
		}
		
		List<CuentaBanco> listaCuentaColaborador = cuentaBancoRepository.buscarCuentaActivaColaborador(cuentaBancoDto.getPersonaDto().getId());
		
		cuentaBancoAct = new CuentaBanco();
		cargarCuentaBandoBD(cuentaBancoDto, cuentaBancoAct, Boolean.TRUE);
		
		if (esCarga) {
			Banco banco = bancoRepository.consultarBancoPorDescripcion(cuentaBancoDto.getDescripcionBanco());
			logger.info("/*** Es carga cuenta banco");
			cuentaBancoAct.setBanco(banco);
			cuentaBancoAct.setFechaAlta(cuentaBancoDto.getFechaAlta());
			
			cuentaBancoRepository.save(cuentaBancoAct);
		} else {
			cuentaBancoRepository.save(cuentaBancoAct);
			
			if (!listaCuentaColaborador.isEmpty()) {
				for (CuentaBanco cuentaBanco : listaCuentaColaborador) {
					cuentaBanco.setEsActivo(Boolean.FALSE);
					cuentaBancoRepository.save(cuentaBanco);
				}
			}	
		}
		
		return buscarCuentasColaborador(cuentaBancoDto.getPersonaDto().getId());
		
	}
	
	private void cargarCuentaBandoBD(CuentaBancoDto cuentaBancoDTO, CuentaBanco cuentaBancoAct, Boolean esCrear) throws BusinessException {
		
		if (esCrear) {
			logger.info("/*** Es crear cuenta banco");
			Persona persona = new Persona();
			persona.setPersonaId(cuentaBancoDTO.getPersonaDto().getId());
			cuentaBancoAct.setPersona(persona);
			cuentaBancoAct.setClabe(cuentaBancoDTO.getClabe());
			cuentaBancoAct.setEsActivo(Boolean.TRUE);
			if (cuentaBancoDTO.getBanco()!=null) {
				cuentaBancoAct.setBanco(bancoRepository.findById(cuentaBancoDTO.getBanco())
						.orElseThrow(() -> new BusinessException("No se encontro el banco indicado ",401)));
			}
			cuentaBancoAct.setNumeroCta(cuentaBancoDTO.getCuenta());
			cuentaBancoAct.setMondId(cuentaBancoDTO.getMondId());
			cuentaBancoAct.setFechaAlta(new Date());
			cuentaBancoAct.setDescripcion(cuentaBancoDTO.getDescripcion());
		} else {
			cuentaBancoAct.setNumeroCta(cuentaBancoDTO.getCuenta());
			cuentaBancoAct.setMondId(cuentaBancoDTO.getMondId());
			cuentaBancoAct.setDescripcion(cuentaBancoDTO.getDescripcion());
		}
				
		cuentaBancoAct.setDescripcion(cuentaBancoDTO.getDescripcion());				
		
	}
	
	private List<CuentaBancoDto> buscarCuentasColaborador(Integer idColaborador) {
		List<CuentaBanco> cuentasActual = cuentaBancoRepository.buscarCuentaColaborador(idColaborador);			
		List<CuentaBancoDto> cuentasDTO = new ArrayList<>();
		
		for (CuentaBanco bancoBD : cuentasActual) {
			CuentaBancoDto cuentaItemDTO = new CuentaBancoDto();
			
			cuentaItemDTO.setClabe(bancoBD.getClabe());
			cuentaItemDTO.setBanco(bancoBD.getBanco().getId());
			cuentaItemDTO.setDescripcionBanco(bancoBD.getBanco().getDescripcion());
			cuentaItemDTO.setDescripcion(bancoBD.getDescripcion());
			cuentaItemDTO.setCuenta(bancoBD.getNumeroCta());
			cuentaItemDTO.setMondId(bancoBD.getMondId());
			cuentaItemDTO.setEsActivo(bancoBD.getEsActivo());
		
			cuentasDTO.add(cuentaItemDTO);
		}
		
		return cuentasDTO;
	}

}
