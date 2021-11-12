package com.ufape.sistemasdistribuidos.gui;

import java.io.IOException;
import java.util.regex.Pattern;

import com.ufape.sistemasdistribuidos.App;
import com.ufape.sistemasdistribuidos.utils.RequisicoesUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class PrimaryController {
	
	private RequisicoesUtils requisicoesUtils;
	
	@FXML
	public TextField nome;
	
	@FXML
	public TextField senha;
	
	@FXML
	public TextField espacoSolicitado;
	
	@FXML
	public Label status;
	
	@FXML
	public Button cadastrar;
	
	public PrimaryController() {
        this.requisicoesUtils = RequisicoesUtils.getInstance();
    }

    @FXML
    public void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    public void cadastrarUsuario() throws IOException {
    	String nome = this.nome.getText();
    	String senha = this.senha.getText();
    	Integer espacoSolicitado = 0;
    	try {
    		espacoSolicitado = Integer.parseInt(this.espacoSolicitado.getText());    		
    	} catch (NumberFormatException ex) {
    		this.status.setText("Campo espaco solicitado obrigatório");
    	} finally {
    	
	    	if (nome == null || nome.isBlank() || nome.isEmpty()) {
	    		this.status.setText("Campo nome obrigatório");
	    		return;
	    	}
	    	if (senha == null || senha.isBlank() || senha.isEmpty()) {
	    		this.status.setText("Campo senha obrigatório");
	    		return;
	    	}
	    	if (espacoSolicitado == 0) {
	    		this.status.setText("Campo quant. Gb oferecidos deve ser maior que 0");
	    		return;
	    	}
	    	
	    	this.status.setText(this.requisicoesUtils.cadastrarUsuario(nome, senha, espacoSolicitado));
    	}
    }
    
    @FXML
    public void mascaraNumeros() throws IOException {
    	String regex = "^[0-9]*";
    	String textoDigitado = this.espacoSolicitado.getText();
    	if (!Pattern.matches(regex, textoDigitado)) {
    		this.espacoSolicitado.setText("");
    	}
    }
}
