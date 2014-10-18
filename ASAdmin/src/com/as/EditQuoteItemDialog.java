package com.as;

import com.as.orm.Quoteitem;
import com.as.orm.dbobject.DbObject;
import com.as.util.EditRecordDialog;

/**
 *
 * @author Nick Mukhin
 */
class EditQuoteItemDialog extends EditRecordDialog {
    public static boolean okPressed;
    public static int quoteID;

    public EditQuoteItemDialog(String title, DbObject obj) {
        super(title, obj);
    }
    
     @Override
    protected void fillContent() {
        super.fillContent(new EditQuoteItemPanel((DbObject) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }   

    @Override
    public boolean getOkPressed() {
        return okPressed;
    }
}
