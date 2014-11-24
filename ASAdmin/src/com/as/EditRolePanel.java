/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.Department;
import com.as.orm.Role;
import com.as.orm.dbobject.DbObject;
import com.as.util.RecordEditPanel;
import static com.as.util.RecordEditPanel.getGridPanel;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author nick
 */
class EditRolePanel extends RecordEditPanel {
    private JTextField idField;
    private JTextField roleNameTF;

    public EditRolePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Role Name:"
        };
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            roleNameTF = new JTextField(30)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }
    
    @Override
    public void loadData() {
        Role role = (Role) getDbObject();
        if (role != null) {
            idField.setText(role.getRoleId().toString());
            roleNameTF.setText(role.getRoleName());
        }
    }
    
    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Role role = (Role) getDbObject();
        if (role == null) {
            role = new Role(null);
            role.setRoleId(0);
            isNew = true;
        }
        role.setRoleName(roleNameTF.getText());
        return saveDbRecord(role, isNew);
    }
    
}
