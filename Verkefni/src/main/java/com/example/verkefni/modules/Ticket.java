package com.example.verkefni.modules;

public class Ticket {
    private String ticketID;
    private Customer ticketHolder;
    private String seatID;
    private Flight flight;
    private int extraBaggage;


    // general creator
    public Ticket(Customer customer, String seatID, Flight flight, int extraBaggage){
        this.ticketID = (flight.getFlightID()) + seatID;
        this.ticketHolder = customer;
        this.seatID = seatID;
        this.flight = flight;
        this.extraBaggage = extraBaggage;
    }

    // creator for database
    public Ticket(String ticketID, Customer customer, String seatID, Flight flight, int extraBaggage){
        this.ticketID = ticketID;
        this.ticketHolder = customer;
        this.seatID = seatID;
        this.flight = flight;
        this.extraBaggage = extraBaggage;
    }
    public void cancelTicket() {
        this.ticketHolder = null;
        this.seatID = null;
        this.flight = null;
        this.extraBaggage = 0;
    }

    public void addExtraBaggage(int weight) {
        this.extraBaggage += weight;
    }

    public int getExtraBaggage() {
        return extraBaggage;
    }

    // Getters
    public String getTicketID() {
        return ticketID;
    }

    public Customer getHolder() {
        return ticketHolder;
    }

    public Flight getFlight() {
        return flight;
    }

    public String getSeat() {
        return seatID;
    }
}
