package com.edu.reserva_salas.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//teste welcome
@RestController
@RequestMapping("/api")
public class WelcomeToAPI {

    @GetMapping
    public String welcome() {
        return "Bem-vindo(a)!";
    }
}
