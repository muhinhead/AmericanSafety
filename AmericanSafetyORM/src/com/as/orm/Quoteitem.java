// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Nov 27 11:32:33 EET 2014
// generated file: do not modify
package com.as.orm;

import com.as.orm.dbobject.DbObject;
import com.as.orm.dbobject.ForeignKeyViolationException;
import com.as.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Quoteitem extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer quoteitemId = null;
    private Integer quoteId = null;
    private Integer itemId = null;
    private Integer qty = null;
    private Double price = null;
    private Timestamp updatedAt = null;
    private Timestamp createdAt = null;
    private Integer tax = null;

    public Quoteitem(Connection connection) {
        super(connection, "quoteitem", "quoteitem_id");
        setColumnNames(new String[]{"quoteitem_id", "quote_id", "item_id", "qty", "price", "updated_at", "created_at", "tax"});
    }

    public Quoteitem(Connection connection, Integer quoteitemId, Integer quoteId, Integer itemId, Integer qty, Double price, Timestamp updatedAt, Timestamp createdAt, Integer tax) {
        super(connection, "quoteitem", "quoteitem_id");
        setNew(quoteitemId.intValue() <= 0);
//        if (quoteitemId.intValue() != 0) {
            this.quoteitemId = quoteitemId;
//        }
        this.quoteId = quoteId;
        this.itemId = itemId;
        this.qty = qty;
        this.price = price;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.tax = tax;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Quoteitem quoteitem = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT quoteitem_id,quote_id,item_id,qty,price,updated_at,created_at,tax FROM quoteitem WHERE quoteitem_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                quoteitem = new Quoteitem(getConnection());
                quoteitem.setQuoteitemId(new Integer(rs.getInt(1)));
                quoteitem.setQuoteId(new Integer(rs.getInt(2)));
                quoteitem.setItemId(new Integer(rs.getInt(3)));
                quoteitem.setQty(new Integer(rs.getInt(4)));
                quoteitem.setPrice(rs.getDouble(5));
                quoteitem.setUpdatedAt(rs.getTimestamp(6));
                quoteitem.setCreatedAt(rs.getTimestamp(7));
                quoteitem.setTax(new Integer(rs.getInt(8)));
                quoteitem.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return quoteitem;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO quoteitem ("+(getQuoteitemId().intValue()!=0?"quoteitem_id,":"")+"quote_id,item_id,qty,price,updated_at,created_at,tax) values("+(getQuoteitemId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getQuoteitemId().intValue()!=0) {
                 ps.setObject(++n, getQuoteitemId());
             }
             ps.setObject(++n, getQuoteId());
             ps.setObject(++n, getItemId());
             ps.setObject(++n, getQty());
             ps.setObject(++n, getPrice());
             ps.setObject(++n, getUpdatedAt());
             ps.setObject(++n, getCreatedAt());
             ps.setObject(++n, getTax());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getQuoteitemId().intValue()==0) {
             stmt = "SELECT max(quoteitem_id) FROM quoteitem";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setQuoteitemId(new Integer(rs.getInt(1)));
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
                    "UPDATE quoteitem " +
                    "SET quote_id = ?, item_id = ?, qty = ?, price = ?, updated_at = ?, created_at = ?, tax = ?" + 
                    " WHERE quoteitem_id = " + getQuoteitemId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getQuoteId());
                ps.setObject(2, getItemId());
                ps.setObject(3, getQty());
                ps.setObject(4, getPrice());
                ps.setObject(5, getUpdatedAt());
                ps.setObject(6, getCreatedAt());
                ps.setObject(7, getTax());
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
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM quoteitem " +
                "WHERE quoteitem_id = " + getQuoteitemId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setQuoteitemId(new Integer(-getQuoteitemId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getQuoteitemId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT quoteitem_id,quote_id,item_id,qty,price,updated_at,created_at,tax FROM quoteitem " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Quoteitem(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getDouble(5),rs.getTimestamp(6),rs.getTimestamp(7),new Integer(rs.getInt(8))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Quoteitem[] objects = new Quoteitem[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Quoteitem quoteitem = (Quoteitem) lst.get(i);
            objects[i] = quoteitem;
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
        String stmt = "SELECT quoteitem_id FROM quoteitem " +
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
    //    return getQuoteitemId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return quoteitemId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setQuoteitemId(id);
        setNew(prevIsNew);
    }

    public Integer getQuoteitemId() {
        return quoteitemId;
    }

    public void setQuoteitemId(Integer quoteitemId) throws ForeignKeyViolationException {
        setWasChanged(this.quoteitemId != null && this.quoteitemId != quoteitemId);
        this.quoteitemId = quoteitemId;
        setNew(quoteitemId.intValue() == 0);
    }

    public Integer getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Integer quoteId) throws SQLException, ForeignKeyViolationException {
        if (quoteId!=null && !Quote.exists(getConnection(),"quote_id = " + quoteId)) {
            throw new ForeignKeyViolationException("Can't set quote_id, foreign key violation: quoteitem_quote_fk");
        }
        setWasChanged(this.quoteId != null && !this.quoteId.equals(quoteId));
        this.quoteId = quoteId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) throws SQLException, ForeignKeyViolationException {
        if (itemId!=null && !Item.exists(getConnection(),"item_id = " + itemId)) {
            throw new ForeignKeyViolationException("Can't set item_id, foreign key violation: quoteitem_item_fk");
        }
        setWasChanged(this.itemId != null && !this.itemId.equals(itemId));
        this.itemId = itemId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.qty != null && !this.qty.equals(qty));
        this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.price != null && !this.price.equals(price));
        this.price = price;
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

    public Integer getTax() {
        return tax;
    }

    public void setTax(Integer tax) throws SQLException, ForeignKeyViolationException {
        if (null != tax)
            tax = tax == 0 ? null : tax;
        setWasChanged(this.tax != null && !this.tax.equals(tax));
        this.tax = tax;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[8];
        columnValues[0] = getQuoteitemId();
        columnValues[1] = getQuoteId();
        columnValues[2] = getItemId();
        columnValues[3] = getQty();
        columnValues[4] = getPrice();
        columnValues[5] = getUpdatedAt();
        columnValues[6] = getCreatedAt();
        columnValues[7] = getTax();
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
            setQuoteitemId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setQuoteitemId(null);
        }
        try {
            setQuoteId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setQuoteId(null);
        }
        try {
            setItemId(Integer.parseInt(flds[2]));
        } catch(NumberFormatException ne) {
            setItemId(null);
        }
        try {
            setQty(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setQty(null);
        }
        try {
            setPrice(Double.parseDouble(flds[4]));
        } catch(NumberFormatException ne) {
            setPrice(null);
        }
        setUpdatedAt(toTimeStamp(flds[5]));
        setCreatedAt(toTimeStamp(flds[6]));
        try {
            setTax(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setTax(null);
        }
    }
}
