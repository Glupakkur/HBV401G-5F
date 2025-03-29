package com.example.verkefni;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;
import modules.Flight;

public class FlightController {
    private Flight[] flights;
    private Flight selectedFlight;

    // Populate "flights".
    public void setFlights(Flight[] flights) {
        this.flights = flights;
    }

    // Search flights and pack them into a vector.
    public Flight[] searchFlights(String departure, String arrival) {
        ArrayList<Flight> results = new ArrayList<>();

        if (flights != null) {
            for (int i = 0; i < flights.length; i++) {
                Flight flight = flights[i];

                if (flight.getDepartureLocation().equals(departure) && flight.getArrivalLocation().equals(arrival)) {
                    results.add(flight);
                }
            }
        }
        // Convert the vector of all relevant flights into an array.
        return results.toArray(new Flight[0]);
    }

    // Update the remaining seats of a given flight.
    public void updateRemainingSeats(Flight f, int seatsUsed) {
        f.updateSeatCount(seatsUsed);
    }

    // Select a flight chosen by the user.
    public void select(Flight f) {
        this.selectedFlight = f;
    }

}
