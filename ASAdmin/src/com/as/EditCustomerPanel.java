package com.as;

import com.as.orm.Customer;
import com.as.orm.dbobject.DbObject;
import com.as.util.RecordEditPanel;
import static com.as.util.RecordEditPanel.getBorderPanel;
import static com.as.util.RecordEditPanel.getGridPanel;
import com.as.util.SelectedDateSpinner;
import com.as.util.Util;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.rmi.RemoteException;

/**
 *
 * @author Nick Mukhin
 */
class EditCustomerPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField nameTF;
    private JTextField addressTF;
    private SelectedDateSpinner updatedSP;
    private SelectedDateSpinner createdSP;

    public EditCustomerPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "",
            "ID:",
            "Customer Name:",
            "Customer Address:",
            "Created:",
            "Updated:",
            ""
        };
        JComponent[] edits = new JComponent[]{
            new JPanel(),
            getGridPanel(idField = new JTextField(), 8),
            getBorderPanel(nameTF = new JTextField(20)),
            getBorderPanel(addressTF = new JTextField(40)),
            getBorderPanel(createdSP = new SelectedDateSpinner()),
            getBorderPanel(updatedSP = new SelectedDateSpinner()),
            new JPanel()
        };
        idField.setEnabled(false);
        createdSP.setEnabled(false);
        updatedSP.setEnabled(false);
        createdSP.setEditor(new JSpinner.DateEditor(createdSP, "yyyy-MM-dd hh:mm"));
        updatedSP.setEditor(new JSpinner.DateEditor(createdSP, "yyyy-MM-dd hh:mm"));
        Util.addFocusSelectAllAction(createdSP);
        Util.addFocusSelectAllAction(updatedSP);
        organizePanels(titles, edits, null);

    }

    @Override
    public void loadData() {
        Customer cust = (Customer) getDbObject();
        if (cust != null) {
            idField.setText(cust.getPK_ID().toString());
            nameTF.setText(cust.getCustomerName());
            addressTF.setText(cust.getCustomerAddress());
            if (cust.getCreatedAt() != null) {
                createdSP.setValue(cust.getCreatedAt());
            }
            if (cust.getUpdatedAt() != null) {
                updatedSP.setValue(cust.getUpdatedAt());
            }
            try {
                ContactGrid cg = new ContactGrid(ASAdmin.getExchanger(), cust.getCustomerId().intValue());
                cg.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Contacts"));
                add(cg, BorderLayout.SOUTH);
                cg.setPreferredSize(new Dimension(900, 200));
            } catch (RemoteException ex) {
                ASAdmin.logAndShowMessage(ex);
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Customer cust = (Customer) getDbObject();
        if (cust == null) {
            cust = new Customer(null);
            cust.setCustomerId(0);
            isNew = true;
        }
        cust.setCustomerName(nameTF.getText());
        cust.setCustomerAddress(addressTF.getText());
        return saveDbRecord(cust, isNew);
    }

}
