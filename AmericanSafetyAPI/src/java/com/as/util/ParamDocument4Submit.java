package com.as.util;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
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
public class ParamDocument4Submit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer userID;
    @Temporal(TemporalType.DATE)
    private Date dateIn;
    @Temporal(TemporalType.DATE)
    private Date dateOut;
    private String documentType; //instead of documentID in specs
    private Integer customerID;
    private Integer contactID;
    private String location;
    private String contractor;
    private String rigTankEquipment;
    private byte[] imageSignature;
    private BigDecimal discount;

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
        if (!(object instanceof ParamDocument4Submit)) {
            return false;
        }
        ParamDocument4Submit other = (ParamDocument4Submit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ParamDocument4Submit[ id=" + id + " ]";
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
     * @return the dateIn
     */
    public Date getDateIn() {
        return dateIn;
    }

    /**
     * @param dateIn the dateIn to set
     */
    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    /**
     * @return the dateOut
     */
    public Date getDateOut() {
        return dateOut;
    }

    /**
     * @param dateOut the dateOut to set
     */
    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
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

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the contractor
     */
    public String getContractor() {
        return contractor;
    }

    /**
     * @param contractor the contractor to set
     */
    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    /**
     * @return the rigTankEquipment
     */
    public String getRigTankEquipment() {
        return rigTankEquipment;
    }

    /**
     * @param rigTankEquipment the rigTankEquipment to set
     */
    public void setRigTankEquipment(String rigTankEquipment) {
        this.rigTankEquipment = rigTankEquipment;
    }

    /**
     * @return the imageSignature
     */
    public byte[] getImageSignature() {
        return imageSignature;
    }

    /**
     * @param imageSignature the imageSignature to set
     */
    public void setImageSignature(byte[] imageSignature) {
        this.imageSignature = imageSignature;
    }

    /**
     * @return the discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    
}
