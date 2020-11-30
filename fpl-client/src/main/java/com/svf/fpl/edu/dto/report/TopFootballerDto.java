package com.svf.fpl.edu.dto.report;

public class TopFootballerDto {
    private String footballerName;
    private String footballerType;
    private String team;
    private int cost;
    private double pointsPerGame;
    private int totalPoints;
    private double creativity;
    private double influence;

    public String getFootballerName() {
        return footballerName;
    }

    public void setFootballerName(String footballerName) {
        this.footballerName = footballerName;
    }

    public String getFootballerType() {
        return footballerType;
    }

    public void setFootballerType(String footballerType) {
        this.footballerType = footballerType;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public double getPointsPerGame() {
        return pointsPerGame;
    }

    public void setPointsPerGame(double pointsPerGame) {
        this.pointsPerGame = pointsPerGame;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public double getCreativity() {
        return creativity;
    }

    public void setCreativity(double creativity) {
        this.creativity = creativity;
    }

    public double getInfluence() {
        return influence;
    }

    public void setInfluence(double influence) {
        this.influence = influence;
    }
}
