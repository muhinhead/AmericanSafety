/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.Po;
import com.as.orm.dbobject.DbObject;
import com.as.util.RecordEditPanel;
import static com.as.util.RecordEditPanel.getGridPanel;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author nick
 */
public class EditPoPanel extends RecordEditPanel {
    private JTextField idField;
    private JTextField poNameTF;

    public EditPoPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "PO type:"
        };
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            poNameTF = new JTextField(30)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }
    
    @Override
    public void loadData() {
        Po po = (Po) getDbObject();
        if (po != null) {
            idField.setText(po.getPoId().toString());
            poNameTF.setText(po.getPoDescription());
        }
    }
    
    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Po po = (Po) getDbObject();
        if (po == null) {
            po = new Po(null);
            po.setPoId(0);
            isNew = true;
        }
        po.setPoDescription(poNameTF.getText());
        return saveDbRecord(po, isNew);
    }
    
}
