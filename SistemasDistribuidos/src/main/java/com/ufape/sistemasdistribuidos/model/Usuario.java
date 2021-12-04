package com.ufape.sistemasdistribuidos.model;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author rafael.cavalcanti
 */
public class Usuario {

    private Long id;
    private String nome;
    private String senha;
    private String espacoSolicitado;
    private String espacoDisponivel;
    private String diretorio;

    public Usuario() {
    }

    public Usuario(Long id, String nome, String senha, String espacoSolicitado, String espacoDisponivel, String diretorio) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.espacoSolicitado = espacoSolicitado;
        this.espacoDisponivel = espacoDisponivel;
        this.diretorio = diretorio;
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

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public String getEspacoSolicitado() {
		return espacoSolicitado;
	}

	public void setEspacoSolicitado(String espacoSolicitado) {
		this.espacoSolicitado = espacoSolicitado;
	}

	public String getEspacoDisponivel() {
		return espacoDisponivel;
	}

	public void setEspacoDisponivel(String espacoDisponivel) {
		this.espacoDisponivel = espacoDisponivel;
	}

	public void jsonToObject() throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject usuario = (JSONObject) parser.parse(new FileReader("./user-properties.json"));
        this.id = (Long) usuario.get("id");
        this.nome = (String) usuario.get("nome");
        this.senha = (String) usuario.get("senha");
        this.diretorio = (String) usuario.get("diretorio");
    }
	
	public void objectToJson() throws IOException {
		Path path = Paths.get(this.diretorio);
		HashMap<String, Object> mapObj = new HashMap<String, Object>();
		mapObj.put("id", this.id);
		mapObj.put("nome", this.nome);
		mapObj.put("senha", this.senha);
		mapObj.put("espacoSolicitado", this.espacoSolicitado.toString());
		mapObj.put("espacoDisponivel", this.espacoDisponivel);
		mapObj.put("diretorio", path.toString().strip());

		JSONObject jsonObj = new JSONObject(mapObj);
		BufferedWriter writer = new BufferedWriter(new FileWriter("./user-properties.json"));
		writer.write(jsonObj.toJSONString());
		writer.close();
	}
	
	public void logoutJson() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("./user-properties.json"));
		writer.write("");
		writer.close();
	}

}
