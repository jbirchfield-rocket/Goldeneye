package com.goldeneye.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goldeneye.dto.OrderDTO;
import com.goldeneye.dto.OrderItemDTO;
import com.goldeneye.repo.OrderItemRepo;
import com.goldeneye.repo.OrderRepo;

/**
 *
 * @author dshelby
 */
@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;

    public OrderService(OrderRepo orderRepo, OrderItemRepo orderItemRepo) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
    }

    @Transactional
    public int createOrder(OrderDTO orderDTO) {
        orderRepo.insertOrder(orderDTO.getCustId(), orderDTO.getLocationId());
        int orderId = orderRepo.getLastGeneratedId();

        for (OrderItemDTO dto : orderDTO.getOrderItems()) {
            orderItemRepo.insertOrderItem(
                orderId,
                dto.getProductId(),
                dto.getMaterialId(),
                dto.getWidthId(),
                dto.getStoneId(),
                null, // TODO: populate once calculateUnitPrice() is implemented
                dto.getQuantity()
            );
        }

        return orderId;
    }
}