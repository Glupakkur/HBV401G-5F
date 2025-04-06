package database;
import com.example.verkefni.modules.Customer;
import com.example.verkefni.modules.Flight;
import com.example.verkefni.modules.Ticket;
import java.sql.*;
import java.util.ArrayList;

public class TicketDB {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/sql/flight.db";

    public Ticket[] getAllTickets(){
        ArrayList<Ticket> tickets = new ArrayList<>();
        String query = "SELECT " +
                "t.ticketID, t.seatID, t.extraBaggage, " +
                "c.dateOfBirth, c.sex, c.nationality, c.firstName, c.lastName, " +
                "c.phone, c.email, " +
                "f.flightID, f.airline, f.departureTime, f.arrivalTime, f.departureLocation, " +
                "f.arrivalLocation, f.emptySeatsCount " +
                "FROM Tickets t " +
                "JOIN Customers c ON t.customerID = c.customerID " +
                "JOIN Flights f ON t.flightID = f.flightID";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()){
                Customer customer = new Customer(
                        rs.getDate("dateOfBirth").toLocalDate(),
                        rs.getString("sex"),
                        rs.getString("nationality"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phone"),
                        rs.getString("email"));
                Flight flight = new Flight(
                        rs.getString("flightID"),
                        rs.getString("airline"),
                        rs.getTimestamp("departureTime").toLocalDateTime(),
                        rs.getTimestamp("arrivalTime").toLocalDateTime(),
                        rs.getString("departureLocation"),
                        rs.getString("arrivalLocation"),
                        null,  // Seat[] - skipped for now
                        rs.getInt("emptySeatsCount"),
                        null   // Ticket[] - circular, will handle later if needed
                );
                tickets.add(new Ticket(
                        rs.getString("ticketID"),
                        customer,
                        rs.getString("seatID"),
                        flight,
                        rs.getInt("extraBaggage")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tickets.toArray(new Ticket[0]);
    }

    public void addTicketToDB(Ticket ticket){
        checkTables();
        Customer customer = ticket.getHolder();
        String insertCustomerSQL = "INSERT INTO Customers " +
                "(dateOfBirth, sex, nationality, firstName, lastName, phone, email) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);) {
            conn.setAutoCommit(false);

            // Höndla Customer
            int customerID;
            try (PreparedStatement psCustomer = conn.prepareStatement(insertCustomerSQL, Statement.RETURN_GENERATED_KEYS)) {
                psCustomer.setDate(1, Date.valueOf(customer.getDateOfBirth()));
                psCustomer.setString(2, customer.getSex());
                psCustomer.setString(3, customer.getNationality());
                psCustomer.setString(4, customer.getFirstName());
                psCustomer.setString(5, customer.getLastName());
                psCustomer.setString(6, customer.getPhone());
                psCustomer.setString(7, customer.getEmail());
                psCustomer.executeUpdate();

                try (ResultSet rs = psCustomer.getGeneratedKeys()) {
                    if (rs.next()) {
                        customerID = rs.getInt(1);
                    } else {
                        throw new SQLException("Creating customer failed, no ID obtained.");
                    }
                }
            }

            // Höndla miða
            String insertTicketSQL = "INSERT INTO Tickets (ticketID, customerID, seatID, flightID, extraBaggage) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement psTicket = conn.prepareStatement(insertTicketSQL)) {
                psTicket.setString(1, ticket.getTicketID());
                psTicket.setInt(2, customerID);
                psTicket.setString(3, ticket.getSeat());
                psTicket.setString(4, ticket.getFlight().getFlightID());
                psTicket.setInt(5, ticket.getExtraBaggage());

                psTicket.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void checkTables() {
        String createCustomersTableSQL = "CREATE TABLE IF NOT EXISTS Customers ("
                + "customerID INT AUTO_INCREMENT PRIMARY KEY, "
                + "dateOfBirth DATE, "
                + "sex VARCHAR(10), "
                + "nationality VARCHAR(50), "
                + "firstName VARCHAR(100), "
                + "lastName VARCHAR(100), "
                + "phone VARCHAR(20), "
                + "email VARCHAR(100))";

        String createTicketsTableSQL = "CREATE TABLE IF NOT EXISTS Tickets ("
                + "ticketID VARCHAR(50) PRIMARY KEY, "
                + "customerID INT, "
                + "seatID VARCHAR(50), "
                + "flightID VARCHAR(50), "
                + "extraBaggage INT, "
                + "FOREIGN KEY (customerID) REFERENCES Customers(customerID) ON DELETE CASCADE)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            //
            stmt.executeUpdate(createCustomersTableSQL);
            System.out.println("Customers table checked/created successfully.");

            //
            stmt.executeUpdate(createTicketsTableSQL);
            System.out.println("Tickets table checked/created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
