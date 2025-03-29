package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IUsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final Logger LOGGGER = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/registro")
    public String registroUsuario() {
        return "usuario/registro";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(Model model ,Usuario usuario) {
        LOGGGER.info("Usuario guardado: " + usuario);
        usuario.setTipo("USER");
        usuarioService.guardarUsuario(usuario);
        return "redirect:/";
    }

}
