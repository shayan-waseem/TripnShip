package com.tripandship.service;

import com.tripandship.model.Booking;
import com.tripandship.model.TravelPackage;
import com.tripandship.patterns.factory.BookingFactory;
import com.tripandship.patterns.strategy.BankTransferPayment;
import com.tripandship.patterns.strategy.CreditCardPayment;
import com.tripandship.patterns.strategy.PaymentStrategy;
import com.tripandship.repository.BookingRepository;
import com.tripandship.repository.TravelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final TravelRepository travelRepository;

    public BookingService(BookingRepository bookingRepository, TravelRepository travelRepository) {
        this.bookingRepository = bookingRepository;
        this.travelRepository = travelRepository;
    }

    public List<Booking> getBookingsByUser(String userId) {
        return bookingRepository.findAll().stream()
                .filter(b -> b.getUserId().equalsIgnoreCase(userId))
                .collect(Collectors.toList());
    }

    public Booking processTravelBooking(String userId, String packageId, String paymentMethod) {
        TravelPackage travelPackage = travelRepository.findById(packageId)
                .orElseThrow(() -> new IllegalArgumentException("Target holiday package not found."));

        // Design Pattern: Factory Pattern creates the concrete item safely
        Booking booking = BookingFactory.createBooking("TRAVEL", userId, packageId, travelPackage.getPrice());

        // Design Pattern: Strategy Pattern evaluates payment based on runtime context
        PaymentStrategy strategy;
        if ("BANK".equalsIgnoreCase(paymentMethod)) {
            strategy = new BankTransferPayment();
        } else {
            strategy = new CreditCardPayment();
        }

        boolean paymentSuccess = strategy.executePayment(travelPackage.getPrice());
        if (paymentSuccess) {
            booking.setStatus("CONFIRMED");
        } else {
            booking.setStatus("FAILED");
        }

        bookingRepository.save(booking);
        return booking;
    }
}