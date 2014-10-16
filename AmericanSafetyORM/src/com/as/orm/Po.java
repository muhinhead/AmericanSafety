// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Oct 09 20:06:39 EEST 2014
// generated file: do not modify
package com.as.orm;

import com.as.orm.dbobject.DbObject;
import com.as.orm.dbobject.ForeignKeyViolationException;
import com.as.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Po extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer poId = null;
    private String poDescription = null;

    public Po(Connection connection) {
        super(connection, "po", "po_id");
        setColumnNames(new String[]{"po_id", "po_description"});
    }

    public Po(Connection connection, Integer poId, String poDescription) {
        super(connection, "po", "po_id");
        setNew(poId.intValue() <= 0);
//        if (poId.intValue() != 0) {
            this.poId = poId;
//        }
        this.poDescription = poDescription;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Po po = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT po_id,po_description FROM po WHERE po_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                po = new Po(getConnection());
                po.setPoId(new Integer(rs.getInt(1)));
                po.setPoDescription(rs.getString(2));
                po.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return po;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO po ("+(getPoId().intValue()!=0?"po_id,":"")+"po_description) values("+(getPoId().intValue()!=0?"?,":"")+"?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getPoId().intValue()!=0) {
                 ps.setObject(++n, getPoId());
             }
             ps.setObject(++n, getPoDescription());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getPoId().intValue()==0) {
             stmt = "SELECT max(po_id) FROM po";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setPoId(new Integer(rs.getInt(1)));
                 }
             } finally {
                 try {
                     if (rs != null) rs.close();
                 } finally {
                     if (ps != null) ps.close();
                 }
             }
         }
         setNew(false);
         setWasChanged(false);
         if (getTriggers() != null) {
             getTriggers().afterInsert(this);
         }
    }

    public void save() throws SQLException, ForeignKeyViolationException {
        if (isNew()) {
            insert();
        } else {
            if (getTriggers() != null) {
                getTriggers().beforeUpdate(this);
            }
            PreparedStatement ps = null;
            String stmt =
                    "UPDATE po " +
                    "SET po_description = ?" + 
                    " WHERE po_id = " + getPoId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getPoDescription());
                ps.execute();
            } finally {
                if (ps != null) ps.close();
            }
            setWasChanged(false);
            if (getTriggers() != null) {
                getTriggers().afterUpdate(this);
            }
        }
    }

    public void delete() throws SQLException, ForeignKeyViolationException {
        if (Order.exists(getConnection(),"po_type_id = " + getPoId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: order_po_fk");
        }
        if (Quote.exists(getConnection(),"po_type_id = " + getPoId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: quote_po_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM po " +
                "WHERE po_id = " + getPoId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setPoId(new Integer(-getPoId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getPoId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT po_id,po_description FROM po " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Po(con,new Integer(rs.getInt(1)),rs.getString(2)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Po[] objects = new Po[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Po po = (Po) lst.get(i);
            objects[i] = po;
        }
        return objects;
    }

    public static boolean exists(Connection con, String whereCondition) throws SQLException {
        if (con == null) {
            return true;
        }
        boolean ok = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT po_id FROM po " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                "WHERE " + whereCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            ok = rs.next();
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return ok;
    }

    //public String toString() {
    //    return getPoId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return poId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setPoId(id);
        setNew(prevIsNew);
    }

    public Integer getPoId() {
        return poId;
    }

    public void setPoId(Integer poId) throws ForeignKeyViolationException {
        setWasChanged(this.poId != null && this.poId != poId);
        this.poId = poId;
        setNew(poId.intValue() == 0);
    }

    public String getPoDescription() {
        return poDescription;
    }

    public void setPoDescription(String poDescription) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.poDescription != null && !this.poDescription.equals(poDescription));
        this.poDescription = poDescription;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[2];
        columnValues[0] = getPoId();
        columnValues[1] = getPoDescription();
        return columnValues;
    }

    public static void setTriggers(Triggers triggers) {
        activeTriggers = triggers;
    }

    public static Triggers getTriggers() {
        return activeTriggers;
    }

    //for SOAP exhange
    @Override
    public void fillFromString(String row) throws ForeignKeyViolationException, SQLException {
        String[] flds = splitStr(row, delimiter);
        try {
            setPoId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setPoId(null);
        }
        setPoDescription(flds[1]);
    }
}
