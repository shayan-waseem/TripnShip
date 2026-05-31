package com.tripandship.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String summaryMessage) {
        super(summaryMessage);
    }
}