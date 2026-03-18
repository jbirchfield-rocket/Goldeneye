package com.goldeneye.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.goldeneye.constants.AppConstants.PRICESCALE;

/**
 *
 * @author dshelby
 */
public class OrderItemDTO {
    private int productId;
    private int materialId;
    private int widthId;
    private int stoneId;
    private int quantity;

    public OrderItemDTO() {
    }

    public OrderItemDTO(int productId, int materialId, int widthId, int stoneId, int quantity) {
        this.productId = productId;
        this.materialId = materialId;
        this.widthId = widthId;
        this.stoneId = stoneId;
        this.quantity = quantity;
    }


    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    
    public int getMaterialId() { return materialId; }
    public void setMaterialId(int materialId) { this.materialId = materialId; }

    public int getWidthId() { return widthId; }
    public void setWidthId(int widthId) { this.widthId = widthId; }

    public int getStoneId() { return stoneId; }
    public void setStoneId(int stoneId) { this.stoneId = stoneId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}