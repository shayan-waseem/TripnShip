package com.tripandship.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tripandship.model.Payment;
import com.tripandship.repository.PaymentRepository;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() { return paymentRepository.findAll(); }
    public void recordPayment(Payment p) { paymentRepository.save(p); }
}