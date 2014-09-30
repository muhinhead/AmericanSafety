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
@Table(name = "quote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Quote.findAll", query = "SELECT q FROM Quote q"),
    @NamedQuery(name = "Quote.findByQuoteID", query = "SELECT q FROM Quote q WHERE q.quoteID = :quoteID"),
    @NamedQuery(name = "Quote.findByLocation", query = "SELECT q FROM Quote q WHERE q.location = :location"),
    @NamedQuery(name = "Quote.findByContactor", query = "SELECT q FROM Quote q WHERE q.contactor = :contactor"),
    @NamedQuery(name = "Quote.findByRigTankEq", query = "SELECT q FROM Quote q WHERE q.rigTankEq = :rigTankEq"),
    @NamedQuery(name = "Quote.findByDiscount", query = "SELECT q FROM Quote q WHERE q.discount = :discount"),
    @NamedQuery(name = "Quote.findByTaxProc", query = "SELECT q FROM Quote q WHERE q.taxProc = :taxProc"),
    @NamedQuery(name = "Quote.findByPoType", query = "SELECT q FROM Quote q WHERE q.poType = :poType"),
    @NamedQuery(name = "Quote.findByPoNumber", query = "SELECT q FROM Quote q WHERE q.poNumber = :poNumber"),
    @NamedQuery(name = "Quote.findByDateIn", query = "SELECT q FROM Quote q WHERE q.dateIn = :dateIn"),
    @NamedQuery(name = "Quote.findByUpdatedAt", query = "SELECT q FROM Quote q WHERE q.updatedAt = :updatedAt"),
    @NamedQuery(name = "Quote.findByCreatedAt", query = "SELECT q FROM Quote q WHERE q.createdAt = :createdAt")})
public class Quote implements Serializable {
    @Lob
    @Column(name = "signature")
    private byte[] signature;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "quote_id")
    private Integer quoteID;
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
    @Size(max = 12)
    @Column(name = "po_type")
    private String poType;
    @Size(max = 32)
    @Column(name = "po_number")
    private String poNumber;
    @Column(name = "date_in")
    @Temporal(TemporalType.DATE)
    private Date dateIn;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quoteID")
    private Collection<Quoteitem> quoteitemCollection;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customerID;

    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User createdBY;

    public Quote() {
    }

    public Quote(Integer quoteID) {
        this.quoteID = quoteID;
    }

    public Quote(Integer quoteID, Date updatedAt, Date createdAt) {
        this.quoteID = quoteID;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Integer getQuoteID() {
        return quoteID;
    }

    public void setQuoteID(Integer quoteID) {
        this.quoteID = quoteID;
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
        hash += (quoteID != null ? quoteID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quote)) {
            return false;
        }
        Quote other = (Quote) object;
        if ((this.quoteID == null && other.quoteID != null) || (this.quoteID != null && !this.quoteID.equals(other.quoteID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Quote[ quoteID=" + quoteID + " ]";
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
