package com.g.fot;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FotController {
    private final FlightMapper flightRepository;
    private final AirlinesRepository airlinesRepository;
    private final AirportRepository airportRepository;

    public FotController(FlightMapper flightRepository, AirlinesRepository airlinesRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airlinesRepository = airlinesRepository;
        this.airportRepository = airportRepository;
    }

    @GetMapping(value = "cities", produces = "application/json")
    public Iterable<String> getCities() {
        return airportRepository.findDistinctCity();
    }

    @GetMapping(value = "/countAll", produces = "application/json")
    public FlightStat countAll(@RequestParam(name = "source", defaultValue = "") String source,
                               @RequestParam(name = "destination", defaultValue = "") String destination) {
        final Optional<FlightStat> flightStat = flightRepository.countAll(source, destination);
        return flightStat.get();
    }

    @GetMapping(value = "/countByAirlines", produces = "application/json")
    public Iterable<FlightStat> countByAirlines(@RequestParam(name = "source", defaultValue = "") String source,
                                                @RequestParam(name = "destination", defaultValue = "") String destination) {
        final Iterable<FlightStat> iterable = flightRepository.countByAirlines(source, destination);
        iterable.forEach(item -> item.setCategory(getAirlines(item.getCategory())));
        return iterable;
    }

    @GetMapping(value = "/countByHour", produces = "application/json")
    public Iterable<FlightStat> countByHour(@RequestParam(name = "source", defaultValue = "") String source,
                                            @RequestParam(name = "destination", defaultValue = "") String destination) {
        return flightRepository.countByHour(source, destination);
    }

    @GetMapping(value = "/findTop", produces = "application/json")
    public Iterable<FlightStat> findTop(@RequestParam(name = "source", defaultValue = "") String source,
                                        @RequestParam(name = "destination", defaultValue = "") String destination) {
        return flightRepository.findTopFlight(source, destination);
    }

    @GetMapping(value = "/findBottom", produces = "application/json")
    public Iterable<FlightStat> findBottom(@RequestParam(name = "source", defaultValue = "") String source,
                                           @RequestParam(name = "destination", defaultValue = "") String destination) {
        return flightRepository.findBottomFlight(source, destination);
    }

    private String getAirlines(String code) {
        return airlinesRepository.findByIataCode(code).map(Airlines::getName).orElse(code);
    }
}
