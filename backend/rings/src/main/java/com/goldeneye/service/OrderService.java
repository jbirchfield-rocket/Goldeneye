package com.goldeneye.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goldeneye.dto.OrderDTO;
import com.goldeneye.dto.OrderItemDTO;
import com.goldeneye.repo.OrderItemRepo;
import com.goldeneye.repo.OrderRepo;


/**
 *
 * @author dshelby, scanalesR
 */
@Service
public class OrderService {

    private final OrderRepo orderRepo;
    private final OrderItemRepo orderItemRepo;
    private final ProductService productService;
    private final StoneService stoneService;
    private final MaterialService materialService;
    private final WidthService widthService;

    public OrderService(OrderRepo orderRepo, OrderItemRepo orderItemRepo, ProductService productService, StoneService stoneService, MaterialService materialService, WidthService widthService) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.productService = productService;
        this.stoneService = stoneService;
        this.materialService = materialService;
        this.widthService = widthService;
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
                calculateUnitPrice(dto.getProductId(), dto.getMaterialId(), dto.getWidthId(), dto.getStoneId(), dto.getQuantity()),
                dto.getQuantity()
            );
        }

        return orderId;
    }

    public BigDecimal calculateUnitPrice(int productId, int materialId, int widthId, int stoneId, int quantity) {
        // get base price from product
        // get stone price from stone
        // get material multiplier from material
        // get width multiplier from width
        // calculate final price: (stone price + (base price * material multiplier * width multiplier)) * quantity
        
        return BigDecimal.ZERO;
    }
}