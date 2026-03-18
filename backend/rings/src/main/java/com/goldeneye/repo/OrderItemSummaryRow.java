package com.goldeneye.repo;

import java.math.BigDecimal;

/**
 * 
 * @author dshelby
 */
public class OrderItemSummaryRow {
    private Integer orditmid;
    private String prodname;
    private String mattname;
    private int width;
    private String stonename;
    private BigDecimal unitprice;
    private int qty;
    
    public OrderItemSummaryRow() {
    }

    public OrderItemSummaryRow(Integer orditmid, String prodname, String mattname, int width, String stonename, BigDecimal unitprice,
            int qty) {
        this.orditmid = orditmid;
        this.prodname = prodname;
        this.mattname = mattname;
        this.width = width;
        this.stonename = stonename;
        this.unitprice = unitprice;
        this.qty = qty;
    }

    public Integer getOrderItemId() {
        return orditmid;
    }

    public void setOrderItemId(Integer orditmid) {
        this.orditmid = orditmid;
    }

    public String getProdName() {
        return prodname;
    }

    public void setProdName(String prodname) {
        this.prodname = prodname;
    }

    public String getMattName() {
        return mattname;
    }

    public void setMattName(String mattname) {
        this.mattname = mattname;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getStoneName() {
        return stonename;
    }

    public void setStoneName(String stonename) {
        this.stonename = stonename;
    }

    public BigDecimal getUnitPrice() {
        return unitprice;
    }

    public void setUnitPrice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    
}