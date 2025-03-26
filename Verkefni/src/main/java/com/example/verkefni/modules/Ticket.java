package modules;

public class Ticket {
    private String ticketID;
    private Customer ticketHolder;
    private String seatID;
    private Flight flight;
    private int extraBaggage;

    public Ticket(Customer customer, String seatID, Flight flight, int extrabaggge){
        this.ticketID = (flight.getFlightID()) + seatID;
        this.ticketHolder = customer;
        this.seatID = seatID;
        this.flight = flight;
        this.extraBaggage= extrabaggge;
    }

}
