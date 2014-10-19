package com.as;

import com.as.orm.Item;
import com.as.orm.Orderitem;
import com.as.orm.dbobject.DbObject;
import com.as.util.RecordEditPanel;
import static com.as.util.RecordEditPanel.comboPanelWithLookupBtn;
import static com.as.util.RecordEditPanel.getBorderPanel;
import static com.as.util.RecordEditPanel.getGridPanel;
import static com.as.util.RecordEditPanel.getSelectedCbItem;
import static com.as.util.RecordEditPanel.selectComboItem;
import com.as.util.SelectedDateSpinner;
import com.as.util.SelectedNumberSpinner;
import com.as.util.Util;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
class EditOrderItemPanel extends RecordEditPanel {

    private JTextField idField;
    private JComboBox itemCB;
    private ItemLookupAction cbAction;
    private SelectedNumberSpinner priceSP;
    private JCheckBox taxCB;
    private SelectedDateSpinner createdSP;
    private SelectedDateSpinner updatedSP;
    private SelectedNumberSpinner qtyCB;

    public EditOrderItemPanel(DbObject dbObject) {
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
                new JLabel("Qty:", SwingConstants.RIGHT)
            }),
            getGridPanel(qtyCB = new SelectedNumberSpinner(0, 0, Integer.MAX_VALUE, 1), 2)
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
        Orderitem oi = (Orderitem) getDbObject();
        if (oi != null) {
            idField.setText(oi.getPK_ID().toString());
            selectComboItem(itemCB, oi.getItemId());
            priceSP.setValue(oi.getPrice());
            qtyCB.setValue(oi.getQty());
            taxCB.setSelected(oi.getTax() != null && oi.getTax() == 1);
            if (oi.getCreatedAt() != null) {
                createdSP.setValue(oi.getCreatedAt());
            }
            if (oi.getUpdatedAt() != null) {
                updatedSP.setValue(oi.getUpdatedAt());
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Orderitem oi = (Orderitem) getDbObject();
        if (oi == null) {
            oi = new Orderitem(null);
            oi.setPK_ID(0);
            oi.setOrderId(EditOrderItemDialog.orderID);
            isNew = true;
        }
        Integer itemID;
        oi.setItemId(itemID = getSelectedCbItem(itemCB));
        Item itm = (Item) ASAdmin.getExchanger().loadDbObjectOnID(Item.class, itemID);
        oi.setPrice((Double) priceSP.getValue());
        oi.setTax(taxCB.isSelected() ? 1 : 0);
        oi.setQty((Integer) qtyCB.getValue());
        boolean ok = saveDbRecord(oi, isNew);
//        if (ok) {
//            itm.setLastPrice(oi.getPrice());
//            ASAdmin.getExchanger().saveDbObject(itm);
//        }
        return ok;
    }
}
