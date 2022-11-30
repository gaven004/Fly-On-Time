package com.g.fot;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FlightMapper {
    Optional<FlightStat> countAll(String source, String destination);

    List<FlightStat> countByAirlines(String source, String destination);

    List<FlightStat> countByHour(String source, String destination);

    List<FlightStat> findTopFlight(String source, String destination);

    List<FlightStat> findBottomFlight(String source, String destination);

}
