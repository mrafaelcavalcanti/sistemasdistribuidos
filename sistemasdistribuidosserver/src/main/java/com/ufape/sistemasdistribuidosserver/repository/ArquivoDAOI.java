package com.ufape.sistemasdistribuidosserver.repository;

import java.util.List;

import com.ufape.sistemasdistribuidosserver.model.Arquivo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArquivoDAOI extends JpaRepository<Arquivo, Long>{
    
    @Query("SELECT a FROM Arquivo a WHERE a.idUsuario=:idUsuario")
    public List<Arquivo> findAllByUsuarioId(Long idUsuario);

    @Query("SELECT a FROM Arquivo a WHERE a.id=:id")
    public Arquivo findArchiveById(Long id);

}
