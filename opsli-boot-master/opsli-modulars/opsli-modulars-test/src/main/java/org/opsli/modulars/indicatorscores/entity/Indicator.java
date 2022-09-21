package org.opsli.modulars.indicatorscores.entity;

import lombok.Data;

@Data
public class Indicator {
    private long id;
    private String sn;
    private String name;
    private boolean isCalc;
    private boolean isValue;
    private double weight;
}
