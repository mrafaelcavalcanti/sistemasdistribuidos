package com.ufape.sistemasdistribuidosserver.model;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table (name = "usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String senha;
    @Column(name = "espaco_solicitado")
    private String espacoSolicitado;
    @Column(name = "espaco_disponivel")
    private String espacoDisponivel;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String senha, String espacoSolicitado, String espacoDisponivel) {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEspacoSolicitado() {
        return this.espacoSolicitado;
    }

    public void setEspacoSolicitado(String espacoSolicitado) {
        this.espacoSolicitado = espacoSolicitado;
    }

    public String getEspacoDisponivel() {
        return this.espacoDisponivel;
    }

    public void setEspacoDisponivel(String espacoDisponivel) {
        this.espacoDisponivel = espacoDisponivel;
    }

}
