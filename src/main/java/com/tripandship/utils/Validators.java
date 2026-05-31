package com.tripandship.utils;

public class Validators {
    public static boolean isValidString(String data) {
        return data != null && !data.trim().isEmpty();
    }
}