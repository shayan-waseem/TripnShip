package com.tripandship.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tripandship.model.Booking;
import com.tripandship.model.CargoShipment;
import com.tripandship.model.TravelPackage;
import com.tripandship.repository.BookingRepository;
import com.tripandship.repository.CargoRepository;
import com.tripandship.repository.TravelRepository;
import com.tripandship.repository.UserRepository;
import com.tripandship.service.AnalyticsService;
import com.tripandship.service.CargoService;
import com.tripandship.patterns.command.AddPackageCommand;
import com.tripandship.patterns.command.DeletePackageCommand;
import com.tripandship.patterns.command.UpdateCargoStatusCommand;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AnalyticsService analyticsService;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final CargoRepository cargoRepository;
    private final TravelRepository travelRepository;
    private final CargoService cargoService;

    public AdminController(AnalyticsService analyticsService,
            UserRepository userRepository,
            BookingRepository bookingRepository,
            CargoRepository cargoRepository,
            TravelRepository travelRepository,
            CargoService cargoService) {
        this.analyticsService = analyticsService;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
        this.cargoRepository = cargoRepository;
        this.travelRepository = travelRepository;
        this.cargoService = cargoService;
    }

    @GetMapping("/dashboard")
    public String showAdminDashboard(Model model) {
        addMetrics(model);
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("allGlobalBookings", bookingRepository.findAll());
        model.addAttribute("allShipments", cargoRepository.findAll());
        model.addAttribute("packages", travelRepository.findAll());
        return "admin/dashboard";
    }

    /*
     * =========================================================================
     * CRUD METRIC PERSISTENCE MANAGEMENT
     * =========================================================================
     */

    @PostMapping("/packages/save")
    public String executePackagePayloadCommit(@ModelAttribute("package") TravelPackage travelPackage) {
        // Use command pattern to perform admin package add/update
        AddPackageCommand cmd = new AddPackageCommand(travelRepository, travelPackage);
        cmd.execute();
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/packages/delete/{id}")
    public String purgePackageConfigurationNode(@PathVariable("id") String id) {
        DeletePackageCommand cmd = new DeletePackageCommand(travelRepository, id);
        cmd.execute();
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/bookings/verify/{id}")
    public String verifyPassengerManifestEntity(@PathVariable("id") String id,
            @RequestParam("action") String operationalAction) {
        // ID Type Changed from Long to String to map seamlessly to your JSON records
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Booking ID: " + id));

        if ("CONFIRM".equalsIgnoreCase(operationalAction)) {
            booking.setStatus("CONFIRMED");
        } else {
            booking.setStatus("CANCELLED");
        }

        bookingRepository.save(booking); // Clean execution on JSON pipeline save
        return "redirect:/admin/dashboard";
    }

    /*
     * =========================================================================
     * ISOLATED STANDALONE PAGES
     * =========================================================================
     */
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
        Object grossRev = platformMetrics.getOrDefault("totalRevenue", 0.0);
        Object totalBk = platformMetrics.getOrDefault("totalBookingsCount", 0);
        Object totalShip = platformMetrics.getOrDefault("totalShipmentsCount", 0);

        model.addAttribute("revenue", grossRev);
        model.addAttribute("totalRevenue", grossRev);
        model.addAttribute("bookingsCount", totalBk);
        model.addAttribute("totalBookingsCount", totalBk);
        model.addAttribute("shipmentsCount", totalShip);
        model.addAttribute("activeCargoCount", totalShip);
    }

    @PostMapping("/cargo/advance")
    public String advanceCargoStatus(@RequestParam String shipmentId) {
        UpdateCargoStatusCommand cmd = new UpdateCargoStatusCommand(cargoService, shipmentId);
        cmd.execute();
        return "redirect:/admin/dashboard";
    }
}