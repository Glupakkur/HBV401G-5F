package database;

import modules.Flight;

import java.sql.*;
import java.util.ArrayList;

public class FlightDB {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/sql/flight.db";

    public Flight[] getAllFlights() {
        ArrayList<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM Flights";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                flights.add(new Flight(
                        rs.getString("flight_id"),
                        rs.getString("airline"),
                        rs.getTimestamp("departure_time").toLocalDateTime(),
                        rs.getTimestamp("arrival_time").toLocalDateTime(),
                        rs.getString("departure_location"),
                        rs.getString("arrival_location"),
                        rs.getInt("total_seats")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights.toArray(new Flight[0]);
    }

    public Flight[] findFlights(String from, String to) {
        ArrayList<Flight> results = new ArrayList<>();
        String query = "SELECT * FROM Flights WHERE departure_location = ? AND arrival_location = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, from);
            stmt.setString(2, to);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(new Flight(
                        rs.getString("flight_id"),
                        rs.getString("airline"),
                        rs.getTimestamp("departure_time").toLocalDateTime(),
                        rs.getTimestamp("arrival_time").toLocalDateTime(),
                        rs.getString("departure_location"),
                        rs.getString("arrival_location"),
                        rs.getInt("total_seats")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results.toArray(new Flight[0]);
    }
}
