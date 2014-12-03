package com.as;

import com.as.orm.Invoiceitem;
import com.as.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
class InvoiceItemsGrid extends DocItemsGrid {

    public InvoiceItemsGrid(IMessageSender exchanger, Integer pkID) throws RemoteException {
        super(exchanger, "select invoiceitem_id \"Id\","
                + "item_number \"Itm No_\",item_name \"Name\","
                + "qty \"Qty\",price,if(tax,'Yes','No') \"Tax\" from "
                + "invoiceitem,item where invoiceitem.item_id=item.item_id and invoice_id=",pkID);
    }

    @Override
    public AbstractAction addAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p = getSelect().indexOf(" and invoice_id=");
                EditInvoiceItemDialog.invoiceID = p > 0 ? Integer.parseInt(getSelect().substring(p + 16)) : 0;
                EditInvoiceItemDialog ii = new EditInvoiceItemDialog("Add Item", null);
                if (EditInvoiceItemDialog.okPressed) {
                    Invoiceitem qi = (Invoiceitem) ii.getEditPanel().getDbObject();
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
                        Invoiceitem qi = (Invoiceitem) exchanger.loadDbObjectOnID(Invoiceitem.class, id);
                        int p = getSelect().indexOf(" and invoice_id=");
                        EditInvoiceItemDialog.invoiceID = p > 0 ? Integer.parseInt(getSelect().substring(p + 16)) : 0;
                        new EditInvoiceItemDialog("Edit Item", qi);
                        if (EditInvoiceItemDialog.okPressed) {
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
                        Invoiceitem qi = (Invoiceitem) exchanger.loadDbObjectOnID(Invoiceitem.class, id);
                        if (qi != null) {
                            if (GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this item?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(qi);
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
