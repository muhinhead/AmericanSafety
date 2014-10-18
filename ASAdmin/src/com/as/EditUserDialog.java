package com.as;

import com.as.orm.dbobject.DbObject;
import com.as.util.EditRecordDialog;

/**
 *
 * @author Nick Mukhin
 */
class EditUserDialog extends EditRecordDialog {

    public static boolean okPressed;
    
    public EditUserDialog(String title, Object obj) {
        super(title, obj);
    }
    
     @Override
    protected void fillContent() {
        super.fillContent(new EditUserPanel((DbObject) getObject()));
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
