package com.svf.fpl.edu.exeptions;

public class LeagueMemberException extends RuntimeException {
    private String message;

    public LeagueMemberException(String message) {
        super(message);
        this.message = message;
    }

    public LeagueMemberException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}
