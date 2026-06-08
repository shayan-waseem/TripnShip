package com.tripandship.service;

import com.tripandship.model.CargoShipment;
import com.tripandship.patterns.decorator.ExpressDelivery;
import com.tripandship.patterns.decorator.InsuranceDecorator;
import com.tripandship.patterns.strategy.AirFreightStrategy;
import com.tripandship.patterns.strategy.ExpressOverlandStrategy;
import com.tripandship.patterns.strategy.OceanCargoStrategy;
import com.tripandship.patterns.strategy.ShippingStrategy;
import com.tripandship.repository.CargoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CargoService {
    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public List<CargoShipment> getAllShipments() {
        return cargoRepository.findAll();
    }

    public CargoShipment registerShipment(CargoShipment shipment) {

        shipment.setId("SHP-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase());

        double distance = estimateRouteDistance(shipment.getOrigin(), shipment.getDestination());
        ShippingStrategy strategy = chooseShippingStrategy(shipment.getShippingMethod());
        double calculatedCost = strategy.calculateCost(shipment.getWeight(), distance);

        shipment.setCost(calculatedCost);

        if (shipment.isInsuranceApplied()) {
            shipment.setCost(new InsuranceDecorator(shipment).getCost());
        }
        if ("Express".equalsIgnoreCase(shipment.getDeliverySpeed())) {
            shipment.setCost(new ExpressDelivery(shipment).getCost());
        }

        shipment.setStatus("Pending");
        shipment.setPaymentMethod(shipment.getPaymentMethod());

        cargoRepository.save(shipment);

        return shipment;
    }

    private ShippingStrategy chooseShippingStrategy(String shippingMethod) {
        if (shippingMethod == null) {
            return new OceanCargoStrategy();
        }
        return switch (shippingMethod.toLowerCase()) {
            case "air freight" -> new AirFreightStrategy();
            case "express overland" -> new ExpressOverlandStrategy();
            default -> new OceanCargoStrategy();
        };
    }

    private double estimateRouteDistance(String origin, String destination) {
        if (origin == null || destination == null) {
            return 1200.0;
        }
        String route = origin.trim().toLowerCase() + "->" + destination.trim().toLowerCase();
        return switch (route) {
            case "karachi port->dubai cargo terminal", "dubai cargo terminal->karachi port" -> 1220.0;
            case "karachi port->shanghai freight center", "shanghai freight center->karachi port" -> 5800.0;
            case "karachi port->lahore logistics hub", "lahore logistics hub->karachi port" -> 1200.0;
            case "lahore logistics hub->dubai cargo terminal", "dubai cargo terminal->lahore logistics hub" -> 1800.0;
            case "shanghai freight center->dubai cargo terminal", "dubai cargo terminal->shanghai freight center" -> 4200.0;
            default -> 2200.0;
        };
    }

    public CargoShipment advanceShipmentStatus(String shipmentId) {

        CargoShipment shipment = cargoRepository.findAll()
                .stream()
                .filter(s -> s.getId().equals(shipmentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        shipment.nextState();

        cargoRepository.save(shipment);

        return shipment;
    }

    public void updateShipmentStatus(String shipmentId) {

        CargoShipment shipment = cargoRepository.findAll()
                .stream()
                .filter(s -> s.getId().equals(shipmentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Shipment not found"));

        shipment.nextState();

        cargoRepository.save(shipment);
    }
}