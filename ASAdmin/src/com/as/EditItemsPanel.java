package com.as;

import com.as.orm.Item;
import com.as.orm.dbobject.DbObject;
import com.as.util.RecordEditPanel;
import static com.as.util.RecordEditPanel.getBorderPanel;
import static com.as.util.RecordEditPanel.getGridPanel;
import com.as.util.SelectedDateSpinner;
import com.as.util.SelectedNumberSpinner;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JSpinner;

/**
 *
 * @author Nick Mukhin
 */
class EditItemsPanel extends RecordEditPanel {
    private JTextField idField;
    private JTextField itmNumberTF;
    private JTextField itmNameTF;
    private JTextField itmDescrTF;
    private SelectedNumberSpinner lastPriceSP;
    private SelectedDateSpinner createdSP;
    private SelectedDateSpinner updatedSP;

    public EditItemsPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
         String titles[] = new String[]{
            "",
            "ID:",
            "Item Number:",
            "Item Name:",
            "Item Description:",
            "Last Price",
            "Created:",
            "Updated:",
            ""
        };
        JComponent[] edits = new JComponent[]{
            new JPanel(),
            getGridPanel(idField = new JTextField(), 8),
            getGridPanel(itmNumberTF = new JTextField(), 8),
            getBorderPanel(itmNameTF = new JTextField(40)),
            getBorderPanel(itmDescrTF = new JTextField(60)),
            getGridPanel(lastPriceSP = new SelectedNumberSpinner(0.0, 0.0, Double.MAX_VALUE, 0.01),8),
            getBorderPanel(createdSP = new SelectedDateSpinner()),
            getBorderPanel(updatedSP = new SelectedDateSpinner()),
            new JPanel()
        };
        lastPriceSP.setPreferredSize(new Dimension(idField.getPreferredSize().width,
            lastPriceSP.getPreferredSize().height));
        idField.setEnabled(false);
        createdSP.setEnabled(false);
        updatedSP.setEnabled(false);
        createdSP.setEditor(new JSpinner.DateEditor(createdSP, "yyyy-MM-dd hh:mm"));
        updatedSP.setEditor(new JSpinner.DateEditor(createdSP, "yyyy-MM-dd hh:mm"));
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Item itm = (Item) getDbObject();
        if (itm!=null) {
            idField.setText(itm.getItemId().toString());
            itmNumberTF.setText(itm.getItemNumber());
            itmNameTF.setText(itm.getItemName());
            itmDescrTF.setText(itm.getItemDescription());
            lastPriceSP.setValue(itm.getLastPrice());
            createdSP.setValue(itm.getCreatedAt());
            updatedSP.setValue(itm.getUpdatedAt());
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Item itm = (Item) getDbObject();
        if(itm==null) {
            itm = new Item(null);
            itm.setItemId(0);
            isNew = true;
        }
        itm.setItemNumber(itmNumberTF.getText());
        itm.setItemName(itmNameTF.getText());
        itm.setItemDescription(itmDescrTF.getText());
        itm.setLastPrice((Double)lastPriceSP.getValue());
        return saveDbRecord(itm, isNew);
    }
    
}
