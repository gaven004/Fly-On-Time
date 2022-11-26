package com.g.fot;

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
    private float otp;
    private float rod;
    private float roc;
}
