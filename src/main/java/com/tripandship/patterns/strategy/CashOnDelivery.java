package com.tripandship.patterns.strategy;

public class CashOnDelivery implements PaymentStrategy {
    @Override
    public boolean executePayment(double amount) {
        System.out.println("Processing cash transaction context tracking log values for: $" + amount);
        return true;
    }
    @Override
    public String getPaymentMethodName() { return "Cash On Delivery"; }
}