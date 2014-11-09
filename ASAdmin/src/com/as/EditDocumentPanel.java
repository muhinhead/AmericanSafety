package com.as;

import com.as.orm.IDocument;
import com.as.orm.Invoice;
import com.as.orm.Order;
import com.as.orm.Quote;
import com.as.orm.dbobject.DbObject;
import com.as.util.EditPanelWithPhoto;
import static com.as.util.RecordEditPanel.comboPanelWithLookupBtn;
import static com.as.util.RecordEditPanel.getGridPanel;
import static com.as.util.RecordEditPanel.selectComboItem;
import com.as.util.SelectedDateSpinner;
import com.as.util.SelectedNumberSpinner;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
abstract class EditDocumentPanel extends EditPanelWithPhoto {

    protected JTextField idField;
    protected SelectedDateSpinner dateInSP;
    protected SelectedDateSpinner dateOutSP;
    protected SelectedDateSpinner createdSP;
    protected SelectedDateSpinner updatedSP;
    protected JComboBox customerCB;
    private AbstractAction cbAction;
    protected JTextField locationTF;
    protected JComboBox contactCB;
    private AbstractAction cbAction1;
    protected JTextField contractorTF;
    protected JComboBox rigCB;
    protected JLabel dateOutLbl;
    private JComboBox createdByCB;
    private SelectedNumberSpinner taxProcSP;
    private JComboBox poTypeCB;
    private JTextField poValueTF;

    public EditDocumentPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected String getImagePanelLabel() {
        return "Signature";
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "",
            "ID:",//"Date In:", //"Date Out:"
            "Customer:", //"Location:"
            "Contact:", //"Contractor:"
            "Rig/Tank/Equipment",
            "", //PO
            "Created by:"
        //            "Created:"   //"Updated:"

        };
        JComponent[] edits = new JComponent[]{
            new JPanel(),
            getGridPanel(new JComponent[]{idField = new JTextField(),
                new JLabel("Date In:", SwingConstants.RIGHT),
                dateInSP = new SelectedDateSpinner(),
                dateOutLbl = new JLabel("Date Out:", SwingConstants.RIGHT),
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
            }),
            getGridPanel(new JComponent[]{
                rigCB = new JComboBox(ASAdmin.rigTankEquipment()),
                new JLabel("Tax %:", SwingConstants.RIGHT),
                getGridPanel(taxProcSP = new SelectedNumberSpinner(0.0, 0.0, 100.0, 0.5), 3)
            }),
            getGridPanel(
            getGridPanel(new JComponent[]{
                poTypeCB = new JComboBox(new DefaultComboBoxModel(ASAdmin.loadPOtypes())),
                poValueTF = new JTextField()
            }), 3
            ),
            getGridPanel(new JComponent[]{
                createdByCB = new JComboBox(new DefaultComboBoxModel(ASAdmin.loadAllLogins())),
                getBorderPanel(new JLabel("Created at:", SwingConstants.RIGHT), createdSP = new SelectedDateSpinner()),
                getBorderPanel(new JLabel("Updated at:", SwingConstants.RIGHT), updatedSP = new SelectedDateSpinner())
            })
        };
        customerCB.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer cbItm = getSelectedCbItem(customerCB);
                DefaultComboBoxModel model = new DefaultComboBoxModel(ASAdmin.loadContacsOnCustomer(cbItm));
                contactCB.setModel(model);
            }
        });
        customerCB.setSelectedIndex(0);
        idField.setEnabled(false);
        dateInSP.setEditor(new JSpinner.DateEditor(dateInSP, "yyyy-MM-dd hh:mm"));
        dateOutSP.setEditor(new JSpinner.DateEditor(dateOutSP, "yyyy-MM-dd hh:mm"));
        createdSP.setEditor(new JSpinner.DateEditor(createdSP, "yyyy-MM-dd hh:mm"));
        updatedSP.setEditor(new JSpinner.DateEditor(createdSP, "yyyy-MM-dd hh:mm"));
        createdSP.setEnabled(false);
        updatedSP.setEnabled(false);
        rigCB.setEditable(true);
        createdByCB.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        IDocument doc = (IDocument) getDbObject();
        if (doc != null) {
            idField.setText(doc.getPK_ID().toString());
            if (doc.getDateIn() != null) {
                dateInSP.setValue(doc.getDateIn());
            }
            selectComboItem(customerCB, doc.getCustomerId());
            locationTF.setText(doc.getLocation());
            selectComboItem(contactCB, doc.getContactId());
            contractorTF.setText(doc.getContractor());
            rigCB.setSelectedItem(doc.getRigTankEq());
            taxProcSP.setValue(doc.getTaxProc());
            selectComboItem(poTypeCB, doc.getPoTypeId());
            poValueTF.setText(doc.getPoNumber());
            if (doc.getCreatedAt() != null) {
                createdSP.setValue(doc.getCreatedAt());
            }
            if (doc.getUpdatedAt() != null) {
                updatedSP.setValue(doc.getUpdatedAt());
            }
            imageData = (byte[]) doc.getSignature();
            setImage(imageData);
            GeneralGridPanel itemsGrid = null;
            try {
                if (doc instanceof Quote) {
                    itemsGrid = new QuoteItemsGrid(ASAdmin.getExchanger(), doc.getPK_ID());
                } else if (doc instanceof Order) {
                    itemsGrid = new OrderItemsGrid(ASAdmin.getExchanger(), doc.getPK_ID());
                } else if (doc instanceof Invoice) {
                    itemsGrid = new InvoiceItemsGrid(ASAdmin.getExchanger(), doc.getPK_ID());
                }
                if (itemsGrid != null) {
                    itemsGrid.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Items"));
                    add(itemsGrid, BorderLayout.SOUTH);
                    itemsGrid.setPreferredSize(new Dimension(900, 200));
                }
            } catch (RemoteException ex) {
                ASAdmin.logAndShowMessage(ex);
            }

        }
        selectComboItem(createdByCB, ASAdmin.getCurrentUser().getPK_ID());
    }

    public abstract IDocument createDocument();

    protected void setDocumentAdditionsBeforeSave(IDocument doc) throws Exception {
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        IDocument doc = (IDocument) getDbObject();
        if (doc == null) {
            doc = createDocument();
            doc.setPK_ID(0);
            isNew = true;
        }
        java.util.Date ud = (java.util.Date) dateInSP.getValue();
        doc.setDateIn(new java.sql.Date(ud.getTime()));
        doc.setCustomerId(getSelectedCbItem(customerCB));
        doc.setLocation(locationTF.getText());
        doc.setContactId(getSelectedCbItem(contactCB));
        doc.setContractor(contractorTF.getText());
        doc.setRigTankEq((String) rigCB.getSelectedItem());
        doc.setTaxProc((Double) taxProcSP.getValue());
        doc.setPoTypeId(getSelectedCbItem(poTypeCB));
        doc.setPoNumber(poValueTF.getText());
        doc.setSignature(imageData);
        doc.setCreatedBy(ASAdmin.getCurrentUser().getPK_ID());
        setDocumentAdditionsBeforeSave(doc);
        return saveDbRecord((DbObject) doc, isNew);
    }
}
