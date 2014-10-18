package com.as;

import com.as.orm.User;
import com.as.orm.dbobject.DbObject;
import com.as.remote.IMessageSender;
import com.as.rmi.DbConnection;
import com.as.rmi.ExchangeFactory;
import com.as.util.ComboItem;
import com.jidesoft.plaf.LookAndFeelFactory;
import java.awt.Color;
import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.prefs.Preferences;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Nick Mukhin
 */
public class ASAdmin {

    private static final String version = "0.1";
    private static Logger logger = null;
    private static FileHandler fh;
    private static Properties props;
    private static final String PROPERTYFILENAME = "ASAdmin.config";
    private static User currentUser;
    private static IMessageSender exchanger;
    public static final Color HDR_COLOR = new Color(48, 147, 0);//159, 21, 11);
    public static String protocol = "unknown";
    public static final String defaultServerIP = "localhost";//"104.130.138.22";
    private static ConcurrentHashMap listsCached = new ConcurrentHashMap();

    public static Properties getProperties() {
        return props;
    }

    public static String readProperty(String key, String deflt) {
        if (null == props) {
            props = new Properties();
            try {
                File propFile = new File(PROPERTYFILENAME);
                if (!propFile.exists() || propFile.length() == 0) {
                    String curPath = propFile.getAbsolutePath();
                    curPath = curPath.substring(0,
                            curPath.indexOf(PROPERTYFILENAME)).replace('\\', '/');
                    props.setProperty("user", "admin");
                    props.setProperty("userPassword", "admin");
                    propFile.createNewFile();
                } else {
                    props.load(new FileInputStream(propFile));
                }
                DbConnection.setProps(props);
            } catch (IOException e) {
                log(e);
                return deflt;
            }
        }
        return props.getProperty(key, deflt);
    }

    public static void saveProps() {
        if (props != null) {
            if (getCurrentUser() != null) {
                props.setProperty("LastLogin", getCurrentUser().getLogin());
            }
            props.setProperty("ServerAddress", props.getProperty("ServerAddress", "localhost:1099"));
        }
        Preferences userPref = Preferences.userRoot();
        saveProperties();
    }

    public static void saveProperties() {
        try {
            if (props != null) {
                props.store(new FileOutputStream(PROPERTYFILENAME),
                        "-----------------------");
            }
        } catch (IOException e) {
            //e.printStackTrace();
            logAndShowMessage(e);
        }
    }

    /**
     * @return the currentUser
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    public static void logAndShowMessage(Throwable ne) {
        JOptionPane.showMessageDialog(null, ne.getMessage(), "Error:", JOptionPane.ERROR_MESSAGE);
        log(ne);
    }

    public static void log(String msg) {
        log(msg, null);
    }

    public static void log(Throwable th) {
        log(null, th);
    }

    private static void log(String msg, Throwable th) {
        if (logger == null) {
            try {
                logger = Logger.getLogger("ASAdmin");
                fh = new FileHandler("%h/ASAdmin.log", 1048576, 10, true);
                logger.addHandler(fh);
                logger.setLevel(Level.ALL);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.log(Level.SEVERE, msg, th);
    }

    /**
     * @return the version
     */
    public static String getVersion() {
        return version;
    }

