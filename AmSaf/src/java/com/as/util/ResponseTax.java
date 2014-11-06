/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import com.as.Tax;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author nick
 */
@Entity
public class ResponseTax implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer taxId;
    private String taxDescription;

    public ResponseTax(){
    }

    public ResponseTax(Tax tax){
        setTaxID(tax.getTaxID());
        setTaxDescription(tax.getTaxDescription());
    }
    
    public Long getID() {
        return id;
    }

    public void setID(Long id) {
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
        if (!(object instanceof ResponseTax)) {
            return false;
        }
        ResponseTax other = (ResponseTax) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ResponseTax[ id=" + id + " ]";
    }

    /**
     * @return the taxId
     */
    public Integer getTaxID() {
        return taxId;
    }

    /**
     * @param taxId the taxId to set
     */
    public void setTaxID(Integer taxId) {
        this.taxId = taxId;
    }

    /**
     * @return the taxDescription
     */
    public String getTaxDescription() {
        return taxDescription;
    }

    /**
     * @param taxDescription the taxDescription to set
     */
    public void setTaxDescription(String taxDescription) {
        this.taxDescription = taxDescription;
    }
    
}
