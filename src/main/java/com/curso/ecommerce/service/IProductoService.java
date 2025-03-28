package com.curso.ecommerce.service;


import com.curso.ecommerce.model.Producto;

import java.util.List;
import java.util.Optional;

public interface IProductoService {
    public Producto saveProducto(Producto producto);
    public Optional<Producto> getProductoById(Integer id);
    public void updateProducto(Producto producto);
    public void deleteProducto(Integer id);
    public List<Producto> getProductos();

}
