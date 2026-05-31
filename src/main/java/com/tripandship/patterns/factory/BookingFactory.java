package com.tripandship.patterns.factory;

import com.tripandship.model.Booking;

public class BookingFactory {
    public static Booking createBooking(String type, String userId, String referenceId, double totalCost) {
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setReferenceId(referenceId);
        booking.setPrice(totalCost);
        booking.setStatus("PENDING");
        
        if ("TRAVEL".equalsIgnoreCase(type)) {
            booking.setType("TRAVEL_PACKAGE");
        } else if ("CARGO".equalsIgnoreCase(type)) {
            booking.setType("CARGO_DELIVERY");
        }
        return booking;
    }
}