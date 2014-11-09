package com.as;

import com.as.orm.Item;
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
class ItemsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public ItemsGrid(IMessageSender exchanger, boolean hideBtns) throws RemoteException {
        super(exchanger, "select item_id \"Id\", item_number \"Number\", "
                + " item_name \"Name\", item_description \"Description\", last_price \"Last Price\" "
                + " from item", maxWidths, hideBtns);
    }

    public ItemsGrid(IMessageSender exchanger) throws RemoteException {
        this(exchanger, false);
    }

    @Override
    public AbstractAction addAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditItemsDialog itemsDialog = new EditItemsDialog("New Item", null);
                if (EditItemsDialog.okPressed) {
                    Item itm = (Item) itemsDialog.getEditPanel().getDbObject();
                    refresh(itm.getItemId());
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
                        Item item = (Item) exchanger.loadDbObjectOnID(Item.class, id);
                        new EditItemsDialog("Edit Item", item);
                        if (EditItemsDialog.okPressed) {
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
                        Item item = (Item) exchanger.loadDbObjectOnID(Item.class, id);
                        if (item != null) {
                            if (GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this item?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(item);
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
