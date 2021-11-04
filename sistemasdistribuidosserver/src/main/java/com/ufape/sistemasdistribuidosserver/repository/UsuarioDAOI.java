package com.ufape.sistemasdistribuidosserver.repository;

import java.util.List;
import java.util.Optional;

import com.ufape.sistemasdistribuidosserver.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAOI extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.nome=:nome and u.senha=:senha")
    public Usuario findUsuario(String nome, String senha);

    @Query("select u from Usuario u where u.id=:id")
    public Usuario findUsuarioById(Long id);

    @Query("select u from Usuario u where u.nome=:nome")
    public Optional<Usuario> findUsuarioByUsename(String nome);

    @Query(nativeQuery = true, value = "SELECT id FROM usuario where id!=:idUsuario ORDER BY RAND() LIMIT 2")
    public List<Long> obterUsuarios(Long idUsuario);
}
