package com.tripandship.patterns.observer;

import org.springframework.stereotype.Component;

@Component
public class AdminPanel implements Observer {
    
    @Override
    public void update(String eventType, Object data) {
        // Logs updates to standard output for viva verification
        System.out.println("[OBSERVER PATTERN ALERT] Admin Panel updated via event: " + eventType);
        System.out.println("New System metrics stream state data: " + data.toString());
    }
}