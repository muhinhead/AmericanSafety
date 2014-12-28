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
    @NamedQuery(name = "Orderitem.findByOrderitemId", query = "SELECT o FROM Orderitem o WHERE o.orderitemId = :orderitemId"),
    @NamedQuery(name = "Orderitem.findByQty", query = "SELECT o FROM Orderitem o WHERE o.qty = :qty"),
    @NamedQuery(name = "Orderitem.findByPrice", query = "SELECT o FROM Orderitem o WHERE o.price = :price"),
    @NamedQuery(name = "Orderitem.findByOrderId", query = "SELECT o FROM Orderitem o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "Orderitem.findByTax", query = "SELECT o FROM Orderitem o WHERE o.tax = :tax")})
public class Orderitem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "orderitem_id")
    private Integer orderitemId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qty")
    private int qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "tax")
    private Boolean tax;
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    @ManyToOne(optional = false)
    private Item itemId;
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @ManyToOne(optional = false)
    private Order1 orderId;

    public Orderitem() {
    }

    public Orderitem(Integer orderitemId) {
        this.orderitemId = orderitemId;
    }

    public Integer getOrderitemId() {
        return orderitemId;
    }

    public void setOrderitemId(Integer orderitemId) {
        this.orderitemId = orderitemId;
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

    public Boolean getTax() {
        return tax;
    }

    public void setTax(Boolean tax) {
        this.tax = tax;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Order1 getOrderId() {
        return orderId;
    }

    public void setOrderId(Order1 orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderitemId != null ? orderitemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orderitem)) {
            return false;
        }
        Orderitem other = (Orderitem) object;
        if ((this.orderitemId == null && other.orderitemId != null) || (this.orderitemId != null && !this.orderitemId.equals(other.orderitemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Orderitem[ orderitemId=" + orderitemId + " ]";
    }
    
}
