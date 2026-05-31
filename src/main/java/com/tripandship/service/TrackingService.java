package com.tripandship.service;

import org.springframework.stereotype.Service;

@Service
public class TrackingService {
    public String checkStatus(String targetShipmentId) {
        return "In Transit - Custom Hub Processing Checkpoint Verification Loop Verified.";
    }
}