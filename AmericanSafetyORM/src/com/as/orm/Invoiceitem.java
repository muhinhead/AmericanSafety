// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Nov 27 11:32:33 EET 2014
// generated file: do not modify
package com.as.orm;

import com.as.orm.dbobject.DbObject;
import com.as.orm.dbobject.ForeignKeyViolationException;
import com.as.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Invoiceitem extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer invoiceitemId = null;
    private Integer invoiceId = null;
    private Integer itemId = null;
    private Integer qty = null;
    private Double price = null;
    private Timestamp updatedAt = null;
    private Timestamp createdAt = null;
    private Integer tax = null;

    public Invoiceitem(Connection connection) {
        super(connection, "invoiceitem", "invoiceitem_id");
        setColumnNames(new String[]{"invoiceitem_id", "invoice_id", "item_id", "qty", "price", "updated_at", "created_at", "tax"});
    }

    public Invoiceitem(Connection connection, Integer invoiceitemId, Integer invoiceId, Integer itemId, Integer qty, Double price, Timestamp updatedAt, Timestamp createdAt, Integer tax) {
        super(connection, "invoiceitem", "invoiceitem_id");
        setNew(invoiceitemId.intValue() <= 0);
//        if (invoiceitemId.intValue() != 0) {
            this.invoiceitemId = invoiceitemId;
//        }
        this.invoiceId = invoiceId;
        this.itemId = itemId;
        this.qty = qty;
        this.price = price;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.tax = tax;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Invoiceitem invoiceitem = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT invoiceitem_id,invoice_id,item_id,qty,price,updated_at,created_at,tax FROM invoiceitem WHERE invoiceitem_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                invoiceitem = new Invoiceitem(getConnection());
                invoiceitem.setInvoiceitemId(new Integer(rs.getInt(1)));
                invoiceitem.setInvoiceId(new Integer(rs.getInt(2)));
                invoiceitem.setItemId(new Integer(rs.getInt(3)));
                invoiceitem.setQty(new Integer(rs.getInt(4)));
                invoiceitem.setPrice(rs.getDouble(5));
                invoiceitem.setUpdatedAt(rs.getTimestamp(6));
                invoiceitem.setCreatedAt(rs.getTimestamp(7));
                invoiceitem.setTax(new Integer(rs.getInt(8)));
                invoiceitem.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return invoiceitem;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO invoiceitem ("+(getInvoiceitemId().intValue()!=0?"invoiceitem_id,":"")+"invoice_id,item_id,qty,price,updated_at,created_at,tax) values("+(getInvoiceitemId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getInvoiceitemId().intValue()!=0) {
                 ps.setObject(++n, getInvoiceitemId());
             }
             ps.setObject(++n, getInvoiceId());
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
         if (getInvoiceitemId().intValue()==0) {
             stmt = "SELECT max(invoiceitem_id) FROM invoiceitem";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setInvoiceitemId(new Integer(rs.getInt(1)));
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
                    "UPDATE invoiceitem " +
                    "SET invoice_id = ?, item_id = ?, qty = ?, price = ?, updated_at = ?, created_at = ?, tax = ?" + 
                    " WHERE invoiceitem_id = " + getInvoiceitemId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getInvoiceId());
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
                "DELETE FROM invoiceitem " +
                "WHERE invoiceitem_id = " + getInvoiceitemId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setInvoiceitemId(new Integer(-getInvoiceitemId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getInvoiceitemId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT invoiceitem_id,invoice_id,item_id,qty,price,updated_at,created_at,tax FROM invoiceitem " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Invoiceitem(con,new Integer(rs.getInt(1)),new Integer(rs.getInt(2)),new Integer(rs.getInt(3)),new Integer(rs.getInt(4)),rs.getDouble(5),rs.getTimestamp(6),rs.getTimestamp(7),new Integer(rs.getInt(8))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Invoiceitem[] objects = new Invoiceitem[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Invoiceitem invoiceitem = (Invoiceitem) lst.get(i);
            objects[i] = invoiceitem;
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
        String stmt = "SELECT invoiceitem_id FROM invoiceitem " +
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
    //    return getInvoiceitemId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return invoiceitemId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setInvoiceitemId(id);
        setNew(prevIsNew);
    }

    public Integer getInvoiceitemId() {
        return invoiceitemId;
    }

    public void setInvoiceitemId(Integer invoiceitemId) throws ForeignKeyViolationException {
        setWasChanged(this.invoiceitemId != null && this.invoiceitemId != invoiceitemId);
        this.invoiceitemId = invoiceitemId;
        setNew(invoiceitemId.intValue() == 0);
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) throws SQLException, ForeignKeyViolationException {
        if (invoiceId!=null && !Invoice.exists(getConnection(),"invoice_id = " + invoiceId)) {
            throw new ForeignKeyViolationException("Can't set invoice_id, foreign key violation: invoiceitem_invoice_fk");
        }
        setWasChanged(this.invoiceId != null && !this.invoiceId.equals(invoiceId));
        this.invoiceId = invoiceId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) throws SQLException, ForeignKeyViolationException {
        if (itemId!=null && !Item.exists(getConnection(),"item_id = " + itemId)) {
            throw new ForeignKeyViolationException("Can't set item_id, foreign key violation: invoiceitem_item_fk");
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
        columnValues[0] = getInvoiceitemId();
        columnValues[1] = getInvoiceId();
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
            setInvoiceitemId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setInvoiceitemId(null);
        }
        try {
            setInvoiceId(Integer.parseInt(flds[1]));
        } catch(NumberFormatException ne) {
            setInvoiceId(null);
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
