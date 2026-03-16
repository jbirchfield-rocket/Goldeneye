package com.goldeneye.dto;

import java.util.Date;
import java.util.List;

import com.goldeneye.dto.OrderItemDTO;

/**
 *
 * @author dshelby
 */
public class OrderDTO {
    private int custId;
    private int locationId;
    private Date date;
    private List<OrderItemDTO> orderItems;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<OrderItemDTO> getOrderItems() { 
        return orderItems; 
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) { 
        this.orderItems = orderItems; 
    }
}
