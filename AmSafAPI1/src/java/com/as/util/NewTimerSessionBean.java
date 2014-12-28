/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import com.as.Contact;
import com.as.Customer;
import com.as.Document;
import com.as.DocumentPK;
import com.as.Invoice;
import com.as.Invoiceitem;
import com.as.Item;
import com.as.Order1;
import com.as.Orderitem;
import com.as.Quote;
import com.as.Quoteitem;
import com.as.Settings;
import com.as.User;
import com.as.service.AbstractFacade;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nick
 */
@Stateless
public class NewTimerSessionBean {

    private static final String dirname = "Export2QuickBooks";
    private static final int BUFFER = 2048;
    private static long timeDiff = 1000 * 3600; //default - hourly 
    private Date lastModified = new Date(0);
    private String notifyEmail = null;
    private boolean sendEmail = false;
    private boolean sendFtp = false;
    private boolean busy = false;
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ArrayList<File> filesToExport = new ArrayList<File>();

    @PersistenceContext(unitName = "AmSafAPI1PU")
    private EntityManager em;
    private String email;
    private File zipfile;
    private String ftpUrl;
    private String ftpLogin;
    private String ftpPassword;
    private int itmCount;
    private String ftpFolder = "./";

    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "0", persistent = false)
    public void myTimer() {
        try {
            businessMethod();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void businessMethod() {
        if (!busy && em != null && em.isOpen()) {
            sendEmail = sendFtp = false;
            zipfile = null;
            ftpUrl = null;
            ftpLogin = null;
            ftpPassword = null;
            Date now = new Date();
            boolean ftpUploaded = false;
            boolean emailSent = false;
            Collection<Settings> settings = em.createNamedQuery("Settings.findAll", Settings.class).getResultList();
            for (Settings s : settings) {
                em.refresh(s);
                if (s.getName().equals("Schedule")) {
                    if (s.getValue().equals("Daily")) {
                        timeDiff = 1000 * 3600 * 24;
                    } else if (s.getValue().equals("Weekly")) {
                        timeDiff = 1000 * 3600 * 24 * 7;
                    }
                } else if (s.getName().equals("Target")) {
                    sendEmail = s.getValue().indexOf("email") >= 0;
                    sendFtp = s.getValue().indexOf("ftp") >= 0;
                } else if (s.getName().equals("E-mail")) {
                    email = s.getValue();
                } else if (s.getName().equals("Notification")) {
                    notifyEmail = s.getValue();
                } else if (s.getName().equals("FTP address")) {
                    ftpUrl = s.getValue();
                } else if (s.getName().equals("FTP login")) {
                    ftpLogin = s.getValue();
                } else if (s.getName().equals("FTP password")) {
                    ftpPassword = s.getValue();
                } else if (s.getName().equals("Folder")) {
                    ftpFolder = s.getValue();
                }
            }
            if (!sendEmail && !sendFtp) {
                System.out.println("--- No export settings found, nothing to do! ---");
                return;
            }
            File directory = new File(dirname);
            if (!directory.exists()) {
                directory.mkdir();
            }
            lastModified = new Date(directory.lastModified());
            if ((sendEmail || sendFtp) && now.getTime() - lastModified.getTime() >= timeDiff) {
                try {
                    busy = true;
                    String notification = exportData(directory, now);
                    if (sendFtp && ftpUrl != null && ftpLogin != null && ftpPassword != null && filesToExport.size() > 0) {
                        //System.out.println("!!FTP:"+ftpUrl+" login:"+ftpLogin+" password:"+ftpPassword);
                        ftpUploaded = AbstractFacade.upload2FTP(ftpUrl, ftpLogin, ftpPassword, ftpFolder, zipfile);
                    } else {
                        ftpUploaded = true;
                    }
                    if (sendEmail && filesToExport.size() > 0) {
                        emailSent = AbstractFacade.sendEmail(email, "The AmericanSafety API data export",
                                notification + "The content in attachment", zipfile);
                    } else {
                        emailSent = true;
                    }
                    if (notifyEmail != null && filesToExport.size() > 0) {
                        AbstractFacade.sendEmail(notifyEmail, "The AmericanSafety API data export notification",
                                notification + ((sendEmail && filesToExport.size() > 0) ? "File " + zipfile.getName() + " sent to " + email : "")
                                + ((sendFtp && ftpUrl != null && ftpLogin != null && ftpPassword != null && ftpUploaded)
                                        ? "\nFile " + zipfile.getName() + " uploaded to ftp://" + ftpUrl : ""), null);
                    }
                    if (filesToExport.size() > 0) {
                        System.out.println(notification);
                    }
                    filesToExport.clear();
                    if (ftpUploaded && emailSent && zipfile != null && zipfile.exists()) {
                        zipfile.delete();
                    }
                } catch (Exception e) {
                    directory.setLastModified(lastModified.getTime()); //restore timestamp to try export again
                    notifyError(e);
                } finally {
                    busy = false;
                }
            }
        }
    }

    private static String dq(String s) {
        return (s == null ? "" : s.replace("\"", "\"\""));
    }

    private String exportData(File dir, Date now) throws IOException {
        StringBuilder sb = new StringBuilder();
        String lineSep = System.getProperty("line.separator");
        //export users
        exportUsers(dir, now, lineSep, sb);
        exportItems(dir, now, lineSep, sb);
        exportCustomers(dir, now, lineSep, sb);
        exportContacts(dir, now, lineSep, sb);
        exportDocuments(dir, now, lineSep, sb);

        if (filesToExport.size() > 0) {
            System.out.println("==== Data export started at " + new Date().toString());
            zipFiles(dir, getFileName("", now).replace("-.csv", ".zip"));            
            return "Data exported at " + new Date().toString() + lineSep + sb.toString() + lineSep;
        }
        return "Nothing to export at " + new Date().toString() + lineSep;
    }

    private void zipFiles(File dir, String zipFileName) throws FileNotFoundException, IOException {
        BufferedInputStream origin = null;
        FileOutputStream dest = new FileOutputStream(zipfile = new File(dir, zipFileName));
        ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
        //out.setMethod(ZipOutputStream.DEFLATED);
        byte data[] = new byte[BUFFER];

        for (File csvFile : filesToExport) {
            if (csvFile.exists()) {
                origin = new BufferedInputStream(new FileInputStream(csvFile), BUFFER);
                ZipEntry entry = new ZipEntry(csvFile.getName());
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
                origin.close();
                csvFile.delete();
            }
        }
        out.close();
    }

    private void exportDocuments(File dir, Date now, String lineSep, StringBuilder sb) throws IOException {
        Collection<Document> docs = em.createNamedQuery("Document.findLastModified").setParameter("updatedAt", lastModified).getResultList();
        File docsExp = new File(dir, getFileName("document", now));
        File itmsExp = new File(dir, getFileName("document_items", now));
        BufferedWriter out = null;
        BufferedWriter itmOut = null;
        itmCount = 0;
        try {
            if (docs.size() > 0) {
                out = new BufferedWriter(new FileWriter(docsExp));
                itmOut = new BufferedWriter(new FileWriter(itmsExp));
                out.write("\"document_id\",\"doc_type\",\"customer_id\",\"contact_id\",\"location\",\"contractor\","
                        + "\"rig_tank_eq\",\"discount\",\"tax_percent\",\"subtotal\",\"po_type\",\"po_number\","
                        + "\"date_in\",\"date_out\",\"well_name\",\"afe_uww\",\"date_str\",\"cai\",\"aprvr_name\","
                        + "\"user_id\",\"created_at\",\"updated_at\"" + lineSep);
                itmOut.write("\"document_id\",\"document_item_id\",\"item_id\",\"qty\",\"price\",\"tax\""
                        + lineSep);
                for (Document d : docs) {
                    DocumentPK pk = d.getDocumentPK();
                    Integer originalDocID = (Integer) em.createNativeQuery("select document_id from document_ids where document_type='"
                            + pk.getDocType() + "' and id=" + pk.getDocumentID()).getSingleResult();
                    out.write("\"" + pk.getDocumentID() + "\",\"" + dq(pk.getDocType()) + "\",\"" + d.getCustomerId().toString()
                            + "\",\"" + d.getContactId().toString() + "\",\"" + dq(d.getLocation()) + "\",\"" + dq(d.getContractor())
                            + "\",\"" + dq(d.getRigTankEq())
                            + "\",\"" + (d.getDiscount() == null ? "" : d.getDiscount().toString())
                            + "\",\"" + dq(d.getTaxProc() == null ? "" : d.getTaxProc().toString())
                            + "\",\"" + (d.getSubtotal() == null ? "" : d.getSubtotal().toString())
                            + "\",\"" + dq(d.getPoType()) + "\",\"" + dq(d.getPoNumber())
                            + "\",\"" + (d.getDateIn() == null ? "" : dateFormatter.format(d.getDateIn()))
                            + "\",\"" + (d.getDateOut() == null ? "" : dateFormatter.format(d.getDateOut()))
                            + "\",\"" + dq(d.getWellName()) + "\",\"" + dq(d.getAfeUww()) + "\",\"" + dq(d.getDateStr())
                            + "\",\"" + dq(d.getCai()) + "\",\"" + dq(d.getAprvrName()) + "\",\"" + d.getCreatedBy()
                            + "\",\"" + dateFormatter.format(d.getCreatedAt()) + "\",\"" + dateFormatter.format(d.getUpdatedAt()) + "\""
                            + lineSep);
                    if (pk.getDocType().equals("quote")) {
                        writeQuoteItemList(pk.getDocumentID(), originalDocID, itmOut, lineSep);
                    } else if (pk.getDocType().equals("invoice")) {
                        writeInvoiceItemList(pk.getDocumentID(), originalDocID, itmOut, lineSep);
                    } else if (pk.getDocType().equals("order")) {
                        writeOrderItemList(pk.getDocumentID(), originalDocID, itmOut, lineSep);
                    }
                }
                filesToExport.add(docsExp);
                filesToExport.add(itmsExp);
            }
            sb.append("Table Document:" + docs.size() + " rows exported " + lineSep);
            sb.append("Table DocumentItems:" + itmCount + " rows exported " + lineSep);
        } finally {
            if (out != null) {
                out.close();
            }
            if (itmOut != null) {
                itmOut.close();
            }
        }
    }

    private void writeQuoteItemList(Integer documentID, Integer quoteID, BufferedWriter itmOut, String lineSep) throws IOException {
        Quote quote = (Quote) em.createNamedQuery("Quote.findByQuoteId").setParameter("quoteId", quoteID).getSingleResult();
        Collection<Quoteitem> itms = em.createNamedQuery("Quoteitem.findByQuoteId")
                .setParameter("quoteId", quote).getResultList();
        for (Quoteitem qi : itms) {
            itmOut.write("\"" + documentID.toString() + "\",\"" + qi.getQuoteitemId()
                    + "\",\"" + qi.getItemId().getItemId() + "\",\"" + qi.getQty()
                    + "\",\"" + qi.getPrice().toString() + "\",\"" + (qi.getTax() != null && qi.getTax() ? "yes" : "no") + lineSep);
            itmCount++;
        }
    }

    private void writeInvoiceItemList(Integer documentID, Integer invoiceID, BufferedWriter itmOut, String lineSep) throws IOException {
        Invoice invoice = (Invoice) em.createNamedQuery("Invoice.findByInvoiceId").setParameter("invoiceId", invoiceID).getSingleResult();
        Collection<Invoiceitem> itms = em.createNamedQuery("Invoiceitem.findByInvoiceId")
                .setParameter("invoiceId", invoice).getResultList();
        for (Invoiceitem ii : itms) {
            itmOut.write("\"" + documentID.toString() + "\",\"" + ii.getInvoiceitemId()
                    + "\",\"" + ii.getItemId().getItemId() + "\",\"" + ii.getQty()
                    + "\",\"" + ii.getPrice().toString() + "\",\"" + (ii.getTax() != null && ii.getTax() ? "yes" : "no") + lineSep);
            itmCount++;
        }
    }

    private void writeOrderItemList(Integer documentID, Integer orderID, BufferedWriter itmOut, String lineSep) throws IOException {
        Order1 order = (Order1) em.createNamedQuery("Order1.findByOrderId").setParameter("orderId", orderID).getSingleResult();
        Collection<Orderitem> itms = em.createNamedQuery("Orderitem.findByOrderId")
                .setParameter("orderId", order).getResultList();
        for (Orderitem oi : itms) {
            itmOut.write("\"" + documentID.toString() + "\",\"" + oi.getOrderitemId()
                    + "\",\"" + oi.getItemId().getItemId() + "\",\"" + oi.getQty()
                    + "\",\"" + oi.getPrice().toString() + "\",\"" + (oi.getTax() != null && oi.getTax() ? "yes" : "no") + lineSep);
            itmCount++;
        }
    }

    private void exportCustomers(File dir, Date now, String lineSep, StringBuilder sb) throws IOException {
        Collection<Customer> custs = em.createNamedQuery("Customer.findLastModified").setParameter("updatedAt", lastModified).getResultList();
        File custExp = new File(dir, getFileName("customer", now));
        BufferedWriter out = null;
        try {
            if (custs.size() > 0) {
                out = new BufferedWriter(new FileWriter(custExp));
                out.write("\"customer_id\",\"customer_name\",\"customer_address\",\"created_at\",\"updated_at\"" + lineSep);
                for (Customer c : custs) {
                    out.write("\"" + c.getCustomerId().toString() + "\",\"" + dq(c.getCustomerName()) + "\",\"" + dq(c.getCustomerAddress()) + "\",\""
                            + dateFormatter.format(c.getCreatedAt()) + "\",\"" + dateFormatter.format(c.getUpdatedAt()) + "\""
                            + lineSep);
                }
                filesToExport.add(custExp);
            }
            sb.append("Table Customer:" + custs.size() + " rows exported " + lineSep);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private void exportContacts(File dir, Date now, String lineSep, StringBuilder sb) throws IOException {
        Collection<Contact> cntcs = em.createNamedQuery("Contact.findLastModified").setParameter("updatedAt", lastModified).getResultList();
        File cntctExp = new File(dir, getFileName("contact", now));
        BufferedWriter out = null;
        try {
            if (cntcs.size() > 0) {
                out = new BufferedWriter(new FileWriter(cntctExp));
                out.write("\"contact_id\",\"title\",\"first_name\",\"last_name\",\"email\",\"phone\",\"customer_id\",\"created_at\",\"updated_at\"" + lineSep);
                for (Contact c : cntcs) {
                    out.write("\"" + c.getContactId().toString() + "\",\"" + dq(c.getTitle()) + "\",\""
                            + dq(c.getFirstName()) + "\",\"" + dq(c.getLastName()) + "\",\"" + dq(c.getEmail()) + "\",\"" + dq(c.getPhone()) + "\",\""
                            + c.getCustomerId().getCustomerId() + "\",\""
                            + dateFormatter.format(c.getCreatedAt()) + "\",\"" + dateFormatter.format(c.getUpdatedAt()) + "\""
                            + lineSep);
                }
                filesToExport.add(cntctExp);
            }
            sb.append("Table Contact:" + cntcs.size() + " rows exported " + lineSep);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private void exportUsers(File dir, Date now, String lineSep, StringBuilder sb) throws IOException {
        Collection<User> users = em.createNamedQuery("User.findLastModified").setParameter("updatedAt", lastModified).getResultList();
        File usersExp = new File(dir, getFileName("user", now));
        BufferedWriter out = null;
        try {
            if (users.size() > 0) {
                out = new BufferedWriter(new FileWriter(usersExp));
                out.write("\"user_id\",\"first_name\",\"last_name\",\"email\",\"login\",\"password\",\"department\",\"created_at\",\"updated_at\"" + lineSep);
                for (User u : users) {
                    out.write("\"" + u.getUserId().toString() + "\",\"" + dq(u.getFirstName()) + "\",\"" + dq(u.getLastName()) + "\",\""
                            + dq(u.getEmail()) + "\",\"" + dq(u.getLogin()) + "\",\"" + dq(u.getPassword()) + "\",\""
                            + dq(u.getDepartmentId().getDepartmentName()) + "\",\""
                            + dateFormatter.format(u.getCreatedAt()) + "\",\"" + dateFormatter.format(u.getUpdatedAt()) + "\""
                            + lineSep);
                }
                filesToExport.add(usersExp);
            }
            sb.append("Table User:" + users.size() + " rows exported " + lineSep);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private void exportItems(File dir, Date now, String lineSep, StringBuilder sb) throws IOException {
        Collection<Item> items = em.createNamedQuery("Item.findLastModified").setParameter("updatedAt", lastModified).getResultList();
        File itemsExp = new File(dir, getFileName("item", now));
        BufferedWriter out = null;
        try {
            if (items.size() > 0) {
                out = new BufferedWriter(new FileWriter(itemsExp));
                out.write("\"item_id\",\"item_number\",\"item_name\",\"item_description\",\"last_price\",\"created_at\",\"updated_at\"" + lineSep);
                for (Item itm : items) {
                    out.write("\"" + itm.getItemId().toString() + "\","
                            + (itm.getItemNumber() == null ? "" : "\"" + itm.getItemNumber() + "\"") + ","
                            + (itm.getItemName() == null ? "" : "\"" + dq(itm.getItemName()) + "\"") + ","
                            + (itm.getItemDescription() == null ? "" : "\"" + dq(itm.getItemDescription()) + "\"") + ","
                            + (itm.getLastPrice() == null ? "" : "\"" + itm.getLastPrice().toString()) + "\",\""
                            + dateFormatter.format(itm.getCreatedAt()) + "\",\"" + dateFormatter.format(itm.getUpdatedAt()) + "\""
                            + lineSep
                    );
                }
                filesToExport.add(itemsExp);
            }
            sb.append("Table Item:" + items.size() + " rows exported " + lineSep);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static String getFileName(String tabName, Date now) {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(now) + "-" + tabName + ".csv";
    }

    private void notifyError(Exception e) {
        //TODO
        e.printStackTrace();
    }

//    private void notifyExport(String notification) {
//        //TODO: send notification
//        System.out.println("!!!!!" + notification);
//    }
}
