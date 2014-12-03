/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.Orderitem;
import com.as.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
class OrderItemsGrid extends DocItemsGrid {
    
    public OrderItemsGrid(IMessageSender exchanger, Integer pkID) throws RemoteException {
        super(exchanger, "select orderitem_id \"Id\","
                + "item_number \"Itm No_\",item_name \"Name\","
                + "qty \"Qty\",price,if(tax,'Yes','No') \"Tax\" from "
                + "orderitem,item where orderitem.item_id=item.item_id and order_id=", pkID);
    }

    @Override
    public AbstractAction addAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p = getSelect().indexOf(" and order_id=");
                EditOrderItemDialog.orderID = p > 0 ? Integer.parseInt(getSelect().substring(p + 14)) : 0;
                EditOrderItemDialog qd = new EditOrderItemDialog("Add Item", null);
                if (EditOrderItemDialog.okPressed) {
                    Orderitem qi = (Orderitem) qd.getEditPanel().getDbObject();
                    refresh(qi.getPK_ID());
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
                        Orderitem oi = (Orderitem) exchanger.loadDbObjectOnID(Orderitem.class, id);
                        int p = getSelect().indexOf(" and order_id=");
                        EditOrderItemDialog.orderID = p > 0 ? Integer.parseInt(getSelect().substring(p + 14)) : 0;
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
    public AbstractAction delAction() {
        return new AbstractAction("Del") {
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
