package com.goldeneye.dto;

import java.util.List;

import com.goldeneye.dto.OrderItemDTO;

/**
 *
 * @author dshelby
 */
public class OrderDTO {
    private int custId;
    private int locId;
    private List<OrderItemDTO> orderItems;

    public int getCustId() { return custId; }
    public void setCustId(int custId) { this.custId = custId; }

    public List<OrderItemDTO> getOrderItems() { return orderItems; }
    public void setOrderItems(List<OrderItemDTO> orderItems) { this.orderItems = orderItems; }
}
