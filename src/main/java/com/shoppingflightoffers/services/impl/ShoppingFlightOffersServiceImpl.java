package com.shoppingflightoffers.services.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingflightoffers.constants.ValidationConstants;
import com.shoppingflightoffers.exceptions.ApplicationException;
import com.shoppingflightoffers.exceptions.DataException;
import com.shoppingflightoffers.exceptions.NotFoundException;
import com.shoppingflightoffers.models.Flight;
import com.shoppingflightoffers.models.Flight.Create;
import com.shoppingflightoffers.models.Flight.Update;
import com.shoppingflightoffers.repositories.FlightRepository;
import com.shoppingflightoffers.services.ShoppingFlightOffersService;


@SuppressWarnings({ "checkstyle:designForExtension" })
@Service
public class ShoppingFlightOffersServiceImpl implements ShoppingFlightOffersService {
    
//    @Autowired
//    private FlightRepository flightRepository;
    
    /**
     * 
     */
    @Override
    public Page<Flight> getFlightOffers(final String origin, final String destination, final String departureDateAsStr, final Pageable pageable) throws ApplicationException {
    	
    	String url = "https://test.api.amadeus.com/v1/shopping/flight-offers?origin=NYC&destination=MAD&departureDate=2019-08-01&max=2";
    	try{
    	   readAndConvertJsonFromUrl(url);
    	}catch(Exception e){    		
    	}

    	
    	Instant departureDate;
    	if (null == departureDateAsStr){
    		departureDate = Instant.now();
    	}else{
    		try{
    		departureDate = Instant.parse(departureDateAsStr);
    		}catch(Exception e){
    			departureDate = Instant.now();
    		}
    		
    	}
    	List<Flight> list = populateFlights(origin, destination, departureDate, pageable);
    	Page<Flight> page = new PageImpl<Flight>(list);
    	return page;
    	
    }
    
    /**
     * 
     * @param origin
     * @param destination
     * @param departureDate
     * @param pageable
     * @return
     */
    private List<Flight>populateFlights(final String origin, final String destination, final Instant departureDate, final Pageable pageable){
    	List<Flight> list = new ArrayList<Flight>();

    	for (int i=10; i>0; i--){   		
    	
	        int price = i*100 + i*10 + i;     
	        Instant returnDate = Instant.now();
	        String airline = "airline" + i;        
	    	list.add(populateFlight(destination, origin, price, departureDate, returnDate, airline));
    	}
    	Collections.sort(list, Flight.priceComparator);
    	list = (List<Flight>) list.subList(0, pageable.getPageSize());
    	return list;
    }

    /**
     * 
     * @param destination
     * @param origin
     * @param price
     * @param departureDate
     * @param returnDate
     * @param airline
     */
    private Flight populateFlight(String destination, String origin,int price, Instant departureDate,Instant returnDate,String airline){
    	Flight f = new Flight();
    	f.setAirline(airline);
    	f.setDepartureDate(departureDate);
    	f.setDestination(destination);
    	f.setOrigin(origin);
    	f.setPrice(price);
    	f.setReturnDate(returnDate);
    	return f;
    }
    
    public List<Flight> readAndConvertJsonFromUrl(String url) throws Exception {
    	readJsonFromUrl(url);
         //Flight flight = new Gson().fromJson(jSONObject, Flight.class);
    	return null;
    	}    
    
    public /*JSONObject*/String readJsonFromUrl(String url) throws Exception {
        InputStream is = new URL(url).openStream();
        try {
          BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
          String jsonText = readAll(rd);
          //JSONObject json = new JSONObject(jsonText);
          return jsonText;//json;
        } finally {
          is.close();
        }       
      }    
    
    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
          sb.append((char) cp);
        }
        return sb.toString();
      }    
    
}
