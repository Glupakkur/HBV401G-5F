package com.example.verkefni.vidmot;

import com.example.verkefni.modules.Customer;
import com.example.verkefni.modules.Flight;
import com.example.verkefni.modules.Ticket;
import database.FlightDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class VeljaFlugController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<String> departureLocationBox;

    @FXML
    private ComboBox<String> arrivalLocationBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<Flight> flugComboBox;

    private final FlightDB flightDB = new FlightDB();
    private ObservableList<Flight> listOfFlights;

    /**
     * Set up the view with a several comboboxes with all flights, departure locations and arrival location.
     */
    public void initialize() {
        Flight[] flights = flightDB.getAllFlights();
        listOfFlights = FXCollections.observableArrayList(flights);
        flugComboBox.setItems(listOfFlights);

        String[] departures = flightDB.getAllDepartureLocations();
        String[] arrivals = flightDB.getAllArrivalLocations();

        departureLocationBox.setItems(FXCollections.observableArrayList(departures));
        arrivalLocationBox.setItems(FXCollections.observableArrayList(arrivals));
    }
    /**
     * Create new customer and get data from textfields, get the flight that was selected
     * save it in the Dataholder to display on the final screen
     */
    @FXML
    private void onViewSeatMapClick() {
        Customer customer = new Customer(
                LocalDate.of(1990, 1, 1),
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
    /**
     * Filter the combobox of all flights depending on locations and date selected
     */
    @FXML
    private void onSearchFlightsClick() {
        String from = departureLocationBox.getValue();
        String to = arrivalLocationBox.getValue();
        LocalDate date = datePicker.getValue();

        Flight[] filtered;

        if (from != null && to != null && date != null) {
            filtered = flightDB.findFlights(from, to, date);
        } else if (from != null && to != null) {
            filtered = flightDB.findFlights(from, to);
        } else {
            return;
        }

        listOfFlights.setAll(filtered);
        flugComboBox.setItems(listOfFlights);
    }
    /**
     * Returns all flight combobox to original state
     */
    @FXML
    public void onClearSelectionClick(ActionEvent actionEvent) {
        Flight[] flights = flightDB.getAllFlights();
        listOfFlights = FXCollections.observableArrayList(flights);
        flugComboBox.setItems(listOfFlights);

        String[] departures = flightDB.getAllDepartureLocations();
        String[] arrivals = flightDB.getAllArrivalLocations();

        departureLocationBox.setItems(FXCollections.observableArrayList(departures));
        arrivalLocationBox.setItems(FXCollections.observableArrayList(arrivals));
    }
}
