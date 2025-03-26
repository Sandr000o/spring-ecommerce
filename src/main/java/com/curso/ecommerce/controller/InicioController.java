package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class InicioController {

    private Logger LOGGER = LoggerFactory.getLogger(InicioController.class);

    @Autowired
    private ProductoService productoService;
    @GetMapping("")
    public String inicio(Model model) {
        List<Producto> listaProductos=productoService.getProductos();
        model.addAttribute("listaProductos", listaProductos);
        return "usuario/home";
    }

    @GetMapping("productohome/{id}")
    public String verProducto(@PathVariable Integer id, Model model) {
        LOGGER.info("Id del producto: " + id);
        Producto producto= productoService.getProductoById(id).orElse(null);
        model.addAttribute("producto", producto);
        LOGGER.info("Producto: " + producto);
        return "usuario/productohome";
    }
}
