package com.tripandship.patterns.strategy;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public boolean executePayment(double amount) {
        System.out.println("Processing Credit Card authorization for: $" + amount);
        return true; 
    }
    @Override
    public String getPaymentMethodName() { return "Credit Card"; }
}