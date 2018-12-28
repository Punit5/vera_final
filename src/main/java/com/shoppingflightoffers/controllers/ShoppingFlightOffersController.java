package com.shoppingflightoffers.controllers;

import java.util.UUID;

import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingflightoffers.constants.Domains;
import com.shoppingflightoffers.constants.ValidationConstants;
import com.shoppingflightoffers.dto.responses.ObjectResponse;
import com.shoppingflightoffers.exceptions.ApplicationException;
import com.shoppingflightoffers.exceptions.InvalidIdException;
import com.shoppingflightoffers.models.Flight;
import com.shoppingflightoffers.services.ShoppingFlightOffersService;
import com.shoppingflightoffers.utils.JsonUtils;

import io.swagger.annotations.ApiOperation;

@SuppressWarnings({ "checkstyle:designForExtension", "PMD.ExcessiveImports" })
@RestController
@RequestMapping(Domains.FLIGHT_OFFERS)
@Validated
public class ShoppingFlightOffersController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ShoppingFlightOffersController.class);
    
    @Autowired
    private ShoppingFlightOffersService shoppingFlightOffersService;
    
    @GetMapping
    @ApiOperation(value = "Retrieve best flights prices", notes = "Retrieve all flights for a customer")
    public ResponseEntity<Page<Flight>> listFlightOffers(
   		@RequestParam String origin, @RequestParam String destination, @RequestParam String date,
        @PageableDefault(size = Domains.PAGE_SIZE_BEST_OFFERS, sort = Domains.SORT_BY_PRICE, direction=Direction.DESC) final Pageable pageable) throws ApplicationException {
        LOG.debug("Received list Request: ", origin + "; " + destination + "; " + date);
        
        final Page<Flight> flightOffers = this.shoppingFlightOffersService.getFlightOffers(origin, destination, date, pageable);
        return ResponseEntity.ok(flightOffers);
    }
    
    @PostMapping
    @ApiOperation(value = "Create a new Flight Offer", notes = "The new created  Flight Offer is returned.")
    public ResponseEntity<ObjectResponse<Flight>> createFlightOffer(@Validated(Flight.Create.class) @RequestBody final Flight flightOffer, 
                                                           @RequestParam @Min(value = 1) final int customerId) throws ApplicationException {
    	//TO DO 
        final Flight newFlightOffer = null;//this.shoppingFlightOffersService.createFlightOffer(flightOffer);
        return ResponseEntity.ok(new ObjectResponse<>(newFlightOffer));
    }
    
    @PutMapping(Domains.FLIGHT_ID_REGEX)
    @ApiOperation(value = "Update a flight Offer", notes = "The flightOffer to update is an input and The updated flightOffer is returned.")
    public ResponseEntity<ObjectResponse<Flight>> updateFlightOffer(@PathVariable final UUID flightOfferId,
                                                           @RequestParam @Min(value = 1) final int customerId,
                                                           @Validated(Flight.Update.class) @RequestBody final Flight flightOffer) throws ApplicationException {
    	//TO DO 
        if (!flightOfferId.equals(flightOffer.getId())) {                                           
            throw new InvalidIdException(ValidationConstants.BAD_REQUEST, Domains.ID);
        }
        return ResponseEntity.ok(new ObjectResponse<>(null));
    }
    
    @GetMapping(Domains.FLIGHT_ID_REGEX)
    @ApiOperation(value = "Retrieve flightOffer by Id", notes = "Retrieve flightOffer by Id, the id is UUID")
    public ResponseEntity<ObjectResponse<Flight>> viewFlightOffer(@PathVariable final UUID flightOfferId, @RequestParam @Min(value = 1) final int customerId)
        throws ApplicationException {
        LOG.debug("Received viewFlightOffer Request: id = {}, customerId = {}", flightOfferId, customerId);
        //TO DO 
        final Flight flightOffer = null;//this.shoppingFlightOffersService.getFlightOffer(customerId, flightOfferId);
        return ResponseEntity.ok(new ObjectResponse<>(flightOffer));
    }
    
    @DeleteMapping(Domains.FLIGHT_ID_REGEX)
    @ApiOperation(value = "Delete flightOffer by flightOfferId", notes = "Delete flightOffer by flightOfferId, the flightOfferId is UUID. The deleted flightOffer is returned.")
    public ResponseEntity<ObjectResponse<?>> deleteFlightOffer(@PathVariable final UUID flightOfferId, @RequestParam @Min(value = 1) final int customerId)
        throws ApplicationException {
              
        LOG.debug("Received delete Request: id = {}, customerId = {}", flightOfferId, customerId);
        //TO DO 
        //this.shoppingFlightOffersService.deleteFlightOffer(customerId, flightOfferId);
        return ResponseEntity.ok(new ObjectResponse<>(null));
    }
    
}
