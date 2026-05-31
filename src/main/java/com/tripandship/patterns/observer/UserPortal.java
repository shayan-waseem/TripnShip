package com.tripandship.patterns.observer;

import org.springframework.stereotype.Component;

@Component
public class UserPortal implements Observer {
    @Override
    public void update(String eventType, Object data) {
        System.out.println("[OBSERVER ALERT] User Portal notification updated system state: " + eventType);
    }
}