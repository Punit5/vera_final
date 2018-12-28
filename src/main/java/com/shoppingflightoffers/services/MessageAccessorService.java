package com.shoppingflightoffers.services;

import java.util.Collection;

import com.shoppingflightoffers.dto.responses.Status;

public interface MessageAccessorService {
    
    Status buildStatus(String responseStatus, String errorLabel);
    
    Status buildStatus(String responseStatus, String identifier, String errorLabel);
    
    Status buildStatus(String responseStatus, Collection<?> identifiers, String errorLabel);
    
    @SuppressWarnings("PMD.UseObjectForClearerAPI")
    Status buildStatus(String responseStatus, String identifier, String errorLabel, String... args);
    
    Status buildStatus(String responseStatus, String identifier, String errorLabel, Collection<String> args);
    
    String getMessage(String label);
}
