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
@Table(name = "quoteitem")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Quoteitem.findAll", query = "SELECT q FROM Quoteitem q"),
    @NamedQuery(name = "Quoteitem.findByQuoteitemID", query = "SELECT q FROM Quoteitem q WHERE q.quoteitemID = :quoteitemID"),
    @NamedQuery(name = "Quoteitem.findByQty", query = "SELECT q FROM Quoteitem q WHERE q.qty = :qty"),
    @NamedQuery(name = "Quoteitem.findByPrice", query = "SELECT q FROM Quoteitem q WHERE q.price = :price"),
    @NamedQuery(name = "Quoteitem.findByUpdatedAt", query = "SELECT q FROM Quoteitem q WHERE q.updatedAt = :updatedAt"),
    @NamedQuery(name = "Quoteitem.findByCreatedAt", query = "SELECT q FROM Quoteitem q WHERE q.createdAt = :createdAt")})
public class Quoteitem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "quoteitem_id")
    private Integer quoteitemID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qty")
    private int qty;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private BigDecimal price;
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
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    @ManyToOne(optional = false)
    private Item itemID;
    @JoinColumn(name = "quote_id", referencedColumnName = "quote_id")
    @ManyToOne(optional = false)
    private Quote quoteID;

    public Quoteitem() {
    }

    public Quoteitem(Integer quoteitemID) {
        this.quoteitemID = quoteitemID;
    }

    public Quoteitem(Integer quoteitemID, int qty, Date updatedAt, Date createdAt) {
        this.quoteitemID = quoteitemID;
        this.qty = qty;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Integer getQuoteitemID() {
        return quoteitemID;
    }

    public void setQuoteitemID(Integer quoteitemID) {
        this.quoteitemID = quoteitemID;
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

    public Item getItemID() {
        return itemID;
    }

    public void setItemID(Item itemID) {
        this.itemID = itemID;
    }

    public Quote getQuoteID() {
        return quoteID;
    }

    public void setQuoteID(Quote quoteID) {
        this.quoteID = quoteID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quoteitemID != null ? quoteitemID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quoteitem)) {
            return false;
        }
        Quoteitem other = (Quoteitem) object;
        if ((this.quoteitemID == null && other.quoteitemID != null) || (this.quoteitemID != null && !this.quoteitemID.equals(other.quoteitemID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Quoteitem[ quoteitemID=" + quoteitemID + " ]";
    }
    
}
