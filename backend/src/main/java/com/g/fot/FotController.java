package com.g.fot;

import java.util.Optional;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FotController {
    private final FlightRepository flightRepository;
    private final AirlinesRepository airlinesRepository;
    private final AirportRepository airportRepository;

    public FotController(FlightRepository flightRepository, AirlinesRepository airlinesRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airlinesRepository = airlinesRepository;
        this.airportRepository = airportRepository;
    }

    @GetMapping(value = "cities", produces = "application/json")
    public Iterable<String> getCities() {
        return airportRepository.findDistinctCity();
    }

    @GetMapping(value = "/countAll", produces = "application/json")
    public Mono<FlightStat> countAll(String source, String destination) {
        if (!StringUtils.hasText(source) || !StringUtils.hasText(destination)) {
            return Mono.empty();
        }

        /**
         * Possibly blocking call in non-blocking context could lead to thread starvation
         */
        final Optional<FlightStat> flightStat = flightRepository.countAll(source, destination);
        return flightStat.map(Mono::just)
                .orElse(Mono.empty());
    }

    @GetMapping(value = "/countByAirlines", produces = "application/json")
    public Flux<FlightStat> countByAirlines(String source, String destination) {
        if (!StringUtils.hasText(source) || !StringUtils.hasText(destination)) {
            return Flux.empty();
        }

        final Iterable<FlightStat> iterable = flightRepository.countByAirlines(source, destination);
        iterable.forEach(item -> item.setCategory(getAirlines(item.getCategory())));
        return Flux.fromIterable(iterable);
    }

    @GetMapping(value = "/countByHour", produces = "application/json")
    public Flux<FlightStat> countByHour(String source, String destination) {
        if (!StringUtils.hasText(source) || !StringUtils.hasText(destination)) {
            return Flux.empty();
        }

        final Iterable<FlightStat> iterable = flightRepository.countByHour(source, destination);
        return Flux.fromIterable(iterable);
    }

    @GetMapping(value = "/findTop", produces = "application/json")
    public Flux<FlightStat> findTop(String source, String destination) {
        if (!StringUtils.hasText(source) || !StringUtils.hasText(destination)) {
            return Flux.empty();
        }

        final Iterable<FlightStat> iterable = flightRepository.findTopFlight(source, destination);
        return Flux.fromIterable(iterable);
    }

    @GetMapping(value = "/findBottom", produces = "application/json")
    public Flux<FlightStat> findBottom(String source, String destination) {
        if (!StringUtils.hasText(source) || !StringUtils.hasText(destination)) {
            return Flux.empty();
        }

        final Iterable<FlightStat> iterable = flightRepository.findBottomFlight(source, destination);
        return Flux.fromIterable(iterable);
    }

    private String getAirlines(String code) {
        return airlinesRepository.findByIataCode(code).map(Airlines::getName).orElse(code);
    }
}
