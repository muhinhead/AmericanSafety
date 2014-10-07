// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Tue Oct 07 17:15:15 EEST 2014
// generated file: do not modify
package com.as.orm;

import com.as.orm.dbobject.DbObject;
import com.as.orm.dbobject.ForeignKeyViolationException;
import com.as.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Stamps extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer stampsId = null;
    private String stamps = null;
    private Timestamp updatedAt = null;
    private Timestamp createdAt = null;

    public Stamps(Connection connection) {
        super(connection, "stamps", "stamps_id");
        setColumnNames(new String[]{"stamps_id", "stamps", "updated_at", "created_at"});
    }

    public Stamps(Connection connection, Integer stampsId, String stamps, Timestamp updatedAt, Timestamp createdAt) {
        super(connection, "stamps", "stamps_id");
        setNew(stampsId.intValue() <= 0);
//        if (stampsId.intValue() != 0) {
            this.stampsId = stampsId;
//        }
        this.stamps = stamps;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Stamps stamps = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT stamps_id,stamps,updated_at,created_at FROM stamps WHERE stamps_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                stamps = new Stamps(getConnection());
                stamps.setStampsId(new Integer(rs.getInt(1)));
                stamps.setStamps(rs.getString(2));
                stamps.setUpdatedAt(rs.getTimestamp(3));
                stamps.setCreatedAt(rs.getTimestamp(4));
                stamps.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return stamps;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO stamps ("+(getStampsId().intValue()!=0?"stamps_id,":"")+"stamps,updated_at,created_at) values("+(getStampsId().intValue()!=0?"?,":"")+"?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getStampsId().intValue()!=0) {
                 ps.setObject(++n, getStampsId());
             }
             ps.setObject(++n, getStamps());
             ps.setObject(++n, getUpdatedAt());
             ps.setObject(++n, getCreatedAt());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getStampsId().intValue()==0) {
             stmt = "SELECT max(stamps_id) FROM stamps";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setStampsId(new Integer(rs.getInt(1)));
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
                    "UPDATE stamps " +
                    "SET stamps = ?, updated_at = ?, created_at = ?" + 
                    " WHERE stamps_id = " + getStampsId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getStamps());
                ps.setObject(2, getUpdatedAt());
                ps.setObject(3, getCreatedAt());
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
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM stamps " +
                "WHERE stamps_id = " + getStampsId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setStampsId(new Integer(-getStampsId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getStampsId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT stamps_id,stamps,updated_at,created_at FROM stamps " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Stamps(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getTimestamp(3),rs.getTimestamp(4)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Stamps[] objects = new Stamps[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Stamps stamps = (Stamps) lst.get(i);
            objects[i] = stamps;
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
        String stmt = "SELECT stamps_id FROM stamps " +
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
    //    return getStampsId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return stampsId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setStampsId(id);
        setNew(prevIsNew);
    }

    public Integer getStampsId() {
        return stampsId;
    }

    public void setStampsId(Integer stampsId) throws ForeignKeyViolationException {
        setWasChanged(this.stampsId != null && this.stampsId != stampsId);
        this.stampsId = stampsId;
        setNew(stampsId.intValue() == 0);
    }

    public String getStamps() {
        return stamps;
    }

    public void setStamps(String stamps) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.stamps != null && !this.stamps.equals(stamps));
        this.stamps = stamps;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.updatedAt != null && !this.updatedAt.equals(updatedAt));
        this.updatedAt = updatedAt;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.createdAt != null && !this.createdAt.equals(createdAt));
        this.createdAt = createdAt;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[4];
        columnValues[0] = getStampsId();
        columnValues[1] = getStamps();
        columnValues[2] = getUpdatedAt();
        columnValues[3] = getCreatedAt();
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
            setStampsId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setStampsId(null);
        }
        setStamps(flds[1]);
        setUpdatedAt(toTimeStamp(flds[2]));
        setCreatedAt(toTimeStamp(flds[3]));
    }
}
