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
    @NamedQuery(name = "Quoteitem.findByQuoteitemId", query = "SELECT q FROM Quoteitem q WHERE q.quoteitemId = :quoteitemId"),
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
    private Integer quoteitemId;
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
    private Item itemId;
    @JoinColumn(name = "quote_id", referencedColumnName = "quote_id")
    @ManyToOne(optional = false)
    private Quote quoteId;

    public Quoteitem() {
    }

    public Quoteitem(Integer quoteitemId) {
        this.quoteitemId = quoteitemId;
    }

    public Quoteitem(Integer quoteitemId, int qty, Date updatedAt, Date createdAt) {
        this.quoteitemId = quoteitemId;
        this.qty = qty;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public Integer getQuoteitemId() {
        return quoteitemId;
    }

    public void setQuoteitemId(Integer quoteitemId) {
        this.quoteitemId = quoteitemId;
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

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Quote getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Quote quoteId) {
        this.quoteId = quoteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quoteitemId != null ? quoteitemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quoteitem)) {
            return false;
        }
        Quoteitem other = (Quoteitem) object;
        if ((this.quoteitemId == null && other.quoteitemId != null) || (this.quoteitemId != null && !this.quoteitemId.equals(other.quoteitemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Quoteitem[ quoteitemId=" + quoteitemId + " ]";
    }
    
}
