package com.curso.ecommerce.controller;


import com.curso.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/productos")
public class ProductoController {


    @GetMapping("")
    public String show(){
        return "productos/show";
    }
}
