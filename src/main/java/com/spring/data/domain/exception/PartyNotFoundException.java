package com.spring.data.domain.exception;

public class PartyNotFoundException extends EntityNotFoundException {
    static final long serialVersionUID = 1L;

    public PartyNotFoundException(String message) {
        super(message);
    }

    public PartyNotFoundException(Long partyId) {
        this(String.format("No register with code %d", partyId));
    }
}
