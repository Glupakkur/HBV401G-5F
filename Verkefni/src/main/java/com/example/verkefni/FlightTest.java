package com.example.verkefni;
import com.example.verkefni.modules.Flight;

import java.time.LocalDateTime;

public class FlightTest {
    private Flight f;

    void setup(){
        f = new Flight("testID","foobar Express", FlightMock.generateRandomDateTime(1999),
                FlightMock.generateRandomDateTime(1999), "foosville",
                "bar-state Area", 20);
        f = new Flight();
    }

    public void teardown(){
        f = null;
    }
}
