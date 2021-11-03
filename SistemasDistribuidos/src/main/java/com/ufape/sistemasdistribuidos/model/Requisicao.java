/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ufape.sistemasdistribuidos.model;

import java.io.Serializable;

/**
 *
 * @author rafael.cavalcanti
 */
public class Requisicao implements Serializable {

    private Long idUsuario;
    private Long idArquivo;
    private Long tipoRequisicao;

    public Requisicao() {
    }

    public Requisicao(Long idUsuario, Long idArquivo, Long tipoRequisicao) {
        this.idUsuario = idUsuario;
        this.idArquivo = idArquivo;
        this.tipoRequisicao = tipoRequisicao;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdArquivo() {
        return idArquivo;
    }

    public void setIdArquivo(Long idArquivo) {
        this.idArquivo = idArquivo;
    }

    public Long getTipoRequisicao() {
        return tipoRequisicao;
    }

    public void setTipoRequisicao(Long tipoRequisicao) {
        this.tipoRequisicao = tipoRequisicao;
    }
}
