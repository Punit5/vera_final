package com.shoppingflightoffers.exceptions;

/*
 *   ApplicationException
 *   It is a category for business rules exception and not for something like unexpected exception.
 */
public class ApplicationException extends Exception {
    
    private static final long serialVersionUID = -5135633441357119547L;
    
    public ApplicationException(final String message) {
        super(message);
    }
    
    public ApplicationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
