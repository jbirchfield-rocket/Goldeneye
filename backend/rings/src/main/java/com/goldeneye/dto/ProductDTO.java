package com.goldeneye.dto;

import java.math.BigDecimal;

/**
 *
 * @author dshelby
 */

public class ProductDTO {
    private int prodId;
    private String name;
    private String description;
    private BigDecimal basePrice;

    public ProductDTO(int custId, String name) {
        this.prodId = prodId;
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
    }

    public int getProdId() {
        return prodId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }


}

