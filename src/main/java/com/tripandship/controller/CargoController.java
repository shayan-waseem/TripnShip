package com.tripandship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    /**
     * 🚢 Cargo Freight Logistics Portal
     * Home search query string inputs parameters ko intercept karke backend storage state binding model pipeline ko synchronize karta hai.
     */
    @GetMapping
    public String showCargoPage(@RequestParam(value = "from", required = false) String from,
                                @RequestParam(value = "to", required = false) String to,
                                @RequestParam(value = "qty", required = false) String qty,
                                @RequestParam(value = "date", required = false) String date,
                                HttpSession session, 
                                Model model) {
        
        String userId = sessionService.requireUserId(session);
        
        // 1. Initialized object instantiation layer mapping criteria
        CargoShipment draftShipment = new CargoShipment();
        
        // Agar parameters empty na hon toh framework inputs layout form fields ke liye state pre-populate parameters sequence apply karega
        if (from != null && !from.trim().isEmpty()) draftShipment.setOrigin(from.trim());
        if (to != null && !to.trim().isEmpty()) draftShipment.setDestination(to.trim());
        
        if (qty != null && !qty.trim().isEmpty()) {
            try {
                // Tracking parameter payload matching rules to primitive weights numeric type
                draftShipment.setWeight(Double.parseDouble(qty.trim()));
            } catch (NumberFormatException e) {
                System.out.println("[WEIGHT CONVERSION WARNING]: Qty parameter text value cannot be parsed to double.");
            }
        }

        // 2. Binding structures into Thymeleaf lifecycle model layer parameters mapping matches cargo.html controls
        model.addAttribute("shipment", draftShipment);
        model.addAttribute("selectedFrom", from);
        model.addAttribute("selectedTo", to);
        model.addAttribute("selectedQty", qty);
        model.addAttribute("selectedDate", date);
        
        model.addAttribute("allShipments", cargoService.getAllShipments());
        model.addAttribute("myShipments", cargoService.getAllShipments().stream()
                .filter(s -> userId.equals(s.getUserId()))
                .toList());
                
        return "cargo";
    }

    /**
     * 🔐 Finalize Consignment Manifest Save Pipeline
     */
    @PostMapping("/process")
    public String processShipment(@ModelAttribute("shipment") CargoShipment shipment, HttpSession session) {
        shipment.setUserId(sessionService.requireUserId(session));
        cargoService.registerShipment(shipment);
        
        // Redirection statement returns parameters back into cargo logs table layout frame alongside true banner response state
        return "redirect:/cargo?success=true";
    }
}