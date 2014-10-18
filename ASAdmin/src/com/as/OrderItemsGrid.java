/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.remote.IMessageSender;
import java.util.HashMap;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
class OrderItemsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }
    
    public OrderItemsGrid(IMessageSender exchanger, Integer pkID) throws RemoteException {
        super(exchanger, "select * from orderitem where order_id="+pkID.toString(), maxWidths, false);
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
