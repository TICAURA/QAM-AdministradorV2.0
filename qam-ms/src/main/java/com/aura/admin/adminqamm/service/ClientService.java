package com.aura.admin.adminqamm.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aura.admin.adminqamm.dto.ClienteDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Cliente;
import com.aura.admin.adminqamm.model.Dispersor;
import com.aura.admin.adminqamm.model.Facturador;
import com.aura.admin.adminqamm.model.Interfaz;
import com.aura.admin.adminqamm.model.Servicio;
import com.aura.admin.adminqamm.repository.ClientRepository;
import com.aura.admin.adminqamm.repository.DispersorRepository;
import com.aura.admin.adminqamm.repository.FacturadorRepository;
import com.aura.admin.adminqamm.repository.InterfaceRepository;
import com.aura.admin.adminqamm.repository.ServiceRepository;
import com.aura.admin.adminqamm.util.PermitionENUM;

@Service
public class ClientService {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private InterfaceRepository interfaceRepository;

    @Autowired
    private DispersorRepository dispersorRepository;

    @Autowired
    private FacturadorRepository facturadorRepository;

    Logger logger = LoggerFactory.getLogger(ClientService.class);
    
    public List<ClienteDto> getClients(int loggedUser) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        List<Cliente> clientes = clientRepository.findAll();
        List<ClienteDto> clienteDtos = new ArrayList<>();
        for (Cliente cliente: clientes) {
            ClienteDto clienteDto = new ClienteDto();
            clienteDto.setIdClient(cliente.getClientId());
            clienteDto.setCurp(cliente.getCurp());
            clienteDto.setRfc(cliente.getRfc());
            clienteDto.setRazon(cliente.getRazon());
            clienteDto.setDispersorId(cliente.getDispersor().getId());
            clienteDto.setFacturadorId(cliente.getFacturador().getId());
            clienteDto.setDispersorName(cliente.getDispersor().getNombre());
            clienteDto.setFacturadorName(cliente.getFacturador().getNombre());
            clienteDto.setEmailSender(cliente.getEmailSender());
            clienteDto.setPeriodoFactura(cliente.getPeriodoFactura());
            clienteDto.setMensajeEmailFactura(cliente.getMensajeEmailFactura());
            clienteDto.setMensajeEmailRecuperarContra(cliente.getMensajeEmailRecuperarContra());
            if(cliente.getInterfaz()!=null){
            clienteDto.setInterfazId(cliente.getInterfaz().getIdInterfaz());
            clienteDto.setInterfazName(cliente.getInterfaz().getNombre());
            }
            if(cliente.getServicio()!=null){
            clienteDto.setServicioId(cliente.getServicio().getIdServicio());
            clienteDto.setServicioName(cliente.getServicio().getNombre());
            }
            clienteDtos.add(clienteDto);
        }
        return clienteDtos;

    }

    public ClienteDto getClient(int loggedUser,int clientId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        Cliente cliente = clientRepository.findById(clientId).orElseThrow(() -> new BusinessException("CLIENT NOT FOUND",404));
        ClienteDto clienteDto = new ClienteDto();
        clienteDto.setIdClient(cliente.getClientId());
        clienteDto.setCurp(cliente.getCurp());
        clienteDto.setRfc(cliente.getRfc());
        clienteDto.setRazon(cliente.getRazon());
        clienteDto.setDispersorId(cliente.getDispersor().getId());
        clienteDto.setFacturadorId(cliente.getFacturador().getId());
        clienteDto.setDispersorName(cliente.getDispersor().getNombre());
        clienteDto.setFacturadorName(cliente.getFacturador().getNombre());
        clienteDto.setEmailSender(cliente.getEmailSender());
        clienteDto.setPeriodoFactura(cliente.getPeriodoFactura());
        clienteDto.setMensajeEmailFactura(cliente.getMensajeEmailFactura());
        clienteDto.setMensajeEmailRecuperarContra(cliente.getMensajeEmailRecuperarContra());
        if(cliente.getInterfaz()!=null){
        clienteDto.setInterfazId(cliente.getInterfaz().getIdInterfaz());
        clienteDto.setInterfazName(cliente.getInterfaz().getNombre());
        }
        if(cliente.getServicio()!=null) {
            clienteDto.setServicioId(cliente.getServicio().getIdServicio());
            clienteDto.setServicioName(cliente.getServicio().getNombre());
        }
        return clienteDto;
    }

    public void modifyClient(int loggedUser, ClienteDto clienteDto) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }


        Cliente client = clientRepository.findById(clienteDto.getIdClient()).orElseThrow(() -> new BusinessException("CLIENT NOT FOUND",404));

        Dispersor dispersor = dispersorRepository.findById(clienteDto.getDispersorId()).orElseThrow(() -> new BusinessException("DISPERSOR NOT FOUND",404));
        Facturador facturador = facturadorRepository.findById(clienteDto.getFacturadorId()).orElseThrow(() -> new BusinessException("FACTURADOR NOT FOUND",404));


        client.setDispersor(dispersor);
        client.setFacturador(facturador);
        client.setPeriodoFactura(clienteDto.getFacturadorId());
        client.setEmailSender(clienteDto.getEmailSender());
        client.setMensajeEmailFactura(clienteDto.getMensajeEmailFactura());
        client.setMensajeEmailRecuperarContra(clienteDto.getMensajeEmailRecuperarContra());

        Servicio servicio = null;
        Interfaz interfaz = null;

        if(clienteDto.getServicioId()!=null && clienteDto.getServicioId() != -1) {
             servicio = serviceRepository.findById(clienteDto.getServicioId()).orElseThrow(() -> new BusinessException("SERVICE NOT FOUND", 404));
        }
        if(clienteDto.getInterfazId()!=null && clienteDto.getInterfazId() != -1) {
             interfaz = interfaceRepository.findById(clienteDto.getInterfazId()).orElseThrow(() -> new BusinessException("INTERFACE NOT FOUND", 404));
        }

        client.setServicio(servicio);
        client.setInterfaz(interfaz);

        validateCliente(client);

        clientRepository.save(client);

    }

    private void validateCliente(Cliente cliente)throws BusinessException{
        int periodoFactura = cliente.getPeriodoFactura();

        if(periodoFactura<0 || periodoFactura>31){
            throw new BusinessException("Periodo Factura invalido, porfavor ingrese un periodo factura valido.",406);
        }
    }
    
    public List<ClienteDto> getCentroCostoAll(int loggedUser) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        List<Cliente> clientes = clientRepository.getCentroCostoAll();
        List<ClienteDto> clienteDtos = new ArrayList<>();
        for (Cliente cliente: clientes) {
            ClienteDto clienteDto = new ClienteDto();
            clienteDto.setIdClient(cliente.getClientId());
            clienteDto.setRazon(cliente.getRazon());
            clienteDto.setRegistroPatronal(cliente.getRegistroPatronal());
            
            clienteDtos.add(clienteDto);
        }
        return clienteDtos;

    }

	public List<ClienteDto> getSubcontrato(int loggedIdUser)throws BusinessException {
		
		if(!permissionService.validatePermition(loggedIdUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
		
		List<Cliente> clientes = clientRepository.findByEsSubcontractorOrderByRazonAsc(Boolean.TRUE);
		
		List<ClienteDto> listClienteDto = new ArrayList<ClienteDto>();
		
		for (Cliente cliente : clientes) {
			if (cliente.getEsPersonaFisica()) {
				ClienteDto itemDTO = new ClienteDto();
				StringBuilder stringStr = new StringBuilder();
				stringStr.append(StringUtils.isNotBlank(cliente.getNombre())?cliente.getNombre():StringUtils.EMPTY)
						.append(" ")
						.append(StringUtils.isNotBlank(cliente.getApellidoPat())?cliente.getApellidoPat():StringUtils.EMPTY)
						.append(" ")
						.append(StringUtils.isNotBlank(cliente.getApellidoMat())?cliente.getApellidoMat():StringUtils.EMPTY);
				
				itemDTO.setRazon(stringStr.toString());
				itemDTO.setIdClient(cliente.getClientId());
				itemDTO.setSubRazonSocial(cliente.getSubRazonSocial());
				
				listClienteDto.add(itemDTO);
			}
		}
	
		return listClienteDto;
	}
	
	public ClienteDto buscarClienteCarga(String rfc, String registroPatronal, String subcRfc) throws BusinessException {
		Cliente clienteBD = null;

		if (StringUtils.isNotBlank(subcRfc)) {
			clienteBD = clientRepository.findByRfcAndRegistroPatronalAndSubRFC(rfc, registroPatronal, subcRfc);
		} else {
			clienteBD = clientRepository.getByRfcAndRegistroPatronal(subcRfc, registroPatronal);	
		}
		
		if (clienteBD==null) {
			throw new BusinessException("No hay cliente para asociar al colaborador", 401);
		}

		ClienteDto clienteDTO = new ClienteDto();

		clienteDTO.setIdClient(clienteBD.getClientId());
		clienteDTO.setRazon(clienteBD.getRazon());
		
		return clienteDTO;
	}

}
