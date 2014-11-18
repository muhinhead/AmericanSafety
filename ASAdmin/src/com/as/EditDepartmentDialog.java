/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.Department;
import com.as.orm.dbobject.DbObject;
import com.as.util.EditRecordDialog;

/**
 *
 * @author nick
 */
class EditDepartmentDialog extends EditRecordDialog {

    public static boolean okPressed;
    
    public EditDepartmentDialog(String title, Object obj) {
        super(title, obj);
    }
     @Override
    protected void fillContent() {
        super.fillContent(new EditDepartmentPanel((DbObject) getObject()));
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
