/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nick
 */
@Entity
@Table(name = "orderitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orderitem.findAll", query = "SELECT o FROM Orderitem o"),
    @NamedQuery(name = "Orderitem.findByOrderitemID", query = "SELECT o FROM Orderitem o WHERE o.orderitemID = :orderitemID"),
    @NamedQuery(name = "Orderitem.findByQty", query = "SELECT o FROM Orderitem o WHERE o.qty = :qty"),
    @NamedQuery(name = "Orderitem.findByPrice", query = "SELECT o FROM Orderitem o WHERE o.price = :price")})
public class Orderitem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orderitem_id")
    private Integer orderitemID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qty")
    private int qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private BigDecimal price;
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    @ManyToOne(optional = false)
    private Item itemID;
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @ManyToOne(optional = false)
    private Order1 orderID;

    public Orderitem() {
    }

    public Orderitem(Integer orderitemID) {
        this.orderitemID = orderitemID;
    }

//    public Orderitem(Integer orderitemID, int qty, Date updatedAt, Date createdAt) {
//        this.orderitemID = orderitemID;
//        this.qty = qty;
//        this.updatedAt = updatedAt;
//        this.createdAt = createdAt;
//    }

    public Integer getOrderitemID() {
        return orderitemID;
    }

    public void setOrderitemID(Integer orderitemID) {
        this.orderitemID = orderitemID;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Item getItemID() {
        return itemID;
    }

    public void setItemID(Item itemID) {
        this.itemID = itemID;
    }

    public Order1 getOrderID() {
        return orderID;
    }

    public void setOrderID(Order1 orderID) {
        this.orderID = orderID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderitemID != null ? orderitemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderitem)) {
            return false;
        }
        Orderitem other = (Orderitem) object;
        if ((this.orderitemID == null && other.orderitemID != null) || (this.orderitemID != null && !this.orderitemID.equals(other.orderitemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Orderitem[ orderitemID=" + orderitemID + " ]";
    }
    
}
