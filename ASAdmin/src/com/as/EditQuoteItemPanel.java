package com.as;

import com.as.orm.Item;
import com.as.orm.Quoteitem;
import com.as.orm.dbobject.DbObject;
import com.as.util.RecordEditPanel;
import static com.as.util.RecordEditPanel.comboPanelWithLookupBtn;
import static com.as.util.RecordEditPanel.getBorderPanel;
import static com.as.util.RecordEditPanel.getGridPanel;
import com.as.util.SelectedDateSpinner;
import com.as.util.SelectedNumberSpinner;
import com.as.util.Util;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick Mukhin
 */
class EditQuoteItemPanel extends RecordEditPanel {

    private JTextField idField;
    private JComboBox itemCB;
    private ItemLookupAction cbAction;
    private SelectedNumberSpinner priceSP;
    private JCheckBox taxCB;
    private SelectedDateSpinner createdSP;
    private SelectedDateSpinner updatedSP;
    private SelectedNumberSpinner qtyCB;

    public EditQuoteItemPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Item Name:",
            "Price:", //"Tax:",
            //            "Qty:",
            "Created:",
            "Updated:"
        };
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 6),
            comboPanelWithLookupBtn(itemCB = new JComboBox(ASAdmin.loadItems()),
            cbAction = new ItemLookupAction(itemCB)),
            getBorderPanel(
                priceSP = new SelectedNumberSpinner(0.0, 0.0, 9999999.0, 0.1),
                getGridPanel(new JComponent[]{
                    taxCB = new JCheckBox("tax"),
                    new JLabel("Qty:",SwingConstants.RIGHT)
                }),
                getGridPanel(qtyCB = new SelectedNumberSpinner(0, 0, Integer.MAX_VALUE, 1),2)
            ),
            getBorderPanel(createdSP = new SelectedDateSpinner()),
            getBorderPanel(updatedSP = new SelectedDateSpinner())
        };
        idField.setEnabled(false);
        createdSP.setEditor(new JSpinner.DateEditor(createdSP, "yyyy-MM-dd hh:mm"));
        updatedSP.setEditor(new JSpinner.DateEditor(createdSP, "yyyy-MM-dd hh:mm"));
        Util.addFocusSelectAllAction(createdSP);
        Util.addFocusSelectAllAction(updatedSP);
        itemCB.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer itemID = getSelectedCbItem(itemCB);
                try {
                    Item itm = (Item) ASAdmin.getExchanger().loadDbObjectOnID(Item.class, itemID);
                    if (itm != null) {
                        priceSP.setValue(itm.getLastPrice());
                    }
                } catch (RemoteException ex) {
                    ASAdmin.logAndShowMessage(ex);
                }
            }
        });
        itemCB.setSelectedIndex(0);
        createdSP.setEnabled(false);
        updatedSP.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Quoteitem qi = (Quoteitem) getDbObject();
        if (qi != null) {
            idField.setText(qi.getPK_ID().toString());
            selectComboItem(itemCB, qi.getItemId());
            priceSP.setValue(qi.getPrice());
            qtyCB.setValue(qi.getQty());
            taxCB.setSelected(qi.getTax() != null && qi.getTax() == 1);
            createdSP.setValue(qi.getCreatedAt());
            updatedSP.setValue(qi.getUpdatedAt());
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Quoteitem qi = (Quoteitem) getDbObject();
        if (qi == null) {
            qi = new Quoteitem(null);
            qi.setPK_ID(0);
            qi.setQuoteId(EditQuoteItemDialog.quoteID);
            isNew = true;
        }
        Integer itemID;
        qi.setItemId(itemID = getSelectedCbItem(itemCB));
        Item itm = (Item) ASAdmin.getExchanger().loadDbObjectOnID(Item.class, itemID);
        qi.setPrice((Double) priceSP.getValue());
        qi.setTax(taxCB.isSelected() ? 1 : 0);
        qi.setQty((Integer) qtyCB.getValue());
        boolean ok = saveDbRecord(qi, isNew);
        if (ok) {
            itm.setLastPrice(qi.getPrice());
            ASAdmin.getExchanger().saveDbObject(itm);
        }
        return ok;
    }
}
