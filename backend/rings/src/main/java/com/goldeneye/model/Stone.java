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
 * @author dshelby
 */
@Table("TBSTONE")
public class Stone {
    @Id
    @Column("STONEID")
    private int stoneId;

    @Column("NAME")
    private String stoneName;

    @Column("INVENTORY")
    private int inventory;
    
    @Column("PRICE")
    private BigDecimal price;

    public Stone(int stoneId, String stoneName, int inventory, BigDecimal price) {
        this.stoneId = stoneId;
        this.stoneName = stoneName;
        this.inventory = inventory;
        this.price = price;
    }

    public int getStoneId() {
        return stoneId;
    }

    public void setStoneId(int stoneId) {
        this.stoneId = stoneId;
    }

    public String getStoneName() {
        return stoneName;
    }

    public void setStoneName(String stoneName) {
        this.stoneName = stoneName;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
