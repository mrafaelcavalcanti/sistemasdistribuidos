package com.ufape.sistemasdistribuidos.gui;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.ufape.sistemasdistribuidos.App;
import com.ufape.sistemasdistribuidos.model.Arquivo;
import com.ufape.sistemasdistribuidos.model.Response;
import com.ufape.sistemasdistribuidos.services.ArquivosService;
import com.ufape.sistemasdistribuidos.services.UsuarioService;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ArquivosController extends GUIController {
	
	private ArquivosService arquivosService;
	private UsuarioService usuarioService;
	private ObservableList<Arquivo> arquivosList;
	private File arquivoEscolhido;
	
	@FXML
	public Button usuarioButton;
	
	@FXML
	public Button removerButton;
	
	@FXML
	public Button enviarButton;
	
	@FXML
	public ListView<Arquivo> arquivos;
	
	@FXML
	public TextField arquivoSelecionado;

	public ArquivosController() {
		this.arquivosService = ArquivosService.getInstance();
		this.usuarioService = UsuarioService.getInstance();
	}
	
	@FXML
    public void initialize() throws IOException {
		this.usuarioButton.setText(this.usuarioService.getUsuarioLogado().getNome());
		
		this.arquivos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);;
		
		this.listarArquivos();
    }
	
	@FXML
	public void encerrarSessao() throws IOException {
		this.usuarioService.getUsuarioLogado().logoutJson();
		this.usuarioService.setUsuarioLogado(null);
		App.setRoot("entrar");
	}
	
	@FXML
	public void sair() {
		Platform.exit();
	}
	
	@FXML
	public void escolherArquivoTeclado(KeyEvent event) {
		if (event.getCode().getName() == "Space" || 
				event.getCode().getName() == "Enter") {
			this.escolherArquivo();
		}		
	}
	
	@FXML
	public void escolherArquivoMouse(MouseEvent event) {
		if (event.getButton().name() == "PRIMARY") {
			this.escolherArquivo();
		}		
	}
	
	
	@FXML
	public void remover() {
		this.arquivoEscolhido = null;
		this.removerButton.setDisable(true);
		this.enviarButton.setDisable(true);
		this.arquivoSelecionado.setText(null);
	}
	
	@FXML
	public void recarregar() throws IOException {
		this.listarArquivos();
	}
	
	@FXML
	public void baixar() {
		
	}
	
	@FXML
	public void excluir() {
		
	}

	public void listarArquivos() throws IOException {
		new Thread(() -> {
			this.enableLoading();
			Response<List<Arquivo>> response = this.arquivosService.listarArquivos(
					this.usuarioService.getUsuarioLogado().getId().intValue());

			Platform.runLater(() -> {
				this.arquivosList = FXCollections.observableArrayList(response.getPayload());
				this.arquivos.setItems(arquivosList);
				this.disableLoading();
			});
		}).start();
	}
	
	private void escolherArquivo() {
		FileChooser chooser = new FileChooser();
		File file = chooser.showOpenDialog(new Stage());
		if (file != null) {
			this.arquivoEscolhido = file;
            this.arquivoSelecionado.setText(file.getName());
            this.removerButton.setDisable(false);
            this.enviarButton.setDisable(false);
        }
	}
}
