// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Oct 09 20:06:39 EEST 2014
// generated file: do not modify
package com.as.orm;

import com.as.orm.dbobject.DbObject;
import com.as.orm.dbobject.ForeignKeyViolationException;
import com.as.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Contact extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer contactId = null;
    private String title = null;
    private String firstName = null;
    private String lastName = null;
    private String email = null;
    private String phone = null;
    private Timestamp updatedAt = null;
    private Timestamp createdAt = null;
    private Integer customerId = null;

    public Contact(Connection connection) {
        super(connection, "contact", "contact_id");
        setColumnNames(new String[]{"contact_id", "title", "first_name", "last_name", "email", "phone", "updated_at", "created_at", "customer_id"});
    }

    public Contact(Connection connection, Integer contactId, String title, String firstName, String lastName, String email, String phone, Timestamp updatedAt, Timestamp createdAt, Integer customerId) {
        super(connection, "contact", "contact_id");
        setNew(contactId.intValue() <= 0);
//        if (contactId.intValue() != 0) {
            this.contactId = contactId;
//        }
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.customerId = customerId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Contact contact = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT contact_id,title,first_name,last_name,email,phone,updated_at,created_at,customer_id FROM contact WHERE contact_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                contact = new Contact(getConnection());
                contact.setContactId(new Integer(rs.getInt(1)));
                contact.setTitle(rs.getString(2));
                contact.setFirstName(rs.getString(3));
                contact.setLastName(rs.getString(4));
                contact.setEmail(rs.getString(5));
                contact.setPhone(rs.getString(6));
                contact.setUpdatedAt(rs.getTimestamp(7));
                contact.setCreatedAt(rs.getTimestamp(8));
                contact.setCustomerId(new Integer(rs.getInt(9)));
                contact.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return contact;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO contact ("+(getContactId().intValue()!=0?"contact_id,":"")+"title,first_name,last_name,email,phone,updated_at,created_at,customer_id) values("+(getContactId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getContactId().intValue()!=0) {
                 ps.setObject(++n, getContactId());
             }
             ps.setObject(++n, getTitle());
             ps.setObject(++n, getFirstName());
             ps.setObject(++n, getLastName());
             ps.setObject(++n, getEmail());
             ps.setObject(++n, getPhone());
             ps.setObject(++n, getUpdatedAt());
             ps.setObject(++n, getCreatedAt());
             ps.setObject(++n, getCustomerId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getContactId().intValue()==0) {
             stmt = "SELECT max(contact_id) FROM contact";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setContactId(new Integer(rs.getInt(1)));
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
                    "UPDATE contact " +
                    "SET title = ?, first_name = ?, last_name = ?, email = ?, phone = ?, updated_at = ?, created_at = ?, customer_id = ?" + 
                    " WHERE contact_id = " + getContactId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getTitle());
                ps.setObject(2, getFirstName());
                ps.setObject(3, getLastName());
                ps.setObject(4, getEmail());
                ps.setObject(5, getPhone());
                ps.setObject(6, getUpdatedAt());
                ps.setObject(7, getCreatedAt());
                ps.setObject(8, getCustomerId());
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
                "DELETE FROM contact " +
                "WHERE contact_id = " + getContactId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setContactId(new Integer(-getContactId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getContactId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT contact_id,title,first_name,last_name,email,phone,updated_at,created_at,customer_id FROM contact " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Contact(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getTimestamp(7),rs.getTimestamp(8),new Integer(rs.getInt(9))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Contact[] objects = new Contact[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Contact contact = (Contact) lst.get(i);
            objects[i] = contact;
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
        String stmt = "SELECT contact_id FROM contact " +
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
    //    return getContactId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return contactId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setContactId(id);
        setNew(prevIsNew);
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) throws ForeignKeyViolationException {
        setWasChanged(this.contactId != null && this.contactId != contactId);
        this.contactId = contactId;
        setNew(contactId.intValue() == 0);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.title != null && !this.title.equals(title));
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.firstName != null && !this.firstName.equals(firstName));
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.lastName != null && !this.lastName.equals(lastName));
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.email != null && !this.email.equals(email));
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.phone != null && !this.phone.equals(phone));
        this.phone = phone;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) throws SQLException, ForeignKeyViolationException {
        if (customerId!=null && !Customer.exists(getConnection(),"customer_id = " + customerId)) {
            throw new ForeignKeyViolationException("Can't set customer_id, foreign key violation: contact_customer_fk");
        }
        setWasChanged(this.customerId != null && !this.customerId.equals(customerId));
        this.customerId = customerId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[9];
        columnValues[0] = getContactId();
        columnValues[1] = getTitle();
        columnValues[2] = getFirstName();
        columnValues[3] = getLastName();
        columnValues[4] = getEmail();
        columnValues[5] = getPhone();
        columnValues[6] = getUpdatedAt();
        columnValues[7] = getCreatedAt();
        columnValues[8] = getCustomerId();
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
            setContactId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setContactId(null);
        }
        setTitle(flds[1]);
        setFirstName(flds[2]);
        setLastName(flds[3]);
        setEmail(flds[4]);
        setPhone(flds[5]);
        setUpdatedAt(toTimeStamp(flds[6]));
        setCreatedAt(toTimeStamp(flds[7]));
        try {
            setCustomerId(Integer.parseInt(flds[8]));
        } catch(NumberFormatException ne) {
            setCustomerId(null);
        }
    }
}