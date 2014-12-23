/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import com.as.Login;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Nick Mukhin
 */
@Entity
//@Table(name = "response_login")
@XmlRootElement
public class ResponseLogin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 512)
    @Column(name = "error_msg")
    private String[] errorMsg;
    @JoinColumn(name = "login_id", referencedColumnName = "userID")
    @ManyToOne
    private LoginWIthRoleList response;
    

    public ResponseLogin() {
    }

    public ResponseLogin(Login login, Collection<Integer> docIDs, String[] errMsg) {
        setResponse(login!=null?new LoginWIthRoleList(login,docIDs):null);
        setErrorMsg(errMsg);
    }

    public ResponseLogin(Integer id) {
        this.id = id;
    }

    public Integer getID() {
        return id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public String[] getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String[] errorMsg) {
        this.errorMsg = errorMsg;
    }

    public LoginWIthRoleList getResponse() {
        return response;
    }

    public void setResponse(LoginWIthRoleList response) {
        this.response = response;
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
        if (!(object instanceof ResponseLogin)) {
            return false;
        }
        ResponseLogin other = (ResponseLogin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mmi.web.ResponseLogin[ id=" + id + " ]";
    }
}
