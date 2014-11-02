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
     * @return the contactID
     */
    Contact getContactID();

    String getContactor();

    /**
     * @return the customerID
     */
    Customer getCustomerID();

    Date getDateIn();

    Date getDateOut();

    /**
     * @return the dateStr
     */
    String getDateStr();

    BigDecimal getDiscount();

    String getLocation();

    String getPoNumber();

    Po getPoTypeID();

    String getRigTankEq();

    byte[] getSignature();

    Stamps getStampsID();
    
    Tax getTaxID();

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
     * @param contactID the contactID to set
     */
    void setContactID(Contact contactID);

    void setContactor(String contractor);

    /**
     * @param createdBY the createdBY to set
     */
    void setCreatedBY(User createdBY);

    /**
     * @param customerID the customerID to set
     */
    void setCustomerID(Customer customerID);

    void setDateIn(Date dateIn);

    void setDateOut(Date dateOut);

    /**
     * @param dateStr the dateStr to set
     */
    void setDateStr(String dateStr);

    void setDiscount(BigDecimal discount);

    void setLocation(String location);

    void setPoNumber(String poNumber);

    void setPoTypeID(Po poTypeID);

    void setRigTankEq(String rigTankEq);

    void setSignature(byte[] signature);

    void setStampsID(Stamps stampsID);

    /**
     * @param subtotal the subtotal to set
     */
    void setSubtotal(BigDecimal subtotal);

    void setTaxID(Tax taxID);

    void setTaxProc(BigDecimal taxProc);

    /**
     * @param wellName the wellName to set
     */
    void setWellName(String wellName);
    
}
