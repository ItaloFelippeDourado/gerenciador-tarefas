package com.gerenciador.tarefas.request;

import com.gerenciador.tarefas.entity.Usuario;
import com.gerenciador.tarefas.status.TarefaStatusEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa o JSON de request para ATUALIZAR uma tarefa.
 */
@Getter
@Setter
public class AtualizarTarefaRequest {

    @NotBlank(message = "{cadastrar.tarefa.request.titulo.obrigatorio}")
    private String titulo;
    private String descricao;
    private TarefaStatusEnum status;
    private Long responsavelId;
    private Integer quantidadeHorasRealizadas;

}
