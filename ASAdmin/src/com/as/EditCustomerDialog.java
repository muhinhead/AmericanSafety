package com.as;

import static com.as.EditContactDialog.okPressed;
import com.as.orm.dbobject.DbObject;
import com.as.util.EditRecordDialog;

/**
 *
 * @author Nick Mukhin
 */
public class EditCustomerDialog extends EditRecordDialog {

    public static boolean okPressed;
    
    public EditCustomerDialog(String title, Object obj) {
        super(title, obj);
    }
    
     @Override
    protected void fillContent() {
        super.fillContent(new EditCustomerPanel((DbObject) getObject()));
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
