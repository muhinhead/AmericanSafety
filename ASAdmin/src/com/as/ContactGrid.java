/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.Contact;
import com.as.orm.Item;
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
public class ContactGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
//    private static int customerID;

    static {
        maxWidths.put(0, 40);
    }

    public ContactGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, "select "
                + "contact_id \"Id\", title \"Title\", first_name \"First Name\", last_name \"Last Name\", "
                + "email \"Email\", phone \"Phone\", "
                + "(select customer_name from  customer where customer_id=contact.customer_id) \"Customer\","
                + "created_at \"Created\", updated_at \"Last update\" "
                + "from contact where 0=0", maxWidths, false);
    }

    public ContactGrid(IMessageSender exchanger, int customer_id) throws RemoteException {
        super(exchanger, "select "
                + "contact_id \"Id\", title \"Title\", first_name \"First Name\", last_name \"Last Name\", "
                + "email \"Email\", phone \"Phone\", created_at \"Created\", updated_at \"Last update\" "
                + "from contact where customer_id=" + customer_id, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add", new ImageIcon("images/add.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p = getSelect().indexOf("from contact where customer_id=");
                EditContactDialog.customerID = p > 0 ? Integer.parseInt(getSelect().substring(p + 31)) : 0;
                EditContactDialog cd = new EditContactDialog("New Contact", null);
                if (EditContactDialog.okPressed) {
                    Contact contact = (Contact) cd.getEditPanel().getDbObject();
                    refresh(contact.getContactId());
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
                        Contact contact = (Contact) exchanger.loadDbObjectOnID(Contact.class, id);
                        int p = getSelect().indexOf("from contact where customer_id=");
                        EditContactDialog.customerID = p > 0 ? Integer.parseInt(getSelect().substring(p + 31)) : 0;
                        new EditContactDialog("Edit Contact", contact);
                        if (EditContactDialog.okPressed) {
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
        return new AbstractAction("Del", new ImageIcon("images/delete.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Contact contact = (Contact) exchanger.loadDbObjectOnID(Contact.class, id);
                        if (contact != null) {
                            if (GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this contact?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(contact);
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
