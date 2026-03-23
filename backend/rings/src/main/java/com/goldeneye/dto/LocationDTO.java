package com.goldeneye.dto;

/**
 *
 * @author dshelby
 */

public class LocationDTO {
    private Integer locId;
    private Integer custId;
    private String street;
    private String city;
    private String state;
    private String zip;

    public LocationDTO() {
    }

    public LocationDTO(Integer locId, Integer custId, String street, String city, String state, String zip) {
        this.locId = locId;
        this.custId = custId;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public Integer getLocId() {
        return locId;
    }

    public void setLocId(Integer locId) {
        this.locId = locId;
    }
    
    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}

