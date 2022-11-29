package com.g.fot;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AirportRepository extends PagingAndSortingRepository<Airport, String> {
    @Cacheable("cities")
    @Query("select distinct city from airport order by city")
    Iterable<String> findDistinctCity();
}
