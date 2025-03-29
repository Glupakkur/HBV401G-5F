import modules.Flight;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class FlightTest {
    private Flight flight;

    @BeforeEach
    void setup(){
        flight = new Flight("testID","foobar Express",
                FlightMock.generateRandomDateTime(1999),
                FlightMock.generateRandomDateTime(1999),
                "foosville",
                "bar-state Area",
                20);
    }

    @AfterEach
    public void teardown(){
        flight = null;
    }

    @Test
    void testHasEmptySeat() {
        assertTrue(flight.hasEmptySeat(), "This flight does in fact have empty seats.");
    }

    @Test
    void testHasEmptySeatWhenFull() {
        int seatcount = flight.getEmptySeatsCount();

        for (int i = 0; i < seatcount; i++) {
            flight.updateSeatCount();
        }

        assertFalse(flight.hasEmptySeat(), "After using all seats, this flight should not have any empty seats.");
    }

    @Test
    void testUpdateSeatCount() {
        int initialSeatCount = flight.getEmptySeatCount();

        flight.updateSeatCount();
        assertEquals(initialSeatCount - 1, flight.getEmptySeatCount(), "Empty seat count should decrease by 1 after reserving 1 seat.");
    }

    @Test
    void testUpdateSeatCountException() {
        while (flight.hasEmptySeat()) {
            flight.updateSeatCount();
        }

        assertThrows(RuntimeException.class, flight::updateSeatCount, "Exception not thrown when all seats are taken.");
    }

    @Test
    void testSeatGeneration() {
        Seat[] seats = flight.getSeats();

        assertEquals("A1", seats[0].getSeatID(), "First seat ID should be A1");
        assertEquals("C4", seats[11].getSeatID(), "12th seat ID should be C4");
    }

    @Test
    void testProperFlightGeneration() {
        int flightCount = 10;
        FlightMock flightMock = new FlightMock(flightCount);

        assertNotNull(flightMock.mock, "The 'mock' array should not be null.");
        assertEquals(flightCount, flightMock.mock.length, "The number of flights does not match.");

        for (int i = 0; i < flightMock.mock.length; i++) {
            Flight flight = flightMock.mock[i];
            assertNotNull(flight.getDepartureLocation(), "Departure location should not be null.");
            assertNotNull(flight.getArrivalLocation(), "Arrival location should not be null.");
        }
    }

    @Test
    void testSeatArrayLength() {
        assertNotNull(flight.getSeats(), "The seats array should not be null.");
        assertEquals(20, flight.getSeats().length, "The seat array length should match the seat count.");
    }

    @Test
    void testTicketArrayLength() {
        assertNotNull(flight.getTickets(), "The ticket array should not be null.");
        assertEquals(20, flight.getTickets().length, "The ticket array length should match the seat count.");
    }

    // Test the getters.
    @Test
    void testGetterValues() {
        assertEquals("testID", flight.getFlightID(), "Flight ID does not match.");
        assertEquals("foobar Express", flight.getAirline(), "Airline does not match.");
        assertEquals("foosville", flight.getDepartureLocation(), "Departure location does not match.");
        assertEquals("bar-state Area", flight.getArrivalLocation(), "Arrival location does not match.");
    }
}
