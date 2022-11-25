package com.g.fot;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("flight")
public class Flight {
    @Column("flight_schedules")
    private String flightSchedules;

    @Column("airlines")
    private String airlines;

    @Column("departure_city")
    private String departureCity;

    @Column("departure_airport")
    private String departureAirport;

    @Column("landing_city")
    private String landingCity;

    @Column("landing_airport")
    private String landingAirport;

    @Column("schedule_departure_time")
    private OffsetDateTime scheduleDepartureTime;

    @Column("schedule_landing_time")
    private OffsetDateTime scheduleLandingTime;

    @Column("actual_departure_time")
    private OffsetDateTime actualDepartureTime;

    @Column("actual_landing_time")
    private OffsetDateTime actualLandingTime;

    @Column("flight_no")
    private String flightNo;

    @Column("status")
    private String status;
}