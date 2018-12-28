package com.shoppingflightoffers.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shoppingflightoffers.exceptions.ApplicationException;
import com.shoppingflightoffers.models.Flight;

public interface ShoppingFlightOffersService {
    
    Page<Flight> getFlightOffers(final String origin, final String destination, final String date, final Pageable pageable) throws ApplicationException;
}
