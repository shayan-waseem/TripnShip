package com.tripandship.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tripandship.model.CargoShipment;
import com.tripandship.model.TravelPackage;
import com.tripandship.repository.BookingRepository;
import com.tripandship.repository.CargoRepository;
import com.tripandship.repository.TravelRepository;
import com.tripandship.repository.UserRepository;
import com.tripandship.service.AnalyticsService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final AnalyticsService analyticsService;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CargoRepository cargoRepository;
    private final TravelRepository travelRepository;

    public AdminController(AnalyticsService analyticsService,
                           UserRepository userRepository,
                           BookingRepository bookingRepository,
                           CargoRepository cargoRepository,
                           TravelRepository travelRepository) {
        this.analyticsService = analyticsService;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.cargoRepository = cargoRepository;
        this.travelRepository = travelRepository;
    }

    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model) {
        addMetrics(model);
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("allBookings", bookingRepository.findAll());
        model.addAttribute("allShipments", cargoRepository.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String manageUsers(Model model) {
        model.addAttribute("allUsers", userRepository.findAll());
        return "admin/users";
    }

    @GetMapping("/packages")
    public String managePackages(Model model) {
        List<TravelPackage> packages = travelRepository.findAll();
        model.addAttribute("packages", packages);
        return "admin/manage-packages";
    }

    @GetMapping("/cargo")
    public String manageCargo(Model model) {
        List<CargoShipment> shipments = cargoRepository.findAll();
        model.addAttribute("allShipments", shipments);
        return "admin/manage-cargo";
    }

    private void addMetrics(Model model) {
        Map<String, Object> platformMetrics = analyticsService.getMetricsSummary();
        model.addAttribute("revenue", platformMetrics.getOrDefault("totalRevenue", 0.0));
        model.addAttribute("bookingsCount", platformMetrics.getOrDefault("totalBookingsCount", 0));
        model.addAttribute("shipmentsCount", platformMetrics.getOrDefault("totalShipmentsCount", 0));
    }
}
