package com.as;

import com.as.orm.IDocument;
import com.as.orm.Invoice;
import com.as.orm.Order;
import com.as.orm.Quote;
import com.as.orm.dbobject.DbObject;
import com.as.util.ComboItem;
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
import java.util.Vector;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
abstract class EditDocumentPanel extends EditPanelWithPhoto {

    private static final int QTY_INDEX = 3;
    private static final int PRICE_INDEX = 4;
    private static final int TAXED_INDEX = 5;

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
//    private SelectedNumberSpinner taxProcSP;
    private JComboBox poTypeCB;
    private JTextField poValueTF;
    private JComboBox stampsCB;
    private JLabel chevronLbl1;
    private JLabel chevronLbl2;
    private JLabel chevronLbl3;
    private JLabel chevronLbl4;
    private JLabel chevronLbl5;
    private JTextField chewronWellNameTF;
    private JTextField chewronAfeUwvTF;
    private JTextField chewronDateTF;
    private JTextField chewronAprNameTF;
    private JTextField chewronCaiTF;
    private SelectedNumberSpinner discountSP;
    private SelectedNumberSpinner taxBaksSP;
    private SelectedNumberSpinner subTotalSP;
    private JComboBox taxCB;
    private JLabel totalLbl;
    private DocItemsGrid itemsGrid = null;

    private class DiscountListener implements ChangeListener {
        private DocItemsGrid grid;
        
        DiscountListener(DocItemsGrid itemsGrid) {
            this.grid = itemsGrid;
        }
        
        @Override
        public void stateChanged(ChangeEvent e) {
            recalcTotal(grid);
        }
    }
    
    private class TaxCbListener extends AbstractAction {
        private DocItemsGrid grid;

        TaxCbListener(DocItemsGrid itemsGrid) {
            this.grid = itemsGrid;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            recalcTotal(grid);
        }        
    }
    
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
            "ID:",//"Date In:", //"Date Out:"
            "Customer:", //"Location:"
            "Contact:", //"Contractor:"
            "Rig/Tank/Equipment",
            "", //PO //Stamps
            "", "Created by:",
            "Created At:",
            "Updated At:",
            "", "", "", ""
        };
        JComponent[] edits = new JComponent[]{
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
                new JLabel("Stamps:", SwingConstants.RIGHT),
                stampsCB = new JComboBox(ASAdmin.loadStamps())
            }),
            getGridPanel(new JComponent[]{
                getGridPanel(new JComponent[]{
                    poTypeCB = new JComboBox(new DefaultComboBoxModel(ASAdmin.loadPOtypes())),
                    poValueTF = new JTextField()
                }), chevronLbl1 = new JLabel("Well name:", SwingConstants.RIGHT), chewronWellNameTF = new JTextField()
            }),
            getGridPanel(new JComponent[]{new JPanel(),
                chevronLbl2 = new JLabel("AFE/UWV #:", SwingConstants.RIGHT), chewronAfeUwvTF = new JTextField()}),
            getGridPanel(new JComponent[]{createdByCB = new JComboBox(ASAdmin.loadAllLogins()),
                chevronLbl3 = new JLabel("Date:", SwingConstants.RIGHT), chewronDateTF = new JTextField()}),
            getGridPanel(new JComponent[]{getBorderPanel(createdSP = new SelectedDateSpinner()),
                chevronLbl4 = new JLabel("CAI:", SwingConstants.RIGHT), chewronCaiTF = new JTextField()}),
            getGridPanel(new JComponent[]{getBorderPanel(updatedSP = new SelectedDateSpinner()),
                chevronLbl5 = new JLabel("Aprvr Name:", SwingConstants.RIGHT), chewronAprNameTF = new JTextField()}),
            getGridPanel(new JComponent[]{new JPanel(), new JLabel("Discount $", SwingConstants.RIGHT),
                getBorderPanel(discountSP = new SelectedNumberSpinner(0.0, 0.0, 999999.99, 0.01))
            }),
            getGridPanel(new JComponent[]{new JPanel(), new JLabel("Sub Total $", SwingConstants.RIGHT),
                getBorderPanel(subTotalSP = new SelectedNumberSpinner(0.0, 0.0, 999999.99, 0.01))
            }),
            getGridPanel(new JComponent[]{new JPanel(), new JLabel("Tax %", SwingConstants.RIGHT),
                getBorderPanel(
                taxCB = new JComboBox(ASAdmin.loadTaxTypes()), new JLabel("$", SwingConstants.RIGHT),
                taxBaksSP = new SelectedNumberSpinner(0.0, 0.0, 999999.99, 0.01)
                )
            }),
            getGridPanel(new JComponent[]{new JPanel(), new JLabel("Total Amonut $", SwingConstants.RIGHT),
                getBorderPanel(totalLbl = new JLabel("0.00", SwingConstants.RIGHT))
            }),};
        customerCB.setAction(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e
                    ) {
                        Integer cbItm = getSelectedCbItem(customerCB);
                        DefaultComboBoxModel model = new DefaultComboBoxModel(ASAdmin.loadContacsOnCustomer(cbItm));
                        contactCB.setModel(model);
                    }
                }
        );
        stampsCB.setAction(
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e
                    ) {
                        ComboItem ci = (ComboItem) stampsCB.getSelectedItem();
                        boolean isChevron = ci.getValue().equals("Chevron");
                        for (JComponent c : new JComponent[]{
                            chevronLbl1, chevronLbl2, chevronLbl3, chevronLbl4, chevronLbl5,
                            chewronAfeUwvTF, chewronAprNameTF, chewronDateTF,
                            chewronWellNameTF, chewronCaiTF
                        }) {
                            c.setVisible(isChevron);
                        }
                    }
                }
        );
