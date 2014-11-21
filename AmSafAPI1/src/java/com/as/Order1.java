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
@Table(name = "order")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Order1.findAll", query = "SELECT o FROM Order1 o"),
    @NamedQuery(name = "Order1.findByOrderId", query = "SELECT o FROM Order1 o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "Order1.findByLocation", query = "SELECT o FROM Order1 o WHERE o.location = :location"),
    @NamedQuery(name = "Order1.findByContractor", query = "SELECT o FROM Order1 o WHERE o.contractor = :contractor"),
    @NamedQuery(name = "Order1.findByRigTankEq", query = "SELECT o FROM Order1 o WHERE o.rigTankEq = :rigTankEq"),
    @NamedQuery(name = "Order1.findByDiscount", query = "SELECT o FROM Order1 o WHERE o.discount = :discount"),
    @NamedQuery(name = "Order1.findByTaxProc", query = "SELECT o FROM Order1 o WHERE o.taxProc = :taxProc"),
    @NamedQuery(name = "Order1.findByPoNumber", query = "SELECT o FROM Order1 o WHERE o.poNumber = :poNumber"),
    @NamedQuery(name = "Order1.findByDateIn", query = "SELECT o FROM Order1 o WHERE o.dateIn = :dateIn"),
    @NamedQuery(name = "Order1.findByDateOut", query = "SELECT o FROM Order1 o WHERE o.dateOut = :dateOut"),
    @NamedQuery(name = "Order1.findByUpdatedAt", query = "SELECT o FROM Order1 o WHERE o.updatedAt = :updatedAt"),
    @NamedQuery(name = "Order1.findByCreatedAt", query = "SELECT o FROM Order1 o WHERE o.createdAt = :createdAt"),
    @NamedQuery(name = "Order1.findBySubtotal", query = "SELECT o FROM Order1 o WHERE o.subtotal = :subtotal"),
    @NamedQuery(name = "Order1.findByWellName", query = "SELECT o FROM Order1 o WHERE o.wellName = :wellName"),
    @NamedQuery(name = "Order1.findByAfeUww", query = "SELECT o FROM Order1 o WHERE o.afeUww = :afeUww"),
    @NamedQuery(name = "Order1.findByCai", query = "SELECT o FROM Order1 o WHERE o.cai = :cai"),
    @NamedQuery(name = "Order1.findByAprvrName", query = "SELECT o FROM Order1 o WHERE o.aprvrName = :aprvrName"),
    @NamedQuery(name = "Order1.findByDateStr", query = "SELECT o FROM Order1 o WHERE o.dateStr = :dateStr")})
public class Order1 implements Serializable, IDocument {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "order_id")
    private Integer orderId;
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
    @Column(name = "date_out")
    @Temporal(TemporalType.DATE)
    private Date dateOut;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private Collection<Orderitem> orderitemCollection;
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

    public Order1() {
    }

    public Order1(Integer orderId) {
        this.orderId = orderId;
    }

    public Order1(Integer orderId, Date updatedAt, Date createdAt) {
        this.orderId = orderId;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
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
    public Collection<Orderitem> getOrderitemCollection() {
        return orderitemCollection;
    }

    public void setOrderitemCollection(Collection<Orderitem> orderitemCollection) {
        this.orderitemCollection = orderitemCollection;
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
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Order1)) {
            return false;
        }
        Order1 other = (Order1) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Order1[ orderId=" + orderId + " ]";
    }
    
}
