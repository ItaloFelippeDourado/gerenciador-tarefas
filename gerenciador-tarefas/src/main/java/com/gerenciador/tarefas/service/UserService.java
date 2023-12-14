package com.gerenciador.tarefas.service;

import com.gerenciador.tarefas.entity.Usuario;
import com.gerenciador.tarefas.repository.IRoleRepository;
import com.gerenciador.tarefas.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private IRoleRepository iRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvarUser(Usuario usuario) {
        usuario.setRoles(usuario.getRoles()
                .stream()
                .map(role -> iRoleRepository.findByNome(role.getNome()))
                .toList());

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return this.iUserRepository.save(usuario);
    }

    public Usuario atualizarUser(Usuario usuario) {

        usuario.setRoles(usuario.getRoles()
                .stream()
                .map(role -> iRoleRepository.findByNome(role.getNome()))
                .toList());

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return this.iUserRepository.save(usuario);
    }

    public void excluirUser(Usuario usuario) {this.iUserRepository.deleteById(usuario.getId());}

    public List<Usuario> obterUsers() {return this.iUserRepository.findAll();}

    public Optional<Usuario> findExistByname(Usuario usuario) {return this.iUserRepository.findByUsername(usuario.getUsername());}

    public Optional<Usuario> obterUserId(Long usuarioId) {return this.iUserRepository.findById(usuarioId);}

}
