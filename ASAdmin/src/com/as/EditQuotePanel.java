package com.as;

import com.as.orm.IDocument;
import com.as.orm.Invoice;
import com.as.orm.Quote;
import com.as.orm.dbobject.DbObject;
import java.sql.Date;

/**
 *
 * @author Nick Mukhin
 */
class EditQuotePanel extends EditDocumentPanel {

    public EditQuotePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        super.fillContent();
        dateOutLbl.setVisible(false);
        dateOutSP.setVisible(false);
    }
    
//    @Override
//    public void loadData() {
//        Quote q = (Quote) getDbObject();
//        if(q != null) {
//            idField.setText(q.getPK_ID().toString());
//            dateInSP.setValue(q.getDateIn());
//            selectComboItem(customerCB, q.getCustomerId());
//            locationTF.setText(q.getLocation());
//            selectComboItem(contactCB, q.getContactId());
//            contractorTF.setText(q.getContractor());
//            rigCB.setSelectedItem(q.getRigTankEq());
//            createdSP.setValue(q.getCreatedAt());
//            updatedSP.setValue(q.getUpdatedAt());
//        }
//    }

//    @Override
//    public boolean save() throws Exception {
//        //TOOD
//        return true;
//    }

    @Override
    public IDocument createDocument() {
        return new Quote(null);
    }

}
