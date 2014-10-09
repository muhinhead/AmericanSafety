package com.as;

import com.as.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

/**
 *
 * @author nick
 */
public class DocumentsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public DocumentsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, "select * from document", maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add",new ImageIcon("images/add.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit",new ImageIcon("images/edit.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Del",new ImageIcon("images/delete.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
    }
}
