/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import java.io.Serializable;

/**
 *
 * @author nick
 */
public class Param4newContact implements Serializable {

    private Integer customerID; //ignored on Customer creation, used on single Contact creation
    private Integer contactID;  //ignored on input, used for output only
    private String contactTitle, contactFirstName, contactLastName, contactEmail, contactPhone;

    /**
     * @return the contactFirstName
     */
    public String getContactFirstName() {
        return contactFirstName;
    }

    /**
     * @param contactFirstName the contactFirstName to set
     */
    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    /**
     * @return the contactLastName
     */
    public String getContactLastName() {
        return contactLastName;
    }

    /**
     * @param contactLastName the contactLastName to set
     */
    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    /**
     * @return the contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail the contactEmail to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * @return the contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * @param contactPhone the contactPhone to set
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * @return the contactTitle
     */
    public String getContactTitle() {
        return contactTitle;
    }

    /**
     * @param contactTitle the contactTitle to set
     */
    public void setContactTitle(String contactTitle) {
        this.contactTitle = contactTitle;
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
     * @return the contactID
     */
    public Integer getContactID() {
        return contactID;
    }

    /**
     * @param contactID the contactID to set
     */
    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }
}
