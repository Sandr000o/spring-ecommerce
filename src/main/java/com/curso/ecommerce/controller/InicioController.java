package com.curso.ecommerce.controller;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.service.IDetalleOrdenService;
import com.curso.ecommerce.service.IOrdenService;
import com.curso.ecommerce.service.IProductoService;
import com.curso.ecommerce.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class InicioController {

    private Logger LOGGER = LoggerFactory.getLogger(InicioController.class);

    //Alamancer los detalles de cada orden
    List<DetalleOrden> detalles = new ArrayList<>();

    //Datos de la orden
    Orden orden = new Orden();

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IProductoService productoService;

    @Autowired
    private IOrdenService ordenService;

    @Autowired
    private IDetalleOrdenService detalleOrdenService;

    @GetMapping("")
    public String inicio(Model model, HttpSession session) {
        List<Producto> listaProductos = productoService.getProductos();
        model.addAttribute("listaProductos", listaProductos);
        //Si hay una sesion mostramos una vista
        model.addAttribute("session.id", session.getAttribute("idUsuario"));

        return "usuario/home";
    }


    @GetMapping("productohome/{id}")
    public String verProducto(@PathVariable Integer id, Model model, HttpSession sesion) {
        LOGGER.info("Id del producto: " + id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoService.getProductoById(id);
        producto = productoOptional.get();
        model.addAttribute("session", sesion.getAttribute("idUsuario"));
        model.addAttribute("producto", producto);
        LOGGER.info("Producto: " + producto);
        return "usuario/productohome";
    }


    @PostMapping("/cart")
    public String agregarAlCarrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> productoOptional = productoService.getProductoById(id);
        LOGGER.info("Producto añadido: " + productoOptional.get());
        LOGGER.info("Cantidad: " + cantidad);
        producto = productoOptional.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setProducto(producto);
        detalleOrden.setTotal(producto.getPrecio() * cantidad);

        //Validación para evitar que el producto no se añada más de una vez
        Integer idProducto = producto.getId();
        boolean ingresado = detalles.stream().anyMatch(p -> p.getProducto().getId() == idProducto);

        if (!ingresado) {
            detalles.add(detalleOrden);
        }


        sumaTotal = detalles.stream().mapToDouble(dto -> dto.getTotal()).sum();
        orden.setTotal(sumaTotal);
        LOGGER.info("Detalle Orden: " + detalleOrden);
        LOGGER.info("Orden: " + orden);

        model.addAttribute("detalles", detalles);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    @GetMapping("/mostrarCarrito" )
    public String mostrarCarrito(Model model, HttpSession sesion) {
        model.addAttribute("detalles", detalles);
        model.addAttribute("orden", orden);

        //Sesion
        model.addAttribute("session", sesion.getAttribute("idUsuario"));
        return "/usuario/carrito";
    }
    //Método para quitar un producto del carrito

    @GetMapping("/eliminar/carrito/{id}")
    public String eliminarProductos(@PathVariable Integer id, Model model) {
        List<DetalleOrden> ordenesNuevas = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden : detalles) {
            if (detalleOrden.getProducto().getId() != id) {
                ordenesNuevas.add(detalleOrden);
            }
        }

        //Nueva lista con productos actuales en el carrito
        detalles = ordenesNuevas;
        double sumaTotal = 0;
        sumaTotal = ordenesNuevas.stream().mapToDouble(dto -> dto.getTotal()).sum();
        orden.setTotal(sumaTotal);
        model.addAttribute("detalles", detalles = ordenesNuevas);
        model.addAttribute("orden", orden);
        return "usuario/carrito";
    }

    @GetMapping("/orden")
    public String orden(Model model,HttpSession session) {
        Usuario usuario = usuarioService.findUserById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
        model.addAttribute("detalles", detalles);
        model.addAttribute("orden", orden);
        model.addAttribute("usuario", usuario);

        return "/usuario/resumenorden";
    }

    @GetMapping("/guardarOrden")
    public String guardarOrden(Model model,HttpSession session) {
        Date fechaCreacion = new Date();
        orden.setFechaCreacion(fechaCreacion);
        orden.setNumero(ordenService.generarNumeroOrden());

        //El usuario que genera la orden
        Usuario usuario = usuarioService.findUserById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
        orden.setUsuario(usuario);
        ordenService.guardarOrden(orden);

        //Guardar detalles
        for (DetalleOrden detalleOrden : detalles) {
            detalleOrden.setOrden(orden);
            detalleOrdenService.guardarDetalleOrden(detalleOrden);
        }

        //Limpiamos la lista y orden

        orden = new Orden();
        detalles.clear();


        return "redirect:/";
    }

    @PostMapping("/buscar")
    public String buscarProducto(@RequestParam String nombre, Model model) {
        LOGGER.info("Nombre: " + nombre);
        List<Producto> productos = productoService.getProductos().stream().filter(
                p -> p.getNombre().contains(nombre)).collect(Collectors.toList());

        model.addAttribute("listaProductos", productos);
        return "/usuario/home";
    }
}
