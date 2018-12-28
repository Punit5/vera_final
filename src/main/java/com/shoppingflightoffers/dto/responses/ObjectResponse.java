package com.shoppingflightoffers.dto.responses;

import java.io.Serializable;

public class ObjectResponse<T> implements Serializable {
    
    private static final long serialVersionUID = -43974403780991575L;
    private Status status;
    private T response;
    
    public ObjectResponse() {
        super();
    }
    
    public ObjectResponse(final T response) {
        this.response = response;
    }
    
    public ObjectResponse(final Status status) {
        this.status = status;
    }
    
    public ObjectResponse(final T response, final Status status) {
        this.response = response;
        this.status = status;
    }
    
    public final Status getStatus() {
        return this.status;
    }
    
    public final void setStatus(final Status status) {
        this.status = status;
    }
    
    public final T getResponse() {
        return this.response;
    }
    
    public final void setResponse(final T response) {
        this.response = response;
    }
}
