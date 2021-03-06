package com.as.mvc.dbtable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Label;
import java.text.SimpleDateFormat;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Nick Mukhin
 */
public class MyColorRenderer extends JLabel implements TableCellRenderer {

    private static final SimpleDateFormat americanDateFormat = new SimpleDateFormat("MM-d-yyyy HH:mm aaa");
    private Color stripColor = new Color(0, 0, 255, 16);
    private Color searchStringColor = Color.BLUE;
    private Color searchBackColor = Color.CYAN;
    private final ITableView tv;

    public MyColorRenderer(ITableView tv) {
        super();

        Font font = getFont();
        Font newFont = new Font(font.getName(), font.getStyle(), 10);
        setFont(newFont);
        this.tv = tv;
    }

//    protected void alignRight(int column) {
//        JLabel label = null;
//        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
//        rightRenderer.setHorizontalAlignment(label.RIGHT);
//        tableView.getColumnModel().getColumn(column).setCellRenderer(rightRenderer);
//    }
    
    public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (row < tv.getRowData().size()) {
            boolean undefinedDate = false;
            Vector line = (Vector) tv.getRowData().get(row);
            if (value != null) {
                if (value instanceof java.util.Date) {
                    String sd = americanDateFormat.format(value);
                    undefinedDate = sd.equals("01-1-1970 12:00 PM");
                    setText(sd);
                } else if (value instanceof Double) {
                    setText(value.toString());
                } else {
                    setText(value.toString());
                }
            }
            this.setOpaque(true);
            String searchStr = tv.getSearchString();
            String ceilStr = (String) line.get(column);
            boolean found = (searchStr != null && searchStr.length() > 0 && ceilStr.toUpperCase().indexOf(searchStr.toUpperCase()) >= 0);
            Color backColor = (row % 2 == 0 && !isSelected) ? found ? searchBackColor : stripColor : (isSelected
                    ? table.getSelectionBackground()
                    : found ? searchBackColor : table.getBackground());
            Color foreColor = isSelected ? found ? searchStringColor : table.getSelectionForeground()
                    : found ? searchStringColor : table.getForeground();
            setBackground(backColor);
            setForeground(foreColor);
            if(undefinedDate)
                setText("");
        }
        return this;
    }
}
