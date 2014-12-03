/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.IDocument;
import com.as.orm.Invoice;
import com.as.orm.Order;
import com.as.orm.dbobject.DbObject;
import com.as.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class OrdersGrid  extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }
    
    public OrdersGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, "select order_id \"Id\","
                + "DATE_FORMAT(date_in,'%m-%e-%Y %r') \"Date In\","
                + "DATE_FORMAT(date_out,'%m-%e-%Y %r') \"Date Out\","
                + "(select sum(qty*price) from orderitem where order_id=`order`.order_id) \"Sum\","
                + "(select customer_name from customer where customer_id=`order`.customer_id) \"Customer\","
                + "(select concat(first_name,' ',last_name) from contact where contact_id=`order`.contact_id) \"Contact\","
                + "location \"Location\",contractor \"Contractor\","
                + "DATE_FORMAT(created_at,'%m-%e-%Y %r') \"Created\", DATE_FORMAT(updated_at,'%m-%e-%Y %r') \"Updated\" "
                + " from `order`", maxWidths, false);
    }

    @Override
    public AbstractAction addAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditOrderDialog ed = new EditOrderDialog("New Order", null);
                if (ed.getOkPressed()) {
                    Order order = (Order) ed.getEditPanel().getDbObject();
                    refresh(order.getOrderId());
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
                        Order doc = (Order) exchanger.loadDbObjectOnID(Order.class, id);
                        EditOrderDialog dialog = new EditOrderDialog("Edit Order", doc);
                        if (dialog != null && dialog.getOkPressed()) {
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
                        IDocument doc = (IDocument) exchanger.loadDbObjectOnID(Order.class, id);
                        if(doc!=null && GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this document?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject((DbObject) doc);
                            refresh();
                        }
                    } catch (RemoteException ex) {
                        ASAdmin.logAndShowMessage(ex);
                    }
                }                
            }
        };
    }


}
