package com.as.util;

import com.as.Document;
import java.io.Serializable;
import java.util.List;
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
public class ResponseDocumentList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private List<ResponseDocument> response;
    private String[] errMsg;

    public ResponseDocumentList() {
    }

    public ResponseDocumentList(List<Document> doclist, String[] errmsg) {
        if (doclist != null) {
            response = new ArrayList<ResponseDocument>(doclist.size());
            for (Document doc : doclist) {
                ResponseDocument rd = new ResponseDocument();
                rd.setStartRangeTime(doc.getDateIn());
                rd.setFinishRangeTime(doc.getDateOut());
                rd.setDocumentType(doc.getDocumentPK().getDocType());
                rd.setSetPO(doc.getPoNumber() == null);
            }
        } else {
            errMsg = errmsg;
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
        if (!(object instanceof ResponseDocumentList)) {
            return false;
        }
        ResponseDocumentList other = (ResponseDocumentList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.util.ResponseDocumentList[ id=" + id + " ]";
    }

    /**
     * @return the response
     */
    public List<ResponseDocument> getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(List<ResponseDocument> response) {
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
