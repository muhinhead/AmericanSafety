/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

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
public class ResponseNewCustomer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private ResponseCustomerID response;
    private String[] errorMsg;

    public ResponseNewCustomer() {
    }

    public ResponseNewCustomer(Integer id) {
        response = new ResponseCustomerID(id);
    }

    public ResponseNewCustomer(String err) {
        errorMsg = new String[]{err};
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
        if (!(object instanceof ResponseNewCustomer)) {
            return false;
        }
        ResponseNewCustomer other = (ResponseNewCustomer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ResponseNewCustomer[ id=" + id + " ]";
    }

    /**
     * @return the response
     */
    public ResponseCustomerID getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(ResponseCustomerID response) {
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
