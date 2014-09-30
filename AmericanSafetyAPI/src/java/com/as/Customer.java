package com.as;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author nick
 */
@Entity
@Table(name = "customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustomerID", query = "SELECT c FROM Customer c WHERE c.customerID = :customerID"),
    @NamedQuery(name = "Customer.findByCustomerName", query = "SELECT c FROM Customer c WHERE c.customerName = :customerName"),
    @NamedQuery(name = "Customer.findByNameMask", 
            query = "SELECT c FROM Customer c WHERE c.customerName LIKE :mask"),
    @NamedQuery(name = "Customer.findByCustomerAddress", query = "SELECT c FROM Customer c WHERE c.customerAddress = :customerAddress")})
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "customer_id")
    private Integer customerID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "customer_name")
    private String customerName;
    @Size(max = 512)
    @Column(name = "customer_address")
    private String customerAddress;
    
//    @Basic(optional = false)
//    @Column(name = "updated_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt;
//    
//    @Basic(optional = false)
//    @Column(name = "created_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerID")
    private Collection<Contact> contactCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerID")
    private Collection<Quote> quoteCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerID")
    private Collection<Order1> orderCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerID")
    private Collection<Invoice> invoiceCollection;

    public Customer() {
    }

    public Customer(Integer customerID) {
        this.customerID = customerID;
    }

    public Customer(Integer customerID, String customerName) {
        this.customerID = customerID;
        this.customerName = customerName;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

//    @XmlTransient
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }

//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }

//    @XmlTransient
//    public Date getCreatedAt() {
//        return createdAt;
//    }

//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }

    @XmlTransient
    public Collection<Contact> getContactCollection() {
        return contactCollection;
    }

    public void setContactCollection(Collection<Contact> contactCollection) {
        this.contactCollection = contactCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerID != null ? customerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerID == null && other.customerID != null) || (this.customerID != null && !this.customerID.equals(other.customerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Customer[ customerID=" + customerID + " ]";
    }

    /**
     * @return the quoteCollection
     */
    public Collection<Quote> getQuoteCollection() {
        return quoteCollection;
    }

    /**
     * @param quoteCollection the quoteCollection to set
     */
    public void setQuoteCollection(Collection<Quote> quoteCollection) {
        this.quoteCollection = quoteCollection;
    }

    /**
     * @return the orderCollection
     */
    public Collection<Order1> getOrderCollection() {
        return orderCollection;
    }

    /**
     * @param orderCollection the orderCollection to set
     */
    public void setOrderCollection(Collection<Order1> orderCollection) {
        this.orderCollection = orderCollection;
    }

    /**
     * @return the invoiceCollection
     */
    public Collection<Invoice> getInvoiceCollection() {
        return invoiceCollection;
    }

    /**
     * @param invoiceCollection the invoiceCollection to set
     */
    public void setInvoiceCollection(Collection<Invoice> invoiceCollection) {
        this.invoiceCollection = invoiceCollection;
    }
    
}
