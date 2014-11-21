/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nick
 */
public class ResponseDoc implements Serializable {
    private String documentType;
    private String customerName;
    
    @Temporal(TemporalType.DATE)
    private Date startRangeTime;
    
    @Temporal(TemporalType.DATE)
    private Date finishRangeTime;
    private Boolean setPO;
    
    private Integer customerID;
    private Integer userID;

    /**
     * @return the documentType
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType the documentType to set
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
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
     * @return the startRangeTime
     */
    public Date getStartRangeTime() {
        return startRangeTime;
    }

    /**
     * @param startRangeTime the startRangeTime to set
     */
    public void setStartRangeTime(Date startRangeTime) {
        this.startRangeTime = startRangeTime;
    }

    /**
     * @return the finishRangeTime
     */
    public Date getFinishRangeTime() {
        return finishRangeTime;
    }

    /**
     * @param finishRangeTime the finishRangeTime to set
     */
    public void setFinishRangeTime(Date finishRangeTime) {
        this.finishRangeTime = finishRangeTime;
    }

    /**
     * @return the setPO
     */
    public Boolean getSetPO() {
        return setPO;
    }

    /**
     * @param setPO the setPO to set
     */
    public void setSetPO(Boolean setPO) {
        this.setPO = setPO;
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
     * @return the userID
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    
@Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResponseDoc)) {
            return false;
        }
        ResponseDoc other = (ResponseDoc) object;
        if ((this.userID == null && other.getUserID() != null) 
                || (this.userID != null && !this.userID.equals(other.getUserID()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ResponseDoc[ id=" + userID + " ]";
    }
    
}
