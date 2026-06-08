package com.tripandship.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tripandship.model.Booking;
import com.tripandship.model.TravelPackage;
import com.tripandship.patterns.factory.BookingFactory;
import com.tripandship.patterns.strategy.BankTransferPayment;
import com.tripandship.patterns.strategy.CreditCardPayment;
import com.tripandship.patterns.strategy.PaymentStrategy;
import com.tripandship.repository.BookingRepository;
import com.tripandship.repository.TravelRepository;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TravelRepository travelRepository;

    public BookingService(BookingRepository bookingRepository, TravelRepository travelRepository) {
        this.bookingRepository = bookingRepository;
        this.travelRepository = travelRepository;
    }

    public List<Booking> getBookingsByUser(String userId) {
        if (userId == null) return new ArrayList<>();
        return bookingRepository.findAll().stream()
                .filter(b -> b.getUserId() != null && b.getUserId().equalsIgnoreCase(userId.trim()))
                .collect(Collectors.toList());
    }

    public Booking processTravelBooking(String userId, String packageId, Integer passengerQty, 
                                        String phoneNumber, String cardNumber, String cvv, String paymentMethod) {
        
        // 1. Retrieve the target package
        TravelPackage travelPackage = travelRepository.findById(packageId)
                .orElseThrow(() -> new IllegalArgumentException("Target holiday package not found."));

        // 2. Dynamic Price Calculation
        double totalFarePrice = travelPackage.getPrice() * passengerQty;

        // 3. Factory Pattern
        Booking booking = BookingFactory.createBooking("TRAVEL", userId, packageId, totalFarePrice);
        
        // 🛠️ ID Generation: Agar factory unique ID nahi de rahi, toh Repository ke incremental system par chhor dein
        if (booking.getId() == null || booking.getId().trim().isEmpty()) {
            // Isko blank chhor rahe hain taaki aapka BookingRepository ka generateNextId() automatically BKG-1001 jaisi safe IDs banaye
            booking.setId(null); 
        }

        // 4. Update travel slots logic
        try {
            int currentSlots = travelPackage.getAvailableSlots();
            travelPackage.setAvailableSlots(Math.max(0, currentSlots - passengerQty));
            
            // ⚠️ NOTE: travelRepository.save() ko remove kiya hai kyunki TravelRepository me save method nahi bana hua.
            // Agar future me package slots bhi file me update karne hon, toh TravelRepository me save() method banana hoga.
        } catch (Exception e) {
            System.err.println("⚠️ [BookingService] Could not update travel package slots memory: " + e.getMessage());
        }

        // 5. Assign extra fields
        try {
            booking.setPassengerQty(passengerQty);
            booking.setPhoneNumber(phoneNumber);
            booking.setPaymentMethod(paymentMethod);
            booking.setPackageTitle(travelPackage.getTitle());
        } catch (Exception e) {
            System.err.println("⚠️ [BookingService] Failed to set extra booking fields: " + e.getMessage());
        }

        // 6. Strategy Pattern for payment
        PaymentStrategy strategy;
        if ("Digital Wallet".equalsIgnoreCase(paymentMethod) || "Crypto Vector".equalsIgnoreCase(paymentMethod)) {
            strategy = new BankTransferPayment();
        } else {
            strategy = new CreditCardPayment();
        }

        boolean paymentSuccess = strategy.executePayment(totalFarePrice);
        if (paymentSuccess) {
            booking.setStatus("CONFIRMED");
        } else {
            booking.setStatus("FAILED");
        }

        // 7. Save booking to data store (bookings.json)
        // ✅ Yeh bilkul sahi chalega kyunki aapke BookingRepository me save() method majood hai!
        if (paymentSuccess) {
            try {
                bookingRepository.save(booking);
                System.out.println("💾 [BookingService] Booking saved successfully in JSON pipeline.");
            } catch (Exception e) {
                System.err.println("❌ [BookingService] Error writing to bookings.json: " + e.getMessage());
            }
        }
        
        return booking;
    }
}