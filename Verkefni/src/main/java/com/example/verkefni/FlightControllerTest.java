import modules.Flight;
import modules.FlightMock;
import controllers.FlightController;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class FlightControllerTest {
    private FlightController flightController;
    private Flight[] flights;

    @BeforeEach
    void setup() {
        flightController = new FlightController();
        FlightMock flightMock = new FlightMock(10);
        flights = flightMock.mock;
        flightController.setFlights(flights);
    }

    @AfterEach
    public void teardown() {
        flightController = null;
        flights = null;
    }

    @Test
    void testSearchFlightsFound() {
        Flight flight = flights[0];
        String departure = flight.getDepartureLocation();
        String arrival = flight.getArrivalLocation();
        Flight[] results = flightController.searchFlights(departure, arrival);

        assertTrue(results.length >= 1, "No flights found.");

        for (int i = 0; i < results.length; i++) {
            assertEquals(departure, results[i].getDepartureLocation(), "Departure location does not match.");
            assertEquals(arrival, results[i].getArrivalLocation(), "Arrival location does not match.");
        }
    }

    @Test
    void testSearchFlightsNotFound() {
        Flight[] results = flightController.searchFlights("Candyland", "Ultima Thule");

        assertEquals(0, results.length, "The search should return no flights.");
    }

    @Test
    void testSelectFlight() {
        Flight flight = flights[0];

        flightController.select(flight);

        assertEquals(flight, flightController.getSelectedFlight(), "The selected flight does not match.");
    }

    @Test
    void testUpdateRemainingSeats() {
        Flight flight = flights[0];
        int initialCount = flight.getEmptySeatsCount();

        flightController.updateRemainingSeats(flight, 1);

        assertEquals(initialCount - 1, flight.getEmptySeatsCount(), "Empty seat count should decrease by 1 after reserving 1 seat.");
    }
}