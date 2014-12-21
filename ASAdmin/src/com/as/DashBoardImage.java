package com.as;

import static com.as.GeneralFrame.errMessageBox;
import com.as.mvc.dbtable.DbTableDocument;
import com.as.mvc.dbtable.DbTableView.MyTableModel;
import com.as.remote.IMessageSender;
import com.as.util.FileFilterOnExtension;
import com.as.util.ImagePanel;
import com.as.util.RecordEditPanel;
import com.as.util.TexturedPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author Nick Mukhin
 */
public class DashBoardImage extends JFrame {

    private static final String ATTENTION = "Attention!";
    private static final String NO_OPEN_GRIDS = "No open grids";

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
                    if (tp.getSelectedComponent() instanceof GeneralGridPanel) {
                        activatePanel((GeneralGridPanel) tp.getSelectedComponent());
                    }
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
    private GeneralGridPanel taxGrid;
    private GeneralGridPanel stampsGrid;
    private GeneralGridPanel contactGrid;
    private GeneralGridPanel quotesGrid;
    private GeneralGridPanel invoicesGrid;
    private GeneralGridPanel ordersGrid;
    private GeneralGridPanel departspmentGrid;
    private GeneralGridPanel rolesGrid;
    private JTabbedPane setupPanel = null;
    private JTabbedPane customersPanel = null;
    private JPopupMenu sharePopup;
    private ImagePanel shareImg;
    private JTextField searchField;

    private static void disableSearchField() {
        ourInstance.searchField.setText("");
        ourInstance.highlightFound();
        ourInstance.searchField.setEnabled(false);
    }

