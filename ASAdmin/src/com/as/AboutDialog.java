package com.as;

import com.as.util.ImagePanel;
import com.as.util.PopupDialog;
import com.as.util.TexturedPanel;
import com.as.util.Util;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class AboutDialog extends PopupDialog {

    private static final String BACKGROUNDIMAGE = "about.png";
    private AbstractAction closeAction;
    private JButton closeBtn;

    public AboutDialog() {
        super(null, "American Safety Admin console", null);
        ASAdmin.setWindowIcon(this, "cust_sol16.png");
    }

    @Override
    protected Object getHeaderBackground() {
        return null;//new Color(102, 125, 158);
    }

    protected void fillContent() {
        Color fg = ASAdmin.HDR_COLOR;
        super.fillContent();
        JPanel main = new TexturedPanel(BACKGROUNDIMAGE);
        getContentPane().add(main, BorderLayout.CENTER);
        ImagePanel img = new ImagePanel(Util.loadImage(BACKGROUNDIMAGE));
        this.setMinimumSize(new Dimension(img.getWidth(), img.getHeight() + 17));
        closeBtn = new JButton(closeAction = new AbstractAction("Ok") {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JLabel version = new JLabel("American Safety Admin Console. Version " + ASAdmin.getVersion());
        version.setBounds(100, 100, version.getPreferredSize().width, version.getPreferredSize().height);
        version.setForeground(fg);
        main.add(version);

        JLabel devBy = new JLabel("Custom Intel Lean Solutions (c) 2014 email: progress@customintel.com");
        devBy.setFont(devBy.getFont().deriveFont(Font.ITALIC,10));
        devBy.setBounds(43, 137, devBy.getPreferredSize().width, devBy.getPreferredSize().height);
        devBy.setForeground(fg);
        main.add(devBy);
        
        JLabel str1 = new JLabel("American Safety Inc");
        str1.setFont(str1.getFont().deriveFont(Font.BOLD,12));
        str1.setBounds(45, 172, str1.getPreferredSize().width, str1.getPreferredSize().height);
        str1.setForeground(fg);
        main.add(str1);

//        JLabel str2 = new JLabel("The Association for International Broadcasting");
//        str2.setFont(str2.getFont().deriveFont(Font.ITALIC|Font.BOLD,10));
//        str2.setBounds(43, 154, str2.getPreferredSize().width, str2.getPreferredSize().height);
//        str2.setForeground(fg);
//        main.add(str2);
        
//        JLabel str3 = new JLabel("PO Box 141 | Cranbrook");
//        str3.setFont(str3.getFont().deriveFont(Font.ITALIC,10));
//        str3.setBounds(44, 173, str3.getPreferredSize().width, str3.getPreferredSize().height);
//        str3.setForeground(fg);
//        main.add(str3);
        
//        JLabel str4 = new JLabel("TN17 9AJ | United Kingdom");
//        str4.setFont(str4.getFont().deriveFont(Font.ITALIC,10));
//        str4.setBounds(44, 185, str4.getPreferredSize().width, str4.getPreferredSize().height);
//        str4.setForeground(fg);
//        main.add(str4);

        closeBtn.setBounds(285, 180,
                closeBtn.getPreferredSize().width,
                closeBtn.getPreferredSize().height);

        main.add(closeBtn);
        setResizable(false);
    }

    @Override
    public void freeResources() {
        closeBtn.removeActionListener(closeAction);
        closeAction = null;
    }

}
