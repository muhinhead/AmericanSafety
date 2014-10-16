package com.as;

import com.as.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Nick Mukhin
 */
public class GeneralPanelDialog extends PopupDialog {

    public static boolean okPressed = false;
    protected JButton saveButton;
    private AbstractAction saveAction;
    protected JButton cancelButton;
    private AbstractAction cancelAction;

    public GeneralPanelDialog(String title, JPanel genPanel, String okLabel) {
        super(null, title, new Object[]{genPanel,okLabel});
    }

    @Override
    protected void fillContent() {
        super.fillContent();
        ASAdmin.setWindowIcon(this, "cust_sol16.png");
        JPanel btnPanel = new JPanel();

        btnPanel.add(saveButton = new JButton(saveAction = getSaveAction()));
        btnPanel.add(cancelButton = new JButton(cancelAction = getCancelAction()));

        saveButton.setToolTipText("Save changes and close dialog");
        cancelButton.setToolTipText("Discard changes and close dialog");

        JPanel innerPanel = new JPanel(new BorderLayout());

        innerPanel.add(new JPanel(), BorderLayout.WEST);
        innerPanel.add(new JPanel(), BorderLayout.SOUTH);
        Object[] params = (Object[]) getObject();
        innerPanel.add((JPanel)params[0], BorderLayout.CENTER);

        JPanel aroundButton = new JPanel(new BorderLayout());
        aroundButton.add(btnPanel, BorderLayout.SOUTH);
        innerPanel.add(aroundButton, BorderLayout.EAST);//BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(innerPanel), BorderLayout.CENTER);
        getRootPane().setDefaultButton(saveButton);
        //TODO
    }

    protected AbstractAction getSaveAction() {
        Object[] params = (Object[]) getObject();
        return new AbstractAction((String)params[1]) {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    setOkPressed(true);
                    dispose();
                } catch (Exception ex) {
                    ASAdmin.logAndShowMessage(ex);
                }
            }
        };
    }

    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    protected AbstractAction getCancelAction() {
        return new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
    }

    @Override
    public void freeResources() {
        saveButton.removeActionListener(saveAction);
        cancelButton.removeActionListener(cancelAction);
        saveAction = null;
        cancelAction = null;
    }
}
