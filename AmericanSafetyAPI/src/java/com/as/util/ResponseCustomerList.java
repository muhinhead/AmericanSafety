/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import com.as.Customer;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author nick
 */
@Entity
public class ResponseCustomerList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private List<ResponseCustomer> response;
    private String[] errMsg;

    public ResponseCustomerList() {
        response = new ArrayList<ResponseCustomer>();
        errMsg = null;
    }

    public ResponseCustomerList(List<Customer> custList, String[] errList) {
        if (custList != null) {
            response = new ArrayList<ResponseCustomer>(custList.size());
            for (Customer cust : custList) {
                response.add(new ResponseCustomer(cust));
            }
        } else {
            setErrMsg(errList);
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
        if (!(object instanceof ResponseCustomerList)) {
            return false;
        }
        ResponseCustomerList other = (ResponseCustomerList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ResponseCustomerList[ id=" + id + " ]";
    }

    /**
     * @return the response
     */
    public List<ResponseCustomer> getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(List<ResponseCustomer> response) {
        this.response = response;
    }

    /**
     * @return the errMsg
     */
    public String[] getErrMsg() {
        return errMsg;
    }

    /**
     * @param errMsg the errMsg to set
     */
    public void setErrMsg(String[] errMsg) {
        this.errMsg = errMsg;
    }

}
