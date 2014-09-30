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
 * @author Nick Mukhin 
 */
@Entity
@Table(name = "invoice")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
    @NamedQuery(name = "Invoice.findByInvoiceID", query = "SELECT i FROM Invoice i WHERE i.invoiceID = :invoiceID"),
    @NamedQuery(name = "Invoice.findByLocation", query = "SELECT i FROM Invoice i WHERE i.location = :location"),
    @NamedQuery(name = "Invoice.findByContactor", query = "SELECT i FROM Invoice i WHERE i.contactor = :contactor"),
    @NamedQuery(name = "Invoice.findByRigTankEq", query = "SELECT i FROM Invoice i WHERE i.rigTankEq = :rigTankEq"),
    @NamedQuery(name = "Invoice.findByDiscount", query = "SELECT i FROM Invoice i WHERE i.discount = :discount"),
    @NamedQuery(name = "Invoice.findByTaxProc", query = "SELECT i FROM Invoice i WHERE i.taxProc = :taxProc"),
    @NamedQuery(name = "Invoice.findByDateIn", query = "SELECT i FROM Invoice i WHERE i.dateIn = :dateIn"),
    @NamedQuery(name = "Invoice.findByDateOut", query = "SELECT i FROM Invoice i WHERE i.dateOut = :dateOut"),
    @NamedQuery(name = "Invoice.findByPoType", query = "SELECT i FROM Invoice i WHERE i.poType = :poType"),
    @NamedQuery(name = "Invoice.findByPoNumber", query = "SELECT i FROM Invoice i WHERE i.poNumber = :poNumber"),
    @NamedQuery(name = "Invoice.findByUpdatedAt", query = "SELECT i FROM Invoice i WHERE i.updatedAt = :updatedAt"),
    @NamedQuery(name = "Invoice.findByCreatedAt", query = "SELECT i FROM Invoice i WHERE i.createdAt = :createdAt")})
public class Invoice implements Serializable {
    @Lob
    @Column(name = "signature")
    private byte[] signature;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "invoice_id")
    private Integer invoiceID;
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceID")
    private Collection<Invoiceitem> invoiceitemCollection;
    
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customerID;

    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User createdBY;
    
    public Invoice() {
    }

    public Invoice(Integer invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Invoice(Integer invoiceID, Date updatedAt, Date createdAt) {
        this.invoiceID = invoiceID;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Integer getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(Integer invoiceID) {
        this.invoiceID = invoiceID;
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
    public Collection<Invoiceitem> getInvoiceitemCollection() {
        return invoiceitemCollection;
    }

    public void setInvoiceitemCollection(Collection<Invoiceitem> invoiceitemCollection) {
        this.invoiceitemCollection = invoiceitemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceID != null ? invoiceID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.invoiceID == null && other.invoiceID != null) || (this.invoiceID != null && !this.invoiceID.equals(other.invoiceID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Invoice[ invoiceID=" + invoiceID + " ]";
    }

    /**
     * @return the customerID
     */
    public Customer getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(Customer customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the createdBY
     */
    public User getCreatedBY() {
        return createdBY;
    }

    /**
     * @param createdBY the createdBY to set
     */
    public void setCreatedBY(User createdBY) {
        this.createdBY = createdBY;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

}
