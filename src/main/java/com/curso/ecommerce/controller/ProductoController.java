package com.curso.ecommerce.controller;


import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private ProductoService productoService;

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("listaProductos", productoService.getProductos());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/guardar")
    public String guardarProducto(Producto producto) {
        LOGGER.info("Producto guardado" + producto);
        Usuario u = new Usuario(1, "", "", "", "", "", "", "");
        producto.setUsuario(u);
        producto.setUsuario(u);
        productoService.saveProducto(producto);

        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = productoService.getProductoById(id);
        producto = optionalProducto.get();
        model.addAttribute("producto", producto);

        LOGGER.info("Producto buscado" + producto);
        return "productos/edit";
    }

    @PostMapping("/actualizar")
    public String actualizarProducto(Producto producto) {
        productoService.updateProducto(producto);
        return "redirect:/productos";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        productoService.deleteProducto(id);
        return "redirect:/productos";
    }

}