    private KeyAdapter getSrcFieldKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                highlightFound();
            }
        };
    }

    private void highlightFound() {
        if (activeGridPanel != null) {
            try {
                RowFilter<MyTableModel, Object> rf = RowFilter.regexFilter("(?i)" + searchField.getText());
                activeGridPanel.getTableView().getSorter().setRowFilter(rf);
            } catch (Exception ex) {
                ASAdmin.log(ex);
            }
        }
    }

    private static class SortAction extends AbstractAction {

        private GeneralGridPanel grid;

        SortAction(GeneralGridPanel grid) {
            this.grid = grid;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (grid.setCustomSort(ourInstance)) {
                grid.refresh();
            }
        }
    }

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
                    GeneralFrame.errMessageBox(ATTENTION, NO_OPEN_GRIDS);
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
                    GeneralFrame.errMessageBox(ATTENTION, NO_OPEN_GRIDS);
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
                    GeneralFrame.errMessageBox(ATTENTION, NO_OPEN_GRIDS);
                }
            }

        };
    }

    private AbstractAction sortGridRowAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (activeGridPanel != null && activeGridPanel.getSortAction() != null) {
                    activeGridPanel.getSortAction().actionPerformed(null);
                } else {
                    GeneralFrame.errMessageBox(ATTENTION, NO_OPEN_GRIDS);
                }
            }

        };
    }

    private AbstractAction findGridAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (activeGridPanel != null) {
                    if (searchField.isEnabled()) {
                        searchField.setText("");
                        highlightFound();
                    } else {
                        searchField.requestFocus();
                    }
                    searchField.setEnabled(!searchField.isEnabled());
                } else {
                    GeneralFrame.errMessageBox(ATTENTION, NO_OPEN_GRIDS);
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
                        String header = JOptionPane.showInputDialog(rootPane, "Enter title for the document:", "Title");
                        doc.generateHTML(htmlFile = chooseFileForExport("html"), header);
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
                //GeneralFrame.notImplementedYet();
                DbTableDocument doc = (DbTableDocument) activeGridPanel.getController().getDocument();
                File pdfFile = null, htmlFile = null;
                try {
                    String header = JOptionPane.showInputDialog(rootPane, "Enter title for the document:", "Title");
                    pdfFile = chooseFileForExport("pdf");
                    String htmlFileName = pdfFile.getName() + ".html";
                    doc.generateHTML(htmlFile = new File(htmlFileName), header);
                    if (htmlFile != null) {
                        Document document = new Document();
                        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                        document.open();
                        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                                new FileInputStream(htmlFile));
                        document.close();
                        if (pdfFile != null) {
                            Desktop desktop = Desktop.getDesktop();
                            desktop.open(pdfFile);
                            JOptionPane.showMessageDialog(rootPane,
                                    "File " + pdfFile.getAbsolutePath() + " generated",
                                    "Ok!", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(rootPane,
                                    "Can't create file " + pdfFile.getAbsolutePath()
                                    + "! Check the target folder permissions", "Error!",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane,
                                "Can't create temporary file " + htmlFileName
                                + "! Check the target folder permissions", "Error!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ASAdmin.logAndShowMessage(ex);
                } finally {
                    if (htmlFile != null) {
                        htmlFile.delete();
                    }
                }
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
                null, sortGridRowAction());
        img.setBounds(292, 50, img.getWidth(), img.getHeight());
        headerLeft.add(img);

        img = new ImagePanel(ASAdmin.loadImage("Find1.png", this),
                ASAdmin.loadImage("Find.jpg", this),
                null, findGridAction());
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

        searchField = new JTextField(20);
        searchField.setToolTipText("Enter search string here");
        searchField.setBounds(500, 68, searchField.getPreferredSize().width, searchField.getPreferredSize().height);
        headerLeft.add(searchField);
        searchField.setEnabled(false);
        searchField.addKeyListener(getSrcFieldKeyListener());

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
        dashWidth = img.getWidth() * 2 / 3;
        dashHeight = img.getHeight() + TOOLBAR_HEIGHT;
        this.setPreferredSize(new Dimension(dashWidth + insets.left + insets.right, dashHeight + insets.top + insets.bottom));
        this.setMinimumSize(new Dimension(dashWidth + insets.left + insets.right, 200));
        this.setSize(getPreferredSize());
        layers.add(controlsPanel, JLayeredPane.DEFAULT_LAYER);
        getContentPane().add(layers, BorderLayout.CENTER);

    }

    private static GeneralGridPanel activatePanel(GeneralGridPanel p) {
        activeGridPanel = p;
        disableSearchField();
        return activeGridPanel;
    }

    public static void showAdminsFrame() {
        try {
            if (ourInstance.setupPanel == null) {
                ourInstance.main.add(ourInstance.setupPanel = new JTabbedPane(), SETUP);
                ourInstance.setupPanel.add("Users List",
                        activatePanel(ourInstance.usersGrid = new UsersGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }

                            public AbstractAction getSortAction() {
                                return new SortAction(ourInstance.usersGrid);
                            }
                        }));
                ourInstance.setupPanel.add("Roles",
                        ourInstance.rolesGrid = new RolesGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }

                            public AbstractAction getSortAction() {
                                return new SortAction(ourInstance.rolesGrid);
                            }
                        });
                ourInstance.setupPanel.add("Departments",
                        ourInstance.departspmentGrid = new DepartsmentGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }

                            public AbstractAction getSortAction() {
                                return new SortAction(ourInstance.departspmentGrid);
                            }
                        });
                ourInstance.setupPanel.add("PO types",
                        ourInstance.poGrid = new PoGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }

                            public AbstractAction getSortAction() {
                                return new SortAction(ourInstance.poGrid);
                            }
                        });
                ourInstance.setupPanel.add("Taxes",
                        ourInstance.taxGrid = new TaxGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }

                            public AbstractAction getSortAction() {
                                return new SortAction(ourInstance.taxGrid);
                            }
                        });
                ourInstance.setupPanel.add("Stamps",
                        ourInstance.stampsGrid = new StampsGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }

                            public AbstractAction getSortAction() {
                                return new SortAction(ourInstance.stampsGrid);
                            }
                        });
                ourInstance.setupPanel.add("Export data settings",RecordEditPanel.getBorderPanel(new ExportSettingPanel(null)));
                ourInstance.setupPanel.addChangeListener(getChangeTabListener());
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.poGrid.refresh();
            ourInstance.stampsGrid.refresh();
            ourInstance.rolesGrid.refresh();
            ourInstance.departspmentGrid.refresh();
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
                        activatePanel(ourInstance.custGrid = new CustomerGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }

                            public AbstractAction getSortAction() {
                                return new SortAction(ourInstance.custGrid);
                            }
                        }));
                ourInstance.customersPanel.add("Contacts",
                        ourInstance.contactGrid = new ContactGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }

                            public AbstractAction getSortAction() {
                                return new SortAction(ourInstance.contactGrid);
                            }
                        });
                ourInstance.customersPanel.addChangeListener(getChangeTabListener());
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

    private static void showProducts() {
        try {
            if (ourInstance.itemsGrid == null) {
                ourInstance.main.add(
                        ourInstance.itemsGrid = activatePanel(new ItemsGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }

                            public AbstractAction getSortAction() {
                                return new SortAction(ourInstance.itemsGrid);
                            }
                        }), PRODUCTS);
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.itemsGrid.refresh();
            cl.show(ourInstance.main, PRODUCTS);
            SwingUtilities.updateComponentTreeUI(ourInstance);
            activatePanel(ourInstance.itemsGrid);
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
    }

    private void showOrders() {
        try {
            if (ourInstance.ordersGrid == null) {
                ourInstance.main.add(
                        ourInstance.ordersGrid = activatePanel(new OrdersGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }

                            public AbstractAction getSortAction() {
                                return new SortAction(ourInstance.ordersGrid);
                            }
                        }), ORDERS);
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.ordersGrid.refresh();
            cl.show(ourInstance.main, ORDERS);
            SwingUtilities.updateComponentTreeUI(ourInstance);
            activatePanel(ourInstance.ordersGrid);
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
    }

    private void showInvoices() {
        try {
            if (ourInstance.invoicesGrid == null) {
                ourInstance.main.add(
                        ourInstance.invoicesGrid = activatePanel(new InvoicesGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }

                            public AbstractAction getSortAction() {
                                return new SortAction(ourInstance.invoicesGrid);
                            }
                        }), INVOICES);
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.invoicesGrid.refresh();
            cl.show(ourInstance.main, INVOICES);
            SwingUtilities.updateComponentTreeUI(ourInstance);
            activatePanel(ourInstance.invoicesGrid);
        } catch (Exception ex) {
            ASAdmin.logAndShowMessage(ex);
        }
    }

    private void showQuotes() {
        try {
            if (ourInstance.quotesGrid == null) {
                ourInstance.main.add(
                        ourInstance.quotesGrid = activatePanel(new QuotesGrid(exchanger) {
                            protected void addRightBtnPanel(AbstractAction[] acts) {
                            }

                            public AbstractAction getSortAction() {
                                return new SortAction(ourInstance.quotesGrid);
                            }
                        }), QUOTES);
            }
            CardLayout cl = (CardLayout) ourInstance.main.getLayout();
            ourInstance.quotesGrid.refresh();
            cl.show(ourInstance.main, QUOTES);
            SwingUtilities.updateComponentTreeUI(ourInstance);
            activatePanel(ourInstance.quotesGrid);
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

    protected void exit() {
        dispose();
        System.exit(1);
    }
}
