package com.as.util;

import com.as.ASAdmin;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 *
 * @author Nick Mukhin
 */
public class TemplatesGrid extends GeneralGridAdapter {
    public static final String SELECT = "select reportform_id \"Id\","
            + "name \"Template Name\",descr \"Description\" "
            + "from reportform where tablename='@'";
    
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    static {
        maxWidths.put(0, 40);
//        maxWidths.put(1, 200);
    }
    
    public TemplatesGrid(String tableName) throws RemoteException {
        super(ASAdmin.getExchanger(), SELECT.replace("@", tableName), maxWidths, true);
    }
   
}
