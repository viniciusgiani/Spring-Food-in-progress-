package com.spring.data.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long kitchenId) {
        this(String.format("No user with register %d", kitchenId));
    }

}
