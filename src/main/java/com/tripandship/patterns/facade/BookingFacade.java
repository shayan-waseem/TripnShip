package com.tripandship.patterns.facade;

import com.tripandship.model.CargoShipment;
import com.tripandship.service.CargoService;
import com.tripandship.service.AnalyticsService;
import org.springframework.stereotype.Component;

@Component
public class BookingFacade {
    private final CargoService cargoService;
    private final AnalyticsService analyticsService;

    public BookingFacade(CargoService cargoService, AnalyticsService analyticsService) {
        this.cargoService = cargoService;
        this.analyticsService = analyticsService;
    }

    public CargoShipment executeIntegratedCargoWorkflow(CargoShipment shipment) {
        CargoShipment result = cargoService.registerShipment(shipment);
        analyticsService.logSystemActivity("CARGO_SHIPMENT", result.getCost());
        return result;
    }
}