/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.Department;
import com.as.orm.dbobject.DbObject;
import com.as.util.RecordEditPanel;
import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author nick
 */
class EditDepartmentPanel extends RecordEditPanel {
    private JTextField idField;
    private JTextField departmenNameTF;

    public EditDepartmentPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Department Name:"
        };
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 6),
            departmenNameTF = new JTextField(60)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Department dp = (Department) getDbObject();
        if (dp != null) {
            idField.setText(dp.getDepartmentId().toString());
            departmenNameTF.setText(dp.getDepartmentName());
            try {
                UsersGrid ug;
                add(ug = new UsersGrid(ASAdmin.getExchanger(), dp.getDepartmentId()), BorderLayout.SOUTH);
                ug.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Personal of department"));
            } catch (RemoteException ex) {
                ASAdmin.logAndShowMessage(ex);
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Department dp = (Department) getDbObject();
        if (dp == null) {
            dp = new Department(null);
            dp.setDepartmentId(0);
            isNew = true;
        }
        dp.setDepartmentName(departmenNameTF.getText());
        return saveDbRecord(dp, isNew);
    }
    
}
