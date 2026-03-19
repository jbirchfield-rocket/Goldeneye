package com.goldeneye.dto;

import java.math.BigDecimal;

/**
 *
 * @author dshelby
 */

public class StoneDTO {
    private int stoneId;
    private String name;
    private int inventory;
    private BigDecimal price;

    public StoneDTO() {}

    public StoneDTO(int stoneId, String name, int inventory, BigDecimal price) {
        this.stoneId = stoneId;
        this.name = name;
        this.inventory = inventory;
        this.price = price;
    }

    public int getStoneId() { 
        return stoneId; 
    }

    public String getName() { 
        return name; 
    }

    public int getInventory() {
        return inventory;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
