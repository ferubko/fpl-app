package com.svf.fpl.edu.dto.bot;

public class PlayerInfo {
    private long id;
    private long memberId;
    private String name;
    private String news;
    private Statistic statistic;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public void setStatistic(Statistic statistic) {
        this.statistic = statistic;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }
}
