/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author nick
 */
@Embeddable
public class DocumentPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "document_id")
    private int documentID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "doc_type")
    private String docType;

    public DocumentPK() {
    }

    public DocumentPK(int documentId, String docType) {
        this.documentID = documentId;
        this.docType = docType;
    }

    public int getDocumentID() {
        return documentID;
    }

    public void setDocumentID(int documentId) {
        this.documentID = documentId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) documentID;
        hash += (docType != null ? docType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocumentPK)) {
            return false;
        }
        DocumentPK other = (DocumentPK) object;
        if (this.documentID != other.documentID) {
            return false;
        }
        if ((this.docType == null && other.docType != null) || (this.docType != null && !this.docType.equals(other.docType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.as.DocumentPK[ documentId=" + documentID + ", docType=" + docType + " ]";
    }
    
}
