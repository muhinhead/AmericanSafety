package com.as;

import com.as.orm.User;
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
public class UsersGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
        maxWidths.put(8, 40);
    }

    public UsersGrid(IMessageSender exchanger, Integer departmentId, boolean hideBtns) throws RemoteException {
        super(exchanger,
                "select user_id \"Id\",first_name \"First Name\","
                + "last_name \"Last Name\",login \"Login\","
                + "(select group_concat(role_name) from role r, usersrole ur where ur.role_id=r.role_id and ur.user_id=user.user_id) \"Roles\","
                + "(select department_name from department where department_id=user.department_id) \"Department\","
                + "DATE_FORMAT(created_at,'%m-%e-%Y %r') \"Created\", "
                + "DATE_FORMAT(updated_at,'%m-%e-%Y %r') \"Updated\" "
                + "from user " + (departmentId != null ? " where department_id=" + departmentId : ""),
                maxWidths, hideBtns);
    }

    public UsersGrid(IMessageSender exchanger, Integer departmentId) throws RemoteException {
        this(exchanger, departmentId, false);
    }
    
    public UsersGrid(IMessageSender exchanger, boolean hideBtns) throws RemoteException {
        this(exchanger, null, hideBtns);
    }

    public UsersGrid(IMessageSender exchanger) throws RemoteException {
        this(exchanger, false);
    }

    @Override
    public AbstractAction addAction() {
        return new AbstractAction("Add") {
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
    public AbstractAction editAction() {
        return new AbstractAction("Edit") {
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
    public AbstractAction delAction() {
        return new AbstractAction("Del") {
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
