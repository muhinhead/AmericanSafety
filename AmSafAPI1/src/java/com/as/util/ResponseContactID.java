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
public class ResponseContactID implements Serializable {
    @Id
    private Integer contactID;
    
    public ResponseContactID() {}
    
    public ResponseContactID(Integer lastInsertedID) {
        contactID = lastInsertedID;
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
