package com.example.verkefni.modules;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight {
    private String flightID;
    private String airline;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String departureLocation;
    private String arrivalLocation;
    private Seat[] seats;
    private int emptyseatscount;
    private Ticket[] tickets;

    public Flight(String flightID, String airline, LocalDateTime departureTime, LocalDateTime arrivalTime,
                  String departureLocation, String arrivalLocation, int seatCount) {
        this.flightID = flightID;
        this.airline = airline;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;

        emptyseatscount = seatCount;
        tickets = new Ticket[seatCount];
        seats = new Seat[seatCount];

        char letter = 'A';
        for (int i = 0; i < seatCount / 4; i++) {
            for (int j = 0; j < 4; j++) {
                seats[i * 4 + j] = new Seat(letter + Integer.toString(j + 1), false);
            }
            letter++;
        }


    }

    // empty container, useable for testing
    public Flight () {

    }

    // constructor for db handling
    public Flight(String flightID, String airline, LocalDateTime departureTime, LocalDateTime arrivalTime,
                  String departureLocation, String arrivalLocation, Seat [] seats, int emptySeatsCount, Ticket[] ticket) {
    }


    public boolean hasEmptySeat(){
        return emptyseatscount > 0;
    }
   public void updateSeatCount() {
        if (hasEmptySeat()) emptyseatscount--;
        else throw new RuntimeException("Seats are full");
    }




    public String getFlightID() { return flightID; }
    public void setFlightID(String flightID) { this.flightID = flightID; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public String getDepartureLocation() { return departureLocation; }
    public void setDepartureLocation(String departureLocation) { this.departureLocation = departureLocation; }

    public String getArrivalLocation() { return arrivalLocation; }
    public void setArrivalLocation(String arrivalLocation) { this.arrivalLocation = arrivalLocation; }
    public Seat[] getSeats() { return seats; }
    public int getEmptySeatsCount() { return emptyseatscount; }
    public Ticket[] getTickets() { return tickets; }
    public void initTicketsAndSeats(int num) {
        this.tickets = new Ticket[num];
        this.seats = new Seat[num];
        emptyseatscount = num;
    }


    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        return String.format(
                "%s → %s | %s %s–%s ",
                departureLocation,
                arrivalLocation,
                departureTime.format(dateFormat),
                departureTime.format(timeFormat),
                arrivalTime.format(timeFormat)
        );
    }

}
