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
    @NamedQuery(name = "Document.findByDocumentId", query = "SELECT d FROM Document d WHERE d.documentPK.documentID = :documentID"),
    @NamedQuery(name = "Document.findByDocType", query = "SELECT d FROM Document d WHERE d.documentPK.docType = :docType"),
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
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_by")
    private int createdBy;

    public Document() {
    }

    public Document(DocumentPK documentPK) {
        this.documentPK = documentPK;
    }

    public Document(DocumentPK documentPK, Date updatedAt, Date createdAt, int createdBy) {
        this.documentPK = documentPK;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public Document(int documentId, String docType) {
        this.documentPK = new DocumentPK(documentId, docType);
    }

    public DocumentPK getDocumentPK() {
        return documentPK;
    }

    public void setDocumentPK(DocumentPK documentPK) {
        this.documentPK = documentPK;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (documentPK != null ? documentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.documentPK == null && other.documentPK != null) || (this.documentPK != null && !this.documentPK.equals(other.documentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Document[ documentPK=" + documentPK + " ]";
    }
    
}
