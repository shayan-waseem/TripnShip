package com.tripandship.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public void sendAlert(String userAddress, String summaryMessage) {
        System.out.println("[NOTIFICATION ROUTE ALERT SENT TO: " + userAddress + "] " + summaryMessage);
    }
}