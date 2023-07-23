package com.spring.data.domain.exception;

public class StateNotFoundException extends EntityNotFoundException{

    private static final long serialVersionUID = 1L;

    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(Long id) {
        this(String.format("No State with code: %d", id));
    }
}
