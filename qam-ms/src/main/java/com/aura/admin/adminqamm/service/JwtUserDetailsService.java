package com.aura.admin.adminqamm.service;

import com.aura.admin.adminqamm.model.Usuario;
import com.aura.admin.adminqamm.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);



    @Autowired
    private UserRepository userRepository;

    //private PasswordD

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username != null){

            Usuario usuario = userRepository.findByUser(username);

            if(usuario == null){
                logger.info("User not found with username: " + username);
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            return new User(username, usuario.getPassword(),
                    new ArrayList<>());

        } else {
            logger.info("User not found with username: null");
            throw new UsernameNotFoundException("User not found with username: null");
        }
    }

}
