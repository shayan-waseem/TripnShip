package com.tripandship.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tripandship.model.CargoShipment;
import com.tripandship.service.CargoService;
import com.tripandship.service.SessionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TrackingController {

    private final CargoService cargoService;
    private final SessionService sessionService;

    public TrackingController(CargoService cargoService, SessionService sessionService) {
        this.cargoService = cargoService;
        this.sessionService = sessionService;
    }

    @GetMapping("/tracking")
    public String trackShipments(@RequestParam(required = false) String id,
                                 HttpSession session,
                                 Model model) {
        String userId = sessionService.requireUserId(session);
        List<CargoShipment> mine = cargoService.getAllShipments().stream()
                .filter(s -> userId.equals(s.getUserId()))
                .collect(Collectors.toList());

        model.addAttribute("shipments", mine);
        if (id != null && !id.isBlank()) {
            mine.stream()
                    .filter(s -> id.equalsIgnoreCase(s.getId()))
                    .findFirst()
                    .ifPresent(s -> model.addAttribute("tracked", s));
        } else if (!mine.isEmpty()) {
            model.addAttribute("tracked", mine.get(mine.size() - 1));
        }
        return "tracking";
    }
}
