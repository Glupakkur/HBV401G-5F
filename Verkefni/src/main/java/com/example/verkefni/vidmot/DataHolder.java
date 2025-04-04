package com.example.verkefni.vidmot;

import com.example.verkefni.modules.Flight;
import com.example.verkefni.modules.Ticket;

public class DataHolder {
    private static Flight selectedFlight;

    public static Ticket getSelectedTicket() {
        return selectedTicket;
    }

    public static void setSelectedTicket(Ticket selectedTicket) {
        DataHolder.selectedTicket = selectedTicket;
    }

    private static Ticket selectedTicket;

    public static void setFlight(Flight flight) {
        selectedFlight = flight;
    }

    public static Flight getFlight() {
        return selectedFlight;
    }
}
