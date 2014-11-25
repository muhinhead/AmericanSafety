/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import com.as.Stamps;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author nick
 */
@Entity
public class ResponseStamp implements Serializable {
    @Id
    private Integer stampID;
    private String stamp;


    public ResponseStamp() 
    {
    }

    public ResponseStamp(Stamps st) 
    {
        setStampID(st.getStampsId());
        setStamp(st.getStamps());
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getStampID() != null ? getStampID().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResponseStamp)) {
            return false;
        }
        ResponseStamp other = (ResponseStamp) object;
        if ((this.getStampID() == null && other.getStampID() != null) || (this.getStampID() != null && !this.stampID.equals(other.stampID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ResponseStamp[ stampID=" + getStampID() + " ]";
    }


    /**
     * @return the stamp
     */
    public String getStamp() {
        return stamp;
    }

    /**
     * @param stamp the stamp to set
     */
    public void setStamp(String stamp) {
        this.stamp = stamp;
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
    
}
