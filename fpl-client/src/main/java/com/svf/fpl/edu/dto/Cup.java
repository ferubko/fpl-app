package com.svf.fpl.edu.dto;

import java.util.List;

public class Cup {
    public List<String> matches;
    public Status status;

    public List<String> getMatches() {
        return matches;
    }

    public void setMatches(List<String> matches) {
        this.matches = matches;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}