/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nick
 */
@Entity
@Table(name = "document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findByDocumentIDandType", query = "SELECT d FROM Document d "
            + "WHERE d.documentPK.documentID = :documentID AND d.documentPK.docType = :docType"),
    @NamedQuery(name = "Document.findByDocumentId", query = "SELECT d FROM Document d WHERE d.documentId = :documentId"),
    @NamedQuery(name = "Document.findByDocType", query = "SELECT d FROM Document d WHERE d.docType = :docType"),
    @NamedQuery(name = "Document.findByCustomerId", query = "SELECT d FROM Document d WHERE d.customerId = :customerId"),
    @NamedQuery(name = "Document.findByContactId", query = "SELECT d FROM Document d WHERE d.contactId = :contactId"),
    @NamedQuery(name = "Document.findByLocation", query = "SELECT d FROM Document d WHERE d.location = :location"),
    @NamedQuery(name = "Document.findByContractor", query = "SELECT d FROM Document d WHERE d.contractor = :contractor"),
    @NamedQuery(name = "Document.findByRigTankEq", query = "SELECT d FROM Document d WHERE d.rigTankEq = :rigTankEq"),
    @NamedQuery(name = "Document.findByDiscount", query = "SELECT d FROM Document d WHERE d.discount = :discount"),
    @NamedQuery(name = "Document.findByTaxProc", query = "SELECT d FROM Document d WHERE d.taxProc = :taxProc"),
    @NamedQuery(name = "Document.findBySubtotal", query = "SELECT d FROM Document d WHERE d.subtotal = :subtotal"),
    @NamedQuery(name = "Document.findByPoType", query = "SELECT d FROM Document d WHERE d.poType = :poType"),
    @NamedQuery(name = "Document.findByPoNumber", query = "SELECT d FROM Document d WHERE d.poNumber = :poNumber"),
    @NamedQuery(name = "Document.findByDateIn", query = "SELECT d FROM Document d WHERE d.dateIn = :dateIn"),
    @NamedQuery(name = "Document.findByDateOut", query = "SELECT d FROM Document d WHERE d.dateOut = :dateOut"),
    @NamedQuery(name = "Document.findByWellName", query = "SELECT d FROM Document d WHERE d.wellName = :wellName"),
    @NamedQuery(name = "Document.findByAfeUww", query = "SELECT d FROM Document d WHERE d.afeUww = :afeUww"),
    @NamedQuery(name = "Document.findByDateStr", query = "SELECT d FROM Document d WHERE d.dateStr = :dateStr"),
    @NamedQuery(name = "Document.findByCai", query = "SELECT d FROM Document d WHERE d.cai = :cai"),
    @NamedQuery(name = "Document.findByAprvrName", query = "SELECT d FROM Document d WHERE d.aprvrName = :aprvrName"),
    @NamedQuery(name = "Document.findByCreatedBy", query = "SELECT d FROM Document d WHERE d.createdBy = :createdBy")})
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DocumentPK documentPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "document_id")
    private int documentId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "doc_type")
    private String docType;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "contact_id")
    private Integer contactId;
    @Size(max = 255)
    @Column(name = "location")
    private String location;
    @Size(max = 255)
    @Column(name = "contractor")
    private String contractor;
    @Size(max = 255)
    @Column(name = "rig_tank_eq")
    private String rigTankEq;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "tax_proc")
    private BigDecimal taxProc;
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Size(max = 32)
    @Column(name = "po_type")
    private String poType;
    @Size(max = 32)
    @Column(name = "po_number")
    private String poNumber;
    @Column(name = "date_in")
    @Temporal(TemporalType.DATE)
    private Date dateIn;
    @Column(name = "date_out")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOut;
    @Lob
    @Column(name = "signature")
    private byte[] signature;
    @Size(max = 64)
    @Column(name = "well_name")
    private String wellName;
    @Size(max = 64)
    @Column(name = "afe_uww")
    private String afeUww;
    @Size(max = 64)
    @Column(name = "date_str")
    private String dateStr;
    @Size(max = 64)
    @Column(name = "cai")
    private String cai;
    @Size(max = 64)
    @Column(name = "aprvr_name")
    private String aprvrName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_by")
    private int createdBy;

    public Document() {
    }

    public Document(DocumentPK documentPK) {
        this.documentPK = documentPK;
    }

    public Document(int documentID, String docType) {
        this.documentPK = new DocumentPK(documentID, docType);
    }

    public DocumentPK getDocumentPK() {
        return documentPK;
    }

    public void setDocumentPK(DocumentPK documentPK) {
        this.documentPK = documentPK;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    public String getRigTankEq() {
        return rigTankEq;
    }

    public void setRigTankEq(String rigTankEq) {
        this.rigTankEq = rigTankEq;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getTaxProc() {
        return taxProc;
    }

    public void setTaxProc(BigDecimal taxProc) {
        this.taxProc = taxProc;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getPoType() {
        return poType;
    }

    public void setPoType(String poType) {
        this.poType = poType;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public String getWellName() {
        return wellName;
    }

    public void setWellName(String wellName) {
        this.wellName = wellName;
    }

    public String getAfeUww() {
        return afeUww;
    }

    public void setAfeUww(String afeUww) {
        this.afeUww = afeUww;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getCai() {
        return cai;
    }

    public void setCai(String cai) {
        this.cai = cai;
    }

    public String getAprvrName() {
        return aprvrName;
    }

    public void setAprvrName(String aprvrName) {
        this.aprvrName = aprvrName;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    
}
