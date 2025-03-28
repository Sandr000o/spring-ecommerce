package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.repository.IProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IProductoServiceImpl implements IProductoService {

    @Autowired
    private IProductoRepository IProductoRepository;

    @Override
    public Producto saveProducto(Producto producto) {
       return IProductoRepository.save(producto);
    }

    @Override
    public Optional<Producto> getProductoById(Integer id) {
        return IProductoRepository.findById(id);
    }

    @Override
    public void updateProducto(Producto producto) {
        IProductoRepository.save(producto);

    }

    @Override
    public void deleteProducto(Integer id) {
        IProductoRepository.deleteById(id);
    }

    @Override
    public List<Producto> getProductos() {
        return IProductoRepository.findAll();
    }
}
