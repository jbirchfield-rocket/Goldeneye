/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.model;

import java.math.BigDecimal;

/**
 *
 * @author scanales
 */
public class Product {
    private int prodId;
    private String name;
    private String description;
    private BigDecimal basePrice;

    Product(int prodId, String name, String description, BigDecimal basePrice) {
        this.prodId = prodId;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }
}
