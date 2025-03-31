// Prófun á TicketController – staðfestir að miði er búinn til rétt og sæti verði frátekið
// Prófar einnig aukatöskur og jaðartilvik eins og þegar sæti er tekið
import com.example.verkefni.modules.Flight;
import com.example.verkefni.modules.Seat;
import com.example.verkefni.modules.Customer;
import com.example.verkefni.modules.Ticket;
import com.example.verkefni.TicketController;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TicketControllerTest {

    @Test
    void testCreateTicket() {
        // Búa til flug með 10 lausum sætum
        Flight flight = new Flight("FL123", "Icelandair",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2),
                "Reykjavík", "Akureyri", 10);

        // Velja fyrsta sæti
        Seat seat = flight.getSeats()[0];

        // Búa til viðskiptavin
        Customer customer = new Customer(
                LocalDate.of(1990, 1, 1), // dateOfBirth
                "Male",                  // sex
                "Icelandic",             // nationality
                "Valdimar",             // firstName
                "Johannsson",           // lastName
                "1234567890",           // phone
                "valdi@example.com"     // email
        );

        // Búa til TicketController
        TicketController ticketController = new TicketController();

        // Búa til miða
        Ticket ticket = ticketController.createTicket(customer, seat, flight, 20);

        // Prófa að miði sé ekki null og sæti ekki lengur laust
        assertNotNull(ticket, "Miði ætti að hafa verið búinn til.");
        assertFalse(seat.isAvailable(), "Sæti ætti að hafa verið merkt sem tekið.");
    }

    @Test
    void testAddExtraBaggage() {
        // Búa til flug og fyrsta sæti
        Flight flight = new Flight("FL999", "AirIceland",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                "Akureyri", "Reykjavík", 5);
        Seat seat = flight.getSeats()[0];
        Customer customer = new Customer(
                LocalDate.of(1995, 6, 15), // dateOfBirth
                "Female",                 // sex
                "Icelandic",              // nationality
                "Anna",                   // firstName
                "Sigurðardóttir",         // lastName
                "0987654321",             // phone
                "anna@example.com"        // email
        );


        TicketController ticketController = new TicketController();
        Ticket ticket = ticketController.createTicket(customer, seat, flight, 15);

        // Bæta við aukatösku
        ticketController.extraBaggage(ticket, 5);

        // Prófa að þyngd hefur aukist í 20
        assertEquals(20, ticket.getExtraBaggage(), "Farangursþyngd ætti að vera 20kg.");
    }

    @Test
    void testCreateTicketWithTakenSeat() {
        // Búa til sæti og merkja sem tekið
        Seat seat = new Seat("A1", false);
        seat.setAvailable(false);

        // Búa til flug og viðskiptavin
        Flight flight = new Flight("FL456", "Play",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                "Reykjavík", "Egilsstaðir", 5);
        Customer customer = new Customer(
                LocalDate.of(1988, 3, 12), // dateOfBirth
                "Female",                 // sex
                "Icelandic",              // nationality
                "Guðrún",                 // firstName
                "Ólafsdóttir",            // lastName
                "5555555555",             // phone
                "gudrun@example.com"      // email
        );


        // Reikna með að miði verði ekki búinn til
        TicketController controller = new TicketController();
        Ticket ticket = controller.createTicket(customer, seat, flight, 15);
        assertNull(ticket, "Ekki ætti að vera hægt að búa til miða ef sætið er frátekið.");
    }

    @Test
    void testConfirmTickets() {
        // Búa til flug og viðskiptavin
        Flight flight = new Flight("FL789", "Norlandair",
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(3),
                "Akureyri", "Vestmannaeyjar", 10);
        Customer customer = new Customer(
                LocalDate.of(1992, 11, 5), // dateOfBirth
                "Male",                   // sex
                "Icelandic",              // nationality
                "Einar",                  // firstName
                "Björnsson",              // lastName
                "9999999999",             // phone
                "einar@example.com"       // email
        );


        TicketController controller = new TicketController();
        controller.createTicket(customer, flight.getSeats()[0], flight, 10);
        controller.createTicket(customer, flight.getSeats()[1], flight, 15);

        // Sækja staðfesta miða
        Ticket[] tickets = controller.confirmTickets();

        // Próf: ættu að vera 2
        assertEquals(2, tickets.length, "Ætti að skila öllum staðfestum miðum.");
    }
}
