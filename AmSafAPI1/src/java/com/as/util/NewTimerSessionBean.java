/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.util;

import com.as.Item;
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
    private static long timeDiff = 1000 * 120; //default - hourly 
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

    @Schedule(dayOfWeek = "*", month = "*", hour = "*", dayOfMonth = "*", year = "*", minute = "*", second = "0", persistent = false)
    public void myTimer() {
        System.out.println("==== Check for data to export at " + new Date().toString());
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
                }
            }
            File directory = new File(dirname);
            if (!directory.exists()) {
                directory.mkdir();
            }
            lastModified = new Date(directory.lastModified());
            if (now.getTime() - lastModified.getTime() >= timeDiff) {
                try {
                    busy = true;
                    String notification = exportData(directory, now);
                    if (ftpUrl != null && ftpLogin != null && ftpPassword != null && filesToExport.size() > 0) {
                        //System.out.println("!!FTP:"+ftpUrl+" login:"+ftpLogin+" password:"+ftpPassword);
                        ftpUploaded = AbstractFacade.upload2FTP(ftpUrl, ftpLogin, ftpPassword, "FTP", zipfile);
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
                                + ((ftpUrl != null && ftpLogin != null && ftpPassword != null && ftpUploaded) ? 
                                        "\nFile " + zipfile.getName() + " uploaded to ftp://" + ftpUrl : ""), null);
                    }
                    if (filesToExport.size() > 0) {
                        System.out.println(notification);
                    }
                    filesToExport.clear();
                    if (ftpUploaded && emailSent && zipfile!=null && zipfile.exists()) {
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

    private String exportData(File dir, Date now) throws IOException {
        StringBuilder sb = new StringBuilder();
        String lineSep = System.getProperty("line.separator");
        //export users
        exportUsers(dir, now, lineSep, sb);
        exportItems(dir, now, lineSep, sb);

        if (filesToExport.size() > 0) {
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

    private void exportUsers(File dir, Date now, String lineSep, StringBuilder sb) throws IOException {
        Collection<User> users = em.createNamedQuery("User.findLastModified").setParameter("updatedAt", lastModified).getResultList();
        File usersExp = new File(dir, getFileName("user", now));
        BufferedWriter out = null;
        try {
            if (users.size() > 0) {
                out = new BufferedWriter(new FileWriter(usersExp));
                out.write("\"user_id\",\"first_name\",\"last_name\",\"email\",\"login\",\"password\",\"department\",\"created_at\",\"updated_at\"" + lineSep);
                for (User u : users) {
                    out.write("\"" + u.getUserId().toString() + "\",\"" + u.getFirstName() + "\",\"" + u.getLastName() + "\",\""
                            + u.getEmail() + "\",\"" + u.getLogin() + "\",\"" + u.getPassword() + "\",\""
                            + u.getDepartmentId().getDepartmentName() + "\",\""
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
                            + (itm.getItemName() == null ? "" : "\"" + itm.getItemName() + "\"") + ","
                            + (itm.getItemDescription() == null ? "" : "\"" + itm.getItemDescription() + "\"") + ","
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
