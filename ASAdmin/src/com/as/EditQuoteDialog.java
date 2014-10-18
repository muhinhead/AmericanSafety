package com.as;


import com.as.orm.dbobject.DbObject;
import com.as.util.EditRecordDialog;

/**
 *
 * @author Nick Mukhin
 */
public class EditQuoteDialog extends EditRecordDialog {
    public static boolean okPressed;
    
    public EditQuoteDialog(String title, Object obj) {
        super(title, obj);
    }
    
     @Override
    protected void fillContent() {
        super.fillContent(new EditQuotePanel((DbObject) getObject()));
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
