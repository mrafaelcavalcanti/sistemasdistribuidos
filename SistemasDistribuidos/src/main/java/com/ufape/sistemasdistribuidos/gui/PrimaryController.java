package com.ufape.sistemasdistribuidos.gui;

import java.io.IOException;
import java.util.regex.Pattern;

import com.ufape.sistemasdistribuidos.App;
import com.ufape.sistemasdistribuidos.model.Response;
import com.ufape.sistemasdistribuidos.services.UsuarioService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PrimaryController {
	
	private UsuarioService usuarioService;
	
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
	
	@FXML
	public VBox loadingPane;
	
	@FXML
	public ProgressIndicator progress;
	
	public PrimaryController() {
        this.usuarioService = UsuarioService.getInstance();
    }

    @FXML
    public void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    public void cadastrarUsuario() throws IOException {
    	String nome = this.nome.getText();
    	String senha = this.senha.getText();
    	Integer espacoSolicitado;
    	try {
    		espacoSolicitado = Integer.parseInt(this.espacoSolicitado.getText());    		
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
    		
    		new Thread(() -> {
    			this.enableLoading();
    			Response<String> response = this.usuarioService.cadastrarUsuario(100000, nome, senha, espacoSolicitado, 0);
    			
    			Platform.runLater(() -> {
    				this.status.setText(response.getPayload());
    				this.disableLoading();
    			});
    		}).start();
    	} catch (NumberFormatException ex) {
    		this.status.setText("Campo espaco solicitado obrigatório");
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
    
    private void enableLoading() {
    	this.loadingPane.setDisable(false);
    	this.loadingPane.setOpacity(1);
    	this.nome.setDisable(true);
    	this.senha.setDisable(true);
    	this.espacoSolicitado.setDisable(true);
    	this.cadastrar.setDisable(true);
    }
    
    private void disableLoading() {
    	this.loadingPane.setDisable(true);
    	this.loadingPane.setOpacity(0);
    	this.nome.setDisable(false);
    	this.senha.setDisable(false);
    	this.espacoSolicitado.setDisable(false);
    	this.cadastrar.setDisable(false);
    }
}
