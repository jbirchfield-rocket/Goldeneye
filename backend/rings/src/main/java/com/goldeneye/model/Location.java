/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


/**
 *
 * @author scanales
 */
@Table("TBLOC")
public class Location {
    @Id
    @Column("LOCID")
    private int locationId;

    @Column("CUSTID")
    private int custId;

    @Column("STR")
    private String street;

    @Column("CITY")
    private String city;

    @Column("ST")
    private String state;

    @Column("ZIP")
    private String zip;

    public Location(int locationId, int custId, String street, String city, String state, String zip) {
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    // TODO: Implement validateStreet() Method
    // var char 200
    // TODO: Implement validateCity() Method
    // var char 120
    // TODO: Implement validateState Method
    // char 2
    // TODO: Implement validateZip() Method
    // char 10
}