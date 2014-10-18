package com.as;

import com.as.orm.IDocument;
import com.as.orm.Order;
import com.as.orm.dbobject.DbObject;
import com.as.orm.dbobject.ForeignKeyViolationException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        return new Order(null);
    }

    @Override
    protected void setDocumentAdditionsBeforeSave(IDocument doc) throws Exception {
        Order ord = (Order) doc;
        java.util.Date ud = (java.util.Date) dateOutSP.getValue();
        ord.setDateOut(new java.sql.Date(ud.getTime()));
    }
    
}
