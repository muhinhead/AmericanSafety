package com.as;

import com.as.util.LookupDialog;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
class ItemLookupAction  extends AbstractAction {
    private final JComboBox itemCB;

    public ItemLookupAction(JComboBox itemCB) {
        super("...");
        this.itemCB = itemCB;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       try {
            LookupDialog ld = new LookupDialog("Items Lookup", itemCB,
                    new ItemsGrid(ASAdmin.getExchanger()), 
                    new String[]{"item_number","item_name","item_description"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
