package com.shoppingflightoffers.exceptions;

public class InvalidIdException extends ApplicationException {
    private static final long serialVersionUID = -6636850133993665859L;
    private final String identifier;
    
    public InvalidIdException(final String message, final String identifier) {
        super(message);
        this.identifier = identifier;
    }
    
    public InvalidIdException(final String message, final Throwable cause, final String identifier) {
        super(message, cause);
        this.identifier = identifier;
    }
    
    public final String getIdentifier() {
        return this.identifier;
    }
}
