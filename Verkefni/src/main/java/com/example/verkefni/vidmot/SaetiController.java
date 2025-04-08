package com.example.verkefni.vidmot;

import com.example.verkefni.modules.Flight;
import com.example.verkefni.FlightMock;
import com.example.verkefni.modules.Seat;
import com.example.verkefni.modules.Ticket;
import database.TicketDB;
import database.SeatDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;

public class SaetiController {

    @FXML
    private Label hoverOverText;

    @FXML
    private GridPane myGridPane;

    @FXML
    private GridPane myGridPane2;

    private final SeatDB seatDB = new SeatDB();
    private final List<Seat> selectedSeats = new ArrayList<>();
    private Flight selectedFlight;

    private static final int COLUMNS_PER_SIDE = 2;

    /**
     * Sets up the view with 2 gripanes full of seats
     */
    @FXML
    public void initialize() {
        myGridPane.setVgap(1);
        myGridPane.setHgap(2);
        myGridPane2.setVgap(1);
        myGridPane2.setHgap(2);
        myGridPane.setPrefSize(COLUMNS_PER_SIDE * 30, 30 * 30);
        myGridPane2.setPrefSize(COLUMNS_PER_SIDE * 30, 30 * 30);

        selectedFlight = DataHolder.getFlight();

        if (selectedFlight != null) {
            populateSeatsFromFlight(selectedFlight);
        } else {
            hoverOverText.setText("No flight selected.");
        }
    }
    /**
     * Takes in a flight as parameter and sets the seats into 2 gripanes
     */
    private void populateSeatsFromFlight(Flight flight) {
        for (Seat seat : seatDB.getSeatObjectsForFlight(flight.getFlightID())) {
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

            setupSeat(seat);
            grid.add(seat, col, row);
        }
    }
    /**
     * Sets size to seat, sets style to seat, sets methods for interacting with seat.
     */
    private void setupSeat(Seat seat) {
        seat.setPrefSize(25, 25);
        seat.getStyleClass().removeAll("greenseat", "redseat", "emergencyseat", "selectedseat");

        if (!seat.isAvailable()) {
            seat.getStyleClass().add("redseat");
        } else if (selectedSeats.contains(seat)) {
            seat.getStyleClass().add("selectedseat");
        } else {
            seat.getStyleClass().add("greenseat");
        }

        if (seat.isEmergency()) {
            seat.getStyleClass().add("emergencyseat");
        }

        seat.setOnMouseEntered(this::tellMe);
        seat.setOnMouseClicked(this::clickseat);
    }
    /**
     * For the back button, goes back to another view.
     */
    @FXML
    private void onChooseFlightClick() {
        ViewSwitcher.switchTo(View.VELJAFLUG);
    }

    /**
     * Hover over seats with mouse tells you what number the seat is and whether it is available
     */
    @FXML
    public void tellMe(MouseEvent mouseEvent) {
        Seat source = (Seat) mouseEvent.getSource();
        StringBuilder seatInfo = new StringBuilder(source.getSeatID());

        seatInfo.append(source.isAvailable() ? " is available" : " is unavailable");

        if (source.isEmergency()) {
            seatInfo.append(" and is an Emergency Exit");
        }

        hoverOverText.setText("Seat number: " + seatInfo);
    }
    /**
     * Seleting and deselecting seats. Does nothing if seat was taken before the screen was loaded.
     */
    @FXML
    public void clickseat(MouseEvent mouseEvent) {
        Seat source = (Seat) mouseEvent.getSource();

        if (!source.isAvailable()) return;

        if (selectedSeats.contains(source)) {
            selectedSeats.remove(source);
            setupSeat(source);
            hoverOverText.setText("Seat deselected: " + source.getSeatID());
        } else {
            selectedSeats.add(source);
            setupSeat(source);
            hoverOverText.setText("Selected seat: " + source.getSeatID());
        }
    }
    /**
     * For confirmation button set what was selected into database and ready for display on the next screen
     */
    @FXML
    public void onConfirmBookingClick(ActionEvent actionEvent) {
        Flight flight = DataHolder.getFlight();
        Ticket baseTicket = DataHolder.getSelectedTicket();
        TicketDB ticketDB = new TicketDB();

        for (Seat seat : selectedSeats) {
            Ticket ticket = new Ticket(
                    "T" + System.nanoTime(),
                    baseTicket.getHolder(),
                    seat.getSeatID(),
                    flight,
                    baseTicket.getExtraBaggage()
            );
            ticketDB.addTicketToDB(ticket);
        }

        DisplayTicketController.selectedSeats = new ArrayList<>(selectedSeats);
        ViewSwitcher.switchTo(View.TICKET);
    }
}
