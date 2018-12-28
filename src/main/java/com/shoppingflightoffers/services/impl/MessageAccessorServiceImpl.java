package com.shoppingflightoffers.services.impl;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.shoppingflightoffers.dto.responses.Error;
import com.shoppingflightoffers.dto.responses.Status;
import com.shoppingflightoffers.services.MessageAccessorService;

@Service
public class MessageAccessorServiceImpl implements MessageAccessorService {
    
    @Autowired
    private MessageSource messageSource;
    
    @Override
    public final Status buildStatus(final String responseStatus, final String identifier, final String errorLabel) {
        final Status status = new Status();
        status.addError(new Error<>(identifier, this.messageSource.getMessage(errorLabel, new Object[0], Locale.getDefault())));
        status.setResponseStatus(responseStatus);
        return status;
    }
    
    @Override
    @SuppressWarnings("PMD.UseObjectForClearerAPI")
    public final Status buildStatus(final String responseStatus, final String identifier, final String errorLabel, final String... args) {
        final Status status = new Status();
        status.addError(new Error<>(identifier, this.messageSource.getMessage(errorLabel, args, Locale.getDefault())));
        status.setResponseStatus(responseStatus);
        return status;
    }
    
    @Override
    public final Status buildStatus(final String responseStatus, final String errorLabel) {
        final String message = this.messageSource.getMessage(errorLabel, new String[0], Locale.getDefault());
        return new Status(responseStatus, message);
    }
    
    @Override
    public final Status buildStatus(final String responseStatus, final String identifier, final String errorLabel, final Collection<String> args) {
        final Status status = new Status();
        args.forEach(arg -> status
                .addError(new Error<>(identifier, this.messageSource.getMessage(errorLabel, new Object[] { arg }, Locale.getDefault()))));
        status.setResponseStatus(responseStatus);
        return status;
    }
    
    @Override
    public final Status buildStatus(final String responseStatus, final Collection<?> identifiers, final String errorLabel) {
        final Status status = new Status();
        status.addError(new Error<>(identifiers, this.messageSource.getMessage(errorLabel, new Object[0], Locale.getDefault())));
        status.setResponseStatus(responseStatus);
        return status;
    }
    
    @Override
    public final String getMessage(final String label) {
        return this.messageSource.getMessage(label, new String[0], Locale.getDefault());
    }
}
