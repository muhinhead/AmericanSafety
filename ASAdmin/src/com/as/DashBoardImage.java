package com.as;

import com.as.remote.IMessageSender;
import com.as.util.ImagePanel;
import com.as.util.TexturedPanel;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nick Mukhin
 */
public class DashBoardImage extends JFrame {

    private static final String USERS = "USERS";
    private static final String DOCUMENTS = "DOCUMENTS";
    private static final String SETUP = "SETUP";
    private static final String CUSTOMERS = "CUSTOMERS";
    public static DashBoardImage ourInstance;

    private static IMessageSender exchanger;
    private static final int TOOLBAR_HEIGHT = 125;
    private static final String BACKGROUNDIMAGE = "list_bg.png";
    protected JPanel controlsPanel;
    private int dashWidth;
    private int dashHeight;
    private TexturedPanel main;
    private TexturedPanel headerLeft;
    private TexturedPanel manageCustomersProductsPanel;
    //private ArrayList<ImagePanel> concurBtns = new ArrayList<ImagePanel>();
    private GeneralGridPanel usersGrid = null;
    private GeneralGridPanel documentsGrid = null;
    private GeneralGridPanel itemsGrid;
    private GeneralGridPanel custGrid;
    private GeneralGridPanel poGrid;
    private GeneralGridPanel stampsGrid;
    private GeneralGridPanel contactGrid;
    private JTabbedPane setupPanel = null;
    private JTabbedPane customersPanel = null;

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

    public DashBoardImage(String title, IMessageSender exchanger) {
        super(title);
        ourInstance = this;
        DashBoardImage.exchanger = exchanger;
//        ourInstance = this;
        addWindowListener(new DashBoardImage.WinListener(this));
        lowLevelInit();
        initBackground();
        fillControlsPanel();
        setVisible(true);
        setResizable(true);
    }

    private void fillControlsPanel() throws HeadlessException {
        buildMenu();
    }

    public void lowLevelInit() {
        ASAdmin.readProperty("junk", ""); // just to init properties
    }

    private void initBackground() {
        ASAdmin.setWindowIcon(this, "cust_sol16.png");
        controlsPanel = new JPanel(new BorderLayout());
        setLayout(new BorderLayout());
        LayerPanel layers = new LayerPanel();
        main = new TexturedPanel(new CardLayout(), BACKGROUNDIMAGE);
//        headerLeft = new TexturedPanel(new FlowLayout(FlowLayout.LEFT), getBackGroundImage());
//        headerLeft.setPreferredSize(new Dimension(headerLeft.getPreferredSize().width, TOOLBAR_HEIGHT));
        ImagePanel img = new ImagePanel(ASAdmin.loadImage("TopLeft.jpg", this));
        headerLeft = new TexturedPanel("TopLeft.jpg");
        headerLeft.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));

        img = new ImagePanel(ASAdmin.loadImage("NewEntry1.png", this), //"NewEntry1.png");
                ASAdmin.loadImage("NewEntry.jpg", this),
                null, null);
        img.setBounds(119, 50, img.getWidth(), img.getHeight());
        headerLeft.add(img);
//        img.addMouseListener(getImgMouseAdapter());

        img = new ImagePanel(ASAdmin.loadImage("DeleteEntry1.png", this), //"DeleteEntry1.png");
                ASAdmin.loadImage("DeleteEntry.jpg", this),
                null, null);
        img.setBounds(174, 50, img.getWidth(), img.getHeight());
        headerLeft.add(img);
//        img.addMouseListener(getImgMouseAdapter());

        img = new ImagePanel(ASAdmin.loadImage("Sort1.png", this), //"Sort1.png");
                ASAdmin.loadImage("Sort.jpg", this),
                null, null);
        img.setBounds(239, 50, img.getWidth(), img.getHeight());
        headerLeft.add(img);
//        img.addMouseListener(getImgMouseAdapter());

        img = new ImagePanel(ASAdmin.loadImage("Find1.png", this), //"Find1.png");
                ASAdmin.loadImage("Find.jpg", this),
                null, null);
        img.setBounds(289, 50, img.getWidth(), img.getHeight());
        headerLeft.add(img);
