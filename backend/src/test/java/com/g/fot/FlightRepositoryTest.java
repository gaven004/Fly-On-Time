package com.g.fot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlightRepositoryTest {
    @Autowired
    FlightRepository repository;

    @Test
    void testCountAll() {
        final Optional<FlightStat> flightStat = repository.countAll("广州", "北京");
        System.out.println("flightStat = " + flightStat.get());
    }

    @Test
    void testCountByAirlines() {
        final Iterable<FlightStat> iterable = repository.countByAirlines("广州", "北京");
        assertNotNull(iterable);
        iterable.forEach(System.out::println);
    }

    @Test
    void testCountByHour() {
        final Iterable<FlightStat> iterable = repository.countByHour("广州", "北京");
        assertNotNull(iterable);
        iterable.forEach(System.out::println);
    }

    @Test
    void testFindTopFlight() {
        final Iterable<FlightStat> iterable = repository.findTopFlight("广州", "北京");
        assertNotNull(iterable);
        iterable.forEach(System.out::println);
    }

    @Test
    void testFindBottomFlight() {
        final Iterable<FlightStat> iterable = repository.findBottomFlight("广州", "北京");
        assertNotNull(iterable);
        iterable.forEach(System.out::println);
    }
}
