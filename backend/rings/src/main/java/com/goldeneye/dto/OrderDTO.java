package com.goldeneye.dto;

import java.util.Date;
import java.util.List;

/**
 *
 * @author dshelby
 */
public class OrderDTO {
    private Integer orderId;
    private int custId;
    private int locationId;
    private Date date;
    private List<OrderItemDTO> orderItems;

    public OrderDTO() {
    }

    public OrderDTO(int orderId, int custId, int locationId, Date date, List<OrderItemDTO> orderItems) {
        this.orderId = orderId;
        this.custId = custId;
        this.locationId = locationId;
        this.date = date;
        this.orderItems = orderItems;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
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
