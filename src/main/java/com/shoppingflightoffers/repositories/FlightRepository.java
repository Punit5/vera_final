package com.shoppingflightoffers.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.shoppingflightoffers.models.Flight;

public interface FlightRepository extends MongoRepository<Flight, UUID> {
    
}
