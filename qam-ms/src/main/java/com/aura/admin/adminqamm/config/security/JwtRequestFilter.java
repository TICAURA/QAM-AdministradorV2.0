package com.aura.admin.adminqamm.config.security;

import com.aura.admin.adminqamm.model.Usuario;
import com.aura.admin.adminqamm.repository.UserRepository;
import com.aura.admin.adminqamm.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        logger.info("[JWT Request Filter] doFilterInternal ...");
        //logger.info("[JWT Request Filter] request:" + request.getHeaderNames().toString());

        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        //if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
        if (requestTokenHeader != null) {
            logger.info("[JWT Request Filter] JWT Token is not null");
            //logger.info("[JWT Request Filter] requestTokenHeader:" + requestTokenHeader);
            //jwtToken = requestTokenHeader.substring(7);
            jwtToken = requestTokenHeader;
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("[JWT Request Filter] Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.error("[JWT Request Filter] JWT Token has expired");
            }
        } else {
            logger.warn("[JWT Request Filter] JWT Token is null");
            logger.info("[JWT Request Filter] JWT Token is null");
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.info("[JWT Request Filter] Get user Details");

            Usuario usuario = this.userRepository.findByUser(username);
            request.setAttribute("username",usuario.getUserId());
            usuario.setUltimaActividad(new Date());
            if(usuario == null){
                logger.info("User not found with username: " + username);
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            this.userRepository.save(usuario);

            UserDetails userDetails = new User(username, usuario.getPassword(),
                    new ArrayList<>());
            // if token is valid configure Spring Security to manually set
            // authentication
            logger.info("[JWT Request Filter] Validate Token");
            if (jwtTokenUtil.validateToken(jwtToken, usuario)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        logger.info("[JWT Request Filter] doFilterInternal.");
        chain.doFilter(request, response);
    }
}
