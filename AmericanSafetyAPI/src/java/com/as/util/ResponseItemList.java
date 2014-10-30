package com.as.util;

import com.as.Item;
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
public class ResponseItemList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private List<ResponseItem> response;
    private String[] errorMsg;

    public ResponseItemList() {
    }
    
    public ResponseItemList(List<Item> itemsList, String[] errmsg) {
        //setResponse(itemsList);
        response = new ArrayList<ResponseItem>(itemsList.size());
        for (Item itm : itemsList) {
            response.add(new ResponseItem(itm));
        }
        setErrorMsg(errmsg);
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
        if (!(object instanceof ResponseItemList)) {
            return false;
        }
        ResponseItemList other = (ResponseItemList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ResponseItemList[ id=" + id + " ]";
    }

    /**
     * @return the response
     */
    public List<ResponseItem> getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(List<ResponseItem> response) {
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
