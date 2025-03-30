package com.example.verkefni;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;

public class HelloController {

    @FXML
    private void onHelloButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hello");
        alert.setHeaderText(null);
        alert.setContentText("Hello button clicked!");
        alert.showAndWait();
    }
}
