package com.spring.data.domain.exception;

public class CityNotFoundException extends EntityNotFoundException{
    private static final long serialVersionUID = 1L;

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long id) {
        this(String.format("No city with code: %d", id));
    }
}
