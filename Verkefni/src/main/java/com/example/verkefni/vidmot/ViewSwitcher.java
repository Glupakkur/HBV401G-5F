package com.example.verkefni.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class ViewSwitcher {

    private static final Map<View, Parent> cache = new HashMap<>();

    private static Scene scene;

    /**
     * Setur núverandi senu í ViewSwitcher sem scene - enginn breyting á glugga
     *
     * @param scene senan
     */
    public static void setScene(Scene scene) {
        ViewSwitcher.scene = scene;
    }

    /**
     * Skipta yfir í viðmótstré sem er lýst í view
     *
     * @param view nafn á .fxml skrá
     */
    public static void switchTo(View view) {
        if (scene == null) {
            System.out.println("No scene was set");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(ViewSwitcher.class.getResource(view.getFileName()));
            Parent root = loader.load();

            if (view == View.SEATMAP && root instanceof AnchorPane anchorPane) {
                double screenHeight = javafx.stage.Screen.getPrimary().getVisualBounds().getHeight();
                anchorPane.setPrefHeight(screenHeight * 0.85);
            }

            scene.setRoot(root);


            if (view == View.SEATMAP) {
                Object controller = loader.getController();
                if (controller instanceof SaetiController saetiController) {
                    saetiController.refreshSeats();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
