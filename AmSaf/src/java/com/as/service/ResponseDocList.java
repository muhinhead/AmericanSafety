/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import com.as.Document;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author nick
 */
public class ResponseDocList {
    private Collection<ResponseDoc> response;
    private String[] errorMsg;
    
    public ResponseDocList() {
    }

    public ResponseDocList(List<Document> doclist, String[] errmsg) {
        if (doclist != null) {
            response = new ArrayList<ResponseDoc>(doclist.size());
            for (Document doc : doclist) {
                ResponseDoc rd = new ResponseDoc();
                rd.setStartRangeTime(doc.getDateIn());
                rd.setFinishRangeTime(doc.getDateOut());
                rd.setDocumentType(doc.getDocumentPK().getDocType());
                rd.setSetPO(doc.getPoNumber() == null);
                response.add(rd);
            }
        } else {
            errorMsg = errmsg;
        }
    }    

    /**
     * @return the response
     */
    public Collection<ResponseDoc> getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(Collection<ResponseDoc> response) {
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
