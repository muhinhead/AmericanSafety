package com.as;

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
class QuoteItemsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public QuoteItemsGrid(IMessageSender exchanger, Integer pkID) throws RemoteException {
        super(exchanger, "select quoteitem_id \"Id\","
                + "item_number \"Itm No_\",item_name \"Name\","
                + "qty \"Qty\",price,if(tax,'Yes','No') \"Tax\" from "
                + "quoteitem,item where quoteitem.item_id=item.item_id and quote_id=" + pkID.toString(), maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add", new ImageIcon("images/add.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int p = getSelect().indexOf("from quoteitem where quote_id=");
                EditQuoteItemDialog.quoteID = p > 0 ? Integer.parseInt(getSelect().substring(p + 30)) : 0;
                EditQuoteItemDialog qd = new EditQuoteItemDialog("Add Item", null);
                if (EditQuoteItemDialog.okPressed) {
                    Quoteitem qi = (Quoteitem) qd.getEditPanel().getDbObject();
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
                        Quoteitem qi = (Quoteitem) exchanger.loadDbObjectOnID(Quoteitem.class, id);
                        int p = getSelect().indexOf("from quoteitem where quote_id=");
                        EditQuoteItemDialog.quoteID = p > 0 ? Integer.parseInt(getSelect().substring(p + 30)) : 0;
                        new EditQuoteItemDialog("Edit Item", qi);
                        if (EditQuoteItemDialog.okPressed) {
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
                        Quoteitem qi = (Quoteitem) exchanger.loadDbObjectOnID(Quoteitem.class, id);
                        if (qi!=null) {
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
