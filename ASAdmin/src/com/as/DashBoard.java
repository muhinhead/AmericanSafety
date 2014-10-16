package com.as;

import com.as.remote.IMessageSender;
import com.as.util.ImagePanel;
import com.as.util.TexturedPanel;
import com.as.util.ToggleToolBarButton;
import com.as.util.Util;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nick Mukhin
 */
public class DashBoard extends JFrame {//extends AbstractDashBoard {

    private static final int TOOLBAR_HEIGHT = 55;
    private static final String USERS = "USERS";
    private static final String DOCUMENTS = "DOCUMENTS";
    private static final String SETUP = "SETUP";
    private static final String CUSTOMERS = "CUSTOMERS";
    public static DashBoard ourInstance;
    private static IMessageSender exchanger;
    protected JPanel main;
    protected JPanel controlsPanel;
    private int dashWidth;
    private int dashHeight;
    private static final String BACKGROUNDIMAGE = "list_bg.png";
    private ToggleToolBarButton usersButton;
    private ToggleToolBarButton setupButton;
    private GeneralGridPanel usersGrid = null;
    private GeneralGridPanel documentsGrid = null;
    private GeneralGridPanel itemsGrid;
    private GeneralGridPanel custGrid;
    private GeneralGridPanel poGrid;
    private GeneralGridPanel contactGrid;
    private JTabbedPane setupPanel = null;
    private JTabbedPane customersPanel = null;
    private TexturedPanel headerLeft;
    private ToggleToolBarButton docsButton;
    private ToggleToolBarButton[] buttons;
    private ToggleToolBarButton customersButton;

    protected class WinListener extends WindowAdapter {

        public WinListener(JFrame frame) {
        }

        public void windowClosing(WindowEvent e) {
            exit();
        }
    }

    protected class LayerPanel extends JLayeredPane {

        LayerPanel() {
            super();
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            controlsPanel.setBounds(getBounds());
        }
    }

    public DashBoard(String title, IMessageSender exchanger) {
        super(title);
        DashBoard.exchanger = exchanger;
        ourInstance = this;
        addWindowListener(new DashBoard.WinListener(this));
        lowLevelInit();
        initBackground();
        fillControlsPanel();
        setVisible(true);
        setResizable(true);
    }

    private void buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu m = new JMenu("Options");
        m.add(new JMenuItem(new AbstractAction("Connection settings") {
            @Override
            public void actionPerformed(ActionEvent e) {
                ASAdmin.configureConnection();
            }
        }));
        m.add(new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }));
        bar.add(m);
        m = new JMenu("Help");
        m.add(new JMenuItem(new AbstractAction("About") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                new AboutDialog();
            }
        }));
        bar.add(m);
        setJMenuBar(bar);
    }

    protected void initBackground() {
        ASAdmin.setWindowIcon(this, "cust_sol16.png");
        addWindowListener(new DashBoard.WinListener(this));
        controlsPanel = new JPanel(new BorderLayout());

        setLayout(new BorderLayout());

        LayerPanel layers = new LayerPanel();
        main = new TexturedPanel(new CardLayout(), getBackGroundImage());
        headerLeft = new TexturedPanel(new FlowLayout(FlowLayout.LEFT), getBackGroundImage());
        headerLeft.setPreferredSize(new Dimension(headerLeft.getPreferredSize().width, TOOLBAR_HEIGHT));

        JPanel header = new JPanel(new GridLayout(1, 2));
        header.add(headerLeft);

        TexturedPanel headerRight = new TexturedPanel(new FlowLayout(FlowLayout.RIGHT), getBackGroundImage());
        headerRight.add(new JButton(new AbstractAction(null, new ImageIcon("images/exit.png")) {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }));
        headerRight.add(new ImagePanel(Util.loadImage("amsaf60.png")));
        header.add(headerRight);

        controlsPanel.add(header, BorderLayout.NORTH);
        controlsPanel.add(main, BorderLayout.CENTER);
        addNotify();
        ImagePanel img = new ImagePanel(ASAdmin.loadImage(getBackGroundImage(), this));
        Insets insets = getInsets();
        dashWidth = img.getWidth() * 3 / 4;
        dashHeight = img.getHeight() + TOOLBAR_HEIGHT;
        this.setPreferredSize(new Dimension(dashWidth + insets.left + insets.right, dashHeight + insets.top + insets.bottom));
        this.setMinimumSize(new Dimension(dashWidth + insets.left + insets.right, 200));
        this.setSize(getPreferredSize());
        layers.add(controlsPanel, JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(layers, BorderLayout.CENTER);
    }

    protected void fillControlsPanel() throws HeadlessException {
//        String imgName = "Users.png";
        buildMenu();
        usersButton = new ToggleToolBarButton("Users.png", "Users");//, true);
        docsButton = new ToggleToolBarButton("dicts.png", "Documents");//, true);
        customersButton = new ToggleToolBarButton("Client.png", "Customers");//, true);
        setupButton = new ToggleToolBarButton("setup.png", "Settings");//, true);

        buttons = new ToggleToolBarButton[]{
            usersButton,
            docsButton,
            customersButton,
            setupButton
        };

        usersButton.setToolTipText("Users");
        docsButton.setToolTipText("Doсuments");
        customersButton.setToolTipText("Customers & contacts");
        setupButton.setToolTipText("Setup & dictionaries");

        headerLeft.add(usersButton);
        headerLeft.add(docsButton);
        headerLeft.add(customersButton);
        headerLeft.add(setupButton);

        usersButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unselectOthers(usersButton);
                if (usersButton.isSelected()) {
                    showUsers();
                }
            }
        });

        docsButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                unselectOthers(docsButton);
                showDocuments();
            }
        });

        customersButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                unselectOthers(customersButton);
                showCustomers();
            }
        });

        setupButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                unselectOthers(setupButton);
                showAdminsFrame();
            }
        });

