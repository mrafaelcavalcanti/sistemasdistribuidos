package com.ufape.sistemasdistribuidos.model;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author rafael.cavalcanti
 */
public class Usuario {

    private Long id;
    private String nome;
    private String senha;
    private String diretorioArquivos;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String senha, String diretorioArquivos) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.diretorioArquivos = diretorioArquivos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDiretorioArquivos() {
        return diretorioArquivos;
    }

    public void setDiretorioArquivos(String diretorioArquivos) {
        this.diretorioArquivos = diretorioArquivos;
    }

    public void jsonToObject() throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject usuario = (JSONObject) parser.parse(new FileReader("c:\\dadosUsuario.json"));
        this.id = (Long) usuario.get("id");
        this.nome = (String) usuario.get("nome");
        this.senha = (String) usuario.get("senha");
        this.diretorioArquivos = (String) usuario.get("diretorioArquivos");
    }

}
