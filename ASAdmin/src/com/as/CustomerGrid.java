package com.as;

import com.as.orm.Customer;
import com.as.orm.Item;
import com.as.remote.IMessageSender;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
class CustomerGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public CustomerGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, "select customer_id \"Id\",customer_name \"Customer Name\","
                + "customer_address \"Address\",created_at \"Created\",updated_at \"Updated\" "
                + " from customer", maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add", new ImageIcon("images/add.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditCustomerDialog cd = new EditCustomerDialog("New Customer", null);
                if (EditCustomerDialog.okPressed) {
                    Customer cust = (Customer) cd.getEditPanel().getDbObject();
                    refresh(cust.getPK_ID());
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
                        Customer cust = (Customer) exchanger.loadDbObjectOnID(Customer.class, id);
                        EditCustomerDialog cd = new EditCustomerDialog("Edit Customer", cust);
                        if (EditCustomerDialog.okPressed) {
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
        return new AbstractAction("Edit", new ImageIcon("images/edit.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Customer cust = (Customer) exchanger.loadDbObjectOnID(Customer.class, id);
                        if (cust != null) {
                            if (GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this customer?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(cust);
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
