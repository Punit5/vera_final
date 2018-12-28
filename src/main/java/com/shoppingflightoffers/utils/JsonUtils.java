package com.shoppingflightoffers.utils;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingflightoffers.constants.Domains;
import com.shoppingflightoffers.constants.ValidationConstants;
import com.shoppingflightoffers.exceptions.ApplicationException;
import com.shoppingflightoffers.exceptions.InvalidIdException;

public final class JsonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);
    
    private JsonUtils() {
    }
    
    public static String convertToJson(final Object object) throws IOException {
        if (object == null) {
            throw new IOException("object is null");
        }
        
        final ObjectMapper objectMapper = new ObjectMapper();
        try (StringWriter stringw = new StringWriter()) {
            
            objectMapper.writeValue(stringw, object);
            final String json = stringw.toString();
            
            if (StringUtils.isBlank(json)) {
                final String err = "Cannot convert Object to JSON document, " + object;
                LOG.error(err);
                throw new IOException(err);
            }
            
            return json;
        }
    }

}
