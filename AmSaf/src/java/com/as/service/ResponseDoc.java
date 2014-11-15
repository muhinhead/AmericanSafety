/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nick
 */
public class ResponseDoc {
    private String documentType;
    private String customerName;
    
    @Temporal(TemporalType.DATE)
    private Date startRangeTime;
    
    @Temporal(TemporalType.DATE)
    private Date finishRangeTime;
    private Boolean setPO;

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
    
}
