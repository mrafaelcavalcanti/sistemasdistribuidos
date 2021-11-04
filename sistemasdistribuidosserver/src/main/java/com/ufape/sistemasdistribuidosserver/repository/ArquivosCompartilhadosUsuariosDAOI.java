package com.ufape.sistemasdistribuidosserver.repository;

import java.util.List;

import com.ufape.sistemasdistribuidosserver.model.ArquivosCompartilhadosUsuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArquivosCompartilhadosUsuariosDAOI extends JpaRepository<ArquivosCompartilhadosUsuarios, Long> {

    @Query("SELECT a FROM ArquivosCompartilhadosUsuarios a WHERE a.idUsuario=:idUsuario")
    public List<ArquivosCompartilhadosUsuarios> findAllByIdUsuario(Long idUsuario);

    @Query("SELECT a FROM ArquivosCompartilhadosUsuarios a WHERE a.idArquivo=:idArquivo")
    public List<ArquivosCompartilhadosUsuarios> findAllByIdArquivo(Long idArquivo);
}
