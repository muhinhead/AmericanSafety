/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import com.as.Item;
import java.math.BigDecimal;

/**
 *
 * @author nick
 */
public class ResponseItem {
    private Integer itemID;
    private String description;
    private BigDecimal unitPrice;

    public ResponseItem() {
    }
    
    public ResponseItem(Item itm) {
        setItemID(itm.getItemId());
        setDescription(itm.getItemName()+" ("+itm.getItemDescription()+")");
        setUnitPrice(itm.getLastPrice());
    }
    /**
     * @return the itemID
     */
    public Integer getItemID() {
        return itemID;
    }

    /**
     * @param itemID the itemID to set
     */
    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the unitPrice
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
