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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nick
 */
@Entity
@Table(name = "`order`")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Order1.findAll", query = "SELECT o FROM Order1 o"),
    @NamedQuery(name = "Order1.findByOrderID", query = "SELECT o FROM Order1 o WHERE o.orderID = :orderID"),
    @NamedQuery(name = "Order1.findByLocation", query = "SELECT o FROM Order1 o WHERE o.location = :location"),
    @NamedQuery(name = "Order1.findByContactor", query = "SELECT o FROM Order1 o WHERE o.contractor = :contractor"),
    @NamedQuery(name = "Order1.findByRigTankEq", query = "SELECT o FROM Order1 o WHERE o.rigTankEq = :rigTankEq"),
    @NamedQuery(name = "Order1.findByDiscount", query = "SELECT o FROM Order1 o WHERE o.discount = :discount"),
    @NamedQuery(name = "Order1.findByTaxProc", query = "SELECT o FROM Order1 o WHERE o.taxProc = :taxProc"),
    @NamedQuery(name = "Order1.findByPoNumber", query = "SELECT o FROM Order1 o WHERE o.poNumber = :poNumber"),
    @NamedQuery(name = "Order1.findByDateIn", query = "SELECT o FROM Order1 o WHERE o.dateIn = :dateIn"),
    @NamedQuery(name = "Order1.findByDateOut", query = "SELECT o FROM Order1 o WHERE o.dateOut = :dateOut")})
public class Order1 implements Serializable, IDocument {
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
    @Column(name = "order_id")
    private Integer orderID;
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
    @Size(max = 32)
    @Column(name = "po_number")
    private String poNumber;
    @Column(name = "well_name")
    private String wellName;
    @Column(name = "afe_uww")
    private String afeUww;
    @Column(name = "date_str")
    private String dateStr;
    @Column(name = "cai")
    private String cai;
    @Column(name = "aprvr_name")
    private String aprvrName;
    @Column(name = "date_in")
    @Temporal(TemporalType.DATE)
    private Date dateIn;
    @Column(name = "date_out")
    @Temporal(TemporalType.DATE)
    private Date dateOut;
    
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "updated_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date updatedAt;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "created_at")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdAt;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderID")
    private Collection<Orderitem> orderitemCollection;

    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne(optional = false)
    private Customer customerID;

    @JoinColumn(name = "contact_id", referencedColumnName = "contact_id")
    @ManyToOne(optional = false)
    private Contact contactID;

    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User createdBY;
    
    public Order1() {
    }

    public Order1(Integer orderID) {
        this.orderID = orderID;
    }

//    public Order1(Integer orderID, Date updatedAt, Date createdAt) {
//        this.orderID = orderID;
//        this.updatedAt = updatedAt;
//        this.createdAt = createdAt;
//    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String getContactor() {
        return contractor;
    }

    @Override
    public void setContactor(String contractor) {
        this.contractor = contractor;
    }

    @Override
    public String getRigTankEq() {
        return rigTankEq;
    }

    @Override
    public void setRigTankEq(String rigTankEq) {
        this.rigTankEq = rigTankEq;
    }

    @Override
    public BigDecimal getDiscount() {
        return discount;
    }

    @Override
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public BigDecimal getTaxProc() {
        return taxProc;
    }

    @Override
    public void setTaxProc(BigDecimal taxProc) {
        this.taxProc = taxProc;
    }

    @Override
    public String getPoNumber() {
        return poNumber;
    }

    @Override
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    @Override
    public Date getDateIn() {
        return dateIn;
    }

    @Override
    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    @Override
    public Date getDateOut() {
        return dateOut;
    }

    @Override
    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }


//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }

    @XmlTransient
    public Collection<Orderitem> getOrderitemCollection() {
        return orderitemCollection;
    }

    public void setOrderitemCollection(Collection<Orderitem> orderitemCollection) {
        this.orderitemCollection = orderitemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderID != null ? orderID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order1)) {
            return false;
        }
        Order1 other = (Order1) object;
        if ((this.orderID == null && other.orderID != null) || (this.orderID != null && !this.orderID.equals(other.orderID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Order1[ orderID=" + orderID + " ]";
    }

    /**
     * @return the customerID
     */
    @Override
    public Customer getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    @Override
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
    @Override
    public void setCreatedBY(User createdBY) {
        this.createdBY = createdBY;
    }


    /**
     * @return the contactID
     */
    @Override
    public Contact getContactID() {
        return contactID;
    }

    /**
     * @param contactID the contactID to set
     */
    @Override
    public void setContactID(Contact contactID) {
        this.contactID = contactID;
    }


    @Override
    public Po getPoTypeID() {
        return poTypeID;
    }

    @Override
    public void setPoTypeID(Po poTypeID) {
        this.poTypeID = poTypeID;
    }

    @Override
    public Tax getTaxID() {
        return taxID;
    }

    @Override
    public void setTaxID(Tax taxID) {
        this.taxID = taxID;
    }


    @Override
    public Stamps getStampsID() {
        return stampsID;
    }

    @Override
    public void setStampsID(Stamps stampsID) {
        this.stampsID = stampsID;
    }

    @Override
    public byte[] getSignature() {
        return signature;
    }

    @Override
    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    /**
     * @return the subtotal
     */
    @Override
    public BigDecimal getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    @Override
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the wellName
     */
    @Override
    public String getWellName() {
        return wellName;
    }

    /**
     * @param wellName the wellName to set
     */
    @Override
    public void setWellName(String wellName) {
        this.wellName = wellName;
    }

    /**
     * @return the afeUww
     */
    @Override
    public String getAfeUww() {
        return afeUww;
    }

    /**
     * @param afeUww the afeUww to set
     */
    @Override
    public void setAfeUww(String afeUww) {
        this.afeUww = afeUww;
    }

    /**
     * @return the cai
     */
    @Override
    public String getCai() {
        return cai;
    }

    /**
     * @param cai the cai to set
     */
    @Override
    public void setCai(String cai) {
        this.cai = cai;
    }

    /**
     * @return the aprvrName
     */
    @Override
    public String getAprvrName() {
        return aprvrName;
    }

    /**
     * @param aprvrName the aprvrName to set
     */
    @Override
    public void setAprvrName(String aprvrName) {
        this.aprvrName = aprvrName;
    }

    /**
     * @return the dateStr
     */
    @Override
    public String getDateStr() {
        return dateStr;
    }

    /**
     * @param dateStr the dateStr to set
     */
    @Override
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

}
