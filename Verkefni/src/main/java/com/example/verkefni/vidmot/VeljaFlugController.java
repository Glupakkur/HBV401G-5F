package com.example.verkefni.vidmot;

import com.example.verkefni.modules.Flight;
import com.example.verkefni.FlightMock;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class VeljaFlugController {

    @FXML
    private ComboBox<Flight> flugComboBox;

    private ObservableList<Flight> listOfFlights;

    public void initialize() {
        listOfFlights = FXCollections.observableArrayList();

        FlightMock flightMock = new FlightMock(3);
        Flight[] flights = flightMock.getMock();

        listOfFlights.addAll(flights);
        flugComboBox.setItems(listOfFlights);
    }
}
