package com.as.util;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Nick Mukhin
 */
@Entity
public class ResponseDocument implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String documentType;
    private String customerName;
    
    @Temporal(TemporalType.DATE)
    private Date startRangeTime;
    
    @Temporal(TemporalType.DATE)
    private Date finishRangeTime;
    private Boolean setPO;

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
        if (!(object instanceof ResponseDocument)) {
            return false;
        }
        ResponseDocument other = (ResponseDocument) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ResponseDocument[ id=" + id + " ]";
    }

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