    public static String serverSetup(String title) {
        String cnctStr = null;
        String address = readProperty("ServerAddress", defaultServerIP);
        String[] vals = address.split(":");
        JTextField imageDirField = new JTextField(getProperties().getProperty("imagedir"));
        JTextField addressField = new JTextField(16);
        addressField.setText(vals[0]);
        JSpinner portSpinner = new JSpinner(new SpinnerNumberModel(
                vals.length > 1 ? new Integer(vals[1]) : 1099, 0, 65536, 1));
        JTextField dbConnectionField = new JTextField(getProperties()
                .getProperty("JDBCconnection", "jdbc:mysql://"
                        + defaultServerIP
                        + "/amsaf"));
        JTextField dbDriverField = new JTextField(getProperties()
                .getProperty("dbDriverName", "com.mysql.jdbc.Driver"));
        JTextField dbUserField = new JTextField(getProperties()
                .getProperty("dbUser", "root"));
        JPasswordField dbPasswordField = new JPasswordField();

        JComponent[] edits = new JComponent[]{
            imageDirField, addressField, portSpinner,
            dbConnectionField, dbDriverField, dbUserField, dbPasswordField
        };
        new ConfigEditor(title, edits);
        if (ConfigEditor.getProtocol().equals("rmi")) {
            if (addressField.getText().trim().length() > 0) {
                cnctStr = addressField.getText() + ":" + portSpinner.getValue();
                getProperties().setProperty("ServerAddress", cnctStr);
                getProperties().setProperty("imagedir", imageDirField.getText());
            }
        } else if (ConfigEditor.getProtocol().equals("jdbc")) {
            if (dbConnectionField.getText().trim().length() > 0) {
                cnctStr = dbDriverField.getText() + ";"
                        + dbConnectionField.getText() + ";"
                        + dbUserField.getText() + ";"
                        + new String(dbPasswordField.getPassword());
                getProperties().setProperty("JDBCconnection", dbConnectionField.getText());
                getProperties().setProperty("dbDriverName", dbDriverField.getText());
                getProperties().setProperty("dbUser", dbUserField.getText());
                getProperties().setProperty("dbPassword", new String(dbPasswordField.getPassword()));
            }
        }
        return cnctStr;
    }

    public static void setWindowIcon(Window w, String iconName) {
        w.setIconImage(loadImage(iconName, w));
    }

    public static Image loadImage(String iconName, Window w) {
        Image im = null;
        File f = new File("images/" + iconName);
        if (f.exists()) {
            try {
                ImageIcon ic = new javax.swing.ImageIcon("images/" + iconName, "");
                im = ic.getImage();
            } catch (Exception ex) {
                log(ex);
            }
        } else {
            try {
                im = ImageIO.read(w.getClass().getResourceAsStream("/" + iconName));
            } catch (Exception ie) {
                log(ie);
            }
        }
        return im;
    }

    public static void configureConnection() {
        String cnctStr = serverSetup("Options");
        if (cnctStr != null) {
            try {
                if (ConfigEditor.getProtocol().equals("rmi")) {
                    getProperties().setProperty("ServerAddress", cnctStr);
                    setExchanger(ExchangeFactory.createRMIexchanger(cnctStr));
                } else {
                    String[] dbParams = cnctStr.split(";");
                    setExchanger(ExchangeFactory.createJDBCexchanger(dbParams));
                }
                saveProperties();
            } catch (Exception ex) {
                logAndShowMessage(ex);
                System.exit(1);
            }
        }
    }

    public static IMessageSender getExchanger() {
        return exchanger;
    }

    static void setExchanger(IMessageSender iMessageSender) {
        exchanger = iMessageSender;
    }

    private static String removeTail(String s) {
        int p = s.lastIndexOf(".");
        if (p > 0 && s.length() > p + 1) {
            if ("0123456789".indexOf(s.substring(p + 1, p + 2)) < 0) {
                return s.substring(0, p);
            }
        }
        return s;
    }

    private static boolean matchVersions() {
        try {
            String servVersion = getExchanger().getServerVersion();
            boolean match = removeTail(servVersion).equals(removeTail(version));
            if (!match) {
                GeneralFrame.errMessageBox("Error:", "Client's software version (" + version + ") doesn't match server (" + servVersion + ")");
            }
            return match;
        } catch (RemoteException ex) {
            logAndShowMessage(ex);
        }
        return false;
    }

    static void setCurrentUser(User curUser) {
        currentUser = curUser;
    }

    public static ComboItem[] loadAllLogins() {
        return loadOnSelect("select user_id,login from user", null);
    }
    
