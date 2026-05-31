package com.tripandship.patterns.factory;

import com.tripandship.model.Booking;

public class CargoBooking extends Booking {
    public CargoBooking() {
        this.setType("CARGO_DELIVERY");
    }
}