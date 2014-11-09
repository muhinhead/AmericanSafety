package com.as;

import com.as.orm.Customer;
import com.as.remote.IMessageSender;
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

    public CustomerGrid(IMessageSender exchanger, boolean hideBtns) throws RemoteException {
        super(exchanger, "select customer_id \"Id\",customer_name \"Customer Name\","
                + "customer_address \"Address\","
                + "DATE_FORMAT(created_at,'%m-%e-%Y %r') \"Created\","
                + "DATE_FORMAT(updated_at,'%m-%e-%Y %r') \"Updated\" "
                + " from customer", maxWidths, hideBtns);
    }

    public CustomerGrid(IMessageSender exchanger) throws RemoteException {
        this(exchanger, false);
    }

    @Override
    public AbstractAction addAction() {
        return new AbstractAction("Add") {
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
    public AbstractAction editAction() {
        return new AbstractAction("Edit") {
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
    public AbstractAction delAction() {
        return new AbstractAction("Delete") {
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
