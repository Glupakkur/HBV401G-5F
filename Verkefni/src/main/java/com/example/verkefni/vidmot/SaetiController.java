package com.example.verkefni.vidmot;

import com.example.verkefni.modules.Flight;
import com.example.verkefni.FlightMock;
import com.example.verkefni.modules.Seat;
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

    private static final int COLUMNS_PER_SIDE = 2;

    private void populateSeatsFromFlight(Flight flight) {
        int added = 0;

        for (Seat seat : flight.getSeats()) {
            String id = seat.getSeatID();

            if (id == null || id.length() < 2) continue;

            char rowChar = id.charAt(0);
            int row = rowChar - 'A';

            int seatNum;
            try {
                seatNum = Integer.parseInt(id.substring(1)) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Skipping malformed seat ID: " + id);
                continue;
            }

            GridPane grid = seatNum < 2 ? myGridPane : myGridPane2;
            int col = seatNum % 2;

            seat.setPrefSize(40, 40);
            seat.setStyle("-fx-border-color: black; -fx-background-color: lightcoral;");
            setupSeat(seat);
            System.out.println("Adding seat " + seat.getSeatID() + " to " + (grid == myGridPane ? "LEFT" : "RIGHT") + " at row " + row + ", col " + col);

            grid.add(seat, col, row);
            added++;
        }
        System.out.println("Flight seat count: " + flight.getSeats().length);
        System.out.println("Seats added to grid: " + added);

        System.out.println("Added " + added + " seats from flight.");
    }


    @FXML
    public void initialize() {
        myGridPane.setVgap(1);
        myGridPane.setHgap(2);
        myGridPane2.setVgap(1);
        myGridPane2.setHgap(2);
        myGridPane.setPrefSize(COLUMNS_PER_SIDE * 30, 30 * 30);
        myGridPane2.setPrefSize(COLUMNS_PER_SIDE * 30, 30 * 30);

        // Use a mock flight to test
        Flight mockFlight = new FlightMock(1).getMock()[0];
        populateSeatsFromFlight(mockFlight);
    }

    private void setupSeat(Seat seat) {
        seat.setPrefSize(25, 25);

        seat.getStyleClass().removeAll("greenseat", "redseat", "emergencyseat");
        if (seat.isAvailable()) {
            seat.getStyleClass().add("greenseat");
        } else {
            seat.getStyleClass().add("redseat");
        }

        if (seat.isEmergency()) {
            seat.getStyleClass().add("emergencyseat");
        }

        seat.setOnMouseEntered(this::tellMe);
        seat.setOnMouseClicked(this::clickseat);
    }

    @FXML
    public void tellMe(MouseEvent mouseEvent) {
        Seat source = (Seat) mouseEvent.getSource();
        StringBuilder seatInfo = new StringBuilder(source.getSeatID());

        seatInfo.append(source.isAvailable() ? " is available" : " is unavailable");

        if (source.isEmergency()) {
            seatInfo.append(" and is an Emergency Exit");
        }

        welcomeText.setText("Seat number: " + seatInfo);
    }

    public void clickseat(MouseEvent mouseEvent) {
        Seat source = (Seat) mouseEvent.getSource();
        source.setAvailable(!source.isAvailable());

        setupSeat(source); // Reapply styles
    }
}
