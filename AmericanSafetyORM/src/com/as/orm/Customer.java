// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Oct 09 20:06:39 EEST 2014
// generated file: do not modify
package com.as.orm;

import com.as.orm.dbobject.DbObject;
import com.as.orm.dbobject.ForeignKeyViolationException;
import com.as.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Customer extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer customerId = null;
    private String customerName = null;
    private String customerAddress = null;
    private Timestamp updatedAt = null;
    private Timestamp createdAt = null;

    public Customer(Connection connection) {
        super(connection, "customer", "customer_id");
        setColumnNames(new String[]{"customer_id", "customer_name", "customer_address", "updated_at", "created_at"});
    }

    public Customer(Connection connection, Integer customerId, String customerName, String customerAddress, Timestamp updatedAt, Timestamp createdAt) {
        super(connection, "customer", "customer_id");
        setNew(customerId.intValue() <= 0);
//        if (customerId.intValue() != 0) {
            this.customerId = customerId;
//        }
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Customer customer = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT customer_id,customer_name,customer_address,updated_at,created_at FROM customer WHERE customer_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                customer = new Customer(getConnection());
                customer.setCustomerId(new Integer(rs.getInt(1)));
                customer.setCustomerName(rs.getString(2));
                customer.setCustomerAddress(rs.getString(3));
                customer.setUpdatedAt(rs.getTimestamp(4));
                customer.setCreatedAt(rs.getTimestamp(5));
                customer.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return customer;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO customer ("+(getCustomerId().intValue()!=0?"customer_id,":"")+"customer_name,customer_address,updated_at,created_at) values("+(getCustomerId().intValue()!=0?"?,":"")+"?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getCustomerId().intValue()!=0) {
                 ps.setObject(++n, getCustomerId());
             }
             ps.setObject(++n, getCustomerName());
             ps.setObject(++n, getCustomerAddress());
             ps.setObject(++n, getUpdatedAt());
             ps.setObject(++n, getCreatedAt());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getCustomerId().intValue()==0) {
             stmt = "SELECT max(customer_id) FROM customer";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setCustomerId(new Integer(rs.getInt(1)));
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
                    "UPDATE customer " +
                    "SET customer_name = ?, customer_address = ?, updated_at = ?, created_at = ?" + 
                    " WHERE customer_id = " + getCustomerId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getCustomerName());
                ps.setObject(2, getCustomerAddress());
                ps.setObject(3, getUpdatedAt());
                ps.setObject(4, getCreatedAt());
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
        if (Order.exists(getConnection(),"customer_id = " + getCustomerId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: order_customer_fk");
        }
        if (Quote.exists(getConnection(),"customer_id = " + getCustomerId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: quote_customer_fk");
        }
        if (Contact.exists(getConnection(),"customer_id = " + getCustomerId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: contact_customer_fk");
        }
        if (Invoice.exists(getConnection(),"customer_id = " + getCustomerId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: invoice_customer_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM customer " +
                "WHERE customer_id = " + getCustomerId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setCustomerId(new Integer(-getCustomerId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getCustomerId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT customer_id,customer_name,customer_address,updated_at,created_at FROM customer " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Customer(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getTimestamp(4),rs.getTimestamp(5)));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Customer[] objects = new Customer[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Customer customer = (Customer) lst.get(i);
            objects[i] = customer;
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
        String stmt = "SELECT customer_id FROM customer " +
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
    //    return getCustomerId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return customerId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setCustomerId(id);
        setNew(prevIsNew);
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) throws ForeignKeyViolationException {
        setWasChanged(this.customerId != null && this.customerId != customerId);
        this.customerId = customerId;
        setNew(customerId.intValue() == 0);
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.customerName != null && !this.customerName.equals(customerName));
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.customerAddress != null && !this.customerAddress.equals(customerAddress));
        this.customerAddress = customerAddress;
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
        Object[] columnValues = new Object[5];
        columnValues[0] = getCustomerId();
        columnValues[1] = getCustomerName();
        columnValues[2] = getCustomerAddress();
        columnValues[3] = getUpdatedAt();
        columnValues[4] = getCreatedAt();
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
            setCustomerId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setCustomerId(null);
        }
        setCustomerName(flds[1]);
        setCustomerAddress(flds[2]);
        setUpdatedAt(toTimeStamp(flds[3]));
        setCreatedAt(toTimeStamp(flds[4]));
    }
}
