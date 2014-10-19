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
    
    @Override
    public IDocument createDocument() {
        return new Quote(null);
    }

}
