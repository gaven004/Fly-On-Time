package com.g.fot;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FlightRepository extends PagingAndSortingRepository<Flight, String> {
    @Query("""
            select
                   count()                                                                                       as total,
                   countIf(status != '取消' and actual_landing_time <= (flight.schedule_landing_time + 15 * 60)) as ontimes,
                   countIf(status != '取消' and actual_landing_time > (flight.schedule_landing_time + 15 * 60))  as delays,
                   countIf(status = '取消')                                                                      as cancellations,
                   ontimes / total                                                                               as otp,
                   delays / total                                                                                as rod,
                   cancellations / total                                                                         as roc
            from flight
            where departure_airport in (select iata_code from airport where city = :source)
              and landing_airport in (select iata_code from airport where city = :destination)""")
    Optional<FlightStat> countAll(String source, String destination);

    @Query("""
            select substring(flight_schedules, 1, 2)                                                             as category,
                   count()                                                                                       as total,
                   countIf(status != '取消' and actual_landing_time <= (flight.schedule_landing_time + 15 * 60)) as ontimes,
                   countIf(status != '取消' and actual_landing_time > (flight.schedule_landing_time + 15 * 60))  as delays,
                   countIf(status = '取消')                                                                      as cancellations,
                   ontimes / total                                                                               as otp,
                   delays / total                                                                                as rod,
                   cancellations / total                                                                         as roc
            from flight
            where departure_airport in (select iata_code from airport where city = :source)
              and landing_airport in (select iata_code from airport where city = :destination)
            group by substring(flight_schedules, 1, 2)
            having total > 100
            order by otp desc""")
    Iterable<FlightStat> countByAirlines(String source, String destination);

    @Query("""
            select toHour(schedule_departure_time)                                                             as category,
                   count()                                                                                       as total,
                   countIf(status != '取消' and actual_landing_time <= (flight.schedule_landing_time + 15 * 60)) as ontimes,
                   countIf(status != '取消' and actual_landing_time > (flight.schedule_landing_time + 15 * 60))  as delays,
                   countIf(status = '取消')                                                                      as cancellations,
                   ontimes / total                                                                               as otp,
                   delays / total                                                                                as rod,
                   cancellations / total                                                                         as roc
            from flight
            where departure_airport in (select iata_code from airport where city = :source)
              and landing_airport in (select iata_code from airport where city = :destination)
            group by toHour(schedule_departure_time)
            order by category""")
    Iterable<FlightStat> countByHour(String source, String destination);

    @Query("""
            select flight_schedules                                                                              as category,
                   count()                                                                                       as total,
                   countIf(status != '取消' and actual_landing_time <= (flight.schedule_landing_time + 15 * 60)) as ontimes,
                   countIf(status != '取消' and actual_landing_time > (flight.schedule_landing_time + 15 * 60))  as delays,
                   countIf(status = '取消')                                                                      as cancellations,
                   ontimes / total                                                                               as otp,
                   delays / total                                                                                as rod,
                   cancellations / total                                                                         as roc
            from flight
            where departure_airport in (select iata_code from airport where city = :source)
              and landing_airport in (select iata_code from airport where city = :destination)
            group by flight_schedules
            having total > 100
            order by otp desc
            limit 5""")
    Iterable<FlightStat> findTopFlight(String source, String destination);

    @Query("""
            select flight_schedules                                                                              as category,
                   count()                                                                                       as total,
                   countIf(status != '取消' and actual_landing_time <= (flight.schedule_landing_time + 15 * 60)) as ontimes,
                   countIf(status != '取消' and actual_landing_time > (flight.schedule_landing_time + 15 * 60))  as delays,
                   countIf(status = '取消')                                                                      as cancellations,
                   ontimes / total                                                                               as otp,
                   delays / total                                                                                as rod,
                   cancellations / total                                                                         as roc
            from flight
            where departure_airport in (select iata_code from airport where city = :source)
              and landing_airport in (select iata_code from airport where city = :destination)
            group by flight_schedules
            having total > 100
            order by otp
            limit 5""")
    Iterable<FlightStat> findBottomFlight(String source, String destination);

}
