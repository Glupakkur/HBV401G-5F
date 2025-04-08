package com.example.verkefni.vidmot;

import com.example.verkefni.modules.Customer;
import com.example.verkefni.modules.Flight;
import com.example.verkefni.modules.Ticket;
import com.example.verkefni.modules.Seat;
import database.TicketDB;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.List;

public class DisplayTicketController {

    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label flightNameLabel;
    @FXML private Label seatLabel;
    @FXML private Label baggageLabel;

    public static List<Seat> selectedSeats;
    /**
     * Fill into various fields from dataholder class
     */
    @FXML
    public void initialize() {
        Ticket baseTicket = DataHolder.getSelectedTicket();
        if (baseTicket == null) return;

        Customer customer = baseTicket.getHolder();
        Flight flight = baseTicket.getFlight();

        if (customer != null) {
            firstNameLabel.setText(customer.getFirstName());
            lastNameLabel.setText(customer.getLastName());
            phoneLabel.setText(customer.getPhone());
        }

        if (flight != null) {
            flightNameLabel.setText(flight.toString());
        }

        StringBuilder seatText = new StringBuilder();
        if (selectedSeats != null && !selectedSeats.isEmpty()) {
            for (Seat seat : selectedSeats) {
                seatText.append(seat.getSeatID()).append(" ");
            }
        } else {
            seatText.append("-");
        }

        seatLabel.setText(seatText.toString().trim());
        baggageLabel.setText(String.valueOf(baseTicket.getExtraBaggage()));
    }
    /**
     * Go back to first view
     */
    @FXML
    private void onDoneClick() {
        ViewSwitcher.switchTo(View.VELJAFLUG);
    }
}
