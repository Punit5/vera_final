package com.shoppingflightoffers.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shoppingflightoffers.constants.ValidationConstants;
import com.shoppingflightoffers.exceptions.ApplicationException;
import com.shoppingflightoffers.exceptions.DataException;
import com.shoppingflightoffers.exceptions.NotFoundException;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
@RequestMapping("${error.path:/error}")
public class CustomErrorController implements ErrorController {
    
    @Value("${error.path:/error}")
    private String errorPath;
    
    @Override
    public final String getErrorPath() {
        return this.errorPath;
    }
    
    @RequestMapping
    @ResponseBody
    public final ResponseEntity<?> error(final HttpServletRequest request) throws ApplicationException {
        final HttpStatus status = getStatus(request);
        if (status.equals(HttpStatus.NOT_FOUND)) {
            throw new NotFoundException(ValidationConstants.NOT_FOUND);
        } else if (status.equals(HttpStatus.BAD_REQUEST)) {
            throw new DataException(ValidationConstants.BAD_REQUEST);
        } else {
            throw new ApplicationException(ValidationConstants.INTERNAL_SERVER_ERROR);
        }
    }
    
    private HttpStatus getStatus(final HttpServletRequest request) {
        final Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
    
}