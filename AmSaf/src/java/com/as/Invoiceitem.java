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
@Table(name = "invoiceitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invoiceitem.findAll", query = "SELECT i FROM Invoiceitem i"),
    @NamedQuery(name = "Invoiceitem.findByInvoiceitemId", query = "SELECT i FROM Invoiceitem i WHERE i.invoiceitemId = :invoiceitemId"),
    @NamedQuery(name = "Invoiceitem.findByQty", query = "SELECT i FROM Invoiceitem i WHERE i.qty = :qty"),
    @NamedQuery(name = "Invoiceitem.findByPrice", query = "SELECT i FROM Invoiceitem i WHERE i.price = :price"),
    @NamedQuery(name = "Invoiceitem.findByTax", query = "SELECT i FROM Invoiceitem i WHERE i.tax = :tax")})
public class Invoiceitem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "invoiceitem_id")
    private Integer invoiceitemId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qty")
    private int qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "tax")
    private Boolean tax;
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")
    @ManyToOne(optional = false)
    private Invoice invoiceId;
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    @ManyToOne(optional = false)
    private Item itemId;

    public Invoiceitem() {
    }

    public Invoiceitem(Integer invoiceitemId) {
        this.invoiceitemId = invoiceitemId;
    }

    public Integer getInvoiceitemId() {
        return invoiceitemId;
    }

    public void setInvoiceitemId(Integer invoiceitemId) {
        this.invoiceitemId = invoiceitemId;
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

    public Invoice getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Invoice invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceitemId != null ? invoiceitemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoiceitem)) {
            return false;
        }
        Invoiceitem other = (Invoiceitem) object;
        if ((this.invoiceitemId == null && other.invoiceitemId != null) || (this.invoiceitemId != null && !this.invoiceitemId.equals(other.invoiceitemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Invoiceitem[ invoiceitemId=" + invoiceitemId + " ]";
    }
    
}
