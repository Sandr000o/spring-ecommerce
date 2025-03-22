package com.curso.ecommerce.service;


import com.curso.ecommerce.model.Producto;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface ProductoService {
    public Producto saveProducto(Producto producto);
    public Optional<Producto> getProductoById(Integer id);
    public void updateProducto(Producto producto);
    public void deleteProducto(Integer id);

}
