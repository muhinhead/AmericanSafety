/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author nick
 */
@Entity
public class ResponseCustomerID implements Serializable {
    @Id
    private Integer customerID;

    public ResponseCustomerID() {
    }
    
    public ResponseCustomerID(Integer lastInsertedID) {
        customerID = lastInsertedID;
    }

    /**
     * @return the customerID
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }
}
