package com.g.fot;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlightMapperTest {
    @Autowired
    FlightMapper repository;

    @Test
    void testCountAll() {
        Optional<FlightStat> flightStat = repository.countAll("广州", "北京");
        System.out.println("flightStat = " + flightStat.get());

        flightStat = repository.countAll("广州", "");
        System.out.println("flightStat = " + flightStat.get());

        flightStat = repository.countAll("", "北京");
        System.out.println("flightStat = " + flightStat.get());

        flightStat = repository.countAll("", "");
        System.out.println("flightStat = " + flightStat.get());
    }

    @Test
    void testCountByAirlines() {
        final List<FlightStat> iterable = repository.countByAirlines("广州", "北京");
        assertNotNull(iterable);
        iterable.forEach(System.out::println);
    }

    @Test
    void testCountByHour() {
        final List<FlightStat> iterable = repository.countByHour("广州", "北京");
        assertNotNull(iterable);
        iterable.forEach(System.out::println);
    }

    @Test
    void testFindTopFlight() {
        final List<FlightStat> iterable = repository.findTopFlight("广州", "北京");
        assertNotNull(iterable);
        iterable.forEach(System.out::println);
    }

    @Test
    void testFindBottomFlight() {
        final List<FlightStat> iterable = repository.findBottomFlight("广州", "北京");
        assertNotNull(iterable);
        iterable.forEach(System.out::println);
    }
}
