package com.example.verkefni.vidmot;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class FlugVidmotApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(new Pane());
        ViewSwitcher.setScene(scene);
        ViewSwitcher.switchTo(View.VELJAFLUG);
        stage.setTitle("Flightbooker Extreme");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}