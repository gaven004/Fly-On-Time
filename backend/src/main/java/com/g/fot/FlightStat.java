package com.g.fot;

import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightStat {
    private String category;
    private int total;
    private int ontimes;
    private int delays;
    private int cancellations;
    @Column("ontimes_rate")
    private float ontimesRate;
    @Column("delays_rate")
    private float delaysRate;
    @Column("cancellations_rate")
    private float cancellationsRate;
}
