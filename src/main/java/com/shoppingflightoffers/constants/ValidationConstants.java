package com.shoppingflightoffers.constants;

public final class ValidationConstants {
    
    public static final String ERROR_RESPONSE_STATUS = "error";
    
    public static final String REQUEST_PARAMETER_WRONG_TYPE = "error.request.parameter.wrong.type";
    public static final String REQUEST_PARAMETER_MISSING = "error.parameter.missing";
    public static final String OPTIMISTIC_LOCKING_FAILURE = "error.optimistic.locking.failure";
    public static final String JSON_PROCESSING_ERROR = "error.json.processing";
    public static final String DESERIALIZATION_FAILED = "error.deserialization.failed";
    public static final String LABEL_ERROR_CONSTRAINT_VIOLATION = "error.constraint.violation";
//    // regex 
//    public static final String REGEX_DATE = "^PT[0-9][0-9]:(([0-1]\\d)|(2[0-3])):([0-5]\\d)";
//    public static final String REGEX_TYPE = "^(XXX1|XXX2|XXX3)$";
    
    // generic exception messages

    public static final String INTERNAL_SERVER_ERROR = "error.internal.server.error";
    public static final String BAD_REQUEST = "error.bad.request";
    public static final String NOT_FOUND = "error.not.found";
    public static final String FORBIDDEN = "error.forbidden";
    public static final String METHOD_NOT_ALLOWED = "error.method.not.allowed";

    
    private ValidationConstants() {
        
    }
    
}
