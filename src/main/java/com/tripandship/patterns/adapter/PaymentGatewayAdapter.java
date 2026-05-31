package com.tripandship.patterns.adapter;

public class PaymentGatewayAdapter {
    public boolean processExternalWire(String swiftCode, double balance) {
        System.out.println("[ADAPTER PATTERN ROUTING] Converting local payment channel into SWIFT ledger transfer request framework.");
        return true;
    }
}