package com.example.verkefni.vidmot;

import com.example.verkefni.modules.Customer;
import com.example.verkefni.modules.Flight;
import com.example.verkefni.modules.Ticket;
import database.FlightDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class VeljaFlugController {
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public TextField phoneField;

    @FXML private ComboBox<String> departureLocationBox;
    @FXML private ComboBox<String> arrivalLocationBox;
    @FXML private DatePicker datePicker;


    @FXML
    private ComboBox<Flight> flugComboBox;

    private ObservableList<Flight> listOfFlights;
    private final FlightDB flightDB = new FlightDB();

    @FXML
    private void onViewSeatMapClick() {
        Customer customer = new Customer(
                LocalDate.of(1990, 1, 1), // Replace later with real date picker
                null,
                null,
                firstNameField.getText(),
                lastNameField.getText(),
                phoneField.getText(),
                null
        );

        Flight selected = flugComboBox.getValue();
        if (selected != null) {
            Ticket ticket = new Ticket(customer, null, selected, 0); // Create base ticket
            DataHolder.setSelectedTicket(ticket);
            DataHolder.setFlight(selected);
            ViewSwitcher.switchTo(View.SEATMAP);
        }
    }


    public void initialize() {
        Flight[] flights = flightDB.getAllFlights();
        listOfFlights = FXCollections.observableArrayList(flights);
        flugComboBox.setItems(listOfFlights);
        String[] departures = flightDB.getAllDepartureLocations();
        String[] arrivals = flightDB.getAllArrivalLocations();

        departureLocationBox.setItems(FXCollections.observableArrayList(departures));
        arrivalLocationBox.setItems(FXCollections.observableArrayList(arrivals));
    }

    @FXML
    private void onSearchFlightsClick() {
        String from = departureLocationBox.getValue();
        String to = arrivalLocationBox.getValue();
        LocalDate date = datePicker.getValue();

        if (from != null && to != null && date != null) {
            Flight[] filtered = flightDB.findFlights(from, to, date);
            listOfFlights.setAll(filtered);
            flugComboBox.setItems(listOfFlights);
        }
    }


}
