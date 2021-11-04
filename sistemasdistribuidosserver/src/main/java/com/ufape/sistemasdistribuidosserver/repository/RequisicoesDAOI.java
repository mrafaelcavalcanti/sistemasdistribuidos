package com.ufape.sistemasdistribuidosserver.repository;

import java.util.List;

import com.ufape.sistemasdistribuidosserver.model.Requisicao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RequisicoesDAOI extends JpaRepository<Requisicao, Long> {
    
    @Query("SELECT r FROM Requisicao r WHERE r.idUsuario=:id")
    public List<Requisicao> findByUserId(Long id);
}
