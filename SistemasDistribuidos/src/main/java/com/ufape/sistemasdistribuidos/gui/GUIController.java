package com.ufape.sistemasdistribuidos.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;

public class GUIController {
	
	@FXML
	public Label status;
	
	@FXML
	public VBox loadingPane;
	
	@FXML
	public ProgressIndicator progress;
	
	public void enableLoading() {
    	this.loadingPane.setDisable(false);
    	this.loadingPane.setOpacity(1);
    }
    
	public void disableLoading() {
    	this.loadingPane.setDisable(true);
    	this.loadingPane.setOpacity(0);
    }

}
