package com.as.util;

import com.as.GeneralGridPanel;
import com.as.mvc.dbtable.DbTableView;
import com.as.remote.IMessageSender;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
public class GeneralGridAdapter extends GeneralGridPanel {

    public GeneralGridAdapter(IMessageSender exchanger, String select,
            HashMap<Integer, Integer> maxWidths, boolean readOnly, DbTableView tabView) throws RemoteException {
        super(exchanger, select, maxWidths, readOnly, tabView);
    }
    
    public GeneralGridAdapter(IMessageSender exchanger, String select,
            HashMap<Integer, Integer> maxWidths, boolean readOnly) throws RemoteException {
        super(exchanger, select, maxWidths, readOnly, null);
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
