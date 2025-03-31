package com.curso.ecommerce.controller;


import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.CargarImagenService;
import com.curso.ecommerce.service.IProductoService;
import com.curso.ecommerce.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);

    @Autowired
    private IProductoService IProductoService;

    @Autowired
    private IUsuarioService IUsuarioService;
    @Autowired
    private CargarImagenService cargarImagenService;

    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("listaProductos", IProductoService.getProductos());
        return "productos/show";
    }

    @GetMapping("/create")
    public String create() {
        return "productos/create";
    }

    @PostMapping("/guardar")
    public String guardarProducto(Producto producto, @RequestParam("img") MultipartFile file , HttpSession session) throws IOException {
        LOGGER.info("Producto guardado" + producto);
        Usuario u = IUsuarioService.findUserById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
        producto.setUsuario(u);

        //Carga de imagen
        //Hacemos la validacion cuando se crea un producto
        if (producto.getId() == null) {
            String nombreImagen = cargarImagenService.cargarImagen(file);
            producto.setImagen(nombreImagen);
        }

        IProductoService.saveProducto(producto);

        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Producto producto = new Producto();
        Optional<Producto> optionalProducto = IProductoService.getProductoById(id);
        producto = optionalProducto.get();
        model.addAttribute("producto", producto);

        LOGGER.info("Producto buscado" + producto);
        return "productos/edit";
    }

    @PostMapping("/actualizar")
    public String actualizarProducto(Producto producto, @RequestParam("img") MultipartFile file, HttpSession session) throws IOException {
        Producto p = new Producto();
        p = IProductoService.getProductoById(producto.getId()).get();
        p.setUsuario(IUsuarioService.findUserById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get());

        //En el caso de editar un producto pero no se cambia la imagen
        if (file.isEmpty()) {

            producto.setImagen(p.getImagen());
        } else {

            p = IProductoService.getProductoById(producto.getId()).get();

            //Eliminar imagen cuando no sea la que se encuentra por defecto
            if (!p.getImagen().equals("default.jpg")) {
                cargarImagenService.eliminarImagen(p.getImagen());
            }
            String nombreImagen = cargarImagenService.cargarImagen(file);
            producto.setImagen(nombreImagen);

        }
        producto.setUsuario(p.getUsuario());
        IProductoService.updateProducto(producto);
        return "redirect:/productos";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        Producto p = new Producto();
        p = IProductoService.getProductoById(id).get();

        //Eliminar imagen cuando no sea la que se encuentra por defecto
        if (!p.getImagen().equals("default.jpg")) {
            cargarImagenService.eliminarImagen(p.getImagen());
        }
        IProductoService.deleteProducto(id);

        return "redirect:/productos";
    }


}
