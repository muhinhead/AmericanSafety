package com.as;

import com.as.mvc.dbtable.DbTableDocument;
import com.as.remote.IMessageSender;
import com.as.util.FileFilterOnExtension;
import com.as.util.ImagePanel;
import com.as.util.TexturedPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class DashBoardImage extends JFrame {

    private static final String ORDERS = "ORDERS";
    private static final String INVOICES = "INVOICES";
    private static final String PRODUCTS = "PRODUCTS";
    private static final String SETUP = "SETUP";
    private static final String CUSTOMERS = "CUSTOMERS";
    private static final String QUOTES = "QUOTES";
    public static DashBoardImage ourInstance;

    private static IMessageSender exchanger;
    private static final int TOOLBAR_HEIGHT = 125;
    private static final String BACKGROUNDIMAGE = "list_bg.png";
    private static GeneralGridPanel activeGridPanel;

    private static ChangeListener getChangeTabListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    JTabbedPane tp = (JTabbedPane) e.getSource();
                    activeGridPanel = (GeneralGridPanel) tp.getSelectedComponent();
                }
            }
        };
    }
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
    private GeneralGridPanel quotesGrid;
    private GeneralGridPanel invoicesGrid;
    private GeneralGridPanel ordersGrid;
    private JTabbedPane setupPanel = null;
    private JTabbedPane customersPanel = null;
    private JPopupMenu sharePopup;
    private ImagePanel shareImg;

    private AbstractAction notImplementedAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralFrame.notImplementedYet();
            }
        };
    }

    private AbstractAction addGridRowAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (activeGridPanel != null && activeGridPanel.getAddAction() != null) {
                    activeGridPanel.getAddAction().actionPerformed(null);
                } else {
                    GeneralFrame.notImplementedYet();
                }
            }

        };
    }

    private AbstractAction delGridRowAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (activeGridPanel != null && activeGridPanel.getDelAction() != null) {
                    activeGridPanel.getDelAction().actionPerformed(null);
                } else {
                    GeneralFrame.notImplementedYet();
                }
            }

        };
    }

    private AbstractAction editGridRowAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (activeGridPanel != null && activeGridPanel.getDelAction() != null) {
                    activeGridPanel.getEditAction().actionPerformed(null);
                } else {
                    GeneralFrame.notImplementedYet();
                }
            }

        };
    }

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

        sharePopup = new JPopupMenu();
        sharePopup.add(new JMenuItem(new AbstractAction("As HTML") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activeGridPanel != null) {
                    DbTableDocument doc = (DbTableDocument) activeGridPanel.getController().getDocument();
                    try {
                        File htmlFile;
                        doc.generateHTML(htmlFile = chooseFileForExport("html"));
                        if (htmlFile != null) {
                            Desktop desktop = Desktop.getDesktop();
                            desktop.open(htmlFile);
                        }
                    } catch (Exception ex) {
                        ASAdmin.logAndShowMessage(ex);
                    }
                }
            }

        }));
        sharePopup.add(new JMenuItem(new AbstractAction("As CSV") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activeGridPanel != null) {
                    DbTableDocument doc = (DbTableDocument) activeGridPanel.getController().getDocument();
                    try {
                        File csvFile;
                        doc.generateCSV(csvFile = chooseFileForExport("csv"));
                        if (csvFile != null) {
                            Desktop desktop = Desktop.getDesktop();
                            desktop.open(csvFile);
                        }
                    } catch (Exception ex) {
                        ASAdmin.logAndShowMessage(ex);
                    }
                }
            }
        }));
        sharePopup.add(new JMenuItem(new AbstractAction("As PDF") {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralFrame.notImplementedYet();
            }
        }));
    }

    public DashBoardImage(String title, IMessageSender exchanger) {
        super(title);
        ourInstance = this;
        DashBoardImage.exchanger = exchanger;
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
        ImagePanel img = new ImagePanel(ASAdmin.loadImage("TopLeft.jpg", this));
        headerLeft = new TexturedPanel("TopLeft.jpg");
        headerLeft.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));

        img = new ImagePanel(ASAdmin.loadImage("NewEntry1.png", this),
                ASAdmin.loadImage("NewEntry.jpg", this),
                null, addGridRowAction());
        img.setBounds(119, 50, img.getWidth(), img.getHeight());
        headerLeft.add(img);

        img = new ImagePanel(ASAdmin.loadImage("DeleteEntry1.png", this),
                ASAdmin.loadImage("DeleteEntry.jpg", this),
                null, delGridRowAction());
        img.setBounds(174, 50, img.getWidth(), img.getHeight());
        headerLeft.add(img);

        img = new ImagePanel(ASAdmin.loadImage("EditEntry1.png", this),
                ASAdmin.loadImage("EditEntry.png", this),
                null, editGridRowAction());
        img.setBounds(239, 50, img.getWidth(), img.getHeight());
        headerLeft.add(img);

        img = new ImagePanel(ASAdmin.loadImage("Sort1.png", this),
                ASAdmin.loadImage("Sort.jpg", this),
                null, notImplementedAction());
        img.setBounds(292, 50, img.getWidth(), img.getHeight());
        headerLeft.add(img);

        img = new ImagePanel(ASAdmin.loadImage("Find1.png", this),
                ASAdmin.loadImage("Find.jpg", this),
                null, notImplementedAction());
        img.setBounds(345, 50, img.getWidth(), img.getHeight());
        headerLeft.add(img);

        shareImg = new ImagePanel(ASAdmin.loadImage("Share1.png", this),
                ASAdmin.loadImage("Share.jpg", this),
                null, new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        sharePopup.show(shareImg, shareImg.getWidth() / 2, shareImg.getHeight() / 2);
                    }
                });
        shareImg.setBounds(398, 50, shareImg.getWidth(), shareImg.getHeight());
        headerLeft.add(shareImg);

        JPanel header = new JPanel(new BorderLayout());
        header.add(headerLeft, BorderLayout.NORTH);
        img = new ImagePanel(ASAdmin.loadImage("Topleft.PNG", this));
        manageCustomersProductsPanel = new TexturedPanel("Topleft.PNG");
        manageCustomersProductsPanel.setPreferredSize(
                new Dimension(img.getWidth(), img.getHeight()));
        header.add(manageCustomersProductsPanel, BorderLayout.SOUTH);

        img = new ImagePanel(ASAdmin.loadImage("Manage1.PNG", this),
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

        img = new ImagePanel(ASAdmin.loadImage("Customers1.PNG", this),
                ASAdmin.loadImage("Customers.PNG", this),
                ASAdmin.loadImage("Customers2.PNG", this), new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showCustomers();
                    }
                });
        img.setBounds(177, 0, img.getWidth(), img.getHeight());
        manageCustomersProductsPanel.add(img);

        img = new ImagePanel(ASAdmin.loadImage("Products1.PNG", this),
                ASAdmin.loadImage("Products.PNG", this),
                ASAdmin.loadImage("Products2.PNG", this), new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showProducts();
                    }
                });
        img.setBounds(360, 0, img.getWidth(), img.getHeight());
        manageCustomersProductsPanel.add(img);

        img = new ImagePanel(ASAdmin.loadImage("Quotes1.PNG", this),
                ASAdmin.loadImage("Quotes.PNG", this),
                ASAdmin.loadImage("Quotes2.PNG", this), new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showQuotes();
                    }
                });
        img.setBounds(0, 52, img.getWidth(), img.getHeight());
        manageCustomersProductsPanel.add(img);

        img = new ImagePanel(ASAdmin.loadImage("WorkOrders1.PNG", this),
                ASAdmin.loadImage("WorkOrders.PNG", this),
                ASAdmin.loadImage("WorkOrders2.PNG", this), new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showOrders();
                    }
                });
        img.setBounds(369, 52, img.getWidth(), img.getHeight());
        manageCustomersProductsPanel.add(img);

        img = new ImagePanel(ASAdmin.loadImage("Invoiced1.PNG", this),
                ASAdmin.loadImage("Invoiced.PNG", this),
                ASAdmin.loadImage("Invoiced2.PNG", this), new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        showInvoices();
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

    public static void showAdminsFrame() {
        try {
            if (ourInstance.setupPanel == null) {
                ourInstance.main.add(ourInstance.setupPanel = new JTabbedPane(), SETUP);
                ourInstance.setupPanel.add("Users List",
                        activeGridPanel = ourInstance.usersGrid = new UsersGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }
                        });
                ourInstance.setupPanel.add("PO types",
                        ourInstance.poGrid = new PoGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }
                        });
                ourInstance.setupPanel.add("Stamps", ourInstance.stampsGrid = new StampsGrid(exchanger) {
                    protected void addRightBtnPanel(AbstractAction[] acts) {
                    }
                });
                ourInstance.setupPanel.addChangeListener(getChangeTabListener());
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
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
                ourInstance.customersPanel.add("Customers",
                        activeGridPanel = ourInstance.custGrid = new CustomerGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }
                        });
                ourInstance.customersPanel.add("Contacts",
                        ourInstance.contactGrid = new ContactGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }
                        });
                ourInstance.customersPanel.addChangeListener(getChangeTabListener());
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.custGrid.refresh();
            ourInstance.contactGrid.refresh();
            cl.show(ourInstance.main, CUSTOMERS);
            SwingUtilities.updateComponentTreeUI(ourInstance);
            //activeGridPanel = ourInstance.customersPanel.get;
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
    }

    private static void showProducts() {
        try {
            if (ourInstance.itemsGrid == null) {
                ourInstance.main.add(
                        ourInstance.itemsGrid = activeGridPanel = new ItemsGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }
                        }, PRODUCTS);
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.itemsGrid.refresh();
            cl.show(ourInstance.main, PRODUCTS);
            SwingUtilities.updateComponentTreeUI(ourInstance);
            activeGridPanel = ourInstance.itemsGrid;
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
    }

    private void showOrders() {
        try {
            if (ourInstance.ordersGrid == null) {
                ourInstance.main.add(
                        ourInstance.ordersGrid = activeGridPanel = new OrdersGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }
                        }, ORDERS);
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.ordersGrid.refresh();
            cl.show(ourInstance.main, ORDERS);
            SwingUtilities.updateComponentTreeUI(ourInstance);
            activeGridPanel = ourInstance.ordersGrid;
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
    }
    
    private void showInvoices() {
        try {
            if (ourInstance.invoicesGrid == null) {
                ourInstance.main.add(
                        ourInstance.invoicesGrid = activeGridPanel = new InvoicesGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }
                        }, INVOICES);
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.invoicesGrid.refresh();
            cl.show(ourInstance.main, INVOICES);
            SwingUtilities.updateComponentTreeUI(ourInstance);
            activeGridPanel = ourInstance.invoicesGrid;
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
    }
    
    private void showQuotes() {
        try {
            if (ourInstance.quotesGrid == null) {
                ourInstance.main.add(
                        ourInstance.quotesGrid = activeGridPanel = new QuotesGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }
                        }, QUOTES);
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.quotesGrid.refresh();
            cl.show(ourInstance.main, QUOTES);
            SwingUtilities.updateComponentTreeUI(ourInstance);
            activeGridPanel = ourInstance.quotesGrid;
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
    }

    private File chooseFileForExport(String extension) {
        while (extension.startsWith(".")) {
            extension = extension.substring(1);
        }
        JFileChooser chooser = new JFileChooser(System.getProperty("user.home"));
        chooser.setFileFilter(new FileFilterOnExtension(extension));
        chooser.setDialogTitle("File to export");
        chooser.setApproveButtonText("Save");
        int retVal = chooser.showOpenDialog(null);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            String name = chooser.getSelectedFile().getAbsolutePath();
            if (!name.endsWith("." + extension)) {
                name += "." + extension;
            }
            return new File(name);
        } else {
            return null;
        }
    }

    protected void exit() {
        dispose();
        System.exit(1);
    }
}
