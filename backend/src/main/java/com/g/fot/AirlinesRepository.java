package com.g.fot;

import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AirlinesRepository extends PagingAndSortingRepository<Airlines, String> {
    @Cacheable("airlines")
    Optional<Airlines> findByIataCode(String iataCode);
}
