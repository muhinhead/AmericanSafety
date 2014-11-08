package com.as.util;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class ImagePanel extends JPanel {

    private Image img, origImg;
    private Image hoveredImg, clickedImg;
    private String imgName;
    private static ImagePanel current = null;

    public static void releaseCurrent() {
        if (current != null) {
            current.restoreImage();
        }
        current = null;
    }
    private AbstractAction action;

    public ImagePanel(Image orig, Image hovered, Image clicked, AbstractAction action) {
        this(orig, null);
        hoveredImg = hovered;
        clickedImg = clicked;
        this.action = action;
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                ImagePanel im = (ImagePanel) e.getSource();
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                if (!im.equals(current)) {
                    setImg(hovered);
                }
            }

            public void mouseClicked(MouseEvent e) {
                if (clickedImg != null) {
                    setImg(clickedImg);
                    releaseCurrent();
                    current = ImagePanel.this;
                } else if (hoveredImg != null) {
                    setImg(hoveredImg);
                }
                if (action!=null) {
                    action.actionPerformed(null);
                }
            }

            public void mouseExited(MouseEvent e) {
                ImagePanel im = (ImagePanel) e.getSource();
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (!im.equals(current)) {
                    restoreImage();
                }
            }
        });
    }

    public ImagePanel(String img) {
        this(new ImageIcon(img).getImage(), img);
        imgName = img;
    }

    public ImagePanel(Image img) {
        this(img, null);
    }

    public ImagePanel(Image img, String imgName) {
        this.img = origImg = img;
        this.imgName = imgName;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    public String getImgName() {
        return imgName;
    }

    public void setImg(Image img) {
        this.img = img;
        repaint();
    }

//    public void setImg(String img) {
//        this.img = new ImageIcon(img).getImage();
//        repaint();
//    }
    public void restoreImage() {
        this.img = origImg;
        repaint();
    }
}
