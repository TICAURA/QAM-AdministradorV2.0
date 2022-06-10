package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.dto.LoginDto;
import com.aura.admin.adminqamm.dto.request.UsuarioRequestDto;
import com.aura.admin.adminqamm.dto.response.UsuarioResponseDto;
import com.aura.admin.adminqamm.exception.BusinessException;
import com.aura.admin.adminqamm.model.Usuario;
import com.aura.admin.adminqamm.repository.UserRepository;
import com.aura.admin.adminqamm.util.JwtTokenUtil;
import com.aura.admin.adminqamm.util.PermitionENUM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public List<UsuarioResponseDto> getUsers(int loggedUser) throws BusinessException {

        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }

        List<Usuario> usuarios = userRepository.findAll();
        List<UsuarioResponseDto> users = new ArrayList<>();
        for (Usuario user: usuarios) {
            users.add(new UsuarioResponseDto().build(user));
        }

        return users;
    }

    public UsuarioResponseDto getUser(int loggedUser, int userId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_VER.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        return new UsuarioResponseDto().build(userRepository.findById(userId).orElseThrow(() -> new BusinessException("USER NOT FOUND",404)));
    }

    public int createUser(int loggedUser,Usuario user) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_CREAR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        user.setPassword(bcryptEncoder.encode(user.getPassword()));

        user.setFechaModificacion(new Date());
        user.setFechaRegistro(new Date());

        validateUser(user);

        return userRepository.saveAndFlush(user).getUserId();

    }

    public void modificarUser(int loggedUser, UsuarioRequestDto user) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_MODIFICAR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }



        Usuario usuarioOriginal = userRepository.findById(user.getUserId()).orElseThrow(() -> new BusinessException("USER NOT FOUND",404));
        validateUser(user,usuarioOriginal.getUser());
        if(user.getPassword()!=null && !user.getPassword().isEmpty()){usuarioOriginal.setPassword(bcryptEncoder.encode(user.getPassword()));}

        usuarioOriginal.setName(user.getName());
        usuarioOriginal.setUser(user.getUser());
        usuarioOriginal.setActivo(user.isActivo());
        usuarioOriginal.setFechaModificacion(new Date());

        userRepository.save(usuarioOriginal);

        return;
    }
    public void borrarUser(int loggedUser,int userId) throws BusinessException {
        if(!permissionService.validatePermition(loggedUser, PermitionENUM.SUDO.getId(),PermitionENUM.USUARIOS_ADMINISTRADOR.getId(),PermitionENUM.USUARIOS_BORRAR.getId())){
            throw new BusinessException("El usuario no tiene acceso a esta información.",401);
        }
        if(loggedUser!=userId){ userRepository.deleteById(userId);}
        else{throw new BusinessException("El usuario en operación es el mismo que el usuario a borrar.",401);}


    }

    public String login(LoginDto login) throws BusinessException{
        Usuario usuario = userRepository.findByUser(login.getUser());
        boolean matches = bcryptEncoder.matches(login.getPassword(),usuario.getPassword());
        if(matches){
            String token = jwtTokenUtil.generateToken(usuario.getUser());
            usuario.setUltimoLogin( new Date());
            userRepository.save(usuario);
            return token;
        }else{
            throw new BusinessException("Error contraseña o usuario incorrecto.",401);
        }

    }

    public boolean existName(String name){
        return userRepository.findByUser(name)!=null;
    }

    private void validateUser(Usuario usuario)throws BusinessException{

        if(existName(usuario.getUser())){
            throw new BusinessException("Usuario invalido, porfavor ingrese otro nombre de usuario.",406);
        }
        if(usuario.getUser() == null||usuario.getUser().isEmpty()  ){
            throw new BusinessException("Usuario invalido, porfavor ingrese un usuario valido.",406);
        }
        if(usuario.getPassword() == null||usuario.getPassword().isEmpty() ){
            throw new BusinessException("Contraseña invalida, porfavor ingrese un contraseña valida.",406);
        }

    }

    private void validateUser(UsuarioRequestDto usuario,String originalName)throws BusinessException{

        if(!usuario.getUser().equals(originalName)){
            if(existName(usuario.getUser())){
                throw new BusinessException("Usuario invalido, porfavor ingrese otro nombre de usuario.",406);
            }
        }
        if(usuario.getUser() == null||usuario.getUser().isEmpty()  ){
            throw new BusinessException("Usuario invalido, porfavor ingrese un usuario valido.",406);
        }

    }

}
