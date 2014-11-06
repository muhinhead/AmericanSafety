package com.as.util;

import com.as.Tax;
import java.io.Serializable;
import java.util.Collection;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Nick Mukhin
 */
@Entity
public class ResponseTaxList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Collection<ResponseTax> response;
    private String[] errorMsg;

    public ResponseTaxList() {
    }
    
    public ResponseTaxList(Collection<Tax> taxList, String[] errmsg) {
        //setResponse(taxList);
        setErrorMsg(errmsg);
        response = new ArrayList<ResponseTax>(taxList.size());
        for (Tax tax : taxList) {
            response.add(new ResponseTax(tax));
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
        if (!(object instanceof ResponseTaxList)) {
            return false;
        }
        ResponseTaxList other = (ResponseTaxList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ResponseTaxList[ id=" + id + " ]";
    }

    /**
     * @return the response
     */
    public Collection<ResponseTax> getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(Collection<ResponseTax> response) {
        this.response = response;
    }

    /**
     * @return the errorMsg
     */
    public String[] getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg the errorMsg to set
     */
    public void setErrorMsg(String[] errorMsg) {
        this.errorMsg = errorMsg;
    }
    
}
