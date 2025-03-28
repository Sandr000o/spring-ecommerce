package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.repository.IOrdenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrdenServiceImpl implements IOrdenService {

    @Autowired
    private IOrdenRepository ordenRepository;

    @Override
    public List<Orden> obtenerOrdenes() {
        return ordenRepository.findAll();
    }

    @Override
    public Orden guardarOrden(Orden orden) {
        return ordenRepository.save(orden);
    }


    public String generarNumeroOrden() {
        int numeroOrden = 0;
        String numeroConcatenado = "";
        List<Orden> ordenes = obtenerOrdenes();
        List<Integer> numeroCorrelativo = new ArrayList<Integer>();
        ordenes.stream().forEach(orden ->
                numeroCorrelativo.add(Integer.parseInt(orden.getNumero())));

        if (ordenes.isEmpty()) {
            numeroOrden=1;
        }else{
            numeroOrden=numeroCorrelativo.stream().max(Integer::compare).get();
            numeroOrden++;
        }

        numeroConcatenado = String.format("%09d", numeroOrden);

        return numeroConcatenado;
    }

}