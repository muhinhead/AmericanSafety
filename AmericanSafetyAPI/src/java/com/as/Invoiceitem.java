/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import java.io.Serializable;
import java.math.BigDecimal;
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
    @NamedQuery(name = "Invoiceitem.findByInvoiceitemID", query = "SELECT i FROM Invoiceitem i WHERE i.invoiceitemID = :invoiceitemID"),
    @NamedQuery(name = "Invoiceitem.findByQty", query = "SELECT i FROM Invoiceitem i WHERE i.qty = :qty"),
    @NamedQuery(name = "Invoiceitem.findByPrice", query = "SELECT i FROM Invoiceitem i WHERE i.price = :price")})
public class Invoiceitem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "invoiceitem_id")
    private Integer invoiceitemID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qty")
    private int qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private BigDecimal price;
    @JoinColumn(name = "invoice_id", referencedColumnName = "invoice_id")
    @ManyToOne(optional = false)
    private Invoice invoiceID;
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    @ManyToOne(optional = false)
    private Item itemID;

    public Invoiceitem() {
    }

    public Invoiceitem(Integer invoiceitemID) {
        this.invoiceitemID = invoiceitemID;
    }

//    public Invoiceitem(Integer invoiceitemID, int qty, Date updatedAt, Date createdAt) {
//        this.invoiceitemID = invoiceitemID;
//        this.qty = qty;
//        this.updatedAt = updatedAt;
//        this.createdAt = createdAt;
//    }

    public Integer getInvoiceitemID() {
        return invoiceitemID;
    }

    public void setInvoiceitemID(Integer invoiceitemID) {
        this.invoiceitemID = invoiceitemID;
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

    public Invoice getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(Invoice invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Item getItemID() {
        return itemID;
    }

    public void setItemID(Item itemID) {
        this.itemID = itemID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invoiceitemID != null ? invoiceitemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invoiceitem)) {
            return false;
        }
        Invoiceitem other = (Invoiceitem) object;
        if ((this.invoiceitemID == null && other.invoiceitemID != null) || (this.invoiceitemID != null && !this.invoiceitemID.equals(other.invoiceitemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Invoiceitem[ invoiceitemID=" + invoiceitemID + " ]";
    }
    
}
