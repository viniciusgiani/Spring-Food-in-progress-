package com.spring.data.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public OrderNotFoundException(String orderCode) {
        super(String.format("No order with code %s", orderCode));
    }
}
