/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import com.as.Contact;
import com.as.Customer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author nick
 */
public class ResponseCustomer implements Serializable {
    private Integer customerID;
    private String customerName;
    private String customerAddress;
    private Collection<ResponseCustContact> contacts;

    public ResponseCustomer() {
    }
    
    public ResponseCustomer(Customer cust) {
        setCustomerID(cust.getCustomerId());
        setCustomerName(cust.getCustomerName());
        setCustomerAddress(cust.getCustomerAddress());
        if (cust.getContactCollection() != null) {
            contacts = new ArrayList<ResponseCustContact>(cust.getContactCollection().size());
            for (Contact c : cust.getContactCollection()) {
                contacts.add(new ResponseCustContact(c));
            }
        }
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
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * @param customerAddress the customerAddress to set
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * @return the contacts
     */
    public Collection<ResponseCustContact> getContacts() {
        return contacts;
    }

    /**
     * @param contacts the contacts to set
     */
    public void setContacts(Collection<ResponseCustContact> contacts) {
        this.contacts = contacts;
    }

}
