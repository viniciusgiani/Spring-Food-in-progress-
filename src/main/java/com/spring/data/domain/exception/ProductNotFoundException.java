package com.spring.data.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(Long restaurantId, Long productId) {
        this(String.format("No product with code %d for restaurant with id %d",
                productId, restaurantId));
    }
}
