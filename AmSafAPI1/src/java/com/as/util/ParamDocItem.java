/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author nick
 */
public class ParamDocItem implements Serializable {
    private Integer itemID;
    private Integer qty;
    private BigDecimal sum;

    public ParamDocItem() {
    }

    public ParamDocItem(Integer itemID, Integer qty, BigDecimal sum) {
        this.itemID = itemID;
        this.qty = qty;
        this.sum = sum;
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
     * @return the qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * @return the sum
     */
    public BigDecimal getSum() {
        return sum;
    }

    /**
     * @param sum the sum to set
     */
    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
