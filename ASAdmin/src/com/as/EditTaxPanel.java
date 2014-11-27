/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.Tax;
import com.as.orm.dbobject.DbObject;
import com.as.util.RecordEditPanel;
import static com.as.util.RecordEditPanel.getGridPanel;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author nick
 */
class EditTaxPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField taxTF;

    public EditTaxPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Tax:"
        };
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            taxTF = new JTextField(30)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }
    
    @Override
    public void loadData() {
        Tax tax = (Tax) getDbObject();
        if (tax != null) {
            idField.setText(tax.getTaxId().toString());
            taxTF.setText(tax.getTaxDescription());
        }
    }
    
    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Tax tax = (Tax) getDbObject();
        if (tax == null) {
            tax = new Tax(null);
            tax.setTaxId(0);
            isNew = true;
        }
        tax.setTaxDescription(taxTF.getText());
        return saveDbRecord(tax, isNew);
    }    
}
