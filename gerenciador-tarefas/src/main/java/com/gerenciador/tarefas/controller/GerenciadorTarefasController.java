package com.gerenciador.tarefas.controller;

import com.gerenciador.tarefas.entity.Tarefa;
import com.gerenciador.tarefas.request.AtualizarTarefaRequest;
import com.gerenciador.tarefas.request.CadastrarTarefaRequest;
import com.gerenciador.tarefas.response.AtualizarTarefaResponse;
import com.gerenciador.tarefas.response.CadastrarTarefaResponse;
import com.gerenciador.tarefas.response.ObterTarefasPaginadasResponse;
import com.gerenciador.tarefas.response.ObterTarefasResponse;
import com.gerenciador.tarefas.service.GerenciadorTarefasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gerenciador-tarefas")
public class GerenciadorTarefasController {

    @Autowired
    private GerenciadorTarefasService service;

    @PostMapping
    public ResponseEntity<CadastrarTarefaResponse> salvarTarefa(@Valid @RequestBody CadastrarTarefaRequest request) {

        Tarefa tarefa = service.salvarTarefa(request);
        CadastrarTarefaResponse response = CadastrarTarefaResponse.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .criadorId(tarefa.getCriador().getId())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<ObterTarefasPaginadasResponse> obterTarefas(
            @RequestParam(required = false) String titulo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {

        Page<Tarefa> tarefasPaginadas;

        if (titulo == null) {
            tarefasPaginadas = this.service.obtemTodasTarefas(PageRequest.of(page, size));
        } else {
            tarefasPaginadas = this.service.obtemTarefas(titulo, PageRequest.of(page, size));
        }

        List<ObterTarefasResponse> tarefas = tarefasPaginadas
                .getContent()
                .stream()
                .map(tarefa -> {
                    return ObterTarefasResponse
                            .builder()
                            .titulo(tarefa.getTitulo())
                            .descricao(tarefa.getDescricao())
                            .responsavel(tarefa.getResponsavel() != null ? tarefa.getResponsavel().getUsername() : "NÃO ATRIBUIDA")
                            .criador(tarefa.getCriador().getUsername())
                            .status(tarefa.getStatus())
                            .quantidadeHorasEstimadas(tarefa.getQuantidadeHorasEstimadas())
                            .quantidadeHorasRealizadas(tarefa.getQuantidadeHorasRealizadas())
                            .dataCadastro(tarefa.getDataCadastro())
                            .dataAtualizacao(tarefa.getDataAtualizacao())
                            .build();
        })
                .toList();

        ObterTarefasPaginadasResponse response = ObterTarefasPaginadasResponse
                .builder()
                .paginaAtual(tarefasPaginadas.getNumber())
                .totalPaginas(tarefasPaginadas.getTotalPages())
                .totalItens(tarefasPaginadas.getTotalElements())
                .tarefas(tarefas)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void excluirTarefa(@PathVariable Long id) {
        this.service.excluirTarefa(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AtualizarTarefaResponse> atualizarTarefa(@PathVariable Long id, @RequestBody AtualizarTarefaRequest request) {

        Tarefa tarefa = service.atualizarTarefa(id, request);

        AtualizarTarefaResponse response = AtualizarTarefaResponse.builder()
                .id(tarefa.getId())
                .titulo(tarefa.getTitulo())
                .descricao(tarefa.getDescricao())
                .responsavel(tarefa.getResponsavel().getUsername())
                .status(tarefa.getStatus().toString())
                .quantidadeHorasRealizadas(tarefa.getQuantidadeHorasRealizadas())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
