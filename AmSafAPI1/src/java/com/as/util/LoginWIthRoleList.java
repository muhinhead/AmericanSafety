/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import com.as.Login;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author nick
 */
@Entity
public class LoginWIthRoleList implements Serializable {
    @Id
    private Integer userId;
    private String username;
    private String email;
    private String url;
    private String[] roles;
    private int departmentId;
    private Collection<Integer> documentID;
    private byte[] avatar;

    public LoginWIthRoleList() {}
    
    public LoginWIthRoleList(Login login, Collection<Integer> ids) {
        setUserId(login.getUserID());
        setDocumentID(ids);
        setUsername(login.getUsername());
        setEmail(login.getEmail());
        setUrl(login.getUrl());
        setDepartmentId(login.getDepartmentID());
        setAvatar(login.getAvatar());
//        setImageUrl(createImageUrl(login.getUserID()));
        setRoles(login.getRoles().split(","));
    }
    
//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (getUserId() != null ? getUserId().hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof LoginWIthRoleList)) {
//            return false;
//        }
//        LoginWIthRoleList other = (LoginWIthRoleList) object;
//        if ((this.getUserId() == null && other.getUserId() != null) || (this.getUserId() != null && !this.userId.equals(other.userId))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.as.util.LoginWIthRoleList[ userId=" + getUserId() + " ]";
//    }

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the roles
     */
    public String[] getRoles() {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    /**
     * @return the departmentId
     */
    public int getDepartmentId() {
        return departmentId;
    }

    /**
     * @param departmentId the departmentId to set
     */
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * @return the avatar
     */
    public byte[] getAvatar() {
        return avatar;
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }


    private String createImageUrl(Integer userID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the documentID
     */
    public Collection<Integer> getDocumentID() {
        return documentID;
    }

    /**
     * @param documentID the documentID to set
     */
    public void setDocumentID(Collection<Integer> documentID) {
        this.documentID = documentID;
    }
    
}
