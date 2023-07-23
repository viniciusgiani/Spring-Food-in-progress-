package com.spring.data.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Long id) {
        this(String.format("No restaurant with code: %d", id));
    }
}
