/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.Department;
import com.as.orm.Role;
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
public class RolesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public RolesGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger,"select role_id \"Id\",role_name \"Role\" from role", maxWidths, false);
    }

    @Override
    public AbstractAction addAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditRoleDialog ed = new EditRoleDialog("New Role", null);
                if (EditRoleDialog.okPressed) {
                    Role role = (Role) ed.getEditPanel().getDbObject();
                    refresh(role.getRoleId());
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
                        Role role = (Role) exchanger.loadDbObjectOnID(Role.class, id);
                        new EditRoleDialog("Edit Role", role);
                        if (EditRoleDialog.okPressed) {
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
                        Role role = (Role) exchanger.loadDbObjectOnID(Role.class, id);
                        if (role!=null) {
                            if (GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this department?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(role);
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