//
//        setupButton.setBounds(shift + step, 38, img.getWidth(), img.getHeight());
//        main.add(setupButton);
//
//        compsButton.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (companyFrame == null) {
//                    companyFrame = new CompanyFrame(ASAdmin.getExchanger());
//                } else {
//                    try {
//                        companyFrame.setLookAndFeel(ASAdmin.readProperty("LookAndFeel",
//                                UIManager.getSystemLookAndFeelClassName()));
//                    } catch (Exception ex) {
//                    }
//                    companyFrame.setVisible(true);
//                }
//            }
//        });
//
//        peopleButton.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                if (peopleFrame == null) {
//                    peopleFrame = new PeopleFrame(ASAdmin.getExchanger());
//                } else {
//                    try {
//                        peopleFrame.setLookAndFeel(ASAdmin.readProperty("LookAndFeel",
//                                UIManager.getSystemLookAndFeelClassName()));
//                    } catch (Exception ex) {
//                    }
//                    peopleFrame.setVisible(true);
//                }
//            }
//        });
//
//        locationsButton.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                if (locationsFrame == null) {
//                    locationsFrame = new LocationsFrame(ASAdmin.getExchanger());
//                } else {
//                    try {
//                        locationsFrame.setLookAndFeel(ASAdmin.readProperty("LookAndFeel",
//                                UIManager.getSystemLookAndFeelClassName()));
//                    } catch (Exception ex) {
//                    }
//                    locationsFrame.setVisible(true);
//                }
//            }
//        });
//
//        setupButton.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//                if (adminsFrame == null) {
//                    adminsFrame = new AdminsFrame(ASAdmin.getExchanger());
//                } else {
//                    try {
//                        adminsFrame.setLookAndFeel(ASAdmin.readProperty("LookAndFeel",
//                                UIManager.getSystemLookAndFeelClassName()));
//                    } catch (Exception ex) {
//                    }
//                    adminsFrame.setVisible(true);
//                }
//            }
//        });
        centerOnScreen();
    }

    private void unselectOthers(ToggleToolBarButton btn) {
        for (ToggleToolBarButton b : buttons) {
            if (b != btn) {
                b.setSelected(false);
            }
        }
    }

    public void centerOnScreen() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2);
    }

    public static void centerWindow(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setExtendedState(Frame.NORMAL);
        frame.validate();
    }

    public static float getXratio(JFrame frame) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return (float) frame.getWidth() / d.width;
    }

    public static float getYratio(JFrame frame) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return (float) frame.getHeight() / d.height;
    }

    public static void setSizes(JFrame frame, double x, double y) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (x * d.width), (int) (y * d.height));
    }

    public void setVisible(boolean show) {
        super.setVisible(show);
    }

    protected String getBackGroundImage() {
        return BACKGROUNDIMAGE;
    }

    public void lowLevelInit() {
        ASAdmin.readProperty("junk", ""); // just to init properties
    }

    protected void exit() {
        dispose();
        System.exit(1);
    }

    private static void showCustomers() {
        try {
            if (ourInstance.customersPanel == null) {
                ourInstance.main.add(ourInstance.customersPanel = new JTabbedPane(), CUSTOMERS);
                ourInstance.customersPanel.add("Customers", ourInstance.custGrid = new CustomerGrid(exchanger));
                ourInstance.customersPanel.add("Contacts", ourInstance.contactGrid = new ContactGrid(exchanger));
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.custGrid.refresh();
            ourInstance.contactGrid.refresh();
            cl.show(ourInstance.main, CUSTOMERS);
            SwingUtilities.updateComponentTreeUI(ourInstance);
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
    }

    public static void showAdminsFrame() {
        try {
            if (ourInstance.setupPanel == null) {
                ourInstance.main.add(ourInstance.setupPanel = new JTabbedPane(), SETUP);
                ourInstance.setupPanel.add("Items List", ourInstance.itemsGrid = new ItemsGrid(exchanger));
                ourInstance.setupPanel.add("PO types", ourInstance.poGrid = new PoGrid(exchanger));
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.itemsGrid.refresh();
            ourInstance.poGrid.refresh();
            cl.show(ourInstance.main, SETUP);
            SwingUtilities.updateComponentTreeUI(ourInstance);
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
    }

    public static void showDocuments() {
        try {
            if (ourInstance.documentsGrid == null) {
                ourInstance.main.add(ourInstance.documentsGrid = new DocumentsGrid(exchanger), DOCUMENTS);
                ourInstance.documentsGrid.refresh();
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.documentsGrid.refresh();
            cl.show(ourInstance.main, DOCUMENTS);
            SwingUtilities.updateComponentTreeUI(ourInstance);
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
    }

    public static void showUsers() {
        try {
            if (ourInstance.usersGrid == null) {
                ourInstance.main.add(ourInstance.usersGrid = new UsersGrid(exchanger), USERS);
                ourInstance.usersGrid.refresh();
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.usersGrid.refresh();
            cl.show(ourInstance.main, USERS);
            SwingUtilities.updateComponentTreeUI(ourInstance);
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
    }

//    public static GeneralFrame getAuditsFrame(boolean visible) {
//        if (ourInstance.auditFrame == null) {
//            ourInstance.auditFrame = new AuditFrame(exchanger, visible);
//        } else {
//            try {
//                ourInstance.auditFrame.setLookAndFeel(ASAdmin.readProperty("LookAndFeel",
//                        UIManager.getSystemLookAndFeelClassName()));
//            } catch (Exception ex) {
//            }
//            if (!ourInstance.auditFrame.isVisible()) {
//                ourInstance.auditFrame.setVisible(visible);
//            }
//        }
//        return ourInstance.auditFrame;
//    }
//    public static GeneralFrame getPushesFrame(boolean visible) {
//        if (ourInstance.pushesFrame == null) {
//            ourInstance.pushesFrame = new PushesFrame(exchanger);
//        } else {
//            try {
//                ourInstance.pushesFrame.setLookAndFeel(ASAdmin.readProperty("LookAndFeel",
//                        UIManager.getSystemLookAndFeelClassName()));
//            } catch (Exception ex) {
//            }
//            if (!ourInstance.pushesFrame.isVisible()) {
//                ourInstance.pushesFrame.setVisible(visible);
//            }
//        }
//        return ourInstance.pushesFrame;
//    }
//    public static void showAdminsFrame() {
//        if (adminsFrame == null) {
//            adminsFrame = new AdminsFrame(exchanger);
//        } else {
//            try {
//                adminsFrame.setLookAndFeel(ASAdmin.readProperty("LookAndFeel",
//                        UIManager.getSystemLookAndFeelClassName()));
//            } catch (Exception ex) {
//            }
//            adminsFrame.setVisible(true);
//        }
//    }
}