//        img.addMouseListener(getImgMouseAdapter());

        img = new ImagePanel(ASAdmin.loadImage("Share1.png", this), //"Share1.png");
                ASAdmin.loadImage("Share.jpg", this),
                null, null);
        img.setBounds(345, 50, img.getWidth(), img.getHeight());
        headerLeft.add(img);
//        img.addMouseListener(getImgMouseAdapter());

        JPanel header = new JPanel(new BorderLayout());
        header.add(headerLeft, BorderLayout.NORTH);
        img = new ImagePanel(ASAdmin.loadImage("Topleft.PNG", this));
        manageCustomersProductsPanel = new TexturedPanel("Topleft.PNG");
        manageCustomersProductsPanel.setPreferredSize(
                new Dimension(img.getWidth(), img.getHeight()));
        header.add(manageCustomersProductsPanel, BorderLayout.SOUTH);

        img = new ImagePanel(ASAdmin.loadImage("Manage1.PNG", this), //"Manage1.PNG"));
                ASAdmin.loadImage("Manage.PNG", this),
                ASAdmin.loadImage("Manage2.PNG", this),
                new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showAdminsFrame();
                    }
                });

        img.setBounds(0, 0, img.getWidth(), img.getHeight());
        manageCustomersProductsPanel.add(img);

        img = new ImagePanel(ASAdmin.loadImage("Customers1.PNG", this), //"Customers1.PNG"));
                ASAdmin.loadImage("Customers.PNG", this),
                ASAdmin.loadImage("Customers2.PNG", this), new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showCustomers();
                    }
                });
        img.setBounds(177, 0, img.getWidth(), img.getHeight());
        manageCustomersProductsPanel.add(img);

        img = new ImagePanel(ASAdmin.loadImage("Products1.PNG", this), //"Customers1.PNG"));
                ASAdmin.loadImage("Products.PNG", this),
                ASAdmin.loadImage("Products2.PNG", this), new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
        img.setBounds(360, 0, img.getWidth(), img.getHeight());
        manageCustomersProductsPanel.add(img);

        img = new ImagePanel(ASAdmin.loadImage("Quotes1.PNG", this),
                ASAdmin.loadImage("Quotes.PNG", this),
                ASAdmin.loadImage("Quotes2.PNG", this), new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
        img.setBounds(0, 52, img.getWidth(), img.getHeight());
        manageCustomersProductsPanel.add(img);
        
        img = new ImagePanel(ASAdmin.loadImage("WorkOrders1.PNG", this),
                ASAdmin.loadImage("WorkOrders.PNG", this),
                ASAdmin.loadImage("WorkOrders2.PNG", this), new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
        img.setBounds(369, 52, img.getWidth(), img.getHeight());
        manageCustomersProductsPanel.add(img);
        
        img = new ImagePanel(ASAdmin.loadImage("Invoiced1.PNG", this),
                ASAdmin.loadImage("Invoiced.PNG", this),
                ASAdmin.loadImage("Invoiced2.PNG", this), new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });
        img.setBounds(177, 52, img.getWidth(), img.getHeight());
        manageCustomersProductsPanel.add(img);
        
        controlsPanel.add(main, BorderLayout.CENTER);
        controlsPanel.add(header, BorderLayout.NORTH);

        addNotify();
        img = new ImagePanel(ASAdmin.loadImage(BACKGROUNDIMAGE, this));
        Insets insets = getInsets();
        dashWidth = img.getWidth() * 3 / 4;
        dashHeight = img.getHeight() + TOOLBAR_HEIGHT;
        this.setPreferredSize(new Dimension(dashWidth + insets.left + insets.right, dashHeight + insets.top + insets.bottom));
        this.setMinimumSize(new Dimension(dashWidth + insets.left + insets.right, 200));
        this.setSize(getPreferredSize());
        layers.add(controlsPanel, JLayeredPane.DEFAULT_LAYER);
        getContentPane().add(layers, BorderLayout.CENTER);

    }

//    private MouseAdapter getImgMouseAdapter() {
//        return new MouseAdapter() {
//            public void mouseEntered(MouseEvent e) {
//                ImagePanel im = (ImagePanel) e.getSource();
//                im.setCursor(new Cursor(Cursor.HAND_CURSOR));
//                if (im.getImgName() != null) {
//                    if (im.getImgName().startsWith("Sort")) {
//                        im.setImg(ASAdmin.loadImage("Sort.jpg", DashBoardImage.this));
//                    } else if (im.getImgName().startsWith("Find")) {
//                        im.setImg(ASAdmin.loadImage("Find.jpg", DashBoardImage.this));
//                    } else if (im.getImgName().startsWith("NewEntry")) {
//                        im.setImg(ASAdmin.loadImage("NewEntry.jpg", DashBoardImage.this));
//                    } else if (im.getImgName().startsWith("DeleteEntry")) {
//                        im.setImg(ASAdmin.loadImage("DeleteEntry.jpg", DashBoardImage.this));
//                    } else if (im.getImgName() != null && im.getImgName().startsWith("Share")) {
//                        im.setImg(ASAdmin.loadImage("Share.jpg", DashBoardImage.this));
//                    } else if (im.getImgName() != null && im.getImgName().startsWith("Manage")) {
//                        im.setImg(ASAdmin.loadImage("Manage.PNG", DashBoardImage.this));
//                    } else if (im.getImgName() != null && im.getImgName().startsWith("Customers")) {
//                        im.setImg(ASAdmin.loadImage("Customers.PNG", DashBoardImage.this));
//                    } else {
//                        System.out.println("image:" + im.getImgName());
//                    }
//                }
//            }
//
//            public void mouseClicked(MouseEvent e) {
//                ImagePanel im = (ImagePanel) e.getSource();
//                if (inCincurentList(im)) {
//                    darkOthers(im);
//                    if (im.getImgName().startsWith("Manage")) {
//                        
//                    }
//                }
//            }
//
//            public void mouseExited(MouseEvent e) {
//                ImagePanel im = (ImagePanel) e.getSource();
//                im.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
//                if (im.getImgName() != null && (im.getImgName().startsWith("DeleteEntry")
//                        || im.getImgName().startsWith("Find")
//                        || im.getImgName().startsWith("Sort")
//                        || im.getImgName().startsWith("NewEntry")
//                        || im.getImgName().startsWith("Share")
//                        || im.getImgName().startsWith("Manage")
//                        || im.getImgName().startsWith("Customers"))) {
//                    im.restoreImage();
//                }
//            }
//
//            private boolean inCincurentList(ImagePanel im) {
//                for (ImagePanel i : concurBtns) {
//                    if (i.equals(i)) {
//                        return true;
//                    }
//                }
//                return false;
//            }
//
//            private void darkOthers(ImagePanel im) {
//                for (ImagePanel i : concurBtns) {
//                    if(!im.equals(i)) {
//                        i.restoreImage();
//                    }
//                }
//            }
//        };
//    }
//    public static void showUsers() {
//        try {
//            if (ourInstance.usersGrid == null) {
//                ourInstance.main.add(ourInstance.usersGrid = new UsersGrid(exchanger), USERS);
//                ourInstance.usersGrid.refresh();
//            }
//            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
//            ourInstance.usersGrid.refresh();
//            cl.show(ourInstance.main, USERS);
//            SwingUtilities.updateComponentTreeUI(ourInstance);
//        } catch (Exception ex) {
//            ASAdmin.logAndShowMessage(ex);
//        }
//    }
    public static void showAdminsFrame() {
        try {
            if (ourInstance.setupPanel == null) {
                ourInstance.main.add(ourInstance.setupPanel = new JTabbedPane(), SETUP);
                ourInstance.setupPanel.add("Users List", ourInstance.usersGrid = new UsersGrid(exchanger));
//                ourInstance.setupPanel.add("Items List", ourInstance.itemsGrid = new ItemsGrid(exchanger));
                ourInstance.setupPanel.add("PO types", ourInstance.poGrid = new PoGrid(exchanger));
                ourInstance.setupPanel.add("Stamps", ourInstance.stampsGrid = new StampsGrid(exchanger));
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
//            ourInstance.itemsGrid.refresh();
            ourInstance.poGrid.refresh();
            ourInstance.stampsGrid.refresh();
            cl.show(ourInstance.main, SETUP);
            SwingUtilities.updateComponentTreeUI(ourInstance);
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
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

    protected void exit() {
        dispose();
        System.exit(1);
    }
}
