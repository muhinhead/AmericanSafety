/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.remote.IMessageSender;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;

/**
 *
 * @author nick
 */
public abstract class DocItemsGrid extends GeneralGridPanel {
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }
    private EditDocumentPanel docPanel;

    public DocItemsGrid(IMessageSender exchanger, String select, Integer pkID) throws RemoteException {
        super(exchanger, select + pkID.toString(), maxWidths, false);
    }
    
    public void setDocPanel(EditDocumentPanel docPanel) {
        this.docPanel = docPanel;
    }
    
    @Override
    public void refresh(int id) {
        super.refresh(id);
        if (docPanel != null) {
            docPanel.recalcTotal(this);
        }
    }

    @Override
    public void refresh() {
        super.refresh();
        if (docPanel != null) {
            docPanel.recalcTotal(this);
        }
    }    

}
