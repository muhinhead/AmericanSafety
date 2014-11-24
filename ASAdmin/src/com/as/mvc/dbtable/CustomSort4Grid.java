/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.mvc.dbtable;

import com.as.GeneralGridPanel;
import com.as.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author nick
 */
public class CustomSort4Grid extends PopupDialog {

    public static boolean okPressed = false;
    private JButton okBtn;
    private AbstractAction okAction;
    private AbstractAction cancelAction;
    private JButton cancelBtn;
    private JTextField filterField;
    private JCheckBox[] checkBoxes;

    public CustomSort4Grid(Frame owner, DbTableGridPanel gridPanel) {
        super(owner, "Set sort order", gridPanel);
    }

    @Override
    protected void fillContent() {
        super.fillContent();
        getContentPane().add(getMainPanel(), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(okBtn = new JButton(okAction = new AbstractAction("Ok") {

            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralGridPanel grid = (GeneralGridPanel) getObject();
                grid.setSortOrder(filterField.getText());
                if (filterField.getText().isEmpty()) {
                    grid.setSelect(grid.getTableDoc().getOriginalSelectStatement());
                } else {
                    grid.setSelect("select * from ("
                            + grid.getTableDoc().getOriginalSelectStatement() + ") as t order by " + filterField.getText());
                }
                okPressed = true;
                dispose();
            }
        }));
        btnPanel.add(cancelBtn = new JButton(cancelAction = new AbstractAction("Cancel") {

            @Override
            public void actionPerformed(ActionEvent e) {
                okPressed = false;
                dispose();
            }
        }));
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        getContentPane().add(new JPanel(), BorderLayout.WEST);
        getContentPane().add(new JPanel(), BorderLayout.EAST);
    }

    @Override
    public void freeResources() {
        okBtn.removeActionListener(okAction);
        okAction = null;
        cancelBtn.removeActionListener(cancelAction);
        cancelAction = null;
    }

    private JComponent getMainPanel() {
        GeneralGridPanel grid = (GeneralGridPanel) getObject();
        String select = grid.getTableDoc().getOriginalSelectStatement();
        Vector colnames = grid.getTableDoc().getColNames();
        JPanel mainPanel = new JPanel(new GridLayout(colnames.size() + 1, 1, 5, 5));
        String prevSort = grid.getSortOrder();
        checkBoxes = new JCheckBox[colnames.size()];
        for (int i = 0; i < colnames.size(); i++) {
            mainPanel.add(checkBoxes[i] = new JCheckBox((String) colnames.get(i)));
            checkBoxes[i].addActionListener(new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    JCheckBox cb = (JCheckBox) e.getSource();
                    if (cb.isSelected()) {
                        filterField.setText(filterField.getText() + (filterField.getText().isEmpty() ? "" : ",") + "`" + cb.getText() + "`");
                    } else {
                        filterField.setText(filterField.getText().replace("`"+cb.getText()+"`", "").replace(",,", ","));
                    }
                    String txt = filterField.getText();
                    if(txt.endsWith(",")) {
                        filterField.setText(txt.substring(0,txt.length()-1));
                    }
                    if(txt.startsWith(",")) {
                        filterField.setText(txt.substring(1));
                    }
                }
            });
            if (prevSort != null) {
                if (prevSort.indexOf("`" + checkBoxes[i].getText() + "`") >= 0) {
                    checkBoxes[i].setSelected(true);
                }
            }
        }
        mainPanel.add(filterField = new JTextField(50));
        filterField.setText(prevSort==null?"":prevSort);
        filterField.setEditable(false);
        return mainPanel;
    }

}
