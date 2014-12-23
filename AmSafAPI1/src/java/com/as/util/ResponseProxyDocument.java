/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import com.as.Contact;
import com.as.Customer;
import com.as.IDocument;
import com.as.Po;
import com.as.Stamps;
import com.as.Tax;
import com.as.User;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author nick
 */
public class ResponseProxyDocument implements Serializable, IDocument {

    private final IDocument original;
    private final Integer uniqueID;

    public ResponseProxyDocument(IDocument original, Integer uniqueID) {
        this.original = original;
        this.uniqueID = uniqueID;
    }

    @Override
    public String getAfeUww() {
        return original.getAfeUww();
    }

    @Override
    public String getAprvrName() {
        return original.getAprvrName();
    }

    @Override
    public String getCai() {
        return original.getCai();
    }

    @Override
    public Contact getContact() {
        return original.getContact();
    }

    @Override
    public String getContractor() {
        return original.getContractor();
    }

    @Override
    public Customer getCustomer() {
        return original.getCustomer();
    }

    @Override
    public Date getDateIn() {
        return original.getDateIn();
    }

    @Override
    public Date getDateOut() {
        return original.getDateOut();
    }

    @Override
    public String getDateStr() {
        return original.getDateStr();
    }

    @Override
    public BigDecimal getDiscount() {
        return original.getDiscount();
    }

    @Override
    public String getLocation() {
        return original.getLocation();
    }

    @Override
    public String getPoNumber() {
        return original.getPoNumber();
    }

    @Override
    public Po getPoType() {
        return original.getPoType();
    }

    @Override
    public String getRigTankEq() {
        return original.getRigTankEq();
    }

    @Override
    public byte[] getSignature() {
        return original.getSignature();
    }

    @Override
    public Stamps getStamp() {
        return original.getStamp();
    }

    @Override
    public Tax getTax() {
        return original.getTax();
    }

    @Override
    public BigDecimal getSubtotal() {
        return original.getSubtotal();
    }

    @Override
    public BigDecimal getTaxProc() {
        return original.getTaxProc();
    }

    @Override
    public String getWellName() {
        return original.getWellName();
    }

    @Override
    public String getDocumentType() {
        return original.getDocumentType();
    }

    @Override
    public void setDocumentType(String docType) {
    }

    @Override
    public void setAfeUww(String afeUww) {
    }

    @Override
    public void setAprvrName(String aprvrName) {
    }

    @Override
    public void setCai(String cai) {
    }

    @Override
    public void setContact(Contact contactId) {
    }

    @Override
    public void setContractor(String contractor) {
    }

    @Override
    public void setCreatedBy(User createdBy) {
    }

    @Override
    public void setCustomer(Customer customerId) {
    }

    @Override
    public void setDateIn(Date dateIn) {
    }

    @Override
    public void setDateOut(Date dateOut) {
    }

    @Override
    public void setDateStr(String dateStr) {
    }

    @Override
    public void setDiscount(BigDecimal discount) {
    }

    @Override
    public void setLocation(String location) {
    }

    @Override
    public void setPoNumber(String poNumber) {
    }

    @Override
    public void setPoType(Po poTypeId) {
    }

    @Override
    public void setRigTankEq(String rigTankEq) {
    }

    @Override
    public void setSignature(byte[] signature) {
    }

    @Override
    public void setStamp(Stamps stampsId) {
    }

    @Override
    public void setSubtotal(BigDecimal subtotal) {
    }

    @Override
    public void setTax(Tax taxId) {
    }

    @Override
    public void setTaxProc(BigDecimal taxProc) {
    }

    @Override
    public void setWellName(String wellName) {
    }

    @Override
    public void setDocumentId(Integer docID) {
    }

    @Override
    public Integer getDocumentId() {
        return uniqueID;
    }

    @Override
    public void setDocItems(Collection<ResponseDocItem> docitms) {
    }

    @Override
    public Collection<ResponseDocItem> getDocItems() {
        return original.getDocItems();
    }
}
