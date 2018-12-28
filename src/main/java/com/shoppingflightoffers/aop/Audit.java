package com.shoppingflightoffers.aop;

import java.util.Date;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingflightoffers.channels.AuditChannel;
import com.shoppingflightoffers.channels.AuditMessage;
import com.shoppingflightoffers.constants.Domains;
import com.shoppingflightoffers.models.Flight;

@Aspect
@Profile("!developer-machine")
@Component
//@EnableBinding(AuditChannel.class)
public class Audit {
    
    private static final String PLACEHOLDER_ID = "0001";
    
    private static final String FLIGHT_OFFER = "FLIGHT_OFFER";
    
    private static final String FLIGHT_OFFER_MICROSERVICE_NAME = "LDZCS";
    
    @Autowired
    private ObjectMapper objectMapper;
    
// kafka calls    
//    @Autowired
//    private AuditChannel channels;
    
    @Pointcut("within(com.shoppingflightoffers.controllers.ShoppingFlightOffersController+)")
    public void shoppingFlightOffersController() {
    }
    
    @Pointcut("execution(@org.springframework.web.bind.annotation.PostMapping * *.*(..))")
    public void postMethod() {
    }
    
    @Pointcut("execution(@org.springframework.web.bind.annotation.PutMapping * *.*(..))")
    public void putMethod() {
    }
    
    @Pointcut("execution(@org.springframework.web.bind.annotation.DeleteMapping * *.*(..))")
    public void deleteMethod() {
    }
    
   
    @Around("shoppingFlightOffersController() && postMethod()")
    @SuppressWarnings({ "checkstyle:all" })
    public final Object flightOffersGet(final ProceedingJoinPoint pjp) throws Throwable {
        final Flight dto = (Flight) pjp.getArgs()[0];
        final String operation = pjp.getSignature().getName();
        
        @SuppressWarnings("unchecked")
        final ResponseEntity<Object> retVal = (ResponseEntity<Object>) pjp.proceed();
        
        final String respnoseBody = this.objectMapper.writeValueAsString(retVal.getBody());
        
        // need to add link user id
        final AuditMessage msg = new AuditMessage((dto == null || dto.getOrigin() == null) ? "" : String.valueOf(dto.getOrigin()), PLACEHOLDER_ID,
                String.format("shoppingFlightOffersController %s: %s", operation, respnoseBody), respnoseBody, FLIGHT_OFFER, FLIGHT_OFFER_MICROSERVICE_NAME, new Date());
        //kafka call : this.channels.outputchannel1().send(MessageBuilder.withPayload(msg).build());
        
        return retVal;
    }
   
}
