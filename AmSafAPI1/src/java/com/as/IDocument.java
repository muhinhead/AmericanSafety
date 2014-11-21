/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Nick Mukhin
 */
public interface IDocument {

    /**
     * @return the afeUww
     */
    String getAfeUww();

    /**
     * @return the aprvrName
     */
    String getAprvrName();

    /**
     * @return the cai
     */
    String getCai();

    /**
     * @return the contactId
     */
    Contact getContactId();

    String getContractor();

    /**
     * @return the customerId
     */
    Customer getCustomerId();

    Date getDateIn();

    Date getDateOut();

    /**
     * @return the dateStr
     */
    String getDateStr();

    BigDecimal getDiscount();

    String getLocation();

    String getPoNumber();

    Po getPoTypeId();

    String getRigTankEq();

    byte[] getSignature();

    Stamps getStampsId();
    
    Tax getTaxId();

    /**
     * @return the subtotal
     */
    BigDecimal getSubtotal();

    BigDecimal getTaxProc();

    /**
     * @return the wellName
     */
    String getWellName();

    /**
     * @param afeUww the afeUww to set
     */
    void setAfeUww(String afeUww);

    /**
     * @param aprvrName the aprvrName to set
     */
    void setAprvrName(String aprvrName);

    /**
     * @param cai the cai to set
     */
    void setCai(String cai);

    /**
     * @param contactId the contactId to set
     */
    void setContactId(Contact contactId);

    void setContractor(String contractor);

    /**
     * @param createdBy the createdBy to set
     */
    void setCreatedBy(User createdBy);

    /**
     * @param customerId the customerId to set
     */
    void setCustomerId(Customer customerId);

    void setDateIn(Date dateIn);

    void setDateOut(Date dateOut);

    /**
     * @param dateStr the dateStr to set
     */
    void setDateStr(String dateStr);

    void setDiscount(BigDecimal discount);

    void setLocation(String location);

    void setPoNumber(String poNumber);

    void setPoTypeId(Po poTypeId);

    void setRigTankEq(String rigTankEq);

    void setSignature(byte[] signature);

    void setStampsId(Stamps stampsId);

    /**
     * @param subtotal the subtotal to set
     */
    void setSubtotal(BigDecimal subtotal);

    void setTaxId(Tax taxId);

    void setTaxProc(BigDecimal taxProc);

    /**
     * @param wellName the wellName to set
     */
    void setWellName(String wellName);
    
}
