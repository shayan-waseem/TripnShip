package com.tripandship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; // Import this
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tripandship.model.CargoShipment;
import com.tripandship.service.CargoService;
import com.tripandship.service.SessionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cargo")
public class CargoController {
    private final CargoService cargoService;
    private final SessionService sessionService;

    public CargoController(CargoService cargoService, SessionService sessionService) {
        this.cargoService = cargoService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public String showCargoPage(@RequestParam(value = "from", required = false) String from,
                                @RequestParam(value = "to", required = false) String to,
                                @RequestParam(value = "qty", required = false) String qty,
                                HttpSession session, 
                                Model model) {
        
        String userId = sessionService.requireUserId(session);
        
        CargoShipment draftShipment = new CargoShipment();
        if (from != null) draftShipment.setOrigin(from.trim());
        if (to != null) draftShipment.setDestination(to.trim());
        if (qty != null) {
            try { draftShipment.setWeight(Double.parseDouble(qty.trim())); } 
            catch (NumberFormatException e) { /* log error */ }
        }

        model.addAttribute("shipment", draftShipment);
        model.addAttribute("myShipments", cargoService.getAllShipments().stream()
                .filter(s -> userId.equals(s.getUserId()))
                .toList());
        
        return "cargo";
    }

    @PostMapping("/process")
    public String processShipment(@ModelAttribute("shipment") CargoShipment shipment, 
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) { // Added RedirectAttributes
        
        // 1. Calculate business logic
        double baseRate = 10.0;
        double total = shipment.getWeight() * baseRate;
        if ("Express".equalsIgnoreCase(shipment.getDeliverySpeed())) {
            total += 150.0;
        }
        
        shipment.setCost(total);
        shipment.setStatus("CONFIRMED");
        shipment.setUserId(sessionService.requireUserId(session));

        // 2. Persist
        cargoService.registerShipment(shipment);
        
        // 3. Use FlashAttributes for temporary, single-use success message
        redirectAttributes.addFlashAttribute("successMessage", "Manifest Registered Successfully!");
        
        // Clean URL redirect
        return "redirect:/cargo";
    }
}