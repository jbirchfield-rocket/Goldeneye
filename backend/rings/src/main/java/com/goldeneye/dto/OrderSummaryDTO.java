package com.goldeneye.dto;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author dshelby
 */
public class OrderSummaryDTO {
    private int orderId;
    private String customerName;
    private LocalDate orderDate;
    private LocationDTO location;
    private List<OrderItemSummaryDTO> orderItems;

    public OrderSummaryDTO() {}

    public OrderSummaryDTO(int orderId, String customerName, LocalDate orderDate, LocationDTO location, List<OrderItemSummaryDTO> orderItems) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.location = location;
        this.orderItems = orderItems;
    }

    public int getOrderId() { 
        return orderId; 
    }

    public void setOrderId(int orderId) { 
        this.orderId = orderId; 
    }

    public String getCustomerName() { 
        return customerName; 
    }

    public void setCustomerName(String customerName) { 
        this.customerName = customerName; 
    }

    public LocalDate getOrderDate() { 
        return orderDate; 
    }

    public void setOrderDate(LocalDate orderDate) { 
        this.orderDate = orderDate; 
    }

    public LocationDTO getLocation() { 
        return location; 
    }

    public void setLocation(LocationDTO location) { 
        this.location = location; 
    }

    public List<OrderItemSummaryDTO> getOrderItems() { 
        return orderItems; 
    }
    public void setOrderItems(List<OrderItemSummaryDTO> orderItems) { 
        this.orderItems = orderItems; 
    }
}