package com.as;

import com.as.orm.IDocument;
import com.as.orm.Invoice;
import com.as.orm.dbobject.DbObject;
import java.sql.Date;

/**
 *
 * @author Nick Mukhin
 */
class EditInvoicePanel extends EditDocumentPanel {

    public EditInvoicePanel(DbObject dbObject) {
        super(dbObject);
    }

//    @Override
//    protected void fillContent() {
//        //TOOD
//    }

    @Override
    public IDocument createDocument() {
        return (IDocument) new Invoice(null);
    }

    @Override
    protected void setDocumentAdditionsBeforeSave(IDocument doc) throws Exception {
        Invoice inv = (Invoice) doc;
        java.util.Date ud = (java.util.Date) dateOutSP.getValue();
        inv.setDateOut(new java.sql.Date(ud.getTime()));
    }   
}
