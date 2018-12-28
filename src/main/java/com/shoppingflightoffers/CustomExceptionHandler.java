package com.shoppingflightoffers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.shoppingflightoffers.constants.Constants;
import com.shoppingflightoffers.constants.ValidationConstants;
import com.shoppingflightoffers.dto.responses.Error;
import com.shoppingflightoffers.dto.responses.ObjectResponse;
import com.shoppingflightoffers.dto.responses.Status;
import com.shoppingflightoffers.exceptions.ApplicationException;
import com.shoppingflightoffers.exceptions.DataException;
import com.shoppingflightoffers.exceptions.InvalidIdException;
import com.shoppingflightoffers.exceptions.NotFoundException;
import com.shoppingflightoffers.services.MessageAccessorService;

import feign.FeignException;

@ControllerAdvice
@SuppressWarnings({ "PMD.BeanMembersShouldSerialize" })
public class CustomExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);
    
    @Autowired
    private MessageAccessorService messageAccessorService;
    
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public final ResponseEntity<ObjectResponse<?>> handleValidation(final MethodArgumentNotValidException e, final WebRequest request) {
        
        final List<Error> errors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(err -> errors.add(new Error<>(Constants.SEPARATOR + err.getField(), err.getDefaultMessage())));
        e.getBindingResult().getGlobalErrors().forEach(err -> errors.add(new Error<>(err.getObjectName(), err.getDefaultMessage())));
        
        final Status status = new Status(ValidationConstants.ERROR_RESPONSE_STATUS, errors);
        LOG.debug(status.toString(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ObjectResponse<>(status));
    }
    
    @ExceptionHandler({ ApplicationException.class })
    public final ResponseEntity<ObjectResponse<?>> handleValidation(final ApplicationException e, final WebRequest request) {
        LOG.error(e.getMessage(), e);
        final ObjectResponse<?> objectResponse = new ObjectResponse<>(
                this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, ValidationConstants.INTERNAL_SERVER_ERROR));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectResponse);
    }
    
    @ExceptionHandler({ InvalidIdException.class })
    public final ResponseEntity<ObjectResponse<?>> handleInvalidId(final InvalidIdException e, final WebRequest request) {
        final Status status = this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, e.getIdentifier(), e.getMessage());
        LOG.debug(status.toString(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ObjectResponse<>(status));
    }
    
    @ExceptionHandler({ NotFoundException.class })
    public final ResponseEntity<ObjectResponse<?>> handleNotFound(final NotFoundException e, final WebRequest request) {
        final Status status = this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, e.getMessage());
        LOG.debug(status.toString(), e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjectResponse<>(status));
    }
    
    @ExceptionHandler({ DataException.class })
    public final ResponseEntity<ObjectResponse<?>> handleNotFound(final DataException e, final WebRequest request) {
        final Status status = this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, e.getMessage());
        LOG.debug(status.toString(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ObjectResponse<>(status));
    }
    
    @ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
    public final ResponseEntity<ObjectResponse<?>> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e,
        final WebRequest request) {
        final Status status =
                this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, ValidationConstants.METHOD_NOT_ALLOWED);
        LOG.debug(status.toString(), e);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ObjectResponse<>(status));
    }
    
    @ExceptionHandler(HystrixRuntimeException.class)
    public final ResponseEntity<ObjectResponse<?>> handleHystrixRuntimeException(final HystrixRuntimeException e, final WebRequest request) {
        ResponseEntity<ObjectResponse<?>> responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ObjectResponse<>(
                this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, ValidationConstants.INTERNAL_SERVER_ERROR)));
        LOG.info(e.getMessage(), e);
        if (e.getCause() != null && e.getCause() instanceof FeignException) {
            final FeignException fe = (FeignException) e.getCause();
            if (fe.status() == HttpStatus.BAD_REQUEST.value()) {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ObjectResponse<>(
                        this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, ValidationConstants.BAD_REQUEST)));
            } else if (fe.status() == HttpStatus.NOT_FOUND.value()) {
                responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ObjectResponse<>(
                        this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, ValidationConstants.NOT_FOUND)));
            } else if (fe.status() == HttpStatus.FORBIDDEN.value()) {
                responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ObjectResponse<>(
                        this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, ValidationConstants.FORBIDDEN)));
            }
        }
        return responseEntity;
        
    }
    
    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public final ResponseEntity<ObjectResponse<?>> handleHttpMessageNotReadableException(final HttpMessageNotReadableException e,
        final WebRequest request) {
        
        Status status = this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, ValidationConstants.BAD_REQUEST);
        
        final Throwable cause = e.getCause();
        if (cause != null) {
            if (cause instanceof InvalidFormatException) {
                final InvalidFormatException ife = (InvalidFormatException) cause;
                final List<Error> errors = new ArrayList<>();
                final StringBuilder stringBuilder = new StringBuilder();
                ife.getPath().forEach(ref -> stringBuilder.append(Constants.SEPARATOR).append(ref.getFieldName()));
                errors.add(new Error<>(stringBuilder.toString(), this.messageAccessorService.getMessage(ValidationConstants.DESERIALIZATION_FAILED)));
                status = new Status(ValidationConstants.ERROR_RESPONSE_STATUS, errors);
            } else if (cause instanceof JsonProcessingException) {
                status = this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS,
                                                                 ValidationConstants.JSON_PROCESSING_ERROR);
            }
        }
        LOG.debug(status.toString(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ObjectResponse<>(status));
    }
    
    @ExceptionHandler({ MissingServletRequestParameterException.class })
    public final ResponseEntity<ObjectResponse<?>> handleMissingServletRequestParameterException(final MissingServletRequestParameterException e,
        final WebRequest request) {
        final Status status =
                this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, e.getParameterName(),
                                                        ValidationConstants.REQUEST_PARAMETER_MISSING, e.getParameterName(), e.getParameterType());
        LOG.debug(status.toString(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ObjectResponse<>(status));
    }
    
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public final ResponseEntity<ObjectResponse<?>> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e,
        final WebRequest request) {
        final Class<?> requiredType = e.getRequiredType();
        final String fieldName = e.getName();
        
        final Status status;
        if (requiredType == null || requiredType.getName() == null || fieldName == null) {
            status = this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, ValidationConstants.BAD_REQUEST);
        } else {
            status = this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, fieldName,
                                                             ValidationConstants.REQUEST_PARAMETER_WRONG_TYPE, requiredType.getName());
        }
        LOG.debug(status.toString(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ObjectResponse<>(status));
    }
    
    @ExceptionHandler({ OptimisticLockingFailureException.class })
    public final ResponseEntity<ObjectResponse<?>> handleOptimisticLockingFailureException(final OptimisticLockingFailureException e,
        final WebRequest request) {
        final Status status =
                this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, ValidationConstants.OPTIMISTIC_LOCKING_FAILURE);
        LOG.debug(status.toString(), e);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ObjectResponse<>(status));
    }
    
    @ExceptionHandler({ ConstraintViolationException.class })
    public final ResponseEntity<ObjectResponse<?>> handleConstraintViolationException(final ConstraintViolationException e,
        final WebRequest request) {
        
        final List<Error> errors = e.getConstraintViolations().stream()
                .map(error -> new Error<>(error.getPropertyPath().toString(), error.getMessage())).collect(Collectors.toList());
        
        final Status status = new Status(ValidationConstants.ERROR_RESPONSE_STATUS, errors);
        LOG.debug(status.toString(), e);
        return ResponseEntity.badRequest().body(new ObjectResponse<>(status));
    }
    
    @ExceptionHandler({ Exception.class })
    public final ResponseEntity<ObjectResponse<?>> handleUnknown(final Exception e, final WebRequest request) {
        LOG.error(e.getMessage(), e);
        final ObjectResponse<?> objectResponse = new ObjectResponse<>(
                this.messageAccessorService.buildStatus(ValidationConstants.ERROR_RESPONSE_STATUS, ValidationConstants.INTERNAL_SERVER_ERROR));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectResponse);
    }
}
