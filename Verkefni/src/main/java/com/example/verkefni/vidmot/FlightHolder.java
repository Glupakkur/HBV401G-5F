package com.example.verkefni.shared;

import com.example.verkefni.modules.Flight;

public class FlightHolder {
    private static Flight selectedFlight;

    public static void setFlight(Flight flight) {
        selectedFlight = flight;
    }

    public static Flight getFlight() {
        return selectedFlight;
    }
}
