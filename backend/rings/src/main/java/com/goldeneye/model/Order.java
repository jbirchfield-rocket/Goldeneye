/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.goldeneye.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


/**
 *
 * @author dshelby
 */
@Table("TBORDER")
public class Order {
    @Id
    @Column("ORDERID")
    private int orderId;

    @Column("CUSTID")
    private int custId;

    @Column("LOCID")
    private int locationId;
    private LocalDate orderDate;

    public Order (int orderId, int custId, int locationId, LocalDate orderDate) {
        this.orderId = orderId;
        this.custId = custId;
        this.locationId = locationId;
        this.orderDate = orderDate;
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

    
        public int getLocationId() {
            return locationId;
        }
    
        public void setLocationId(int locationId) {
            this.locationId = locationId;
        }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

}