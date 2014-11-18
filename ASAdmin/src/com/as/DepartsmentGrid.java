/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.Department;
import com.as.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author nick
 */
class DepartsmentGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public DepartsmentGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger,"select department_id \"Id\",department_name \"Dpt.Name\" from department", maxWidths, false);
    }

    @Override
    public AbstractAction addAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditDepartmentDialog ed = new EditDepartmentDialog("New Department", null);
                if (EditDepartmentDialog.okPressed) {
                    Department department = (Department) ed.getEditPanel().getDbObject();
                    refresh(department.getDepartmentId());
                }
            }
        };
    }

    @Override
    public AbstractAction editAction() {
        return new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Department department = (Department) exchanger.loadDbObjectOnID(Department.class, id);
                        new EditDepartmentDialog("Edit Department", department);
                        if (EditDepartmentDialog.okPressed) {
                            refresh();
                        }
                    } catch (RemoteException ex) {
                        ASAdmin.logAndShowMessage(ex);
                    }
                }
            }
        };
    }

    @Override
    public AbstractAction delAction() {
        return new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Department department = (Department) exchanger.loadDbObjectOnID(Department.class, id);
                        if (department!=null) {
                            if (GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this department?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(department);
                                refresh();
                            }
                        }
                    } catch (RemoteException ex) {
                        ASAdmin.logAndShowMessage(ex);
                    }
                }
            }
        };
    }
    
}
