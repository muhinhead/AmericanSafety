/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.util.LookupDialog;
import com.as.util.RecordEditPanel;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author nick
 */
class DepartmentLookupAction extends AbstractAction {
    private final JComboBox dptCB;

    public DepartmentLookupAction(JComboBox departmentCB) {
        super("...");
        this.dptCB = departmentCB;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       try {
           Integer departmentID = RecordEditPanel.getSelectedCbItem(dptCB);
           LookupDialog ld = new LookupDialog("Department lookup",dptCB,
                   new DepartsmentGrid(ASAdmin.getExchanger()),
                   new String[]{"department_name"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
