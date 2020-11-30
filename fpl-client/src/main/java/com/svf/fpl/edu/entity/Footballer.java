package com.svf.fpl.edu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "footballer")
public class Footballer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long elementId;
    private String name;
    private Boolean isCaptain;
    private String chancePlayingNextGame;
    private String news;
    private Integer totalPoints;
    private Double pointsPerGame;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leagueMember_id", referencedColumnName = "id")
    @JsonIgnore
    private LeagueMember leagueMember;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getElementId() {
        return elementId;
    }

    public void setElementId(Long elementId) {
        this.elementId = elementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCaptain() {
        return isCaptain;
    }

    public void setCaptain(Boolean captain) {
        isCaptain = captain;
    }

    public String getChancePlayingNextGame() {
        return chancePlayingNextGame;
    }

    public void setChancePlayingNextGame(String chancePlayingNextGame) {
        this.chancePlayingNextGame = chancePlayingNextGame;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Double getPointsPerGame() {
        return pointsPerGame;
    }

    public void setPointsPerGame(Double pointsPerGame) {
        this.pointsPerGame = pointsPerGame;
    }

    public LeagueMember getLeagueMember() {
        return leagueMember;
    }

    public void setLeagueMember(LeagueMember leagueMember) {
        this.leagueMember = leagueMember;
    }

    @Override
    public String toString() {
        return "MemberPlayer{" +
                "id=" + id +
                ", elementId=" + elementId +
                ", name='" + name + '\'' +
                ", isCaptain=" + isCaptain +
                ", chancePlayingNextGame=" + chancePlayingNextGame +
                ", news='" + news + '\'' +
                ", totalPoints=" + totalPoints +
                ", pointsPerGame=" + pointsPerGame +
                '}';
    }
}
