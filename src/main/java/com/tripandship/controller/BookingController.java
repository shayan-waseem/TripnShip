package com.tripandship.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    /**
     * Fallback endpoint jo bookings route root ko redirect karta hai history page par.
     */
    @GetMapping({"/history-log", ""})
    public String listUserBookings() {
        return "redirect:/travel/history";
    }

    /**
     * 🔍 Core Search Registry Handler Matrix
     * Yeh method landing home page widget se input fields receive karta hai aur unhe
     * roleMode ke hisab se relevant views (/travel ya /cargo) par state maintain rakh kar forward karta hai.
     */
    @GetMapping("/search")
    public String dynamicRegistryQuery(@RequestParam(value = "from", required = false) String from,
                                       @RequestParam(value = "to", required = false) String to,
                                       @RequestParam(value = "date", required = false) String date,
                                       @RequestParam(value = "quantity", required = false, defaultValue = "1") String quantity,
                                       @RequestParam(value = "roleMode", required = false, defaultValue = "travel") String roleMode) {
        
        // Null checks handle karne ke liye fallback handling (taake string functions crash na karein)
        String cleanFrom = (from != null) ? from.trim() : "";
        String cleanTo = (to != null) ? to.trim() : "";
        String cleanDate = (date != null) ? date.trim() : "";
        String cleanQty = (quantity != null) ? quantity.trim() : "1";

        // Terminal Log Metrics Trace for Verification
        System.out.println("=========================================");
        System.out.println("[PROCESSING GATEWAY SEARCH REDIRECTION]");
        System.out.println("Role Context Mode : " + roleMode.toUpperCase());
        System.out.println("Passed Origin     : " + cleanFrom);
        System.out.println("Passed Destination: " + cleanTo);
        System.out.println("Passed Date       : " + cleanDate);
        System.out.println("Passed Quantity   : " + cleanQty);
        System.out.println("=========================================");

        // Safe URL Encoding Logic Wrapper (Spaces aur Special Characters safely support karne ke liye)
        String encodedFrom = URLEncoder.encode(cleanFrom, StandardCharsets.UTF_8);
        String encodedTo = URLEncoder.encode(cleanTo, StandardCharsets.UTF_8);
        String encodedDate = URLEncoder.encode(cleanDate, StandardCharsets.UTF_8);
        String encodedQty = URLEncoder.encode(cleanQty, StandardCharsets.UTF_8);

        // Append query params matrix line
        String targetQueryParams = String.format("?from=%s&to=%s&date=%s&qty=%s", 
                                                encodedFrom, encodedTo, encodedDate, encodedQty);

        // Switching core route mechanisms matching your dashboard layouts
        if ("cargo".equalsIgnoreCase(roleMode)) {
            // User ko dashboard view panel mapping par bheje ga parameters pass karke
            return "redirect:/cargo" + targetQueryParams;
        }

        // Default execution pathway: Redirects smoothly to the user's Travel Results page (As seen in image_b2e5ec.png)
        return "redirect:/travel" + targetQueryParams;
    }
}