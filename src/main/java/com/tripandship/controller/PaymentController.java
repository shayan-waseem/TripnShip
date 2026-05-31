package com.tripandship.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tripandship.model.Payment;
import com.tripandship.service.PaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public String showPaymentView(Model model) {
        model.addAttribute("paymentLog", new Payment());
        return "payment";
    }

    @PostMapping("/checkout")
    public String executeCheckout(@ModelAttribute Payment billingRecord) {
        billingRecord.setStatus("PAID");
        paymentService.recordPayment(billingRecord);
        return "redirect:/travel/history?paymentSuccess=true";
    }
}