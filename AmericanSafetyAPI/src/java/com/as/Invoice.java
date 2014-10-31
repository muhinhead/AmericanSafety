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
    @NamedQuery(name = "Invoice.findByContactor", query = "SELECT i FROM Invoice i WHERE i.contractor = :contractor"),
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
    @JoinColumn(name = "stamps_id", referencedColumnName = "stamps_id")
    @ManyToOne
    private Stamps stampsID;
    @JoinColumn(name = "po_type_id", referencedColumnName = "po_id")
    @ManyToOne
    private Po poTypeID;
    @JoinColumn(name = "tax_id", referencedColumnName = "tax_id")
    @ManyToOne
    private Tax taxID;
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
    @Column(name = "contractor")
    private String contractor;
    @Size(max = 255)
    @Column(name = "rig_tank_eq")
    private String rigTankEq;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "discount")
    private BigDecimal discount;
    @Column(name = "subtotal")
    private BigDecimal subtotal;
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
    @Column(name = "well_name")
    private String wellName;
    @Column(name = "afe_uww")
    private String afeUww;
    @Column(name = "cai")
    private String cai;
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoiceID")
    private Collection<Invoiceitem> invoiceitemCollection;
    
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customerID;

    @JoinColumn(name = "contact_id", referencedColumnName = "contact_id")
    @ManyToOne(optional = false)
    private Contact contactID;

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


    /**
     * @return the contactID
     */
    public Contact getContactID() {
        return contactID;
    }

    /**
     * @param contactID the contactID to set
     */
    public void setContactID(Contact contactID) {
        this.contactID = contactID;
    }


    public Po getPoTypeID() {
        return poTypeID;
    }

    public void setPoTypeID(Po poTypeID) {
        this.poTypeID = poTypeID;
    }

    public Tax getTaxID() {
        return taxID;
    }

    public void setTaxID(Tax taxID) {
        this.taxID = taxID;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public Stamps getStampsID() {
        return stampsID;
    }

    public void setStampsID(Stamps stampsID) {
        this.stampsID = stampsID;
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
