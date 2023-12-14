package com.gerenciador.tarefas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApiController {

    @GetMapping("/teste")
    public String teste() { return "Sucesso!"; }

    @GetMapping("/bem-vindo")
    public String testeBemVindo(@RequestParam(name = "nome") String nome) {
        return "Olá " + nome + ", seja bem vindo";
    }
}
