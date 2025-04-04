package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IOrdenService  {
    List<Orden> obtenerOrdenes();
    Orden guardarOrden(Orden orden);
    String generarNumeroOrden();
    List<Orden> obtenerOrdenesPorUsuario(Usuario usuario);
}
