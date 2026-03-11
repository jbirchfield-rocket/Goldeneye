/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.model;

/**
 *
 * @author scanales
 */
public class Location {
    private int locationId;
    private int custId;
    private String street;
    private String city;
    private String state;
    private int zip;

    public Location(int locationId, int custId, String street, String city, String state, int zip) {
        this.locationId = locationId;
        this.custId = custId;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
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

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    // TODO: Implement validateStreet() Method
    // TODO: Implement validateCity() Method
    // TODO: Implement validateState Method
    // TODO: Implement validateZip() Method
}