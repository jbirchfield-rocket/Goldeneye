package com.goldeneye.dto;

/**
 *
 * @author dshelby
 */

public class CustomerDTO {
    private Integer custId;
    private String name;

    public CustomerDTO() {
    }

    public CustomerDTO(Integer custId, String name) {
        this.custId = custId;
        this.name = name;
    }

    public Integer getCustId() { 
        return custId; 
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getName() { 
        return name; 
    }

    public void setName(String name) {
        this.name = name;
    }
}
