package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.Relation;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Cliente;
import com.aura.admin.adminqamm.model.Usuario;
import com.aura.admin.adminqamm.model.UsuarioCliente;
import com.aura.admin.adminqamm.model.UsuarioClienteKey;
import com.aura.admin.adminqamm.repository.ClientRepository;
import com.aura.admin.adminqamm.repository.UserRepository;
import com.aura.admin.adminqamm.repository.UsuarioClienteRepositorio;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserClientService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UsuarioClienteRepositorio usuarioClienteRepositorio;

    @Autowired
    private ClientRepository clientRepository;

    public List<Relation> viewUserClientLinks(int loggedUser, int requestUserId) throws BusinessException{
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        Usuario usuario = userRepository.findById(requestUserId).orElseThrow(() -> new BusinessException("USER NOT FOUND",404));
        Set<UsuarioCliente> usuarioClienteSet = usuario.getUsuarioClientes();
        List<Relation> relations = new ArrayList<>();
        for (UsuarioCliente usuarioCliente: usuarioClienteSet) {
            Relation rel = new Relation();
            rel.setFather(usuarioCliente.getId().getIdUsuario());
            rel.setChild(usuarioCliente.getId().getIdCliente());
            relations.add(rel);
        }
        return relations;
    }

    public void createUserClientLink(int loggedUser, Relation relation) throws BusinessException{
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        UsuarioCliente usuarioCliente = new UsuarioCliente();
        usuarioCliente.setId(new UsuarioClienteKey(relation.getFather(),relation.getChild()));
        usuarioCliente.setRegistro(new Date());
        Usuario usuario = userRepository.findById(relation.getFather()).orElseThrow(()-> new BusinessException("USER NOT FOUND",404));
        Cliente client = clientRepository.findById(relation.getChild()).orElseThrow(() -> new BusinessException("CLIENT NOT FOUND",404));
        usuarioCliente.setCliente(client);
        usuarioCliente.setUsuario(usuario);
        usuarioClienteRepositorio.save(usuarioCliente);
    }

    public void deleteUserClientLink(int loggedUser, Relation relation) throws BusinessException{
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CLIENTE_ADMINISTRADOR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        usuarioClienteRepositorio.deleteById(new UsuarioClienteKey(relation.getFather(),relation.getChild()));

    }

}
