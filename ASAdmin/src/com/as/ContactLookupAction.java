package com.as;

import com.as.util.LookupDialog;
import com.as.util.RecordEditPanel;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author nick
 */
class ContactLookupAction extends AbstractAction {
    private final JComboBox contactCB;
    private final JComboBox customerCB;

    public ContactLookupAction(JComboBox contactCB, JComboBox customerCB) {
        super("...");
        this.contactCB = contactCB;
        this.customerCB = customerCB;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       try {
           Integer customerID = RecordEditPanel.getSelectedCbItem(customerCB);
           int customer_id = customerID==null?0:customerID.intValue();
           LookupDialog ld = new LookupDialog("Contacts Lookup", contactCB,
                    new ContactGrid(ASAdmin.getExchanger(), customer_id), 
                    new String[]{"title","last_name","first_name","email","phone"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
    
}
