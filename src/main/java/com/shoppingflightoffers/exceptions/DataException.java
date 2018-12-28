package com.shoppingflightoffers.exceptions;

public class DataException extends ApplicationException {
    private static final long serialVersionUID = 4198970926021646666L;
    
    public DataException(final String message) {
        super(message);
    }
    
    public DataException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
