package com.gerenciador.tarefas.service;

import com.gerenciador.tarefas.entity.Tarefa;
import com.gerenciador.tarefas.handler.NaoPermitirAlterarStatusException;
import com.gerenciador.tarefas.handler.NaoPermitirExcluirException;
import com.gerenciador.tarefas.repository.GerenciadorTarefasRepository;
import com.gerenciador.tarefas.request.AtualizarTarefaRequest;
import com.gerenciador.tarefas.request.CadastrarTarefaRequest;
import com.gerenciador.tarefas.status.TarefaStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GerenciadorTarefasService {

    @Autowired
    private GerenciadorTarefasRepository tarefasRepository;

    @Autowired
    private UserService userService;

    public Tarefa salvarTarefa(CadastrarTarefaRequest request) {

        Tarefa tarefa = Tarefa.builder()
                .status(TarefaStatusEnum.NOVA)
                .titulo(request.getTitulo())
                .descricao(request.getDescricao())
                .criador(userService.obterUserId(request.getCriadorId()).get())
                .quantidadeHorasEstimadas(request.getQuantidadeHorasEstimadas())
                .build();

        return tarefasRepository.save(tarefa);
    }

    public Page<Tarefa> obtemTarefas(String titulo, Pageable pageable) {
        return this.tarefasRepository.findByTituloContaining(titulo, pageable);
    }

    public Page<Tarefa> obtemTodasTarefas(Pageable pageable) {
        return this.tarefasRepository.findAll(pageable);
    }

    public void excluirTarefa(Long id) {
        Tarefa tarefa = this.tarefasRepository.findById(id).get();
        if(!TarefaStatusEnum.NOVA.equals(tarefa.getStatus())) {
            throw new NaoPermitirExcluirException();
        }

        this.tarefasRepository.deleteById(id);
    }

    public Tarefa atualizarTarefa(Long id, AtualizarTarefaRequest request) {

        Tarefa tarefa = this.tarefasRepository.findById(id).get();

        if(tarefa.getStatus().equals(TarefaStatusEnum.FINALIZADA)) {
            throw new NaoPermitirAlterarStatusException("Não é possível alterar o status de uma tarefa finalizada.");
        }

//        if(tarefa.getStatus().equals(TarefaStatusEnum.NOVA) && request.getStatus().equals(TarefaStatusEnum.FINALIZADA)) {
//            throw new NaoPermitirAlterarStatusException("Não é possível ");
//        }

        if(tarefa.getStatus().equals(TarefaStatusEnum.BLOQUEADA) && request.getStatus().equals(TarefaStatusEnum.FINALIZADA)) {
            throw new NaoPermitirAlterarStatusException("Não é possível alterar o status de uma tarefa bloqueada.");
        }


        tarefa.setTitulo(request.getTitulo());
        tarefa.setStatus(request.getStatus());
        tarefa.setDescricao(request.getDescricao());
        tarefa.setResponsavel(userService.obterUserId(request.getResponsavelId()).get());
        tarefa.setQuantidadeHorasRealizadas(request.getQuantidadeHorasRealizadas());

        this.tarefasRepository.save(tarefa);

        return tarefa;
    }
}
