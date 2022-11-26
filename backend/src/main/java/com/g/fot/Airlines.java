package com.g.fot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("airlines")
public class Airlines {
    @Id
    @Column("iata_code")
    private String iataCode;

    @Column("name")
    private String name;

    @Column("location")
    private String location;
}
