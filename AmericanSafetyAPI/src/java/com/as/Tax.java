package com.as;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Nick Mukhin
 */
@Entity
@Table(name = "tax")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tax.findAll", query = "SELECT t FROM Tax t"),
    @NamedQuery(name = "Tax.findByTaxID", query = "SELECT t FROM Tax t WHERE t.taxID = :taxID"),
    @NamedQuery(name = "Tax.findByTaxDescription", query = "SELECT t FROM Tax t WHERE t.taxDescription = :taxDescription")})
public class Tax implements Serializable {
    @OneToMany(mappedBy = "taxId")
    private Collection<Quote> quoteCollection;
    @OneToMany(mappedBy = "taxId")
    private Collection<Invoice> invoiceCollection;
    @OneToMany(mappedBy = "taxId")
    private Collection<Order1> order1Collection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tax_id")
    private Integer taxID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "tax_description")
    private String taxDescription;

    public Tax() {
    }

    public Tax(Integer taxId) {
        this.taxID = taxId;
    }

    public Tax(Integer taxId, String taxDescription) {
        this.taxID = taxId;
        this.taxDescription = taxDescription;
    }

    public Integer getTaxID() {
        return taxID;
    }

    public void setTaxID(Integer taxId) {
        this.taxID = taxId;
    }

    public String getTaxDescription() {
        return taxDescription;
    }

    public void setTaxDescription(String taxDescription) {
        this.taxDescription = taxDescription;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taxID != null ? taxID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tax)) {
            return false;
        }
        Tax other = (Tax) object;
        if ((this.taxID == null && other.taxID != null) || (this.taxID != null && !this.taxID.equals(other.taxID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Tax[ taxId=" + taxID + " ]";
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
