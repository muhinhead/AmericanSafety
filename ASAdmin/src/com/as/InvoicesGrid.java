/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.IDocument;
import com.as.orm.Invoice;
import com.as.orm.Invoiceitem;
import com.as.orm.dbobject.DbObject;
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
public class InvoicesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public InvoicesGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, "select invoice_id \"Id\","
                + "DATE_FORMAT(date_in,'%m-%e-%Y %r') \"Date In\","
                + "DATE_FORMAT(date_out,'%m-%e-%Y %r') \"Date Out\","
                + "(select sum(qty*price) from invoiceitem where invoice_id=invoice.invoice_id) \"Sum\","
                + "(select customer_name from customer where customer_id=invoice.customer_id) \"Customer\","
                + "(select concat(first_name,' ',last_name) from contact where contact_id=invoice.contact_id) \"Contact\","
                + "location \"Location\",contractor \"Contractor\","
                + "DATE_FORMAT(created_at,'%m-%e-%Y %r') \"Created\", DATE_FORMAT(updated_at,'%m-%e-%Y %r') \"Updated\" "
                + " from invoice", maxWidths, false);
    }

    
    @Override
    public AbstractAction addAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditInvoiceDialog ed = new EditInvoiceDialog("New Invoice", null);
                if (EditQuoteDialog.okPressed) {
                    Invoice invoice = (Invoice) ed.getEditPanel().getDbObject();
                    refresh(invoice.getInvoiceId());
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
                        Invoice doc = (Invoice) exchanger.loadDbObjectOnID(Invoice.class, id);
                        EditInvoiceDialog dialog = new EditInvoiceDialog("Edit Invoice", doc);
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
                        IDocument doc = (IDocument) exchanger.loadDbObjectOnID(Invoice.class, id);
                        if(doc!=null && GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this document?") == JOptionPane.YES_OPTION) {
                            Integer invoiceID = doc.getPK_ID();
                            for (DbObject itm : exchanger.getDbObjects(Invoiceitem.class, 
                                    "invoice_id="+invoiceID, null)) {
                                exchanger.deleteObject(itm);
                            }                            
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
