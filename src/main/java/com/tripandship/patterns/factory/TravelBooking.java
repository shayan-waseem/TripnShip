package com.tripandship.patterns.factory;

import com.tripandship.model.Booking;

public class TravelBooking extends Booking {
    public TravelBooking() {
        this.setType("TRAVEL_PACKAGE");
    }
}