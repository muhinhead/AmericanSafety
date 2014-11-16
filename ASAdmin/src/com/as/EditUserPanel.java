package com.as;

import com.as.orm.User;
import com.as.orm.dbobject.DbObject;
import com.as.util.EditPanelWithPhoto;
import com.as.util.EmailFocusAdapter;
import com.as.util.RecordEditPanel;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
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
    private JCheckBox isAdminCB;
    private JTextField firstNameTF;
    private JTextField lastNameTF;
    private JLabel emailLBL;
    private JTextField urlTF;

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
            "Is Admin:",
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
            isAdminCB = new JCheckBox(),
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
            idField.setText(usr.getUserId().toString());
            loginTF.setText(usr.getLogin());
            passwdTF.setText(usr.getPassword());
            passwdPwdF.setText(usr.getPassword());
            emailTF.setText(usr.getEmail());
            urlTF.setText(usr.getUrl());
            firstNameTF.setText(usr.getFirstName());
            lastNameTF.setText(usr.getLastName());
            isAdminCB.setSelected(usr.getAdmin() != null && usr.getAdmin() == 1);
            imageData = (byte[]) usr.getAvatar();
            setImage(imageData);
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
        usr.setAdmin(isAdminCB.isSelected()?1:0);
        usr.setAvatar(imageData);
        return saveDbRecord(usr, isNew);
    }
}
