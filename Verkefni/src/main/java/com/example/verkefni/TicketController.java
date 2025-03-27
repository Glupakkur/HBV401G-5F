package com.example.verkefni;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TicketController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
