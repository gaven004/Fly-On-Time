package com.g.fot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("airport")
public class Airport {
    @Column("iata_code")
    private String iataCode;

    @Column("name")
    private String name;

    @Column("city")
    private String city;
}