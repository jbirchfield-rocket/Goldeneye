package com.goldeneye.dto;

import java.math.BigDecimal;

/**
 *
 * @author dshelby
 */
public class OrderItemSummaryDTO {
    private String productName;
    private String materialName;
    private int width;
    private String stoneName;
    private BigDecimal unitPrice;
    private int quantity;

    public OrderItemSummaryDTO() {}

    public OrderItemSummaryDTO(String productName, String materialName, int width, String stoneName, BigDecimal unitPrice, int quantity) {
        this.productName = productName;
        this.materialName = materialName;
        this.width = width;
        this.stoneName = stoneName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getProductName() { 
        return productName; 
    }

    public void setProductName(String productName) { 
        this.productName = productName; 
    }

    public String getMaterialName() { 
        return materialName; 
    }
    public void setMaterialName(String materialName) { 
        this.materialName = materialName; 
    }

    public int getWidth() { 
        return width; 
    }
    public void setWidth(int width) { 
        this.width = width; 
    }

    public String getStoneName() { 
        return stoneName; 
    }
    public void setStoneName(String stoneName) { 
        this.stoneName = stoneName; 
    }

    public BigDecimal getUnitPrice() { 
        return unitPrice; 
    }

    public void setUnitPrice(BigDecimal unitPrice) { 
        this.unitPrice = unitPrice; 
    }

    public int getQuantity() { 
        return quantity; 
    }
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }
}