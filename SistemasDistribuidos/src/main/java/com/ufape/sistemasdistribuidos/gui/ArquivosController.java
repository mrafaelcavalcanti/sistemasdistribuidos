package com.ufape.sistemasdistribuidos.gui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;

import com.ufape.sistemasdistribuidos.App;
import com.ufape.sistemasdistribuidos.model.Arquivo;
import com.ufape.sistemasdistribuidos.model.ArquivoAux;
import com.ufape.sistemasdistribuidos.model.Response;
import com.ufape.sistemasdistribuidos.model.Usuario;
import com.ufape.sistemasdistribuidos.services.ArquivosService;
import com.ufape.sistemasdistribuidos.services.UsuarioService;
import com.ufape.sistemasdistribuidos.utils.AESCryptography;
import com.ufape.sistemasdistribuidos.utils.RequisicoesUtils;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
	private RequisicoesUtils requisicoesUtils;
	
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
	public Label status;
	
	@FXML
	public TextField arquivoSelecionado;

	public ArquivosController() {
		this.arquivosService = ArquivosService.getInstance();
		this.usuarioService = UsuarioService.getInstance();
		this.requisicoesUtils = RequisicoesUtils.getInstance();
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
		this.status.setText("");
		this.removerArquivo();
	}
	
	@FXML
	public void recarregar() throws IOException {
		this.status.setText("");
		this.listarArquivos();
	}
	
	@FXML
	public void enviar() {
		this.status.setText("");
		new Thread(() -> {
			this.enableLoading();
			try {
				Usuario usuario = this.usuarioService.getUsuarioLogado();
				byte[] arquivoByte = Files.readAllBytes(arquivoEscolhido.toPath());
				
				byte[] encryptedByte = AESCryptography.encrypt(arquivoByte, usuario.getSenha());
				
				ArquivoAux arquivoAux = new ArquivoAux();
				arquivoAux.setConteudo(encryptedByte);
				arquivoAux.setIdUsuario(usuario.getId());
				arquivoAux.setNome(arquivoEscolhido.getName());
				
				byte[] arquivo = SerializationUtils.serialize(arquivoAux);
				this.requisicoesUtils.enviarArquivo(arquivo);
				Platform.runLater(() -> {
					this.status.setText("Arquivo enviado com sucesso");
					try {
						this.listarArquivos();
					} catch (IOException e) {
						this.status.setText("Erro ao carregar lista de arquivos");
					}
					
				});
			} catch (Exception ex) {
				Platform.runLater(() -> {
					this.status.setText("Erro ao enviar arquivo");
					try {
						this.listarArquivos();
					} catch (IOException e) {
						this.status.setText("Erro ao enviar arquivo");
					}
					
				});
			}
		}).start();
		this.removerArquivo();
	}
	
	@FXML
	public void baixar() {
		this.status.setText("");
		Usuario usuario = this.usuarioService.getUsuarioLogado();
		Arquivo arquivo = this.arquivos.getSelectionModel().getSelectedItem();
		if (arquivo != null) {
			new Thread(() -> {
				this.enableLoading();
				try {
					byte[] arquivoByteArray = requisicoesUtils.obterArquivo(arquivo.getId());
		
		            ArquivoAux arquivoAux = (ArquivoAux) SerializationUtils.deserialize(arquivoByteArray);
		            if (arquivoByteArray != null) {
		                String path = null;
		                if (Objects.equals(arquivoAux.getIdUsuario(), usuario.getId())) {
		                    arquivoByteArray = arquivoAux.getConteudo();
		                    path = usuario.getDiretorio() + "/" + arquivoAux.getNome();
		                } else {
		                    path = usuario.getDiretorio() + "/" + arquivo.getId();
		                }
		                try (FileOutputStream fos = new FileOutputStream(path)) {
		                	byte[] decryptedByte = AESCryptography.decrypt(arquivoByteArray, usuario.getSenha());
		                    fos.write(decryptedByte);
		                    if (Objects.equals(arquivoAux.getIdUsuario(), usuario.getId())) {
		                    	requisicoesUtils.confirmarRecebimento(usuario.getId(), arquivo.getId());		                    	
		                    }
		                }
		            } else {
		                throw new Exception();
		            }
		            
		            Platform.runLater(() -> {
						this.status.setText("Arquivo baixado com sucesso");
						try {
							this.listarArquivos();
						} catch (IOException e) {
							this.status.setText("Erro ao carregar lista de arquivos");
						}
						
					});
		        } catch (Exception ex) {
		        	Platform.runLater(() -> {
						this.status.setText("Erro ao baixar arquivo");
						try {
							this.listarArquivos();
						} catch (IOException e) {
							this.status.setText("Erro ao baixar arquivo");
						}
						this.disableLoading();
					});
		        }
			}).start();
		} else {
			this.status.setText("Selecione um arquivo");
		}
	}
	
	@FXML
	public void excluir() {
		this.status.setText("");
		new Thread(() -> {
			this.enableLoading();
			Response<String> response = this.arquivosService.deletarArquivo(this.arquivos.getSelectionModel().getSelectedItem().getId().intValue());

			Platform.runLater(() -> {
				this.status.setText(response.getMessage());
				try {
					this.listarArquivos();
				} catch (IOException e) {
					this.status.setText("Erro ao carregar lista de arquivos");
				}
				this.disableLoading();
			});
		}).start();
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
	
	private void removerArquivo() {
		this.arquivoEscolhido = null;
		this.removerButton.setDisable(true);
		this.enviarButton.setDisable(true);
		this.arquivoSelecionado.setText(null);
	}
}
