package com.shoppingflightoffers.dto.responses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Status implements Serializable {
    
    private static final long serialVersionUID = 5374010050989777598L;
    private String responseStatus;
    private List<Error> errors;
    
    public Status() {
        super();
    }
    
    public Status(final String responseStatus, final String message) {
        this.responseStatus = responseStatus;
        this.errors = new ArrayList<>();
        final Error error = new Error(message);
        this.errors.add(error);
        
    }
    
    public Status(final String responseStatus, final List<Error> errors) {
        this.responseStatus = responseStatus;
        this.errors = errors;
    }
    
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
    
    public final String getResponseStatus() {
        return this.responseStatus;
    }
    
    public final void setResponseStatus(final String responseStatus) {
        this.responseStatus = responseStatus;
    }
    
    public final List<Error> getErrors() {
        return this.errors;
    }
    
    public void setErrors(final List<Error> errors) {
        this.errors = errors;
    }
    
    public void addError(final Error error) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.add(error);
    }
}
