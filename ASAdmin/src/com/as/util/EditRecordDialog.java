package com.as.util;

//import com.mmi.admin.ASAdmin;
import com.as.ASAdmin;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Nick Mukhin
 */
public abstract class EditRecordDialog extends PopupDialog {

//    protected JButton applyButton;
//    private AbstractAction applyAction;
    protected JButton saveButton;
    private AbstractAction saveAction;
    protected JButton cancelButton;
    private AbstractAction cancelAction;
    private RecordEditPanel editPanel;

    public EditRecordDialog(String title, Object obj) {
        super(null, title, obj);
    }

    protected void fillContent(RecordEditPanel editPanel) {
        super.fillContent();
        setOkPressed(false);
        ASAdmin.setWindowIcon(this, "cust_sol16.png");
        this.editPanel = editPanel;
        this.editPanel.setOwnerDialog(this);
        JPanel btnPanel = new JPanel();

        btnPanel.add(saveButton = new JButton(saveAction = getSaveAction()));
        btnPanel.add(cancelButton = new JButton(cancelAction = getCancelAction()));

        saveButton.setToolTipText("Save changes and close dialog");
        cancelButton.setToolTipText("Discard changes and close dialog");

        JPanel innerPanel = new JPanel(new BorderLayout());

        innerPanel.add(new JPanel(), BorderLayout.WEST);
        innerPanel.add(new JPanel(), BorderLayout.EAST);
        innerPanel.add(editPanel, BorderLayout.CENTER);

        JPanel aroundButton = new JPanel(new BorderLayout());
        aroundButton.add(btnPanel, BorderLayout.EAST);
        innerPanel.add(aroundButton, BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(innerPanel), BorderLayout.CENTER);
        getRootPane().setDefaultButton(saveButton);
        
    }

    @Override
    public void freeResources() {
        saveButton.removeActionListener(saveAction);
        cancelButton.removeActionListener(cancelAction);
        saveAction = null;
        cancelAction = null;
    }

    protected AbstractAction getSaveAction() {
        return new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getEditPanel().save()) {
                        setOkPressed(true);
                        dispose();
                    }
                } catch (Exception ex) {
                    ASAdmin.logAndShowMessage(ex);
                }
            }
        };
    }

    protected abstract void setOkPressed(boolean b);
    public abstract boolean getOkPressed();

    protected AbstractAction getCancelAction() {
        return new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
    }

    /**
     * @return the editPanel
     */
    public RecordEditPanel getEditPanel() {
        return editPanel;
    }
}
