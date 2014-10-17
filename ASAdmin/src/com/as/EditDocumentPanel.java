package com.as;

import com.as.orm.dbobject.DbObject;
import com.as.util.RecordEditPanel;
import static com.as.util.RecordEditPanel.comboPanelWithLookupBtn;
import static com.as.util.RecordEditPanel.getGridPanel;
import com.as.util.SelectedDateSpinner;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
abstract class EditDocumentPanel extends RecordEditPanel {

    protected JTextField idField;
    private SelectedDateSpinner dateInSP;
    private SelectedDateSpinner dateOutSP;
    private JComboBox customerCB;
    private AbstractAction cbAction;
    private JTextField locationTF;
    private JComboBox contactCB;
    private AbstractAction cbAction1;
    private JTextField contractorTF;

    public EditDocumentPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "",
            "ID:",//"Date In:", //"Date Out:"
            "Customer:", //"Location:"
            "Contact:", //"Contractor:"
        //            "Rig/Tank/Equipment",
        //            "Created:"   //"Updated:"
        };
        JComponent[] edits = new JComponent[]{
            new JPanel(),
            getGridPanel(new JComponent[]{idField = new JTextField(),
                new JLabel("Date In:", SwingConstants.RIGHT),
                dateInSP = new SelectedDateSpinner(),
                new JLabel("Date Out:", SwingConstants.RIGHT),
                dateOutSP = new SelectedDateSpinner()
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(customerCB = new JComboBox(ASAdmin.loadCustomers()),
                cbAction = new CustomerLookupAction(customerCB)),
                new JLabel("Location:", SwingConstants.RIGHT),
                locationTF = new JTextField(20)
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(contactCB = new JComboBox(),
                cbAction1 = new ContactLookupAction(contactCB, customerCB)),
                new JLabel("Contractor:", SwingConstants.RIGHT),
                contractorTF = new JTextField(20)
            })
        };

        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        
    }

    @Override
    public boolean save() throws Exception {
        //TOOD
        return true;
    }
}
