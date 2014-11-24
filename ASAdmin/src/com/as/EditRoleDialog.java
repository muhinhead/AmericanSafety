/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import static com.as.EditDepartmentDialog.okPressed;
import com.as.orm.Role;
import com.as.orm.dbobject.DbObject;
import com.as.util.EditRecordDialog;

/**
 *
 * @author nick
 */
class EditRoleDialog extends EditRecordDialog {
    
    public static boolean okPressed;
    
    public EditRoleDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditRolePanel((DbObject) getObject()));
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
