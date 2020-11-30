package com.svf.fpl.edu.dto.report;

import java.io.Serializable;
import java.util.List;

public class FootballerInjuryReportDto implements Serializable {
    private String memberName;
    private List<FootballerInjuryDto> footballerInjurys;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public List<FootballerInjuryDto> getFootballerInjurys() {
        return footballerInjurys;
    }

    public void setFootballerInjurys(List<FootballerInjuryDto> footballerInjurys) {
        this.footballerInjurys = footballerInjurys;
    }

    @Override
    public String toString() {
        return "FootballerInjuryReportDto{" +
                "memberName='" + memberName + '\'' +
                ", footballerInjurys=" + footballerInjurys +
                '}';
    }
}
