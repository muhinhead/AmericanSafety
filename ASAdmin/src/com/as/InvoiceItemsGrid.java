package com.as;

import com.as.remote.IMessageSender;
import java.util.HashMap;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
class InvoiceItemsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }
    
    public InvoiceItemsGrid(IMessageSender exchanger, Integer pkID) throws RemoteException {
        super(exchanger, "select * from invoiceitem where invoice_id="+pkID.toString(), maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return null;
    }

    @Override
    protected AbstractAction editAction() {
        return null;
    }

    @Override
    protected AbstractAction delAction() {
        return null;
    }    
}
