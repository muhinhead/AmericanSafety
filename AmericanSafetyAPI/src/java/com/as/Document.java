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
 * @author Nick Mukhin
 */
@Entity
@Table(name = "document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findByDocumentID", query = "SELECT d FROM Document d WHERE d.documentPK.documentID = :documentID"),
    @NamedQuery(name = "Document.findByDocType", query = "SELECT d FROM Document d WHERE d.documentPK.docType = :docType"),
    @NamedQuery(name = "Document.findByLocation", query = "SELECT d FROM Document d WHERE d.location = :location"),
    @NamedQuery(name = "Document.findByContactor", query = "SELECT d FROM Document d WHERE d.contractor = :contractor"),
    @NamedQuery(name = "Document.findByRigTankEq", query = "SELECT d FROM Document d WHERE d.rigTankEq = :rigTankEq"),
    @NamedQuery(name = "Document.findByDiscount", query = "SELECT d FROM Document d WHERE d.discount = :discount"),
    @NamedQuery(name = "Document.findByTaxProc", query = "SELECT d FROM Document d WHERE d.taxProc = :taxProc"),
    @NamedQuery(name = "Document.findByPoType", query = "SELECT d FROM Document d WHERE d.poType = :poType"),
    @NamedQuery(name = "Document.findByPoNumber", query = "SELECT d FROM Document d WHERE d.poNumber = :poNumber"),
    @NamedQuery(name = "Document.findByUpdatedAt", query = "SELECT d FROM Document d WHERE d.updatedAt = :updatedAt"),
    @NamedQuery(name = "Document.findByCreatedAt", query = "SELECT d FROM Document d WHERE d.createdAt = :createdAt")})
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DocumentPK documentPK;
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
    @Column(name = "date_in")
    @Temporal(TemporalType.DATE)
    private Date dateIn;
    @Column(name = "date_out")
    @Temporal(TemporalType.DATE)
    private Date dateOut;
    
    @Size(max = 12)
    @Column(name = "po_type")
    private String poType;
    @Size(max = 32)
    @Column(name = "po_number")
    private String poNumber;
    
    @Column(name = "well_name")
    private String wellName;
    @Column(name = "afe_uww")
    private String afeUww;
    @Column(name = "cai")
    private String cai;
    @Column(name = "aprvr_name")
    private String aprvrName;
    
    @Lob
    @Column(name = "signature")
    private byte[] signature;
    
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

    @NotNull
    @Column(name="created_by")
    private Integer createdBY;

    public Document() {
    }

    public Document(DocumentPK documentPK) {
        this.documentPK = documentPK;
    }

    public Document(DocumentPK documentPK, Date updatedAt, Date createdAt) {
        this.documentPK = documentPK;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactor() {
        return contractor;
    }

    public void setContactor(String contractor) {
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

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
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
     * @return the createdBY
     */
    public Integer getCreatedBY() {
        return createdBY;
    }

    /**
     * @param createdBY the createdBY to set
     */
    public void setCreatedBY(Integer createdBY) {
        this.createdBY = createdBY;
    }

    /**
     * @return the subtotal
     */
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the wellName
     */
    public String getWellName() {
        return wellName;
    }

    /**
     * @param wellName the wellName to set
     */
    public void setWellName(String wellName) {
        this.wellName = wellName;
    }

    /**
     * @return the afeUww
     */
    public String getAfeUww() {
        return afeUww;
    }

    /**
     * @param afeUww the afeUww to set
     */
    public void setAfeUww(String afeUww) {
        this.afeUww = afeUww;
    }

    /**
     * @return the cai
     */
    public String getCai() {
        return cai;
    }

    /**
     * @param cai the cai to set
     */
    public void setCai(String cai) {
        this.cai = cai;
    }

    /**
     * @return the aprvrName
     */
    public String getAprvrName() {
        return aprvrName;
    }

    /**
     * @param aprvrName the aprvrName to set
     */
    public void setAprvrName(String aprvrName) {
        this.aprvrName = aprvrName;
    }
    
}
