package com.svf.fpl.edu.dto;

import java.util.List;

public class Leagues {
    private List<Classic> classic;
    private List<String> h2h;
    private Cup cup;

    public List<Classic> getClassic() {
        return classic;
    }

    public void setClassic(List<Classic> classic) {
        this.classic = classic;
    }

    public List<String> getH2h() {
        return h2h;
    }

    public void setH2h(List<String> h2h) {
        this.h2h = h2h;
    }

    public Cup getCup() {
        return cup;
    }

    public void setCup(Cup cup) {
        this.cup = cup;
    }

    @Override
    public String toString() {
        return "Leagues{" +
                "classic=" + classic +
                ", h2h=" + h2h +
                '}';
    }
}
