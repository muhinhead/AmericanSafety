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
import javax.persistence.Lob;
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
    @NamedQuery(name = "Quote.findByContactor", query = "SELECT q FROM Quote q WHERE q.contactor = :contactor"),
    @NamedQuery(name = "Quote.findByRigTankEq", query = "SELECT q FROM Quote q WHERE q.rigTankEq = :rigTankEq"),
    @NamedQuery(name = "Quote.findByDiscount", query = "SELECT q FROM Quote q WHERE q.discount = :discount"),
    @NamedQuery(name = "Quote.findByTaxProc", query = "SELECT q FROM Quote q WHERE q.taxProc = :taxProc"),
    @NamedQuery(name = "Quote.findByUpdatedAt", query = "SELECT q FROM Quote q WHERE q.updatedAt = :updatedAt"),
    @NamedQuery(name = "Quote.findByCreatedAt", query = "SELECT q FROM Quote q WHERE q.createdAt = :createdAt")})
public class Quote implements Serializable {
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
    @Column(name = "contactor")
    private String contactor;
    @Size(max = 255)
    @Column(name = "rig_tank_eq")
    private String rigTankEq;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "tax_proc")
    private BigDecimal taxProc;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quoteId")
    private Collection<Quoteitem> quoteitemCollection;

    public Quote() {
    }

    public Quote(Integer quoteId) {
        this.quoteId = quoteId;
    }

    public Quote(Integer quoteId, Date updatedAt, Date createdAt) {
        this.quoteId = quoteId;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
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

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor;
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

    @XmlTransient
    public Collection<Quoteitem> getQuoteitemCollection() {
        return quoteitemCollection;
    }

    public void setQuoteitemCollection(Collection<Quoteitem> quoteitemCollection) {
        this.quoteitemCollection = quoteitemCollection;
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
    
}
