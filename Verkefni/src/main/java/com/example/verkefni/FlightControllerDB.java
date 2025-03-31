package com.example.verkefni;

import com.example.verkefni.modules.Flight;
import database.FlightDB;

class FlightController {
    private FlightDB flightDB;
    private Flight selectedFlight;

    public FlightController() {
        this.flightDB = new FlightDB();
    }

    // Search directly from DB
    public Flight[] searchFlights(String departure, String arrival) {
        return flightDB.findFlights(departure, arrival);
    }

    // Get all flights from DB (optional)
    public Flight[] getAllFlights() {
        return flightDB.getAllFlights();
    }

    // Update local selection
    public void select(Flight f) {
        this.selectedFlight = f;
    }

    // Update seat count logic (if managed locally)
    public void updateRemainingSeats(Flight f, int seatsUsed) {
        for (int i = 0; i < seatsUsed; i++) {
            f.updateSeatCount();
        } // still local
        // You can optionally update DB here too
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }
}
