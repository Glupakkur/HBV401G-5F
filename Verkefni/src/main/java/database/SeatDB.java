package database;

import com.example.verkefni.modules.Seat;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SeatDB {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/sql/flight.db";

    public void generateSeatsForFlight(String flightId, int totalSeats) {
        String sql = "INSERT INTO Seats (flight_id, seat_number) VALUES (?, ?)";
        int seatsPerRow = 4;
        int totalRows = (int) Math.ceil(totalSeats / (double) seatsPerRow);

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int seatCounter = 0;

            for (int rowIndex = 0; rowIndex < totalRows && seatCounter < totalSeats; rowIndex++) {
                String rowLetter = numberToLetter(rowIndex);  // A, B, ..., Z, AA, etc.
                for (int seatNum = 1; seatNum <= seatsPerRow && seatCounter < totalSeats; seatNum++) {
                    String seatLabel = rowLetter + seatNum;
                    stmt.setString(1, flightId);
                    stmt.setString(2, seatLabel);
                    stmt.addBatch();
                    seatCounter++;
                }
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. Get all seat numbers for a flight
    public ArrayList<String> getSeatsForFlight(String flightId) {
        ArrayList<String> seats = new ArrayList<>();
        String query = "SELECT seat_number FROM Seats WHERE flight_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, flightId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                seats.add(rs.getString("seat_number"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }

    // 3. Get seat numbers and taken status
    public Map<String, Boolean> getSeatStatusForFlight(String flightId) {
        Map<String, Boolean> seatStatus = new LinkedHashMap<>();
        String query = "SELECT seat_number, is_taken FROM Seats WHERE flight_id = ? ORDER BY seat_number";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, flightId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                seatStatus.put(rs.getString("seat_number"), rs.getBoolean("is_taken"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seatStatus;
    }


    public void markSeatAsTaken(String flightId, String seatNumber) {
        String sql = "UPDATE Seats SET is_taken = 1 WHERE flight_id = ? AND seat_number = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, flightId);
            stmt.setString(2, seatNumber);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper: Convert index to row letter(s)
    private String numberToLetter(int n) {
        StringBuilder label = new StringBuilder();
        n++; // 1-based
        while (n > 0) {
            n--;
            label.insert(0, (char) ('A' + (n % 26)));
            n /= 26;
        }
        return label.toString();
    }

    public ArrayList<Seat> getSeatObjectsForFlight(String flightId) {
        ArrayList<Seat> seats = new ArrayList<>();
        String query = "SELECT seat_number, is_taken FROM Seats WHERE flight_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, flightId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String seatId = rs.getString("seat_number");
                boolean taken = rs.getBoolean("is_taken");

                Seat seat = new Seat(seatId, false); // emergency = false for now
                seat.setAvailable(!taken);           // inverse logic (DB stores taken)

                seats.add(seat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }




}
