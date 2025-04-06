package com.example.verkefni;
import database.TicketDB;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.example.verkefni.modules.Customer;
import com.example.verkefni.modules.Flight;
import com.example.verkefni.modules.Seat;
import com.example.verkefni.modules.Ticket;

public class TicketController {
    private TicketDB ticketDB;
    private static final int TICKET_LIMIT = 20;
    private Ticket[] tickets;
    private int userTicketCount;

    // List of tickets
    public Ticket[] newTickets() {
        tickets = new Ticket[TICKET_LIMIT];
        userTicketCount = 0;

        return tickets;
    }
    public TicketController(){
        tickets = newTickets();
    }
    public Ticket createTicket(Customer customer, Seat seat, Flight flight, int baggage) {
        if (seat.isAvailable()) {
            seat.setAvailable(false);
            Ticket ticket = new Ticket(customer, seat.getSeatID(), flight, baggage);
            addTicket(ticket);

            return ticket;
        } else {
            System.out.println("Seat " + seat.getSeatID() + " not available.");

            return null;
        }
    }

    // Add ticket to the customer's ticket list.
    private void addTicket(Ticket ticket) {
        if (ticket == null) return;

        if (userTicketCount < TICKET_LIMIT) {
            tickets[userTicketCount++] = ticket;
        } else {
            System.out.println("Max ticket limit reached.");
        }
    }

    public void extraBaggage(Ticket ticket, int weight) {
        ticket.addExtraBaggage(weight);
    }

    public void cancel(Ticket ticket) {
        ticket.cancelTicket();
    }

    // Finalise ticket selection. What else here?
    public Ticket[] confirmTickets() {
        Ticket[] allTickets = new Ticket[userTicketCount];
        System.arraycopy(tickets, 0, allTickets, 0, userTicketCount);
        for (Ticket allTicket : allTickets) {
            ticketDB.addTicketToDB(allTicket);
        }
        return allTickets;
    }

    public void newCustomer(Ticket ticket) {
        //
    }
}


