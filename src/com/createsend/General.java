package com.createsend;

import java.util.Date;

import com.createsend.models.ApiKey;
import com.createsend.models.SystemDate;
import com.createsend.models.clients.Client;
import com.createsend.util.BaseClient;
import com.createsend.util.exceptions.CreateSendException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;


public class General extends BaseClient {
    
    /**
     * Gets the API Key to use with the given authentication data
     * @param siteAddress The create send site address for the account
     * @param username The username used to login
     * @param password The password of the account
     * @return The API key for the specified authentication data
     * @throws CreateSendException
     */
    public String getAPIKey(String siteAddress, String username, String password) throws CreateSendException {
        WebResource r = getAPIResource().path("apikey.json").queryParam("siteurl", siteAddress);
        r.addFilter(new HTTPBasicAuthFilter(username, password));
        
        try { 
            return r.get(ApiKey.class).ApiKey;   
        } catch (UniformInterfaceException ue) {
            throw handleErrorResponse(ue);
        }
    }
    
    /**
     * @return An array of active clients in the create send account. 
     * This method will only set the {@link com.createsend.models.clients.Client#ClientID} 
     * and {@link com.createsend.models.clients.Client#Name} properties of the client
     * @throws CreateSendException
     */
    public Client[] getClients() throws CreateSendException {
        return get(Client[].class, "clients.json");
    }
    
    /**
     * @return An array of valid countries for use in the Create Send API
     * @throws CreateSendException
     */
    public String[] getCountries() throws CreateSendException {
        return get(String[].class, "countries.json");
    }
    
    /**
     * @return An array of valid timezones for use in the Create Send API
     * @throws CreateSendException
     */
    public String[] getTimezones() throws CreateSendException {
        return get(String[].class, "timezones.json");
    }
    
    /**
     * @return The current date in the timezone of the authorised account
     * @throws CreateSendException
     */
    public Date getSystemDate() throws CreateSendException {
        return get(SystemDate.class, "systemdate.json").SystemDate;
    }
}
