package com.as;

import com.as.orm.Invoice;
import com.as.orm.Order;
import com.as.orm.Quote;
import com.as.orm.User;
import com.as.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
        super(exchanger, "select * from document", maxWidths, false);
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

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit", new ImageIcon("images/edit.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Del", new ImageIcon("images/delete.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        };
    }
}
