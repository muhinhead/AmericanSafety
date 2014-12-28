/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as;

import com.as.orm.Settings;
import com.as.orm.Tax;
import com.as.orm.dbobject.DbObject;
import com.as.util.EmailFocusAdapter;
import com.as.util.RecordEditPanel;
import static com.as.util.RecordEditPanel.getGridPanel;
import com.as.util.TexturedPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author nick
 */
class ExportSettingPanel extends RecordEditPanel {

    private JTextField emailTF;
    private JComboBox targetCB;
    private JComboBox scheduleCB;
    private JTextField ftpTF;
    private JTextField ftpLoginTF;
    private JTextField ftpPasswordTF;
    private JCheckBox emailCB;
    private JTextField notifyEmailTF;
    private JCheckBox ftpCB;
//    private JComboBox filesQtyCB;
    private JTextField ftpFolderTF;

    public ExportSettingPanel(DbObject[] obs) {
        super(obs);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "",
            "Target:",
            "E-mail:", // "FTP","Folder","login","password"
            "Schedule:", //hourly/weekly/daily
            //"Orders data in files:", //one/many
            "    E-mail for notification:",
            "",""
        };
        JPanel hdrPanel = new TexturedPanel(new FlowLayout(), "dialog_hdr.png");
        JComponent[] edits = new JComponent[]{
            new JPanel(),
            getBorderPanel(
            getGridPanel(new JComponent[]{
                emailCB = new JCheckBox("E-mail"),
                ftpCB = new JCheckBox("FTP")
            })
            ),
            getBorderPanel(getGridPanel(new JComponent[]{
                emailTF = new JTextField(20),
                getBorderPanel(new JLabel(" FTP address:", SwingConstants.RIGHT), ftpTF = new JTextField()),
                getBorderPanel(new JLabel(" FTP folder:", SwingConstants.RIGHT), ftpFolderTF = new JTextField("./")),
                getBorderPanel(new JLabel(" FTP login:", SwingConstants.RIGHT), ftpLoginTF = new JTextField()),
                getBorderPanel(new JLabel(" FTP password:", SwingConstants.RIGHT), ftpPasswordTF = new JTextField()),
            })),
            getBorderPanel(scheduleCB = new JComboBox(new String[]{"Hourly", "Weekly", "Daily"})),
            //getBorderPanel(filesQtyCB = new JComboBox(new String[]{"one", "many"})),
            getBorderPanel(notifyEmailTF = new JTextField(20)),
            getBorderPanel(getGridPanel(new JComponent[]{new JButton(new AbstractAction("Save") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (save()) {
                        JOptionPane.showMessageDialog(null, "The settings are saved", "Ok!", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }), new JButton(new AbstractAction("Reload") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loadData();
                }
            })})),
            new JPanel()
        };

        organizePanels(titles, edits, null);
        
        JLabel headerLabel = new JLabel("The Scheduled Data Export Settings", SwingConstants.CENTER);
        headerLabel.setFont(headerLabel.getFont().deriveFont(Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        //hdrPanel.setBackground(ASAdmin.HDR_COLOR);
        hdrPanel.add(headerLabel);
        upPanel.add(hdrPanel, BorderLayout.NORTH);
        upPanel.setBorder(BorderFactory.createEtchedBorder());
        for (JLabel lbl : labels) {
            if (lbl.getText().equals("E-mail:")) {
                emailTF.addFocusListener(new EmailFocusAdapter(lbl, emailTF));
            } else if (lbl.getText().trim().equals("E-mail for notification:")) {
                notifyEmailTF.addFocusListener(new EmailFocusAdapter(lbl, notifyEmailTF));
            }
        }
        emailTF.setEnabled(false);
        ftpTF.setEnabled(false);
        ftpLoginTF.setEnabled(false);
        ftpPasswordTF.setEnabled(false);
        emailCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                emailTF.setEnabled(emailCB.isSelected());
            }
        });
        ftpCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ftpTF.setEnabled(ftpCB.isSelected());
                ftpLoginTF.setEnabled(ftpCB.isSelected());
                ftpPasswordTF.setEnabled(ftpCB.isSelected());
            }
        });
    }

    @Override
    public void loadData() {
        DbObject[] records = getParams();
        if (records == null) {
            try {
                records = ASAdmin.getExchanger().getDbObjects(Settings.class, null, null);
            } catch (RemoteException ex) {
                ASAdmin.logAndShowMessage(ex);
            }
        }
        if (records != null) {
            for (DbObject o : records) {
                Settings setting = (Settings) o;
                if (setting.getName().equals("Target")) {
                    if (setting.getValue().indexOf("email") >= 0) {
                        emailCB.setSelected(true);
                    }
                    if (setting.getValue().indexOf("ftp") >= 0) {
                        ftpCB.setSelected(true);
                    }
                } else if (setting.getName().equals("E-mail")) {
                    emailTF.setText(setting.getValue());
                } else if (setting.getName().equals("FTP address")) {
                    ftpTF.setText(setting.getValue());
                } else if (setting.getName().equals("FTP login")) {
                    ftpLoginTF.setText(setting.getValue());
                } else if (setting.getName().equals("FTP password")) {
                    ftpPasswordTF.setText(setting.getValue());
                } else if (setting.getName().equals("Schedule")) {
                    scheduleCB.setSelectedItem(setting.getValue());
//                } else if (setting.getName().equals("Files")) {
//                    filesQtyCB.setSelectedItem(setting.getValue());
                } else if (setting.getName().equals("Folder")) {
                    ftpFolderTF.setText(setting.getValue());
                } else if (setting.getName().equals("Notification")) {
                    notifyEmailTF.setText(setting.getValue());
                }
            }
            emailTF.setEnabled(emailCB.isSelected());
            ftpTF.setEnabled(ftpCB.isSelected());
            ftpLoginTF.setEnabled(ftpCB.isSelected());
            ftpPasswordTF.setEnabled(ftpCB.isSelected());
            ftpFolderTF.setEnabled(ftpCB.isSelected());
        }

    }

    @Override
    public boolean save() {
        return saveSetting("Target", (emailCB.isSelected() ? "email " : "") + (ftpCB.isSelected() ? "ftp" : ""))
                && saveSetting("E-mail", emailTF.getText())
                && saveSetting("FTP address", ftpTF.getText())
                && saveSetting("FTP login", ftpLoginTF.getText())
                && saveSetting("FTP password", ftpPasswordTF.getText())
                && saveSetting("Schedule", (String) scheduleCB.getSelectedItem())
                //&& saveSetting("Files", (String) filesQtyCB.getSelectedItem())
                && saveSetting("Folder", ftpFolderTF.getText())
                && saveSetting("Notification", notifyEmailTF.getText());
    }

    private boolean saveSetting(String name, String value) {
        boolean ok = false;
        try {
            DbObject[] obs = ASAdmin.getExchanger().getDbObjects(Settings.class, "name='" + name + "'", null);
            boolean isNew = obs.length == 0;
            Settings set;
            if (isNew) {
                set = new Settings(null);
            } else {
                set = (Settings) obs[0];
            }
            if (isNew) {
                set.setSettingsId(0);
            }
            set.setName(name);
            set.setValue(value);
            ok = saveDbRecord(set, isNew);
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
        return ok;
    }

}
