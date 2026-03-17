package com.goldeneye.repo;

import java.time.LocalDate;

/**
 *
 * @author dshelby
 */
public class OrderSummaryRow {

    private int orderid;
    private String name;
    private LocalDate orderdate;
    private int locid;

    public OrderSummaryRow() {}

    public int getOrderId() {
        return orderid;
    }

    public void setOrderId(int orderid) {
        this.orderid = orderid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getOrderDate() {
        return orderdate;
    }

    public void setOrderDate(LocalDate orderdate) {
        this.orderdate = orderdate;
    }

    public int getLocId() {
        return locid;
    }

    public void setLocId(int locid) {
        this.locid = locid;
    }

    

}