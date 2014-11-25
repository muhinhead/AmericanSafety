/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nick
 */
@Entity
@Table(name = "quote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Quote.findAll", query = "SELECT q FROM Quote q"),
    @NamedQuery(name = "Quote.findByQuoteId", query = "SELECT q FROM Quote q WHERE q.quoteId = :quoteId"),
    @NamedQuery(name = "Quote.findByLocation", query = "SELECT q FROM Quote q WHERE q.location = :location"),
    @NamedQuery(name = "Quote.findByContractor", query = "SELECT q FROM Quote q WHERE q.contractor = :contractor"),
    @NamedQuery(name = "Quote.findByRigTankEq", query = "SELECT q FROM Quote q WHERE q.rigTankEq = :rigTankEq"),
    @NamedQuery(name = "Quote.findByDiscount", query = "SELECT q FROM Quote q WHERE q.discount = :discount"),
    @NamedQuery(name = "Quote.findByTaxProc", query = "SELECT q FROM Quote q WHERE q.taxProc = :taxProc"),
    @NamedQuery(name = "Quote.findByPoNumber", query = "SELECT q FROM Quote q WHERE q.poNumber = :poNumber"),
    @NamedQuery(name = "Quote.findByDateIn", query = "SELECT q FROM Quote q WHERE q.dateIn = :dateIn"),
    @NamedQuery(name = "Quote.findBySubtotal", query = "SELECT q FROM Quote q WHERE q.subtotal = :subtotal"),
    @NamedQuery(name = "Quote.findByWellName", query = "SELECT q FROM Quote q WHERE q.wellName = :wellName"),
    @NamedQuery(name = "Quote.findByAfeUww", query = "SELECT q FROM Quote q WHERE q.afeUww = :afeUww"),
    @NamedQuery(name = "Quote.findByCai", query = "SELECT q FROM Quote q WHERE q.cai = :cai"),
    @NamedQuery(name = "Quote.findByAprvrName", query = "SELECT q FROM Quote q WHERE q.aprvrName = :aprvrName"),
    @NamedQuery(name = "Quote.findByDateStr", query = "SELECT q FROM Quote q WHERE q.dateStr = :dateStr")})
public class Quote implements Serializable, IDocument {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "quote_id")
    private Integer quoteId;
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
    @Size(max = 32)
    @Column(name = "po_number")
    private String poNumber;
    @Column(name = "date_in")
    @Temporal(TemporalType.DATE)
    private Date dateIn;
    @Lob
    @Column(name = "signature")
    private byte[] signature;
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Size(max = 64)
    @Column(name = "well_name")
    private String wellName;
    @Size(max = 64)
    @Column(name = "afe_uww")
    private String afeUww;
    @Size(max = 64)
    @Column(name = "cai")
    private String cai;
    @Size(max = 64)
    @Column(name = "aprvr_name")
    private String aprvrName;
    @Size(max = 64)
    @Column(name = "date_str")
    private String dateStr;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quoteId")
    private Collection<Quoteitem> quoteitemCollection;
    @JoinColumn(name = "contact_id", referencedColumnName = "contact_id")
    @ManyToOne
    private Contact contactId;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    private Customer customerId;
    @JoinColumn(name = "po_type_id", referencedColumnName = "po_id")
    @ManyToOne
    private Po poTypeId;
    @JoinColumn(name = "stamps_id", referencedColumnName = "stamps_id")
    @ManyToOne
    private Stamps stampsId;
    @JoinColumn(name = "tax_id", referencedColumnName = "tax_id")
    @ManyToOne
    private Tax taxId;
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User createdBy;

    public Quote() {
    }

    public Quote(Integer quoteId) {
        this.quoteId = quoteId;
    }

    public Integer getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Integer quoteId) {
        this.quoteId = quoteId;
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

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
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

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    @XmlTransient
    public Collection<Quoteitem> getQuoteitemCollection() {
        return quoteitemCollection;
    }

    public void setQuoteitemCollection(Collection<Quoteitem> quoteitemCollection) {
        this.quoteitemCollection = quoteitemCollection;
    }

    public Contact getContactId() {
        return contactId;
    }

    public void setContactId(Contact contactId) {
        this.contactId = contactId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Po getPoTypeId() {
        return poTypeId;
    }

    public void setPoTypeId(Po poTypeId) {
        this.poTypeId = poTypeId;
    }

    public Stamps getStampsId() {
        return stampsId;
    }

    public void setStampsId(Stamps stampsId) {
        this.stampsId = stampsId;
    }

    public Tax getTaxId() {
        return taxId;
    }

    public void setTaxId(Tax taxId) {
        this.taxId = taxId;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quoteId != null ? quoteId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quote)) {
            return false;
        }
        Quote other = (Quote) object;
        if ((this.quoteId == null && other.quoteId != null) || (this.quoteId != null && !this.quoteId.equals(other.quoteId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Quote[ quoteId=" + quoteId + " ]";
    }

    @Override
    public Date getDateOut() {
        return null;
    }

    @Override
    public void setDateOut(Date dateOut) {
    }    
}
