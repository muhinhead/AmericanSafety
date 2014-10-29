package com.as.util;

import com.as.Po;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Nick Mukhin
 */
@Entity
public class ResponsePoTypes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String[] errorMsg;
    private List<Po> response;

    public ResponsePoTypes() {
        response = new ArrayList<Po>();
        errorMsg = null;
    }
    
    public ResponsePoTypes(List<Po> poList) {
        setResponse(poList);
        setErrorMsg(null);
    }
    
    public ResponsePoTypes(String[] errlist) {
        setErrorMsg(errlist);
        setResponse(null);
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
        if (!(object instanceof ResponsePoTypes)) {
            return false;
        }
        ResponsePoTypes other = (ResponsePoTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ResponsePoTypes[ id=" + id + " ]";
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

    /**
     * @return the response
     */
    public List<Po> getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(List<Po> response) {
        this.response = response;
    }
    
}
