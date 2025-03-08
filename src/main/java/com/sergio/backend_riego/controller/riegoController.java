package com.sergio.backend_riego.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class riegoController {
    
    @GetMapping("/hello")
    public String sayHello() {
        return "¡Hola desde Spring Boot!";
    }
}
