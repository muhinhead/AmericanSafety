package com.as;

import com.as.util.LookupDialog;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
public class CustomerLookupAction extends AbstractAction {

    private final JComboBox custCB;

    public CustomerLookupAction(JComboBox cb) {
        super("...");
        this.custCB = cb;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
       try {
            LookupDialog ld = new LookupDialog("Customer Lookup", custCB,
                    new CustomerGrid(ASAdmin.getExchanger()), 
                    new String[]{"customer_name","customer_address"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
