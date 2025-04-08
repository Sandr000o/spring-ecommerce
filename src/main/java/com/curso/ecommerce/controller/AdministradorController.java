package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IOrdenService;
import com.curso.ecommerce.service.IProductoService;
import com.curso.ecommerce.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private IProductoService IProductoService;

    @Autowired
    private IUsuarioService IUsuarioService;

    @Autowired
    private IOrdenService IOrdenService;

    @GetMapping("")
    public String home(Model model) {
        List<Producto> listaProductos = IProductoService.getProductos();
        model.addAttribute("listaProductos", listaProductos);
        return "administrador/home";

    }

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        List<Usuario> listaUsuarios = IUsuarioService.findAllUsuarios();
        model.addAttribute("listaUsuarios", listaUsuarios);
        return "administrador/usuarios";
    }

    @GetMapping("/ordenes")
    public String ordenes(Model model) {
        List<Orden> ordenes = IOrdenService.obtenerOrdenes();
        model.addAttribute("listaOrdenes", ordenes);
        return "administrador/ordenes";
    }

    @GetMapping("/detalleOrden/{id}")
    public String detalleOrden(Model model, @PathVariable Integer id) {
        Orden orden=IOrdenService.findOrdenById(id).get();
        model.addAttribute("detalleOrden", orden.getDetalleOrden());
        return "administrador/detalleOrden";
    }
}
