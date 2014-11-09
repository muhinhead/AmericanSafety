package com.as;

import com.as.remote.IMessageSender;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;

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
        return null;
    }

    @Override
    public AbstractAction editAction() {
        return null;
    }

    @Override
    public AbstractAction delAction() {
        return null;
    }    
}