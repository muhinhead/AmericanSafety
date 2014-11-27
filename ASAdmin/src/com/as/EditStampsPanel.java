/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.Stamps;
import com.as.orm.dbobject.DbObject;
import com.as.util.RecordEditPanel;
import static com.as.util.RecordEditPanel.getGridPanel;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author nick
 */
class EditStampsPanel extends RecordEditPanel {
    private JTextField idField;
    private JTextField stampsTF;

    public EditStampsPanel(DbObject dbObject) {
        super(dbObject);
    }
    
    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Stamp description:"
        };
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            stampsTF = new JTextField(30)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }
    
    @Override
    public void loadData() {
        Stamps stamp = (Stamps) getDbObject();
        if (stamp != null) {
            idField.setText(stamp.getStampsId().toString());
            stampsTF.setText(stamp.getStamps());
        }
    }
    
    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Stamps stamp = (Stamps) getDbObject();
        if (stamp == null) {
            stamp = new Stamps(null);
            stamp.setStampsId(0);
            isNew = true;
        }
        stamp.setStamps(stampsTF.getText());
        return saveDbRecord(stamp, isNew);
    }
    
}
