package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class InicioController {

    @Autowired
    private ProductoService productoService;
    @GetMapping("")
    public String inicio(Model model) {
        List<Producto> listaProductos=productoService.getProductos();
        model.addAttribute("listaProductos", listaProductos);
        return "usuario/home";
    }
}
