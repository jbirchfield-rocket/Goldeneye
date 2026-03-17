/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 *
 * @author scanales
 */
@Table("TBPROD")
public class Product {
    @Id
    @Column("PRODID")
    private int prodId;

    @Column("NAME")
    private String name;

    @Column("DSCRP")
    private String description;

    @Column("BASEPRICE")
    private BigDecimal basePrice;

    public Product(int prodId, String name, String description, BigDecimal basePrice) {
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
