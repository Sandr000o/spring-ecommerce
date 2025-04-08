package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.repository.IOrdenRepository;
import com.curso.ecommerce.service.IDetalleOrdenService;
import com.curso.ecommerce.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    private final Logger LOGGGER = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IOrdenRepository ordenRepository;

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

    @GetMapping("/login")
    public String loginUsuario() {
        return "usuario/login";
    }

    @PostMapping("/acceder")
    public String acceder(Usuario usuario, HttpSession session) {
        LOGGGER.info("Usuario accedido: " + usuario);
        Optional<Usuario> usuarioBD=usuarioService.findUserByEmail(usuario.getEmail());
        LOGGGER.info("Usuario encontrado: " + usuarioBD.get());
        if(usuarioBD.isPresent()) {
            session.setAttribute("idUsuario", usuarioBD.get().getId());
            if(usuarioBD.get().getTipo().equals("ADMIN")) {
                return "redirect:/administrador";
            }else{
                return "redirect:/";
            }
        }else{
            LOGGGER.info("Usuario no encontrado");
        }
        return "redirect:/";
    }

    @GetMapping("/historialCompras")
    public String historialCompras(Model model, HttpSession session) {
        LOGGGER.info("Historial de compras");
        model.addAttribute("idUsuario",session.getAttribute("idUsuario"));
        Usuario usuario=usuarioService.findUserById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
        List<Orden> listaOrdenes = ordenRepository.findByUsuario(usuario);
        model.addAttribute("listaOrdenes", listaOrdenes);
        return "usuario/compras";
    }

    @GetMapping("/detalleCompra/{id}")
    public String detalleCompras(@PathVariable Integer id , HttpSession session, Model model) {
        LOGGGER.info("ID del detalle compra: " + id);
        Optional<Orden> ordenOptional = ordenRepository.findById(id);
        LOGGGER.info("Detalles : "+ordenOptional.get().getDetalleOrden());
        model.addAttribute("detalles", ordenOptional.get().getDetalleOrden());
        //Sesion
        model.addAttribute("idUsuario",session.getAttribute("idUsuario"));
        return "usuario/detalleCompra";
    }
    @GetMapping("/cerrar")
    public String cerrar(HttpSession session) {
        LOGGGER.info("Cerrando usuario");
        session.removeAttribute("idUsuario");
        return "redirect:/";

    }

}
