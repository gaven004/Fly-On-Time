package com.g.fot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AirportRepositoryTest {
    @Autowired
    AirportRepository repository;

    @Test
    void testFindAll() {
        final Iterable<Airport> airports = repository.findAll();
        assertNotNull(airports);
        airports.forEach(System.out::println);
    }
}
