package com.ufape.sistemasdistribuidosserver.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "requisicao")
public class Requisicao implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "id_usuario")
	private Long idUsuario;
	@Column(name = "id_arquivo")
	private Long idArquivo;
	@Column(name = "tipo_requisicao")
	private Long tipoRequisicao;

	public Requisicao() {
	}

	public Requisicao(Long id, Long idUsuario, Long idArquivo, Long tipoRequisicao) {
		this.id = id;
		this.idUsuario = idUsuario;
		this.idArquivo = idArquivo;
		this.tipoRequisicao = tipoRequisicao;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdArquivo() {
		return this.idArquivo;
	}

	public void setIdArquivo(Long IdArquivo) {
		this.idArquivo = IdArquivo;
	}

	public Long getTipoRequisicao() {
		return this.tipoRequisicao;
	}

	public void setTipoRequisicao(Long tipoRequisicao) {
		this.tipoRequisicao = tipoRequisicao;
	}

}
