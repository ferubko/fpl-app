package com.svf.fpl.edu.dto.report;

import java.io.Serializable;

public class FootballerInjuryDto implements Serializable {
    private String footballerName;
    private String injuryName;

    enum SOme {
        df, fdd, fdf
    }

    public FootballerInjuryDto(String footballerName, String injuryName) {
        this.footballerName = footballerName;
        this.injuryName = injuryName;
    }

    public FootballerInjuryDto() {
    }

    public String getFootballerName() {
        return footballerName;
    }

    public void setFootballerName(String footballerName) {
        this.footballerName = footballerName;
    }

    public String getInjuryName() {
        return injuryName;
    }

    public void setInjuryName(String injuryName) {
        this.injuryName = injuryName;
    }

    @Override
    public String toString() {
        return "FootballerInjuryDto{" +
                "footballerName='" + footballerName + '\'' +
                ", injuryName='" + injuryName + '\'' +
                '}';
    }
}
