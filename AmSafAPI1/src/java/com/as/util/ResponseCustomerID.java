/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import java.io.Serializable;
import java.util.Collection;
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
    private Collection<Param4newContact> contacts;

    public ResponseCustomerID() {
    }
    
    public ResponseCustomerID(Integer lastInsertedID, Collection<Param4newContact> contacts) {
        this.customerID = lastInsertedID;
        this.contacts = contacts;
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

    /**
     * @return the contacts
     */
    public Collection<Param4newContact> getContacts() {
        return contacts;
    }

    /**
     * @param contacts the contacts to set
     */
    public void setContacts(Collection<Param4newContact> contacts) {
        this.contacts = contacts;
    }

   
}
