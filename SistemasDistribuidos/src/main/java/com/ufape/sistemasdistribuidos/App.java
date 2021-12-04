package com.ufape.sistemasdistribuidos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.ufape.sistemasdistribuidos.model.Usuario;
import com.ufape.sistemasdistribuidos.services.UsuarioService;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
    	try {
    		UsuarioService usuarioService = UsuarioService.getInstance();
    		Usuario usuario = new Usuario();
    		usuario.jsonToObject(); 
    		
    		if (usuario != null) {
    			usuarioService.setUsuarioLogado(usuario);
    			scene = new Scene(loadFXML("arquivos"), 800, 600);
    			stage.setScene(scene);
    			stage.setTitle("DistDrive");
    			stage.show();	
    		}
    	} catch (IOException | ParseException ex) {
    		scene = new Scene(loadFXML("entrar"), 800, 600);
    		stage.setScene(scene);
    		stage.show(); 		
    	}

    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("gui/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}