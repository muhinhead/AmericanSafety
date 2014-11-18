package com.as;

import com.as.orm.Role;
import com.as.orm.User;
import com.as.orm.Usersrole;
import com.as.orm.dbobject.DbObject;
import com.as.util.EditPanelWithPhoto;
import com.as.util.EmailFocusAdapter;
import com.as.util.RecordEditPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
class EditUserPanel extends EditPanelWithPhoto {

    private JTextField idField;
    private JTextField loginTF;
    private CardLayout cl;
    private JPanel passwdCardPanel;
    private JCheckBox showPwdCB;
    private JPasswordField passwdPwdF;
    private String pwdlbl;
    private String tflbl;
    private JTextField passwdTF;
    private JTextField emailTF;
    //private JCheckBox isAdminCB;
    private JTextField firstNameTF;
    private JTextField lastNameTF;
    private JLabel emailLBL;
    private JTextField urlTF;
    private JComboBox departmentCB;
    private Hashtable<Integer, JCheckBox> roleCBtable;

    public EditUserPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected String getImagePanelLabel() {
        return "Avatar";
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "",
            "ID:",
            "First Name:",
            "Last Name:",
            "Login:",
            "Email:",
            "URL:",
            "Password:",
            "Department:",
            ""
        };

        JComponent[] edits = new JComponent[]{
            new JPanel(),
            getGridPanel(idField = new JTextField(), 6),
            firstNameTF = new JTextField(20),
            lastNameTF = new JTextField(20),
            loginTF = new JTextField(20),
            emailTF = new JTextField(20),
            urlTF = new JTextField(20),
            getGridPanel(new JComponent[]{
                passwdCardPanel = new JPanel(cl = new CardLayout()),
                new JLabel("show password", SwingConstants.RIGHT),
                showPwdCB = new JCheckBox()
            }),
            comboPanelWithLookupBtn(departmentCB = new JComboBox(ASAdmin.loadDepartments()),
            new DepartmentLookupAction(departmentCB)),
            new JPanel()
        };
        idField.setEnabled(false);

        passwdCardPanel.add(passwdPwdF = new JPasswordField(), pwdlbl = "JPasswordField");
        passwdCardPanel.add(passwdTF = new JTextField(), tflbl = "JTextField");
        showPwdCB.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (showPwdCB.isSelected()) {
                    passwdTF.setText(new String(passwdPwdF.getPassword()));
                } else {
                    passwdPwdF.setText(passwdTF.getText());
                }
                cl.show(passwdCardPanel, (showPwdCB.isSelected() ? tflbl : pwdlbl));
            }
        });

        organizePanels(titles, edits, null);
        add(getRolesListPanel(), BorderLayout.SOUTH);

        JLabel emailLBL = null;
        for (JLabel lbl : labels) {
            if (lbl.getText().equals("Email:")) {
                emailLBL = lbl;
                break;
            }
        }
        emailTF.addFocusListener(new EmailFocusAdapter(emailLBL, emailTF));
    }

    @Override
    public void loadData() {
        User usr = (User) getDbObject();
        if (usr != null) {
            try {
                idField.setText(usr.getUserId().toString());
                loginTF.setText(usr.getLogin());
                passwdTF.setText(usr.getPassword());
                passwdPwdF.setText(usr.getPassword());
                emailTF.setText(usr.getEmail());
                urlTF.setText(usr.getUrl());
                firstNameTF.setText(usr.getFirstName());
                lastNameTF.setText(usr.getLastName());
                //isAdminCB.setSelected(usr.getAdmin() != null && usr.getAdmin() == 1);
                selectComboItem(departmentCB, usr.getDepartmentId());
                imageData = (byte[]) usr.getAvatar();
                setImage(imageData);
                DbObject[] roles = ASAdmin.getExchanger().getDbObjects(Usersrole.class, "user_id=" + usr.getUserId(), null);
                for (DbObject obj : roles) {
                    Usersrole ur = (Usersrole) obj;
                    JCheckBox cb = roleCBtable.get(ur.getRoleId());
                    if (cb != null) {
                        cb.setSelected(true);
                    }
                }
            } catch (RemoteException ex) {
                ASAdmin.logAndShowMessage(ex);
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        User usr = (User) getDbObject();
        if (usr == null) {
            usr = new User(null);
            usr.setUserId(0);
            isNew = true;
        }
        usr.setLogin(loginTF.getText());
        usr.setEmail(emailTF.getText());
        usr.setUrl(urlTF.getText());
        usr.setPassword(passwdTF.getText());
        usr.setFirstName(firstNameTF.getText());
        usr.setLastName(lastNameTF.getText());
        usr.setDepartmentId(getSelectedCbItem(departmentCB));
        usr.setAvatar(imageData);
        boolean ok = saveDbRecord(usr, isNew);
        Enumeration<Integer> roleKeys = roleCBtable.keys();
        while (roleKeys.hasMoreElements()) {
            Integer roleId = roleKeys.nextElement();
            JCheckBox cb = roleCBtable.get(roleId);
            DbObject[] dbObjArr = ASAdmin.getExchanger().getDbObjects(Usersrole.class,
                    "user_id=" + usr.getUserId() + " and role_id=" + roleId, null);
            if (cb.isSelected()) {
                if (dbObjArr.length==0) {
                    Usersrole ur = new Usersrole(null);
                    ur.setUsersroleId(0);
                    ur.setRoleId(roleId);
                    ur.setUserId(usr.getUserId());
                    ASAdmin.getExchanger().saveDbObject(ur);
                }
            } else {
                if (dbObjArr.length > 0) {
                    ASAdmin.getExchanger().deleteObject(dbObjArr[0]);
                }
            }
        }
        return ok;
    }

    private JPanel getRolesListPanel() {
        JPanel panel = new JPanel();
        try {
            DbObject[] rolesList = ASAdmin.getExchanger().getDbObjects(Role.class, null, "role_name");
            if (rolesList.length > 0) {
                roleCBtable = new Hashtable<Integer, JCheckBox>(rolesList.length);
                panel.setLayout(new FlowLayout());
                for (DbObject o : rolesList) {
                    Role r = (Role) o;
                    JCheckBox cb;
                    panel.add(cb = new JCheckBox(r.getRoleName()));
                    roleCBtable.put(r.getRoleId(), cb);
                }
                panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Roles"));
            }
        } catch (RemoteException ex) {
            ASAdmin.logAndShowMessage(ex);
        }
        return panel;
    }
}
