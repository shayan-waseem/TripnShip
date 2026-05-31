package com.tripandship.exception;

public class PaymentFailedException extends RuntimeException {
    public PaymentFailedException(String reason) {
        super(reason);
    }
}