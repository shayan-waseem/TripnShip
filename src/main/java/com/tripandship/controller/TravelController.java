package com.tripandship.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tripandship.model.Booking;
import com.tripandship.model.TravelPackage;
import com.tripandship.repository.TravelRepository;
import com.tripandship.service.BookingService;
import com.tripandship.service.SessionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/travel")
public class TravelController {
    private final TravelRepository travelRepository;
    private final BookingService bookingService;
    private final SessionService sessionService;

    // Constructor Injection
    public TravelController(TravelRepository travelRepository,
                            BookingService bookingService,
                            SessionService sessionService) {
        this.travelRepository = travelRepository;
        this.bookingService = bookingService;
        this.sessionService = sessionService;
    }

    /**
     * Travel Dashboard showing available tour packages
     */
    @GetMapping
    public String showTravelDashboard(@RequestParam(value = "from", required = false) String from,
                                      @RequestParam(value = "to", required = false) String to,
                                      @RequestParam(value = "date", required = false) String date,
                                      @RequestParam(value = "qty", required = false, defaultValue = "1") String qty,
                                      Model model) {
        
        List<TravelPackage> packages;

        try {
            // Core dynamic database search integration
            if (from != null && !from.trim().isEmpty() && to != null && !to.trim().isEmpty()) {
                packages = travelRepository.findByOriginAndDestination(from.trim(), to.trim()); 
            } else {
                packages = travelRepository.findAll();
            }
        } catch (Exception e) {
            System.err.println("🔍 [TravelDashboard] Error fetching packages: " + e.getMessage());
            packages = new ArrayList<>();
        }

        if (packages == null) {
            packages = new ArrayList<>();
        }

        model.addAttribute("packages", packages);
        model.addAttribute("selectedFrom", (from != null && !from.trim().isEmpty()) ? from : "Any Origin");
        model.addAttribute("selectedTo", (to != null && !to.trim().isEmpty()) ? to : "Any Destination");
        model.addAttribute("selectedDate", (date != null && !date.trim().isEmpty()) ? date : "Flexible");
        model.addAttribute("selectedQty", (qty != null && !qty.trim().isEmpty()) ? qty : "1");

        return "travel";
    }

    /**
     * Processes the booking form submission and credit card authorization
     */
    @PostMapping("/book")
    public String confirmTravelBooking(@RequestParam("packageId") String packageId,
                                       @RequestParam("passengerQty") Integer passengerQty,
                                       @RequestParam("phoneNumber") String phoneNumber,
                                       @RequestParam("username") String username,
                                       @RequestParam("cardNumber") String cardNumber,
                                       @RequestParam("cvv") String cvv,
                                       @RequestParam("paymentMethod") String paymentMethod,
                                       HttpSession session) {
        try {
            String userId = sessionService.requireUserId(session);
            System.out.println("💳 [Booking Process] Initiating booking for User: " + userId + " | Package: " + packageId);

            Booking finalizedBooking = bookingService.processTravelBooking(
                userId, packageId, passengerQty, phoneNumber, cardNumber, cvv, paymentMethod, username
            );
            
            if (finalizedBooking != null && "CONFIRMED".equals(finalizedBooking.getStatus())) {
                System.out.println("✅ [Booking Process] Successfully confirmed Booking ID: " + finalizedBooking.getId());
                return "redirect:/travel?success=true";
            }
        } catch (Exception e) {
            System.err.println("❌ [Booking Process] System fault during booking: " + e.getMessage());
            return "redirect:/travel?error=system_fault";
        }
        return "redirect:/travel?error=payment_failed";
    }

    /**
     * Displays the dynamic booking history dashboard for the logged-in user
     */
    @GetMapping("/history")
    public String showBookingHistory(HttpSession session, Model model) {
        try {
            // Session se active user ki ID nikalna (e.g., "noor" ya "USR-105")
            String userId = sessionService.requireUserId(session);
            System.out.println("⏳ [History Dashboard] Loading bookings for User Session ID: '" + userId + "'");

            // Service layer se bookings fetch karna
            List<Booking> userBookings = bookingService.getBookingsByUser(userId);
            
            if (userBookings == null) {
                userBookings = new ArrayList<>();
            }

            // 📢 CRITICAL DEBUG LOG: Isse terminal me size check karein!
            System.out.println("📊 [History Dashboard] Total bookings loaded from JSON for '" + userId + "': " + userBookings.size());
            for (Booking b : userBookings) {
                System.out.println("   -> ID: " + b.getId() + " | Type: " + b.getType() + " | Price: $" + b.getPrice());
            }

            model.addAttribute("bookings", userBookings);
        } catch (Exception e) {
            System.err.println("❌ [History Dashboard] Error loading history: " + e.getMessage());
            model.addAttribute("bookings", new ArrayList<>());
        }
        return "booking-history";
    }
}