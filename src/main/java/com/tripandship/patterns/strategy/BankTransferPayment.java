package com.tripandship.patterns.strategy;

public class BankTransferPayment implements PaymentStrategy {
    @Override
    public boolean executePayment(double amount) {
        System.out.println("Validating Bank Wire Ledger transfer for: $" + amount);
        return true;
    }
    @Override
    public String getPaymentMethodName() { return "Bank Wire Transfer"; }
}