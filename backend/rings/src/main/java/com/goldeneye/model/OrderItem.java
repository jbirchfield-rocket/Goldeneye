/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

/**
 *
 * @author scanales
 */
@Table("TBORDITM")
public class OrderItem {
    @Id
    @Column("ORDITMID")
    private int orderItemId;

    @Column("ORDERID")
    private int orderId;

    @Column("PRODID")
    private int productId;

    @Column("MATTID")
    private int materialId;

    @Column("WID")
    private int widthId;

    @Column("STONEID")
    private int stoneId;

    @Column("UNITPRICE")
    private BigDecimal unitPrice;

    @Column("QTY")
    private int quantity;

    public OrderItem(int orderItemId, int orderId, int productId, int materialId, int widthId, int stoneId, BigDecimal unitPrice, int quantity) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.materialId = materialId;
        this.widthId = widthId;
        this.stoneId = stoneId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
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
