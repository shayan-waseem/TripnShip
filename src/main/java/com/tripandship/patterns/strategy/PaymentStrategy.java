package com.tripandship.patterns.strategy;

public interface PaymentStrategy {
    boolean executePayment(double amount);
    String getPaymentMethodName();
}