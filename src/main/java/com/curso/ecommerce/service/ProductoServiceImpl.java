package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto saveProducto(Producto producto) {
       return productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> getProductoById(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public void updateProducto(Producto producto) {
        productoRepository.save(producto);

    }

    @Override
    public void deleteProducto(Integer id) {
        productoRepository.deleteById(id);
    }
}
