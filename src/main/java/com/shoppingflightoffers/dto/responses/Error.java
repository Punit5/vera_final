package com.shoppingflightoffers.dto.responses;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Error<T> implements Serializable {
    private static final long serialVersionUID = -5471964218333787338L;
    private T identifier;
    private String message;
    
    public Error() {
        super();
    }
    
    public Error(final String message) {
        this.message = message;
    }
    
    public Error(final T identifier, final String message) {
        this.identifier = identifier;
        this.message = message;
    }
    
    public final T getIdentifier() {
        return this.identifier;
    }
    
    public final void setIdentifier(final T identifier) {
        this.identifier = identifier;
    }
    
    public final String getMessage() {
        return this.message;
    }
    
    public final void setMessage(final String message) {
        this.message = message;
    }
    
    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        
        if (!(o instanceof Error)) {
            return false;
        }
        
        final Error<?> error = (Error<?>) o;
        
        return new EqualsBuilder().append(this.identifier, error.identifier).append(this.message, error.message).isEquals();
    }
    
    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(this.identifier).append(this.message).toHashCode();
    }
    
    @Override
    public final String toString() {
        return new ToStringBuilder(this).append("identifier", this.identifier).append("message", this.message).toString();
    }
}
