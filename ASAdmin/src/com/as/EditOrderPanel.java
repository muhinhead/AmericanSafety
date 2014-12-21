package com.as;

import com.as.orm.IDocument;
import com.as.orm.Order;
import com.as.orm.dbobject.DbObject;
import java.sql.Date;

/**
 *
 * @author Nick Mukhin
 */
class EditOrderPanel extends EditDocumentPanel {

    public EditOrderPanel(DbObject dbObject) {
        super(dbObject);
    }

//    @Override
//    protected void fillContent() {
//        //TOOD
//    }
//    @Override
//    public void loadData() {
//        //TOOD
//    }
//
//    @Override
//    public boolean save() throws Exception {
//        //TOOD
//        return true;
//    }
    @Override
    public IDocument createDocument() {
        return (IDocument) new Order(null);
    }

    @Override
    protected void setDocumentAdditionsBeforeSave(IDocument doc) throws Exception {
        Order ord = (Order) doc;
        if (dateOutNullCB.isSelected()) {
            ord.setDateOut(null);
        } else {
            java.util.Date ud = (java.util.Date) dateOutSP.getValue();
            ord.setDateOut(new java.sql.Date(ud.getTime()));
        }
    }

    @Override
    protected void getDocumentAdditions4Load(IDocument doc) {
        Order ord = (Order) doc;
        Date dateOut = ord.getDateOut();
        dateOutNullCB.setSelected(dateOut == null || dateOut.getYear()<=70);
        if (dateOut == null || dateOut.getYear()<=70) {
            dateOutSP.setEnabled(false);
        } else {
            dateOutSP.setValue(ord.getDateOut());
        }
    }

    @Override
    public String documentType() {
        return "order";
    }
}