    public static List loadLogins(String fld, String whereCond) {
        try {
            DbObject[] admins = exchanger.getDbObjects(User.class,
                    whereCond, fld);
            ArrayList logins = new ArrayList();
            logins.add("");
            for (DbObject o : admins) {
                User up = (User) o;
                if (fld.equals("login")) {
                    logins.add(up.getLogin());
                }
            }
            return logins;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static List loadAdminLogins(String fld) {
        return loadLogins(fld, "admin");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        String serverIP = readProperty("ServerAddress", "localhost");
        while (true) {
            try {
                IMessageSender exc = ExchangeFactory.getExchanger("rmi://" + serverIP + "/ASServer", getProperties());
                if (exc == null) {
                    exc = ExchangeFactory.getExchanger(readProperty("JDBCconnection", "jdbc:mysql://"
                            + defaultServerIP
                            + "/amsaf"),
                            getProperties());
                }
                if (exc == null) {
                    configureConnection();
                } else {
                    setExchanger(exc);
                }
                if (getExchanger() != null && matchVersions() && login(getExchanger())) {
                    new DashBoard("ASAdmin v." + version, exchanger);
                    break;
                } else {
                    System.exit(1);
                }
            } catch (Exception ex) {
                logAndShowMessage(ex);
                if ((serverIP = serverSetup("Check server settings")) == null) {
                    System.exit(1);
                } else {
                    saveProps();
                }
            }
        }
    }

    public static ComboItem[] loadCustomers() {
        return loadOnSelect("select customer_id, customer_name from customer", null);
    }

    public static ComboItem[] loadContacsOnCustomer(int customer_id) {
        return loadOnSelect("select contact_id,concat(first_name,' ',last_name)"
                + " from contact where customer_id="+customer_id,null);
    }
    
    public static String[] rigTankEquipment() {
        return loadStrings("select * from (select distinct rig_tank_eq "
                + "from document "
                + "union select ''"
                + "union select 'Rig'"
                + "union select 'Tank'"
                + "union select 'Equipment' order by rig_tank_eq) as t");
    }

    private static String[] loadStrings(String select) {
        String[] ans = null;
        try {
            Vector[] tab = exchanger.getTableBody(select);
            Vector rows = tab[1];
            ans = new String[rows.size()];
            for (int i = 0; i < rows.size(); i++) {
                Vector line = (Vector) rows.get(i);
                ans[i] = (String) line.get(0);
            }
        } catch (RemoteException ex) {
            logAndShowMessage(ex);
        }
        return ans;
    }

    private static ComboItem[] loadOnSelect(String select, ComboItem startItem) {
        try {
            Vector[] tab = exchanger.getTableBody(select);
            Vector rows = tab[1];
            int delta = (startItem != null ? 1 : 0);
            ComboItem[] ans = new ComboItem[rows.size() + delta];
            for (int i = 0; i < rows.size() + delta; i++) {
                if (startItem != null && i == 0) {
                    ans[i] = startItem;
                } else {
                    Vector line = (Vector) rows.get(i - delta);
                    int id = Integer.parseInt(line.get(0).toString());
                    String tmvnr = line.get(1).toString();
                    ans[i] = new ComboItem(id, tmvnr);
                }
            }
            return ans;
        } catch (RemoteException ex) {
            logAndShowMessage(ex);
        }
        return new ComboItem[]{new ComboItem(0, "")};
    }

    public static boolean login(IMessageSender exchanger) {
        try {
            new LoginImagedDialog(exchanger);//new Object[]{loginField, pwdField, exchanger});
            return LoginImagedDialog.isOkPressed();
        } catch (Throwable ee) {
            JOptionPane.showMessageDialog(null, "Server failure\nCheck your logs please", "Error:", JOptionPane.ERROR_MESSAGE);
            logAndShowMessage(ee);
        }
        return false;
    }
}
