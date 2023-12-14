package com.gerenciador.tarefas.controller;

import com.gerenciador.tarefas.entity.Usuario;
import com.gerenciador.tarefas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<String> salvarUser(@RequestBody Usuario usuario) {
        if (service.findExistByname(usuario).isEmpty()) {
            Usuario usuarioSalvo = service.salvarUser(usuario);
            return new ResponseEntity<>("Usuário criado com sucesso!", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Usuário já existente!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<String> atualizarUser(@RequestBody Usuario usuario) {
        Usuario usuarioSalvo = service.atualizarUser(usuario);
        return new ResponseEntity<>("Usuário atualizado com sucesso!", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obterUsers() {
        return new ResponseEntity<>(service.obterUsers(), HttpStatus.OK);
    }

    @DeleteMapping
    public void excluirUser(@RequestBody Usuario usuario) {
        service.excluirUser(usuario);
    }
}
