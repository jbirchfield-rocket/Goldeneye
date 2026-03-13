package com.goldeneye.dto;

/**
 *
 * @author dshelby
 */

public class LocationDTO {
    // private int locId;
    private int custID;
    private String street;
    private String city;
    private String state;
    private String zip;

    public LocationDTO(int custID, String street, String city, String state, String zip) {
        // this.locId = locId;
        this.custID = custID;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    
    public int getCustID() {
        return custID;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }


}

