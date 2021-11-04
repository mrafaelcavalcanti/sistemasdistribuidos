package com.ufape.sistemasdistribuidos.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author rafael.cavalcanti
 */
public class Arquivo implements Serializable {

    private static final long serialVersionUID = -8298381724524406275L;

    private Long id;
    private Long idUsuario;
    private String nome;
    private byte[] conteudo;

    public Arquivo() {
    }

    public Arquivo(Long id, Long idUsuario, String nome, byte[] conteudo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.conteudo = conteudo;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNomeUsuario() {
        return idUsuario;
    }

    public void serNomeUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }

}
