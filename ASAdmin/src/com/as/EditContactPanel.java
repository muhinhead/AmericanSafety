package com.as;

import com.as.orm.Contact;
import com.as.orm.dbobject.DbObject;
import com.as.util.EmailFocusAdapter;
import com.as.util.RecordEditPanel;
import static com.as.util.RecordEditPanel.getBorderPanel;
import static com.as.util.RecordEditPanel.getGridPanel;
import com.as.util.SelectedDateSpinner;
import com.as.util.Util;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
class EditContactPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField firstNameTF;
    private JTextField lastNameTF;
    private JTextField emailTF;
    private JComboBox customerCB;
    private SelectedDateSpinner createdSP;
    private SelectedDateSpinner updatedSP;
    private JTextField phoneTF;
    private CustomerLookupAction cbAction;

    public EditContactPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "",
            "ID:",
            "First Name:",
            "Last Name:",
            "Email:",//            "Phone:",
            "Customer:",
            "Created:",
            "Updated:",
            ""
        };
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 8),
            firstNameTF = new JTextField(),
            lastNameTF = new JTextField(),
            getBorderPanel(emailTF = new JTextField(20), new JLabel("Phone:"), phoneTF = new JTextField(20)),
            comboPanelWithLookupBtn(customerCB = new JComboBox(ASAdmin.loadCustomers()),
            cbAction = new CustomerLookupAction(customerCB)),
            getBorderPanel(createdSP = new SelectedDateSpinner()),
            getBorderPanel(updatedSP = new SelectedDateSpinner())
        };
        idField.setEnabled(false);
        createdSP.setEnabled(false);
        updatedSP.setEnabled(false);
        createdSP.setEditor(new JSpinner.DateEditor(createdSP, "yyyy-MM-dd hh:mm"));
        updatedSP.setEditor(new JSpinner.DateEditor(createdSP, "yyyy-MM-dd hh:mm"));
        customerCB.setEnabled(EditContactDialog.customerID == 0);
        cbAction.setEnabled(EditContactDialog.customerID == 0);
        Util.addFocusSelectAllAction(createdSP);
        Util.addFocusSelectAllAction(updatedSP);
        organizePanels(titles, edits, null);
        for (JLabel lbl : labels) {
            if (lbl.getText().equals("Email:")) {
                emailTF.addFocusListener(new EmailFocusAdapter(lbl, emailTF));
                break;
            }
        }
    }

    @Override
    public void loadData() {
        Contact cnt = (Contact) getDbObject();
        if (cnt != null) {
            idField.setText(cnt.getPK_ID().toString());
            firstNameTF.setText(cnt.getFirstName());
            lastNameTF.setText(cnt.getLastName());
            emailTF.setText(cnt.getEmail());
            phoneTF.setText(cnt.getPhone());
            selectComboItem(customerCB, cnt.getCustomerId());
            if (cnt.getCreatedAt() != null) {
                createdSP.setValue(cnt.getCreatedAt());
            }
            if (cnt.getUpdatedAt() != null) {
                updatedSP.setValue(cnt.getUpdatedAt());
            }
        } else {
            selectComboItem(customerCB, EditContactDialog.customerID);
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Contact cnt = (Contact) getDbObject();
        if (cnt == null) {
            cnt = new Contact(null);
            cnt.setContactId(0);
            isNew = true;
        }
        cnt.setFirstName(firstNameTF.getText());
        cnt.setLastName(lastNameTF.getText());
        cnt.setEmail(emailTF.getText());
        cnt.setPhone(phoneTF.getText());
        cnt.setCustomerId(getSelectedCbItem(customerCB));
        return saveDbRecord(cnt, isNew);
    }

}
