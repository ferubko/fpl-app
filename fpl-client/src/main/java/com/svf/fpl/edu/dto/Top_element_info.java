package com.svf.fpl.edu.dto;

public class Top_element_info {
    private long id;
    private int points;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "Top_element_info{" +
                "id=" + id +
                ", points=" + points +
                '}';
    }
}
