package com.svf.fpl.edu.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "league_member")
public class LeagueMember implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String playerName;
    private Integer points;
    private Integer total;
    private Long entry;
    private Integer currentEvent;
    @OneToMany(mappedBy = "leagueMember", fetch = FetchType.LAZY)
    private Set<Footballer> footballers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Long getEntry() {
        return entry;
    }

    public void setEntry(Long entry) {
        this.entry = entry;
    }

    public Integer getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Integer currentEvent) {
        this.currentEvent = currentEvent;
    }

    public Set<Footballer> getFootballers() {
        return footballers;
    }

    public void setFootballers(Set<Footballer> footballers) {
        this.footballers = footballers;
    }

    public void addFootballer(Footballer footballer) {
        getFootballers().add(footballer);
        footballer.setLeagueMember(this);
    }

    @Override
    public String toString() {
        return "LeagueMember{" +
                "id=" + id +
                ", playerName='" + playerName + '\'' +
                ", total=" + total +
                ", entry=" + entry +
                ", currentEvent=" + currentEvent +
                '}';
    }
}
