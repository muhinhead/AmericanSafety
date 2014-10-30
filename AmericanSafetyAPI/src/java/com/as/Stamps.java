/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "stamps")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stamps.findAll", query = "SELECT s FROM Stamps s"),
    @NamedQuery(name = "Stamps.findByStampsID", query = "SELECT s FROM Stamps s WHERE s.stampsID = :stampsID"),
    @NamedQuery(name = "Stamps.findByStamps", query = "SELECT s FROM Stamps s WHERE s.stamps = :stamps"),
    @NamedQuery(name = "Stamps.findByUpdatedAt", query = "SELECT s FROM Stamps s WHERE s.updatedAt = :updatedAt"),
    @NamedQuery(name = "Stamps.findByCreatedAt", query = "SELECT s FROM Stamps s WHERE s.createdAt = :createdAt")})
public class Stamps implements Serializable {
    @OneToMany(mappedBy = "stampsID")
    private Collection<Quote> quoteCollection;
    @OneToMany(mappedBy = "stampsID")
    private Collection<Invoice> invoiceCollection;
    @OneToMany(mappedBy = "stampsID")
    private Collection<Order1> order1Collection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "stamps_id")
    private Integer stampsID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "stamps")
    private String stamps;
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

    public Stamps() {
    }

    public Stamps(Integer stampsID) {
        this.stampsID = stampsID;
    }

    public Stamps(Integer stampsID, String stamps, Date updatedAt, Date createdAt) {
        this.stampsID = stampsID;
        this.stamps = stamps;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Integer getStampsID() {
        return stampsID;
    }

    public void setStampsID(Integer stampsID) {
        this.stampsID = stampsID;
    }

    public String getStamps() {
        return stamps;
    }

    public void setStamps(String stamps) {
        this.stamps = stamps;
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
        hash += (stampsID != null ? stampsID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stamps)) {
            return false;
        }
        Stamps other = (Stamps) object;
        if ((this.stampsID == null && other.stampsID != null) || (this.stampsID != null && !this.stampsID.equals(other.stampsID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Stamps[ stampsID=" + stampsID + " ]";
    }

    @XmlTransient
    public Collection<Quote> getQuoteCollection() {
        return quoteCollection;
    }

    public void setQuoteCollection(Collection<Quote> quoteCollection) {
        this.quoteCollection = quoteCollection;
    }

    @XmlTransient
    public Collection<Invoice> getInvoiceCollection() {
        return invoiceCollection;
    }

    public void setInvoiceCollection(Collection<Invoice> invoiceCollection) {
        this.invoiceCollection = invoiceCollection;
    }

    @XmlTransient
    public Collection<Order1> getOrder1Collection() {
        return order1Collection;
    }

    public void setOrder1Collection(Collection<Order1> order1Collection) {
        this.order1Collection = order1Collection;
    }
    
}
