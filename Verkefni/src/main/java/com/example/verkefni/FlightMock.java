package com.example.verkefni;

import com.example.verkefni.modules.Flight;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Random;

public class FlightMock {
    Flight[] mock;
    public FlightMock(int flightCount){
        mock = new Flight[flightCount];
        for (int i=0; i<flightCount; i++){
            int[] rand = randomGenerator();
            LocalDateTime departureTime = generateRandomDateTime(2025);
            mock[i] = new Flight("", randomAirline(), departureTime, offsetFlightTime(departureTime,
                    rand), getLocation(rand[1]),getLocation(rand[2]),40);
        }
    }

    private String randomAirline() {
        Random r = new Random();
        int foo = r.nextInt(3);
        return switch (foo) {
            case 0 -> "Flugfélagið Mýflug";
            case 1 -> "Icelandair";
            case 2 -> "Norlandair";
            default -> null;
        };
    }
    public LocalDateTime offsetFlightTime(LocalDateTime departureTime, int[] i) {
        if(i[0]%2 == i[1]%2){
            return departureTime.plusHours(1).plusMinutes(5);
        } else return departureTime.plusMinutes(45);
    }
    private int[] randomGenerator() {
        Random r = new Random();
        int[] rand = new int[2];
        rand[0] = r.nextInt(4);
        do {
            rand[1]=r.nextInt(4);
        } while (rand[0] != rand[1]);
        return rand;
    }
    public static LocalDateTime generateRandomDateTime(int year) {
        Random random = new Random();

        // Randomize month (1 to 12)
        int month = random.nextInt(12) + 1;

        // Randomize day based on month (handles different month lengths, including leap years)
        int day = random.nextInt(Month.of(month).length(isLeapYear(year))) + 1;

        // Randomize hour (0 to 23)
        int hour = random.nextInt(24);

        // Randomize minute (in increments of 15 -> 0, 15, 30, 45)
        int minute = random.nextInt(4) * 15;

        return LocalDateTime.of(year, month, day, hour, minute);
    }
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    private String getLocation(int number){
        return switch (number) {
            case 0 -> "Akureyri";
            case 1 -> "Egilsstaðir";
            case 2 -> "Vestmannaeyjar";
            case 3 -> "Reykjavík";
            default -> null;
        };
    }
}
