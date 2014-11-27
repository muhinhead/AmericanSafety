package com.as;

import com.as.orm.Tax;
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
public class TaxGrid extends GeneralGridPanel {
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public TaxGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, "select tax_id \"Id\",tax_description \"Description\","
                + "DATE_FORMAT(created_at,'%m-%e-%Y %r') \"Created\", "
                + "DATE_FORMAT(updated_at,'%m-%e-%Y %r') \"Updated\" from tax", maxWidths, false);
    }

    @Override
    public AbstractAction addAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditTaxDialog ed = new EditTaxDialog("New Tax", null);
                if (EditRoleDialog.okPressed) {
                    Tax tax = (Tax) ed.getEditPanel().getDbObject();
                    refresh(tax.getTaxId());
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
                        Tax tax = (Tax) exchanger.loadDbObjectOnID(Tax.class, id);
                        new EditTaxDialog("Edit Tax", tax);
                        if (EditTaxDialog.okPressed) {
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
                        Tax tax = (Tax) exchanger.loadDbObjectOnID(Tax.class, id);
                        if (tax!=null) {
                            if (GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(tax);
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
