package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCrypt;

    @Autowired
    HttpSession sesion;

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioService.findUserByEmail(username);
        if (usuarioOptional.isPresent()) {
            LOGGER.info("Id del usuario: " + usuarioOptional.get().getId());
            sesion.setAttribute("idUsuario", usuarioOptional.get().getId());
            Usuario usuario = usuarioOptional.get();
            return User.builder()
                    .username(usuario.getUsername())
                    .password(bCrypt.encode(usuario.getPassword()))
                    .roles(usuario.getTipo()).build();
        }else{
            throw  new UsernameNotFoundException("Usuario no encontrado");
        }
    }


}
