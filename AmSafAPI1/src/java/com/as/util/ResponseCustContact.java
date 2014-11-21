/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import com.as.Contact;
import java.io.Serializable;

/**
 *
 * @author nick
 */
public class ResponseCustContact implements Serializable {
    private Integer contactID;
    private String contactName;
    private String conctactSurname;
    private String contactPhone;
    private String contactEmail;    

    public ResponseCustContact() {
    }

    public ResponseCustContact(Contact original) {
        setContactID(original.getContactId());
        setContactEmail(original.getEmail());
        setContactName(original.getFirstName());
        setConctactSurname(original.getLastName());
        setContactPhone(original.getPhone());
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

    /**
     * @return the contactName
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName the contactName to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return the conctactSurname
     */
    public String getConctactSurname() {
        return conctactSurname;
    }

    /**
     * @param conctactSurname the conctactSurname to set
     */
    public void setConctactSurname(String conctactSurname) {
        this.conctactSurname = conctactSurname;
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
}
