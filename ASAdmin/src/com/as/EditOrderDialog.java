package com.as;

import static com.as.EditContactDialog.okPressed;
import com.as.orm.dbobject.DbObject;
import com.as.util.EditRecordDialog;

/**
 *
 * @author Nick Mukhin
 */
class EditOrderDialog  extends EditRecordDialog {
    public static boolean okPressed;
    
    public EditOrderDialog(String title, Object obj) {
        super(title, obj);
    }
    
     @Override
    protected void fillContent() {
        super.fillContent(new EditOrderPanel((DbObject) getObject()));
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
