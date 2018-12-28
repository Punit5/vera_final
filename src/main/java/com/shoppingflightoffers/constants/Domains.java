package com.shoppingflightoffers.constants;

public final class Domains {
    public static final int PAGE_SIZE = 25;
    public static final int PAGE_SIZE_BEST_OFFERS = 3;
    public static final String SORT_BY_PRICE = "price";
    
    public static final String ID = "id";
    public static final String CUSTOMER_ID = "customerId";
    
    public static final String FLIGHT_ID_REGEX = "/{flightId:[a-fA-F0-9-]{36}}";
    public static final String FLIGHT_OFFERS = "/shopping/flight-offers";
    
    private Domains() {
    }
}
