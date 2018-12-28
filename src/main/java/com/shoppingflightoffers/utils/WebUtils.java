package com.shoppingflightoffers.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoppingflightoffers.constants.Domains;
import com.shoppingflightoffers.constants.ValidationConstants;
import com.shoppingflightoffers.exceptions.ApplicationException;
import com.shoppingflightoffers.exceptions.InvalidIdException;

public final class WebUtils {
    private static final Logger LOG = LoggerFactory.getLogger(WebUtils.class);
    
    private WebUtils() {
    }
    
    public static String executePost(String targetURL, String urlParameters) {
    	  HttpURLConnection connection = null;

    	  try {
    	    //Create connection
    	    URL url = new URL(targetURL);
    	    connection = (HttpURLConnection) url.openConnection();
    	    connection.setRequestMethod("POST");
    	    connection.setRequestProperty("Content-Type", 
    	        "application/x-www-form-urlencoded");

    	    connection.setRequestProperty("Content-Length", 
    	        Integer.toString(urlParameters.getBytes().length));
    	    connection.setRequestProperty("Content-Language", "en-US");  

    	    connection.setUseCaches(false);
    	    connection.setDoOutput(true);

    	    //Send request
    	    DataOutputStream wr = new DataOutputStream (
    	        connection.getOutputStream());
    	    wr.writeBytes(urlParameters);
    	    wr.close();

    	    //Get Response  
    	    InputStream is = connection.getInputStream();
    	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    	    StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
    	    String line;
    	    while ((line = rd.readLine()) != null) {
    	      response.append(line);
    	      response.append('\r');
    	    }
    	    rd.close();
    	    return response.toString();
    	  } catch (Exception e) {
    	    e.printStackTrace();
    	    return null;
    	  } finally {
    	    if (connection != null) {
    	      connection.disconnect();
    	    }
    	  }
    	}
        
}
