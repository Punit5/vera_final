package com.shoppingflightoffers.models;

import java.time.Instant;
import java.util.Comparator;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.shoppingflightoffers.constants.Constants;

import io.swagger.annotations.ApiModel;

@Document(collection = "Flights")
@ApiModel(description = "The Flight Model Class. ")
@SuppressWarnings({ "checkstyle:designForExtension", "PMD.ShortClassName" })
public class Flight extends BasicModel //implements Comparable 
{
    private static final long serialVersionUID = 6749916606799595079L;
    
    @NotEmpty(groups = { Create.class, Update.class })
    @NotNull(groups = { Create.class, Update.class })
    private String destination;
    
    @NotEmpty(groups = { Create.class, Update.class })
    @NotNull(groups = { Create.class, Update.class })
    private String origin;
    
    @NotNull(groups = { Create.class, Update.class })
    @Min(value = 0L, groups = { Create.class, Update.class })
    private int price;

    @NotEmpty(groups = { Create.class, Update.class })
    @NotNull(groups = { Create.class, Update.class })
    private Instant departureDate;

    @NotEmpty(groups = { Create.class, Update.class })
    @NotNull(groups = { Create.class, Update.class })
    private Instant returnDate;
    
    @NotEmpty(groups = { Create.class, Update.class })
    @NotNull(groups = { Create.class, Update.class })
    private String airline;

    
    public Flight() {
        super();
    }
    
    public Flight(final String origin, final String destination, final int price, final Instant departureDate, final Instant returnDate, final String airline) {
    	this.origin = origin;
    	this.destination = destination;
    	this.price = price;
    	this.departureDate = departureDate;
    	this.returnDate = returnDate;
    	this.airline =airline;
    }
    
    
    public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Instant getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Instant departureDate) {
		this.departureDate = departureDate;
	}

	public Instant getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Instant returnDate) {
		this.returnDate = returnDate;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	@Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        
        if (!(o instanceof Flight)) {
            return false;
        }
        
        final Flight flight = (Flight) o;
        
        return new EqualsBuilder().appendSuper(super.equals(o)).append(this.origin, flight.origin).append(this.price, flight.price)
                .append(this.destination, flight.destination).append(this.departureDate, flight.departureDate).isEquals();
    }

//    @Override
//    public int compareTo(Flight compareF) {
//        int comparePrice=compareF.getPrice();
//        /* For Ascending order*/
//        return this.price-comparePrice;
//
//        /* For Descending order do like this */
//        //return compareage-this.studentage;
//    }
    
    /*Comparator for sorting the list by roll no*/
    public static Comparator<Flight> priceComparator = new Comparator<Flight>() {

	public int compare(Flight s1, Flight s2) {

	   int pr1 = s1.getPrice();
	   int pr2 = s2.getPrice();

	   /*For ascending order*/
	   return pr1-pr2;

	   /*For descending order*/
	   //rollno2-rollno1;
   }};
   
    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(this.origin).append(this.destination).append(this.price).append(this.departureDate).append(this.returnDate).append(this.airline)
                .toHashCode();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("origin", this.origin).append("price", this.price)
        		//....
        		.toString();
    }
    
    public interface Create extends BasicModel.Create {
    }
    
    public interface Update extends BasicModel.Update {
    }
}
