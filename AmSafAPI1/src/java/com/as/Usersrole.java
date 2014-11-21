/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nick
 */
@Entity
@Table(name = "usersrole")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usersrole.findAll", query = "SELECT u FROM Usersrole u"),
    @NamedQuery(name = "Usersrole.findByUsersroleId", query = "SELECT u FROM Usersrole u WHERE u.usersroleId = :usersroleId")})
public class Usersrole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "usersrole_id")
    private Integer usersroleId;
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @ManyToOne(optional = false)
    private Role roleId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private User userId;

    public Usersrole() {
    }

    public Usersrole(Integer usersroleId) {
        this.usersroleId = usersroleId;
    }

    public Integer getUsersroleId() {
        return usersroleId;
    }

    public void setUsersroleId(Integer usersroleId) {
        this.usersroleId = usersroleId;
    }

    public Role getRoleId() {
        return roleId;
    }

    public void setRoleId(Role roleId) {
        this.roleId = roleId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usersroleId != null ? usersroleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usersrole)) {
            return false;
        }
        Usersrole other = (Usersrole) object;
        if ((this.usersroleId == null && other.usersroleId != null) || (this.usersroleId != null && !this.usersroleId.equals(other.usersroleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.Usersrole[ usersroleId=" + usersroleId + " ]";
    }
    
}
