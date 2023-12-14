package com.gerenciador.tarefas.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AtualizarTarefaResponse {

    private Long id;
    private String descricao;
    private String titulo;
    private String responsavel;
    private  String status;
    private int quantidadeHorasRealizadas;

}