//        discountSP.addChangeListener(new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                recalcTotal(getItemsGrid());
//            }
//        });
//        taxCB.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                recalcTotal(getItemsGrid());
//            }
//        });
        for (JComponent c : new JComponent[]{
            chevronLbl1, chevronLbl2, chevronLbl3, chevronLbl4, chevronLbl5,
            chewronAfeUwvTF, chewronAprNameTF, chewronDateTF,
            chewronWellNameTF, chewronCaiTF
        }) {
            c.setVisible(false);
        }

        customerCB.setSelectedIndex(0);
        idField.setEnabled(false);
        subTotalSP.setEnabled(false);
        taxBaksSP.setEnabled(false);
        totalLbl.setEnabled(false);
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
//            taxProcSP.setValue(doc.getTaxProc());
            discountSP.setValue(doc.getDiscount());
            selectComboItem(taxCB, doc.getTaxId());
            selectComboItem(poTypeCB, doc.getPoTypeId());
            poValueTF.setText(doc.getPoNumber());
            selectComboItem(stampsCB, doc.getStampsId());
            chewronAfeUwvTF.setText(doc.getAfeUww());
            chewronAprNameTF.setText(doc.getAprvrName());
            chewronDateTF.setText(doc.getDateStr());
            chewronWellNameTF.setText(doc.getWellName());
            chewronCaiTF.setText(doc.getCai());
            if (doc.getCreatedAt() != null) {
                createdSP.setValue(doc.getCreatedAt());
            }
            if (doc.getUpdatedAt() != null) {
                updatedSP.setValue(doc.getUpdatedAt());
            }
            imageData = (byte[]) doc.getSignature();
            setImage(imageData);
            try {
                if (doc instanceof Quote) {
                    itemsGrid = new QuoteItemsGrid(ASAdmin.getExchanger(), doc.getPK_ID());
                } else if (doc instanceof Order) {
                    itemsGrid = new OrderItemsGrid(ASAdmin.getExchanger(), doc.getPK_ID());
                } else if (doc instanceof Invoice) {
                    itemsGrid = new InvoiceItemsGrid(ASAdmin.getExchanger(), doc.getPK_ID());
                }
                if (getItemsGrid() != null) {
                    getItemsGrid().setDocPanel(this);
                    getItemsGrid().setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Items"));
                    add(getItemsGrid(), BorderLayout.SOUTH);
                    getItemsGrid().setPreferredSize(new Dimension(900, 200));
                    discountSP.addChangeListener(new DiscountListener(getItemsGrid()));
                    taxCB.setAction(new TaxCbListener(getItemsGrid()));
                }
            } catch (RemoteException ex) {
                ASAdmin.logAndShowMessage(ex);
            }
            recalcTotal(getItemsGrid());
        }
        selectComboItem(createdByCB, ASAdmin.getCurrentUser().getPK_ID());
    }

    public void recalcTotal(DocItemsGrid grid) {
        if (grid != null) {
            Double subTotal = 0.0;
            Double taxProc, sumTax = 0.0;
            String staxProc = taxCB.getSelectedItem().toString();
            int p = staxProc.indexOf("%");
            if (p > 0) {
                staxProc = staxProc.substring(0, p);
            }
            taxProc = Double.parseDouble(staxProc) / 100.0;
            grid.setDocPanel(this);
            Object[] content = (Object[]) grid.getTableDoc().getBody();
            if (content != null) {
                Vector lines = (Vector) content[1];
                for (Object o : lines) {
                    Vector line = (Vector) o;
                    double cost = (Integer.parseInt((String) line.get(QTY_INDEX))
                            * Double.parseDouble((String) line.get(PRICE_INDEX)));
                    subTotal += cost;
                    String taxed = (String) line.get(TAXED_INDEX);
                    if (taxed.equalsIgnoreCase("yes") || taxed.equalsIgnoreCase("true")) {
                        sumTax += (cost * taxProc);
                    }
                }
            }
            subTotalSP.setValue(subTotal);
            taxBaksSP.setValue(sumTax);
            totalLbl.setText("" + Math.rint(100 * (subTotal + sumTax - ((Double) discountSP.getValue()).doubleValue())) / 100);
        }
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
//        doc.setTaxProc((Double) taxProcSP.getValue());
        doc.setTaxId(getSelectedCbItem(taxCB));
        String sproc = taxCB.getSelectedItem().toString();
        int p = sproc.indexOf("%");
        sproc = sproc.substring(0, p);
        doc.setTaxProc(Double.parseDouble(sproc));
        doc.setDiscount((Double) discountSP.getValue());
        doc.setPoTypeId(getSelectedCbItem(poTypeCB));
        doc.setPoNumber(poValueTF.getText());
        doc.setSignature(imageData);
        doc.setCreatedBy(ASAdmin.getCurrentUser().getPK_ID());
        doc.setStampsId(getSelectedCbItem(stampsCB));
        doc.setAfeUww(chewronAfeUwvTF.getText());
        doc.setAprvrName(chewronAprNameTF.getText());
        doc.setWellName(chewronWellNameTF.getText());
        doc.setDateStr(chewronDateTF.getText());
        doc.setCai(chewronCaiTF.getText());
        setDocumentAdditionsBeforeSave(doc);
        return saveDbRecord((DbObject) doc, isNew);
    }

    /**
     * @return the itemsGrid
     */
    public DocItemsGrid getItemsGrid() {
        return itemsGrid;
    }
}
