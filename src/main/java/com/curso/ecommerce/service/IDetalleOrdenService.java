package com.curso.ecommerce.service;

import com.curso.ecommerce.model.DetalleOrden;
import org.springframework.stereotype.Service;


public interface IDetalleOrdenService {
    DetalleOrden guardarDetalleOrden(DetalleOrden detalleOrden);
}
