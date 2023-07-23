package com.spring.data.domain.exception;

public class PaymentMethodNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public PaymentMethodNotFoundException(String message) {
        super(message);
    }

    public PaymentMethodNotFoundException(Long paymentId) {
        this(String.format("No payment method code %d", paymentId));
    }
}
