package com.example.verkefni.vidmot;

import com.example.verkefni.modules.Customer;
import com.example.verkefni.modules.Flight;
import com.example.verkefni.modules.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class DisplayTicketController {
    @FXML private Label firstNameLabel;
    @FXML private Label lastNameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label flightNameLabel;
    @FXML private Label seatLabel;
    @FXML private Label baggageLabel;

    @FXML
    public void initialize() {
        Ticket ticket = DataHolder.getSelectedTicket();

        if (ticket == null) return;

        Customer customer = ticket.getHolder();
        Flight flight = ticket.getFlight();

        if (customer != null) {
            firstNameLabel.setText(customer.getFirstName());
            lastNameLabel.setText(customer.getLastName());
            phoneLabel.setText(customer.getPhone());
        }

        if (flight != null) {
            flightNameLabel.setText(flight.toString());  // uses Flight.toString()
        }

        seatLabel.setText(ticket.getSeat() != null ? ticket.getSeat() : "-");
        baggageLabel.setText(ticket.getFlight() != null ? String.valueOf(ticket.getFlight()) : "0");
        baggageLabel.setText(String.valueOf(ticket.getExtraBaggage()));
    }

    @FXML
    private void onDoneClick() {
        ViewSwitcher.switchTo(View.VELJAFLUG);
    }
}
