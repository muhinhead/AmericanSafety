package com.as.util;

import java.math.BigDecimal;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Nick Mukhin
 */
@Entity
public class ParamDocItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamDocItem)) {
            return false;
        }
        ParamDocItem other = (ParamDocItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ParamDocItem[ id=" + id + " ]";
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
