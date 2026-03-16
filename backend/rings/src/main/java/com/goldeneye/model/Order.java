/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author scanales
 */
public class Order {
    private int orderId;
    private int custId;
    private LocalDate orderDate;
    private BigDecimal totalPrice;

    public Order (int orderId, int custId, LocalDate orderDate, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.custId = custId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    // TODO: Implement calculateTotalPrice() method    
}
