package com.tripandship.patterns.adapter;

public class ExternalPaymentAPI {
    public void transmitThirdPartyWire(String targetAccount, double balanceAmount) {
        System.out.println("[EXTERNAL API WIRE] Pushing secure settlement to clear: $" + balanceAmount);
    }
}