package com.spring.data.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException {
    private static final long serialVersionUID = 1L;

    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(Long permissionId) {
        this(String.format("No register with permission code %d", permissionId));
    }
}
