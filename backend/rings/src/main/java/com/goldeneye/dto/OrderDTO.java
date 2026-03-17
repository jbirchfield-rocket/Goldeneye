package com.goldeneye.dto;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dshelby
 */
public class OrderDTO {
    private int orderId;
    private int custId;
    private int locationId;
    private LocalDate date;
    private List<OrderItemDTO> orderItems;

    public OrderDTO(int orderId, int custId, int locationId, Date date, List<OrderItemDTO> orderItems) {
        this.orderId = orderId;
        this.custId = custId;
        this.locationId = locationId;
        this.date = date;
        this.orderItems = orderItems;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<OrderItemDTO> getOrderItems() { 
        return orderItems; 
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) { 
        this.orderItems = orderItems; 
    }
}
