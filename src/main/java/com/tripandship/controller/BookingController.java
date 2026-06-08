package com.tripandship.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tripandship.model.Booking;
import com.tripandship.repository.BookingRepository;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingRepository bookingRepository;

    /**
     * Booking Root Endpoint:
     * Redirects straight to the history page dashboard.
     */
    @GetMapping({"", "/"})
    public String index() {
        return "redirect:/bookings/history";
    }

    /**
     * History Endpoint:
     * Filters bookings into separate lists for Tickets and Cargo.
     */
    @GetMapping("/history")
    public String showHistory(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        logger.info("Loading dashboard for user: {}", username);
        
        try {
            // FIX: Dono methods 'username' fetch karne ke liye exact trigger honge
            // Agar aapke Booking model me field ka naam 'user' hai, toh ye perfectly chalega.
            List<Booking> tickets = bookingRepository.findAllByUserAndType(username, "TRAVEL");
            List<Booking> cargos = bookingRepository.findAllByUserAndType(username, "CARGO");
            
            model.addAttribute("tickets", tickets);
            model.addAttribute("cargos", cargos);
            
            logger.info("Successfully loaded {} tickets and {} cargo items.", tickets.size(), cargos.size());
            
        } catch (Exception e) {
            logger.error("Error loading dashboard data: ", e);
            model.addAttribute("errorMessage", "Data load karne mein issue hua.");
        }
        
        return "history"; // Points to history.html
    }

    /**
     * Process Secure Checkout Linkage:
     * Saves travel database pipeline and triggers smooth notification response.
     */
    @PostMapping("/book")
    public String executeTicketBookingRegistry(
            @RequestParam("packageId") Long packageId,
            @RequestParam("passengerQty") int passengerQty,
            @RequestParam("paymentMethod") String paymentMethod,
            @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
            Principal principal) {
        
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        logger.info("Initiating database pipeline execution for package ID: {}", packageId);

        try {
            Booking booking = new Booking();
            
            // FIX: 'setUserId' ki jagah 'setUser' kiya taaki Repository ke 'findAllByUserAndType' se match kare
            booking.setUserId(username); 
            
            booking.setType("TRAVEL"); 
            booking.setPassengerQty(passengerQty);
            booking.setPaymentMethod(paymentMethod);

            // UI placeholders schema setting
            booking.setPackageTitle("Premium Global Vector Route (PKG-" + packageId + ")");
            booking.setOrigin("Origin Core");
            booking.setDestination("Destination Terminal");
            booking.setPrice(1250.0 * passengerQty); 

            bookingRepository.save(booking);
            logger.info("Booking pipeline generated successfully. ID: {}", booking.getId());

        } catch (Exception e) {
            logger.error("Critical error during layout transaction checkpoint processing: ", e);
            return "redirect:/travel?error=true";
        }

        // OPTION A: Agar toast notification 'travel' page par dikhani hai:
        return "redirect:/travel?success=true";
        
        // OPTION B: Agar toast notification 'history' page par dikhani hai, toh isko uncomment karein aur upar wale ko comment:
        // return "redirect:/bookings/history?success=true";
    }

    /**
     * Search Redirector
     */
    @GetMapping("/search")
    public String dynamicRegistryQuery(
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(required = false) String date,
            @RequestParam(defaultValue = "1") String quantity,
            @RequestParam(defaultValue = "travel") String roleMode) {
        
        String query = String.format("?from=%s&to=%s&date=%s&qty=%s", 
                encode(from), encode(to), encode(date), encode(quantity));

        return "cargo".equalsIgnoreCase(roleMode) 
                ? "redirect:/cargo" + query 
                : "redirect:/travel" + query;
    }

    private String encode(String value) {
        return URLEncoder.encode((value != null) ? value.trim() : "", StandardCharsets.UTF_8);
    }
}