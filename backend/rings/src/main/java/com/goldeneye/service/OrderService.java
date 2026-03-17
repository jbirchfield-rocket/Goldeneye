package com.goldeneye.service;

import java.math.RoundingMode;
import java.util.List;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goldeneye.dto.LocationDTO;
import com.goldeneye.dto.MaterialDTO;
import com.goldeneye.dto.OrderDTO;
import com.goldeneye.dto.OrderItemDTO;
import com.goldeneye.dto.OrderItemSummaryDTO;
import com.goldeneye.dto.OrderSummaryDTO;
import com.goldeneye.dto.ProductDTO;
import com.goldeneye.dto.StoneDTO;
import com.goldeneye.dto.WidthDTO;
import com.goldeneye.repo.OrderItemRepo;
import com.goldeneye.repo.OrderRepo;

import static com.goldeneye.constants.AppConstants.PRICESCALE;


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
}