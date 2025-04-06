package com.example.verkefni.vidmot;

import com.example.verkefni.modules.Flight;
import com.example.verkefni.modules.Ticket;
import com.example.verkefni.modules.Customer;
import com.example.verkefni.modules.Seat;

public class DataHolder {
    private static Flight selectedFlight;
    private static Ticket selectedTicket;
    private static Customer selectedCustomer;
    private static Seat selectedSeat;

    public static Ticket getSelectedTicket() {
        return selectedTicket;
    }

    public static void setSelectedTicket(Ticket selectedTicket) {
        DataHolder.selectedTicket = selectedTicket;
    }

    public static Flight getFlight() {
        return selectedFlight;
    }

    public static void setFlight(Flight flight) {
        selectedFlight = flight;
    }

    public static Customer getCustomer() {
        return selectedCustomer;
    }

    public static void setCustomer(Customer customer) {
        selectedCustomer = customer;
    }

    public static Seat getSelectedSeat() {
        return selectedSeat;
    }

    public static void setSelectedSeat(Seat seat) {
        selectedSeat = seat;
    }
}
