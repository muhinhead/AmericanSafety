/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.IDocument;
import com.as.orm.Quote;
import com.as.orm.dbobject.DbObject;
import com.as.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author nick
 */
public class QuotesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 80);
    }

    public QuotesGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, "select quote_id \"Quote Id\","
                + "(select id from document_ids where document_type='quote' and document_id=quote_id) \"Doc.ID\","
                + "DATE_FORMAT(date_in,'%m-%e-%Y %r') \"Date In\","
                + "(select sum(qty*price) from quoteitem where quote_id=quote.quote_id) \"Sum\","
                + "(select customer_name from customer where customer_id=quote.customer_id) \"Customer\","
                + "(select concat(first_name,' ',last_name) from contact where contact_id=quote.contact_id) \"Contact\","
                + "location \"Location\",contractor \"Contractor\","
                + "DATE_FORMAT(created_at,'%m-%e-%Y %r') \"Created\", DATE_FORMAT(updated_at,'%m-%e-%Y %r') \"Updated\" "
                + " from quote", maxWidths, false);
    }

    @Override
    public AbstractAction addAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditQuoteDialog ed = new EditQuoteDialog("New Quote", null);
                if (ed.getOkPressed()) {
                    Quote quote = (Quote) ed.getEditPanel().getDbObject();
                    refresh(quote.getQuoteId());
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
                        IDocument doc = (IDocument) exchanger.loadDbObjectOnID(Quote.class, id);
                        EditQuoteDialog dialog = new EditQuoteDialog("Edit Quote", doc);
                        if (dialog != null && dialog.getOkPressed()) {
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
                        IDocument doc = (IDocument) exchanger.loadDbObjectOnID(Quote.class, id);
                        if(doc!=null && GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this document?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject((DbObject) doc);
                            refresh();
                        }
                    } catch (RemoteException ex) {
                        ASAdmin.logAndShowMessage(ex);
                    }
                }                
            }
        };
    }

}
