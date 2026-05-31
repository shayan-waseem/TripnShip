package com.tripandship.patterns.observer;

public interface Observer {
    void update(String eventType, Object data);
}