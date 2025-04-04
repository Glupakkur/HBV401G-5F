package com.example.verkefni.vidmot;

import com.example.verkefni.modules.Customer;
import com.example.verkefni.modules.Flight;
import com.example.verkefni.FlightMock;
import com.example.verkefni.modules.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class VeljaFlugController {
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public TextField phoneField;


    @FXML
    private ComboBox<Flight> flugComboBox;
    @FXML
    private void onViewSeatMapClick() {
        Customer customer = new Customer(
                LocalDate.of(1990, 1, 1), // Placeholder birth date
                null,
                null,
                firstNameField.getText(),
                lastNameField.getText(),
                phoneField.getText(),
                null
        );


        Flight selected = flugComboBox.getValue();
        if (selected != null) {
            Ticket ticket = new Ticket(customer, null, selected, 0);
            DataHolder.setSelectedTicket(ticket);
            DataHolder.setFlight(selected);
            ViewSwitcher.switchTo(View.SEATMAP);
        }
    }


    private ObservableList<Flight> listOfFlights;
    private static final Flight[] staticMockFlights = new FlightMock(3).getMock();


    public void initialize() {
        listOfFlights = FXCollections.observableArrayList(staticMockFlights);
        flugComboBox.setItems(listOfFlights);
    }
}
