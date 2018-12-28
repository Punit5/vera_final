package com.shoppingflightoffers.exceptions;

public class NotFoundException extends ApplicationException {
    private static final long serialVersionUID = -4424207881665976640L;
    
    public NotFoundException(final String message) {
        super(message);
    }
    
    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
