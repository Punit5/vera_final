package com.shoppingflightoffers.validators;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

final class ValidCalendarTypeValidator implements ConstraintValidator<ValidCalendarType, String> {
    private static final Logger LOG = LoggerFactory.getLogger(ValidCalendarTypeValidator.class);
    
    @Override
    public void initialize(final ValidCalendarType validCalendarType) {
        //No implementation necessary
    }
    
    @Override
    public boolean isValid(final String iCalString, final ConstraintValidatorContext context) {
        if (StringUtils.isBlank(iCalString)) {
            return false;
        }
        
        Calendar calendar = null;
        try (InputStream is = new ByteArrayInputStream(iCalString.getBytes(StandardCharsets.UTF_8.name()))) {
            final CalendarBuilder builder = new CalendarBuilder();
            calendar = builder.build(is);
        } catch (IOException | ParserException e) {
            LOG.error("Failed validaing iCal String." + e.getMessage());
        }
        
        return null != calendar;
    }
}
