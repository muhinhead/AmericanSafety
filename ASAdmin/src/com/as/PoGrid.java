package com.as;

import com.as.orm.Po;
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
public class PoGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public PoGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, "select po_id \"Id\",po_description \"Description\","
                + "DATE_FORMAT(created_at,'%m-%e-%Y %r') \"Created\", "
                + "DATE_FORMAT(updated_at,'%m-%e-%Y %r') \"Updated\" from po", maxWidths, false);
    }

    @Override
    public AbstractAction addAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditPoDialog ed = new EditPoDialog("New P.O.", null);
                if (EditPoDialog.okPressed) {
                    Po po = (Po) ed.getEditPanel().getDbObject();
                    refresh(po.getPoId());
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
                        Po po = (Po) exchanger.loadDbObjectOnID(Po.class, id);
                        new EditPoDialog("Edit P.O.", po);
                        if (EditPoDialog.okPressed) {
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
                        Po po = (Po) exchanger.loadDbObjectOnID(Po.class, id);
                        if (po!=null) {
                            if (GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(po);
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