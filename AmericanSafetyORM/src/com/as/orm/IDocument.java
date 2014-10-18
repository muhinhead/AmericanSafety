/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.orm;

import com.as.orm.dbobject.DbObject;
import com.as.orm.dbobject.ForeignKeyViolationException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author nick
 */
public interface IDocument {

    void delete() throws SQLException, ForeignKeyViolationException;

    Integer getContactId();

    String getContractor();

    Timestamp getCreatedAt();

    Integer getCreatedBy();

    Integer getCustomerId();

    Date getDateIn();

    Double getDiscount();

    String getLocation();

    Integer getPK_ID();

    String getPoNumber();

    Integer getPoTypeId();

    String getRigTankEq();

    Object getSignature();

    Double getTaxProc();

    Timestamp getUpdatedAt();

    DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException;

    void save() throws SQLException, ForeignKeyViolationException;

    void setContactId(Integer contactId) throws SQLException, ForeignKeyViolationException;

    void setContractor(String contractor) throws SQLException, ForeignKeyViolationException;

    void setCreatedAt(Timestamp createdAt) throws SQLException, ForeignKeyViolationException;

    void setCreatedBy(Integer createdBy) throws SQLException, ForeignKeyViolationException;

    void setCustomerId(Integer customerId) throws SQLException, ForeignKeyViolationException;

    void setDateIn(Date dateIn) throws SQLException, ForeignKeyViolationException;

    void setDiscount(Double discount) throws SQLException, ForeignKeyViolationException;

    void setLocation(String location) throws SQLException, ForeignKeyViolationException;

    void setPK_ID(Integer id) throws ForeignKeyViolationException;

    void setPoNumber(String poNumber) throws SQLException, ForeignKeyViolationException;

    void setPoTypeId(Integer poTypeId) throws SQLException, ForeignKeyViolationException;

    void setRigTankEq(String rigTankEq) throws SQLException, ForeignKeyViolationException;

    void setSignature(Object signature) throws SQLException, ForeignKeyViolationException;

    void setTaxProc(Double taxProc) throws SQLException, ForeignKeyViolationException;

    void setUpdatedAt(Timestamp updatedAt) throws SQLException, ForeignKeyViolationException;
    
}
