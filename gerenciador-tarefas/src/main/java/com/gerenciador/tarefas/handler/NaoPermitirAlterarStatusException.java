package com.gerenciador.tarefas.handler;

public class NaoPermitirAlterarStatusException extends RuntimeException{

    public NaoPermitirAlterarStatusException() {
        super();
    }

    public NaoPermitirAlterarStatusException(String mensagem) {
        super(mensagem);
    }

}
