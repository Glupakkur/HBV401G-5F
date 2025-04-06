package database;

import com.example.verkefni.modules.Flight;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
                        LocalDateTime.parse(rs.getString("departure_time")),
                        LocalDateTime.parse(rs.getString("arrival_time")),
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

    public Flight[] findFlights(String from, String to, LocalDate date) {
        ArrayList<Flight> results = new ArrayList<>();
        String query = "SELECT * FROM Flights WHERE departure_location = ? AND arrival_location = ? AND DATE(departure_time) = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, from);
            stmt.setString(2, to);
            stmt.setString(3, date.toString());  // e.g. "2025-05-28"

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(new Flight(
                        rs.getString("flight_id"),
                        rs.getString("airline"),
                        LocalDateTime.parse(rs.getString("departure_time")),
                        LocalDateTime.parse(rs.getString("arrival_time")),
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


    public String[] getAllDepartureLocations() {
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT DISTINCT departure_location FROM Flights ORDER BY departure_location";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list.toArray(new String[0]);
    }

    public String[] getAllArrivalLocations() {
        ArrayList<String> list = new ArrayList<>();
        String query = "SELECT DISTINCT arrival_location FROM Flights ORDER BY arrival_location";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                list.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list.toArray(new String[0]);
    }


}
