package com.spring.data.domain.exception;

public class KitchenNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public KitchenNotFoundException(String message) {
        super(message);
    }

    public KitchenNotFoundException(Long id) {
        this(String.format("No kitchen with code: %d", id));
    }
}
