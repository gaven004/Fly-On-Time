package com.g.fot;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FlightRepository extends PagingAndSortingRepository<Flight, String> {
    @Query("select \n" +
            "       count()                                                                                       as total,\n" +
            "       countIf(status != '取消' and actual_landing_time <= (flight.schedule_landing_time + 15 * 60)) as ontimes,\n" +
            "       countIf(status != '取消' and actual_landing_time > (flight.schedule_landing_time + 15 * 60))  as delays,\n" +
            "       countIf(status = '取消')                                                                      as cancellations,\n" +
            "       ontimes / total                                                                               as ontimes_rate,\n" +
            "       delays / total                                                                                as delays_rate,\n" +
            "       cancellations / total                                                                         as cancellations_rate\n" +
            "from flight\n" +
            "where departure_airport in (select iata_code from airport where city = :source)\n" +
            "  and landing_airport in (select iata_code from airport where city = :destination)")
    Optional<FlightStat> countAll(String source, String destination);

    @Query("select substring(flight_schedules, 1, 2)                                                             as category,\n" +
            "       count()                                                                                       as total,\n" +
            "       countIf(status != '取消' and actual_landing_time <= (flight.schedule_landing_time + 15 * 60)) as ontimes,\n" +
            "       countIf(status != '取消' and actual_landing_time > (flight.schedule_landing_time + 15 * 60))  as delays,\n" +
            "       countIf(status = '取消')                                                                      as cancellations,\n" +
            "       ontimes / total                                                                               as ontimes_rate,\n" +
            "       delays / total                                                                                as delays_rate,\n" +
            "       cancellations / total                                                                         as cancellations_rate\n" +
            "from flight\n" +
            "where departure_airport in (select iata_code from airport where city = :source)\n" +
            "  and landing_airport in (select iata_code from airport where city = :destination)\n" +
            "group by substring(flight_schedules, 1, 2)\n" +
            "having total > 100\n" +
            "order by ontimes_rate desc")
    Iterable<FlightStat> countByAirlines(String source, String destination);

    @Query("select toHour(schedule_departure_time)                                                             as category,\n" +
            "       count()                                                                                       as total,\n" +
            "       countIf(status != '取消' and actual_landing_time <= (flight.schedule_landing_time + 15 * 60)) as ontimes,\n" +
            "       countIf(status != '取消' and actual_landing_time > (flight.schedule_landing_time + 15 * 60))  as delays,\n" +
            "       countIf(status = '取消')                                                                      as cancellations,\n" +
            "       ontimes / total                                                                               as ontimes_rate,\n" +
            "       delays / total                                                                                as delays_rate,\n" +
            "       cancellations / total                                                                         as cancellations_rate\n" +
            "from flight\n" +
            "where departure_airport in (select iata_code from airport where city = :source)\n" +
            "  and landing_airport in (select iata_code from airport where city = :destination)\n" +
            "group by toHour(schedule_departure_time)\n" +
            "order by category")
    Iterable<FlightStat> countByHour(String source, String destination);

    @Query("select flight_schedules                                                                              as category,\n" +
            "       count()                                                                                       as total,\n" +
            "       countIf(status != '取消' and actual_landing_time <= (flight.schedule_landing_time + 15 * 60)) as ontimes,\n" +
            "       countIf(status != '取消' and actual_landing_time > (flight.schedule_landing_time + 15 * 60))  as delays,\n" +
            "       countIf(status = '取消')                                                                      as cancellations,\n" +
            "       ontimes / total                                                                               as ontimes_rate,\n" +
            "       delays / total                                                                                as delays_rate,\n" +
            "       cancellations / total                                                                         as cancellations_rate\n" +
            "from flight\n" +
            "where departure_airport in (select iata_code from airport where city = :source)\n" +
            "  and landing_airport in (select iata_code from airport where city = :destination)\n" +
            "group by flight_schedules\n" +
            "having total > 100\n" +
            "order by ontimes_rate desc\n" +
            "limit 5")
    Iterable<FlightStat> findTopFlight(String source, String destination);

    @Query("select flight_schedules                                                                              as category,\n" +
            "       count()                                                                                       as total,\n" +
            "       countIf(status != '取消' and actual_landing_time <= (flight.schedule_landing_time + 15 * 60)) as ontimes,\n" +
            "       countIf(status != '取消' and actual_landing_time > (flight.schedule_landing_time + 15 * 60))  as delays,\n" +
            "       countIf(status = '取消')                                                                      as cancellations,\n" +
            "       ontimes / total                                                                               as ontimes_rate,\n" +
            "       delays / total                                                                                as delays_rate,\n" +
            "       cancellations / total                                                                         as cancellations_rate\n" +
            "from flight\n" +
            "where departure_airport in (select iata_code from airport where city = :source)\n" +
            "  and landing_airport in (select iata_code from airport where city = :destination)\n" +
            "group by flight_schedules\n" +
            "having total > 100\n" +
            "order by ontimes_rate asc\n" +
            "limit 5")
    Iterable<FlightStat> findBottomFlight(String source, String destination);

}
