package com.as.util;

import java.util.Date;
import java.io.Serializable;
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
public class DocumentsParams implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Integer userID;
    private String[] documentType;
    private Integer customerID;
    @Temporal(TemporalType.DATE)
    private Date startFirstRangeTime;
    @Temporal(TemporalType.DATE)
    private Date startSecondRangeTime;
    @Temporal(TemporalType.DATE)
    private Date finishFirstRangeTime;
    @Temporal(TemporalType.DATE)
    private Date finishSecondRangeTime;
    private String poNumber;
    private Integer offset;
    private Integer limit;
    private Integer departmentID;

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
        if (!(object instanceof DocumentsParams)) {
            return false;
        }
        DocumentsParams other = (DocumentsParams) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.DocumentsParams[ id=" + id + " ]";
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
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

    /**
     * @return the documentType
     */
    public String[] getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType the documentType to set
     */
    public void setDocumentType(String[] documentType) {
        this.documentType = documentType;
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
     * @return the startFirstRangeTime
     */
    public Date getStartFirstRangeTime() {
        return startFirstRangeTime;
    }

    /**
     * @param startFirstRangeTime the startFirstRangeTime to set
     */
    public void setStartFirstRangeTime(Date startFirstRangeTime) {
        this.startFirstRangeTime = startFirstRangeTime;
    }

    /**
     * @return the startSecondRangeTime
     */
    public Date getStartSecondRangeTime() {
        return startSecondRangeTime;
    }

    /**
     * @param startSecondRangeTime the startSecondRangeTime to set
     */
    public void setStartSecondRangeTime(Date startSecondRangeTime) {
        this.startSecondRangeTime = startSecondRangeTime;
    }

    /**
     * @return the finishFirstRangeTime
     */
    public Date getFinishFirstRangeTime() {
        return finishFirstRangeTime;
    }

    /**
     * @param finishFristRangeTime the finishFirstRangeTime to set
     */
    public void setFinishFirstRangeTime(Date finishFirstRangeTime) {
        this.finishFirstRangeTime = finishFirstRangeTime;
    }

    /**
     * @return the finishSecondRangeTime
     */
    public Date getFinishSecondRangeTime() {
        return finishSecondRangeTime;
    }

    /**
     * @param finishSecondRangeTime the finishSecondRangeTime to set
     */
    public void setFinishSecondRangeTime(Date finishSecondRangeTime) {
        this.finishSecondRangeTime = finishSecondRangeTime;
    }

//    /**
//     * @return the setPO
//     */
//    public Boolean getSetPO() {
//        return setPO;
//    }
//
//    /**
//     * @param setPO the setPO to set
//     */
//    public void setSetPO(Boolean setPO) {
//        this.setPO = setPO;
//    }

    /**
     * @return the offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * @return the limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * @return the departmentID
     */
    public Integer getDepartmentID() {
        return departmentID;
    }

    /**
     * @param departmentID the departmentID to set
     */
    public void setDepartmentID(Integer departmentID) {
        this.departmentID = departmentID;
    }

    /**
     * @return the poNumber
     */
    public String getPoNumber() {
        return poNumber;
    }

    /**
     * @param poNumber the poNumber to set
     */
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

}
