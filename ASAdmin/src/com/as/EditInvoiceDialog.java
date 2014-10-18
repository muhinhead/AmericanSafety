package com.as;

import static com.as.EditContactDialog.okPressed;
import com.as.orm.dbobject.DbObject;
import com.as.util.EditRecordDialog;

/**
 *
 * @author Nick Mukhin
 */
class EditInvoiceDialog extends EditRecordDialog {
    public static boolean okPressed;
    
    public EditInvoiceDialog(String title, Object obj) {
        super(title, obj);
    }
    
     @Override
    protected void fillContent() {
        super.fillContent(new EditInvoicePanel((DbObject) getObject()));
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
