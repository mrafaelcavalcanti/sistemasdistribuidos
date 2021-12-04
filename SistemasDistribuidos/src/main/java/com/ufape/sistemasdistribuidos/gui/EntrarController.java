package com.ufape.sistemasdistribuidos.gui;

import java.io.IOException;

import com.ufape.sistemasdistribuidos.App;
import com.ufape.sistemasdistribuidos.model.Response;
import com.ufape.sistemasdistribuidos.model.Usuario;
import com.ufape.sistemasdistribuidos.services.UsuarioService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class EntrarController {
	
	private UsuarioService usuarioService;

	@FXML
	public TextField nome;
	
	@FXML
	public PasswordField senha;
	
	@FXML
	public Label status;
	
	public EntrarController() {
		this.usuarioService = UsuarioService.getInstance();		
	}
	
    @FXML
    public void switchToPrimary() throws IOException {
        App.setRoot("cadastrar");
    }
    
    @FXML
    public void entrar() throws IOException {
    	String nome = this.nome.getText();
    	String senha = this.senha.getText();

    	if (nome == null || nome.isBlank() || nome.isEmpty()) {
    		this.status.setText("Campo nome obrigatório");
    		return;
    	}
    	if (senha == null || senha.isBlank() || senha.isEmpty()) {
    		this.status.setText("Campo senha obrigatório");
    		return;
    	}
    	
    	Response<Usuario> response = this.usuarioService.login(nome, senha);
    	
    	if (response.getStatus()) {
    		status.setText("Usuário " + response.getPayload().getNome() + " logado com sucesso");    		
    		App.setRoot("arquivos");
    	} else {
    		status.setText(response.getMessage()); 
    	}
    }
}