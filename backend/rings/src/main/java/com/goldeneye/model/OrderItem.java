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
public class OrderItem {
    private int orderId;
    private int productId;
    private int materialId;
    private int widthId;
    private int stoneId;
    private BigDecimal unitPrice;
    private int quantity;

    public OrderItem(int orderId, int productId, int materialId, int widthId, int stoneId, BigDecimal unitPrice, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.materialId = materialId;
        this.widthId = widthId;
        this.stoneId = stoneId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getWidthId() {
        return widthId;
    }

    public void setWidthId(int widthId) {
        this.widthId = widthId;
    }

    public int getStoneId() {
        return stoneId;
    }

    public void setStoneId(int stoneId) {
        this.stoneId = stoneId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // TODO: implement calculateUnitPrice() method
    // public void calculateUnitPrice() {

    //     this.unitPrice = (BandStyleType × MaterialMultiplier × BandWidthMultiplier) + StoneAdder 
    // }
    
    // TODO: implement calculateItemTotal() method
    // public void calculateItemTotal(){
    //     return this.unitPrice * this.quantity;
    // }
    
}
