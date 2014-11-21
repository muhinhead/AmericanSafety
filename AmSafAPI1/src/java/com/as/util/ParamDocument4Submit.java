package com.as.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;


/**
 *
 * @author Nick Mukhin
 */
@Entity
public class ParamDocument4Submit implements Serializable {

    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer userID;
    @Temporal(TemporalType.DATE)
    private Date dateIn;
    @Temporal(TemporalType.DATE)
    private Date dateOut;
    private String documentType; //instead of documentID in specs
    private Integer customerID;
    private Integer contactID;
    private String location;
    private String contractor;
    private String rigTankEquipment;
    private byte[] imageSignature;
    private BigDecimal discount;
    private String wellName;
    private String afeUww;
    private String dateStr;
    private String cai;
    private String aprvrName;
    private Integer poTypeID;  // instead of poID in specs
    private Integer taxID;     // instead of tax in specs
    private String poNumber;   // instead of poID Set // BOOL=YES/NO in specs
    private Integer stampID;   // added above specs, because exists in all documents
    private Collection<ParamDocItem> items;

    public ParamDocument4Submit() {
    }

    public ParamDocument4Submit(HttpServletRequest request) throws ParseException, IOException, ServletException {
        if (request.getParameter("userID") != null) {
            userID = Integer.parseInt(request.getParameter("userID"));
        }
        if (request.getParameter("dateIn") != null) {
            dateIn = dateTimeFormat.parse(request.getParameter("dateIn"));
        }
        if (request.getParameter("dateOut") != null) {
            dateOut = dateTimeFormat.parse(request.getParameter("dateOut"));
        }
        documentType = request.getParameter("documentType");
        if (request.getParameter("customerID") != null) {
            customerID = Integer.parseInt(request.getParameter("customerID"));
        }
        if (request.getParameter("contactID") != null) {
            contactID = Integer.parseInt(request.getParameter("contactID"));
        }
        location = request.getParameter("location");
        contractor = request.getParameter("contractor");
        rigTankEquipment = request.getParameter("rigTankEquipment");
        discount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("discount")));
        wellName = request.getParameter("wellName");
        afeUww = request.getParameter("afeUww");
        dateStr = request.getParameter("dateStr");
        cai = request.getParameter("cai");
        aprvrName = request.getParameter("aprvrName");
        if (request.getParameter("poTypeID") != null) {
            poTypeID = Integer.parseInt(request.getParameter("poTypeID"));
        }
        poNumber = request.getParameter("poNumber");
        if (request.getParameter("stampID") != null) {
            stampID = Integer.parseInt(request.getParameter("stampID"));
        }
        items = new ArrayList<ParamDocItem>();
        int i = 1;
        while (request.getParameter("itemID" + i) != null) {
            BigDecimal sum = BigDecimal.valueOf(Double.parseDouble(request.getParameter("sum" + i)));
            items.add(new ParamDocItem(Integer.parseInt(request.getParameter("itemID" + i)),
                    Integer.parseInt(request.getParameter("qty" + i)), sum));
            ++i;
        }
        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            if (part.getContentType() != null) {
                imageSignature = Utils.createByteArray(part.getInputStream());
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParamDocument4Submit)) {
            return false;
        }
        ParamDocument4Submit other = (ParamDocument4Submit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ParamDocument4Submit[ id=" + id + " ]";
    }

    /**
     * @return the userID
     */
    public Integer getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    /**
     * @return the dateIn
     */
    public Date getDateIn() {
        return dateIn;
    }

    /**
     * @param dateIn the dateIn to set
     */
    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    /**
     * @return the dateOut
     */
    public Date getDateOut() {
        return dateOut;
    }

    /**
     * @param dateOut the dateOut to set
     */
    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    /**
     * @return the documentType
     */
    public String getDocumentType() {
        return documentType;
    }

    /**
     * @param documentType the documentType to set
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    /**
     * @return the customerID
     */
    public Integer getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the contactID
     */
    public Integer getContactID() {
        return contactID;
    }

    /**
     * @param contactID the contactID to set
     */
    public void setContactID(Integer contactID) {
        this.contactID = contactID;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the contractor
     */
    public String getContractor() {
        return contractor;
    }

    /**
     * @param contractor the contractor to set
     */
    public void setContractor(String contractor) {
        this.contractor = contractor;
    }

    /**
     * @return the rigTankEquipment
     */
    public String getRigTankEquipment() {
        return rigTankEquipment;
    }

    /**
     * @param rigTankEquipment the rigTankEquipment to set
     */
    public void setRigTankEquipment(String rigTankEquipment) {
        this.rigTankEquipment = rigTankEquipment;
    }

    /**
     * @return the imageSignature
     */
    public byte[] getImageSignature() {
        return imageSignature;
    }

    /**
     * @param imageSignature the imageSignature to set
     */
    public void setImageSignature(byte[] imageSignature) {
        this.imageSignature = imageSignature;
    }

    /**
     * @return the discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
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

    /**
     * @return the dateStr
     */
    public String getDateStr() {
        return dateStr;
    }

    /**
     * @param dateStr the dateStr to set
     */
    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    /**
     * @return the poTypeID
     */
    public Integer getPoTypeID() {
        return poTypeID;
    }

    /**
     * @param poTypeID the poTypeID to set
     */
    public void setPoTypeID(Integer poTypeID) {
        this.poTypeID = poTypeID;
    }

    /**
     * @return the poNumber
     */
    public String getPoNumber() {
        return poNumber;
    }

    /**
     * @param poNumber the poNumber to set
     */
    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    /**
     * @return the items
     */
    public Collection<ParamDocItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(Collection<ParamDocItem> items) {
        this.items = items;
    }

    /**
     * @return the stampID
     */
    public Integer getStampID() {
        return stampID;
    }

    /**
     * @param stampID the stampID to set
     */
    public void setStampID(Integer stampID) {
        this.stampID = stampID;
    }

    /**
     * @return the taxID
     */
    public Integer getTaxID() {
        return taxID;
    }

    /**
     * @param taxID the taxID to set
     */
    public void setTaxID(Integer taxID) {
        this.taxID = taxID;
    }

}
