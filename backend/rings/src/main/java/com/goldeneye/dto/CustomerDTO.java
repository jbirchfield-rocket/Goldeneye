package com.goldeneye.dto;

/**
 *
 * @author dshelby
 */

public class CustomerDTO {
    private int custId;
    private String name;

    public CustomerDTO(int custId, String name) {
        this.custId = custId;
        this.name = name;
    }

    public int getCustId() { 
        return custId; 
    }

    public String getName() { 
        return name; 
    }
}
