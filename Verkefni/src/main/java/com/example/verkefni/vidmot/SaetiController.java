package com.example.verkefni.vidmot;

import com.example.verkefni.modules.Seat
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class SaetiController {
    @FXML
    private Label welcomeText;
    @FXML
    private GridPane myGridPane;
    @FXML
    private GridPane myGridPane2;


    private static final int ROWS = 30; // Number of rows in the plane
    private static final int COLUMNS = 3; // Seats per side

    @FXML
    public void initialize() {
        myGridPane.setVgap(1);
        myGridPane.setHgap(2);
        myGridPane2.setVgap(1);
        myGridPane2.setHgap(2);
        myGridPane.setPrefSize((COLUMNS * 30), (ROWS * 30));
        myGridPane2.setPrefSize((COLUMNS * 30), (ROWS * 30));

        populateSeats();
    }

    @FXML
    public void tellMe(MouseEvent mouseEvent) {
        Seat source = (Seat) mouseEvent.getSource();
        String seatInfo = source.getSeatID().toString();
        if (source.isAvailable()){
            seatInfo += " is available";
        }
        if(!source.isAvailable()){
            seatInfo += " is unavailable";
        }

        if (source.isEmergency()){
            seatInfo += " and is an Emergency Exit";
        }


        welcomeText.setText("Seat number: " + seatInfo);
    }

    public void clickseat(MouseEvent mouseEvent) {
        Seat source = (Seat) mouseEvent.getSource();
        source.setAvailable(!source.isAvailable());
        source.getStyleClass().removeAll("redseat", "greenseat");
        if (source.isAvailable()) {
            source.getStyleClass().add("greenseat");
        } else {
            source.getStyleClass().add("redseat");
        }

    }


    private void populateSeats() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                addSeat(myGridPane, row, col, "L" + (row + 1) + (char) ('A' + col));

                addSeat(myGridPane2, row, col, "R" + (row + 1) + (char) ('D' + col));
            }
        }
    }

    private void addSeat(GridPane gridPane, int row, int col, String seatNumber) {
        Seat seat = new Seat();
        seat.setPrefSize(25, 25);

        seat.getStyleClass().add(seatNumber);
        if (seat.isAvailable())
        {
            seat.getStyleClass().add("greenseat");
        }
        else
        {
            seat.getStyleClass().add("redseat");
        }

        seat.setOnMouseEntered(this::tellMe);
        seat.setOnMouseClicked(this::clickseat);
        gridPane.add(seat, col, row);
    }
}
