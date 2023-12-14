package com.gerenciador.tarefas.handler;

import com.gerenciador.tarefas.excecoes.ErrorsEnum;
import com.gerenciador.tarefas.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExcecoesHandler {

    @ExceptionHandler(NaoPermitirExcluirException.class)
    public ResponseEntity<ErrorResponse> naoPermitirExcluirTarefaExceptionHandler(NaoPermitirAlterarStatusException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("codigo", ErrorsEnum.NAO_PERMITIDO_EXCLUIR.toString());
        response.put("mensagem", "Não é permitido excluir uma tarefa com o Status diferente de NOVA");

        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.toString())
                .errors(Collections.singletonList(response))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NaoPermitirAlterarStatusException.class)
    public ResponseEntity<ErrorResponse> naoPermitirAlterarTarefaExceptionHandler(NaoPermitirAlterarStatusException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("codigo", ErrorsEnum.NAO_PERMITIDO_ALTERAR_STATUS.toString());
        response.put("mensagem", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .status(HttpStatus.UNPROCESSABLE_ENTITY.toString())
                .errors(Collections.singletonList(response))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
