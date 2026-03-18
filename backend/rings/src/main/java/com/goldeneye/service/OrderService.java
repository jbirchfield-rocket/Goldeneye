package com.goldeneye.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.goldeneye.constants.AppConstants.PRICESCALE;
import com.goldeneye.dto.LocationDTO;
import com.goldeneye.dto.MaterialDTO;
import com.goldeneye.dto.OrderDTO;
import com.goldeneye.dto.OrderItemDTO;
import com.goldeneye.dto.OrderItemSummaryDTO;
import com.goldeneye.dto.OrderSummaryDTO;
import com.goldeneye.dto.ProductDTO;
import com.goldeneye.dto.StoneDTO;
import com.goldeneye.dto.WidthDTO;
import com.goldeneye.exception.InvalidOrderException;
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
    private final LocationService locationService;

    public OrderService(OrderRepo orderRepo, OrderItemRepo orderItemRepo, ProductService productService, StoneService stoneService, MaterialService materialService, WidthService widthService, LocationService locationService) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
        this.productService = productService;
        this.stoneService = stoneService;
        this.materialService = materialService;
        this.widthService = widthService;
        this.locationService = locationService;
    }

    @Transactional
    public int createOrder(OrderDTO orderDTO) {
        if (orderDTO.getOrderItems() == null || orderDTO.getOrderItems().isEmpty()) {
            throw new InvalidOrderException("Order must contain at least one item.");
        }
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

    public List<OrderSummaryDTO> getOrdersByCustId(int custId) {
        return orderRepo.findOrderSummariesByCustId(custId)
            .stream()
            .map(order -> {
                LocationDTO location = locationService.getLocationById(order.getLocId());

                List<OrderItemSummaryDTO> itemSummaries = orderItemRepo.findOrderItemSummariesByOrderId(order.getOrderId())
                    .stream()
                    .map(item -> new OrderItemSummaryDTO(
                        item.getOrderItemId(),
                        item.getProdName(),
                        item.getMattName(),
                        item.getWidth(),
                        item.getStoneName(),
                        item.getUnitPrice(),
                        item.getQty()
                    ))
                    .toList();

                return new OrderSummaryDTO(
                    order.getOrderId(),
                    order.getName(),
                    order.getOrderDate(),
                    location,
                    itemSummaries
                );
            })
            .toList();
    }

    @Transactional
    public void deleteOrderByOrderId(int orderId) {
        orderItemRepo.deleteByOrderId(orderId);
        orderRepo.deleteByOrderId(orderId);
    }

    public void updateOrder(int orderId, OrderDTO orderDTO) {
        orderRepo.updateOrder(orderDTO.getCustId(), orderDTO.getLocationId(), orderId);

        for (OrderItemDTO orderItem : orderDTO.getOrderItems()) {
            if (orderItem.getOrderItemId() != null) {
            updateOrderItem(orderItem.getOrderItemId(), orderItem);
        } else {
            BigDecimal unitPrice = calculateUnitPrice(orderItem.getProductId(), orderItem.getMaterialId(), orderItem.getWidthId(), orderItem.getStoneId(), orderItem.getQuantity());
            orderItemRepo.insertOrderItem(
                orderId,
                orderItem.getProductId(),
                orderItem.getMaterialId(),
                orderItem.getWidthId(),
                orderItem.getStoneId(),
                unitPrice,
                orderItem.getQuantity()
            );
        }
        }

    }

    public void deleteOrderItem(int ordItmId) {
        orderItemRepo.deleteByOrderItemId(ordItmId);
    }

    public void updateOrderItem(int ordItmId, OrderItemDTO orderItem) {
        BigDecimal newUnitPrice = calculateUnitPrice(orderItem.getProductId(), orderItem.getMaterialId(), orderItem.getWidthId(), orderItem.getStoneId(), orderItem.getQuantity());
        orderItemRepo.updateOrderItem(ordItmId, orderItem.getProductId(), orderItem.getMaterialId(), orderItem.getWidthId(), orderItem.getStoneId(), newUnitPrice, orderItem.getQuantity());
    }

    public BigDecimal calculateUnitPrice(int productId, int materialId, int widthId, int stoneId, int quantity) {
        // get base price from product
        ProductDTO product = productService.getProductById(productId);
        BigDecimal basePrice = product.getBasePrice().setScale(PRICESCALE, RoundingMode.HALF_UP);
        // get stone price from stone
        StoneDTO stone = stoneService.getStoneById(stoneId);
        BigDecimal stonePrice = stone.getPrice().setScale(PRICESCALE, RoundingMode.HALF_UP);
        // get material multiplier from material
        MaterialDTO material = materialService.getMaterialById(materialId);
        BigDecimal materialMultiplier = BigDecimal.valueOf(material.getMultiplier());
        // get width multiplier from width
        WidthDTO width = widthService.getWidthById(widthId);
        BigDecimal widthMultiplier = BigDecimal.valueOf(width.getMultiplier());
        // calculate final price: (stone price + (base price * material multiplier * width multiplier)) * quantity
        BigDecimal ringPrice = basePrice
            .multiply(materialMultiplier)
            .multiply(widthMultiplier)
            .setScale(PRICESCALE, RoundingMode.HALF_UP);
        
        BigDecimal individualPrice = stonePrice.add(ringPrice).setScale(PRICESCALE, RoundingMode.HALF_UP);
        BigDecimal totalPrice = individualPrice.multiply(BigDecimal.valueOf(quantity)).setScale(PRICESCALE, RoundingMode.HALF_UP);
        
        return totalPrice;
    }

    public void deleteAllOrderItems(int orderId) {
        orderItemRepo.deleteByOrderId(orderId);
    }
}