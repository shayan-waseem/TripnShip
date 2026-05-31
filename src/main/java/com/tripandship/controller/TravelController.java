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

    public TravelController(TravelRepository travelRepository,
                            BookingService bookingService,
                            SessionService sessionService) {
        this.travelRepository = travelRepository;
        this.bookingService = bookingService;
        this.sessionService = sessionService;
    }

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
                // MS SQL / JpaRepository custom filter method call
                packages = travelRepository.findByOriginAndDestination(from.trim(), to.trim()); 
            } else {
                packages = travelRepository.findAll();
            }
        } catch (Exception e) {
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

    @PostMapping("/book")
    public String confirmTravelBooking(@RequestParam String packageId,
                                       @RequestParam String paymentMethod,
                                       HttpSession session) {
        try {
            String userId = sessionService.requireUserId(session);
            Booking finalizedBooking = bookingService.processTravelBooking(userId, packageId, paymentMethod);
            if (finalizedBooking != null && "CONFIRMED".equals(finalizedBooking.getStatus())) {
                return "redirect:/travel/history?success=true";
            }
        } catch (Exception e) {
            return "redirect:/travel?error=system_fault";
        }
        return "redirect:/travel?error=payment_failed";
    }

    @GetMapping("/history")
    public String showBookingHistory(HttpSession session, Model model) {
        try {
            String userId = sessionService.requireUserId(session);
            List<Booking> userBookings = bookingService.getBookingsByUser(userId);
            model.addAttribute("bookings", userBookings != null ? userBookings : new ArrayList<>());
        } catch (Exception e) {
            model.addAttribute("bookings", new ArrayList<>());
        }
        return "booking-history";
    }
}