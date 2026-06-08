package com.tripandship.patterns.state;

public class CargoStateFactory {

    public static CargoState fromString(String status) {

        if (status == null)
            return new PendingState();

        switch (status.toLowerCase()) {

            case "confirmed":
                return new ConfirmedState();

            case "in transit":
                return new InTransitState();

            case "delivered":
                return new DeliveredState();

            default:
                return new PendingState();
        }
    }
}