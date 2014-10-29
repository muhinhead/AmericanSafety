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
 * @author Nick Mukhin
 */
@Entity
@Table(name = "po")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Po.findAll", query = "SELECT p FROM Po p"),
    @NamedQuery(name = "Po.findByPoId", query = "SELECT p FROM Po p WHERE p.poID = :poID"),
    @NamedQuery(name = "Po.findByPoDescription", query = "SELECT p FROM Po p WHERE p.poDescription = :poDescription")})
public class Po implements Serializable {
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
    @OneToMany(mappedBy = "poTypeId")
    private Collection<Quote> quoteCollection;
    @OneToMany(mappedBy = "poTypeId")
    private Collection<Invoice> invoiceCollection;
    @OneToMany(mappedBy = "poTypeId")
    private Collection<Order1> order1Collection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "po_id")
    private Integer poID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "po_description")
    private String poDescription;

    public Po() {
    }

    public Po(Integer poID) {
        this.poID = poID;
    }

    public Po(Integer poID, String poDescription) {
        this.poID = poID;
        this.poDescription = poDescription;
    }

    public Integer getPoId() {
        return poID;
    }

    public void setPoId(Integer poID) {
        this.poID = poID;
    }

    public String getPoDescription() {
        return poDescription;
    }

    public void setPoDescription(String poDescription) {
        this.poDescription = poDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (poID != null ? poID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Po)) {
            return false;
        }
        Po other = (Po) object;
        if ((this.poID == null && other.poID != null) || (this.poID != null && !this.poID.equals(other.poID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Po[ poID=" + poID + " ]";
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
