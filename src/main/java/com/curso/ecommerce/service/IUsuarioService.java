package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface IUsuarioService {

    Optional<Usuario> findUserById(Integer id);

    Usuario guardarUsuario(Usuario usuario);

    Optional<Usuario> findUserByEmail(String email);

    List<Usuario> findAllUsuarios();

}
