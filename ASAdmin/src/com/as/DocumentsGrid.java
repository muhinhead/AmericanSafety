package com.as;

import com.as.orm.IDocument;
import com.as.orm.Invoice;
import com.as.orm.Order;
import com.as.orm.Quote;
import com.as.orm.User;
import com.as.orm.dbobject.DbObject;
import com.as.remote.IMessageSender;
import com.as.util.EditRecordDialog;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

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
        super(exchanger, "select document_id \"Id\",doc_type \"Document\","
                + "(select customer_name from customer where customer_id=document.customer_id) \"Customer\","
                + "(select concat(first_name,' ',last_name) from contact where contact_id=document.contact_id) \"Contact\","
                + "location \"Location\",contractor \"Contractor\","
                + "created_at, updated_at "
                + " from document", maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add", new ImageIcon("images/add.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridLayout(3, 1));
                ButtonGroup rbGrp = new ButtonGroup();
                JRadioButton quoteRB, orderRB, invoiceRB;
                panel.add(quoteRB = new JRadioButton("Quote", true));
                panel.add(orderRB = new JRadioButton("Order"));
                panel.add(invoiceRB = new JRadioButton("Invoice"));
                rbGrp.add(quoteRB);
                rbGrp.add(orderRB);
                rbGrp.add(invoiceRB);
                new GeneralPanelDialog("Choose document to create", panel, "Ok");
                if (GeneralPanelDialog.okPressed) {
                    if (quoteRB.isSelected()) {
                        EditQuoteDialog ed = new EditQuoteDialog("New Quote", null);
                        if (EditQuoteDialog.okPressed) {
                            Quote quote = (Quote) ed.getEditPanel().getDbObject();
                            refresh(quote.getQuoteId());
                        }
                    } else if (orderRB.isSelected()) {
                        EditOrderDialog ed = new EditOrderDialog("New Order", null);
                        if (EditOrderDialog.okPressed) {
                            Order order = (Order) ed.getEditPanel().getDbObject();
                            refresh(order.getOrderId());
                        }
                    } else if (invoiceRB.isSelected()) {
                        EditInvoiceDialog ed = new EditInvoiceDialog("New Invoice", null);
                        if (EditInvoiceDialog.okPressed) {
                            Invoice invoice = (Invoice) ed.getEditPanel().getDbObject();
                            refresh(invoice.getInvoiceId());
                        }
                    }
                }
            }
        };
    }

    private Class getCurrentDocumentClass() {
        int row = getTableView().getSelectedRow();
        if (row >= 0 && row < getTableView().getRowCount()) {
            String sid = (String) getTableView().getValueAt(row, 1);
            if (sid.equalsIgnoreCase("quote")) {
                return Quote.class;
            } else if (sid.equalsIgnoreCase("order")) {
                return Order.class;
            } else if (sid.equalsIgnoreCase("invoice")) {
                return Invoice.class;
            }
        }
        return null;
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit", new ImageIcon("images/edit.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id != 0) {
                    Class documentClass = getCurrentDocumentClass();
                    if (documentClass != null) {
                        try {
                            EditRecordDialog dialog = null;
                            IDocument doc = (IDocument) exchanger.loadDbObjectOnID(documentClass, id);
                            if (documentClass.equals(Quote.class)) {
                                dialog = new EditQuoteDialog("Edit Quote", doc);
                            } else if (documentClass.equals(Order.class)) {
                                dialog = new EditOrderDialog("Edit Order", doc);
                            }
                            if (documentClass.equals(Invoice.class)) {
                                dialog = new EditInvoiceDialog("Edit Invoice", doc);
                            }
                            if (dialog != null && dialog.getOkPressed()) {
                                refresh();
                            }
                        } catch (RemoteException ex) {
                            ASAdmin.logAndShowMessage(ex);
                        }
                    }
                }
            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Del", new ImageIcon("images/delete.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id != 0) {
                    Class documentClass = getCurrentDocumentClass();
                    try {
                        IDocument doc = (IDocument) exchanger.loadDbObjectOnID(documentClass, id);
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
