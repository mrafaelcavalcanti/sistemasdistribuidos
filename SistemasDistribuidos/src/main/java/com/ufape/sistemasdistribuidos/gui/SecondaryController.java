package com.ufape.sistemasdistribuidos.gui;

import java.io.IOException;

import com.ufape.sistemasdistribuidos.App;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    public void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}