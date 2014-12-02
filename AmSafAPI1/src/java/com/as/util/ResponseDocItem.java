/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import com.as.Invoiceitem;
import com.as.Item;
import com.as.Orderitem;
import com.as.Quoteitem;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author nick
 */
@Entity
public class ResponseDocItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer docItemId;
    private Item item;
    private int qty;
    private BigDecimal price;
    private Boolean tax;

    public ResponseDocItem() {
    }
    
    public ResponseDocItem(Invoiceitem iitm) {
        setDocItemId(iitm.getInvoiceitemId());
        setItem(iitm.getItemId());
        setQty(iitm.getQty());
        setPrice(iitm.getPrice());
        setTax(iitm.getTax());
    }

    public ResponseDocItem(Orderitem oitm) {
        setDocItemId(oitm.getOrderitemId());
        setItem(oitm.getItemId());
        setQty(oitm.getQty());
        setPrice(oitm.getPrice());
        setTax(oitm.getTax());
    }
    
    public ResponseDocItem(Quoteitem qitm) {
        setDocItemId(qitm.getQuoteitemId());
        setItem(qitm.getItemId());
        setQty(qitm.getQty());
        setPrice(qitm.getPrice());
        setTax(qitm.getTax());
    }
    
    public Integer getDocItemId() {
        return docItemId;
    }

    public void setDocItemId(Integer id) {
        this.docItemId = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docItemId != null ? docItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the docItemId fields are not set
        if (!(object instanceof ResponseDocItem)) {
            return false;
        }
        ResponseDocItem other = (ResponseDocItem) object;
        if ((this.docItemId == null && other.docItemId != null) || (this.docItemId != null && !this.docItemId.equals(other.docItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ResponseDocItem[ id=" + docItemId + " ]";
    }

    /**
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param quantity the qty to set
     */
    public void setQty(int quantity) {
        this.qty = quantity;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the tax
     */
    public Boolean getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(Boolean tax) {
        this.tax = tax;
    }
    
}
