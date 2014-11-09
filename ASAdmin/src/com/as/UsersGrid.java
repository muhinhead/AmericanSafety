package com.as;

import com.as.orm.User;
import com.as.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author nick
 */
public class UsersGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
        maxWidths.put(8, 40);
//        maxWidths.put(3, 60);
//        maxWidths.put(4, 130);
//        maxWidths.put(5, 130);
//        maxWidths.put(6, 130);
//        maxWidths.put(7, 130);
    }

    public UsersGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, "select user_id \"Id\",first_name \"First Name\","
                + "last_name \"Last Name\",login \"Login\",if(admin,'Admin','') \"Is admin\","
                + "DATE_FORMAT(created_at,'%m-%e-%Y %r') \"Created\", "
                + "DATE_FORMAT(updated_at,'%m-%e-%Y %r') \"Updated\" from user", maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add", new ImageIcon("images/NewEntry.jpg")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditUserDialog ed = new EditUserDialog("New User", null);
                if (EditUserDialog.okPressed) {
                    User user = (User) ed.getEditPanel().getDbObject();
                    refresh(user.getUserId());
                }
            }
        };
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit", new ImageIcon("images/edit.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        User user = (User) exchanger.loadDbObjectOnID(User.class, id);
                        new EditUserDialog("Edit User", user);
                        if (EditUserDialog.okPressed) {
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
    protected AbstractAction delAction() {
        return new AbstractAction("Del", new ImageIcon("images/DeleteEntry.jpg")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        User user = (User) exchanger.loadDbObjectOnID(User.class, id);
                        if (user != null) {
                            if (id == 1) {
                                GeneralFrame.errMessageBox("Attention!",
                                        "You can't to dismiss the chief administrator!");
                            } else if (GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this user?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(user);
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
