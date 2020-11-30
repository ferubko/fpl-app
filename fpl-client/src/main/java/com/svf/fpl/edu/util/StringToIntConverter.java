package com.svf.fpl.edu.util;

public class StringToIntConverter {
    public static int stringToInt(String intValue) {
        try {
            return Integer.parseInt(intValue);
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Error on convert string to int", ex);
        }
    }
}
