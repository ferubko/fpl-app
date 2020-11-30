package com.svf.fpl.edu.exeptions;

public class FantasyPremierLeagueException extends RuntimeException {
    private String message;

    public FantasyPremierLeagueException(String message) {
        super(message);
        this.message = message;
    }

    public FantasyPremierLeagueException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
