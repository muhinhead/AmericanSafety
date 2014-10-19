/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.Orderitem;
import com.as.orm.Quoteitem;
import com.as.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
class OrderItemsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }
    
    public OrderItemsGrid(IMessageSender exchanger, Integer pkID) throws RemoteException {
        super(exchanger, "select * from orderitem where order_id="+pkID.toString(), maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add", new ImageIcon("images/add.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p = getSelect().indexOf("from orderitem where order_id=");
                EditOrderItemDialog.orderID = p > 0 ? Integer.parseInt(getSelect().substring(p + 30)) : 0;
                EditOrderItemDialog qd = new EditOrderItemDialog("Add Item", null);
                if (EditOrderItemDialog.okPressed) {
                    Orderitem qi = (Orderitem) qd.getEditPanel().getDbObject();
                    refresh(qi.getPK_ID());
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
                        Orderitem oi = (Orderitem) exchanger.loadDbObjectOnID(Orderitem.class, id);
                        int p = getSelect().indexOf("from orderitem where order_id=");
                        EditOrderItemDialog.orderID = p > 0 ? Integer.parseInt(getSelect().substring(p + 30)) : 0;
                        new EditOrderItemDialog("Edit Item", oi);
                        if (EditOrderItemDialog.okPressed) {
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
                        Orderitem oi = (Orderitem) exchanger.loadDbObjectOnID(Orderitem.class, id);
                        if (oi!=null) {
                            if (GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this item?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(oi);
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
