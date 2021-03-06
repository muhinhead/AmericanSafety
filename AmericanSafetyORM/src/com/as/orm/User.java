// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Nov 27 11:32:33 EET 2014
// generated file: do not modify
package com.as.orm;

import com.as.orm.dbobject.DbObject;
import com.as.orm.dbobject.ForeignKeyViolationException;
import com.as.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class User extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer userId = null;
    private String firstName = null;
    private String lastName = null;
    private String email = null;
    private String login = null;
    private String password = null;
    private Timestamp updatedAt = null;
    private Timestamp createdAt = null;
    private Object avatar = null;
    private String url = null;
    private Integer departmentId = null;

    public User(Connection connection) {
        super(connection, "user", "user_id");
        setColumnNames(new String[]{"user_id", "first_name", "last_name", "email", "login", "password", "updated_at", "created_at", "avatar", "url", "department_id"});
    }

    public User(Connection connection, Integer userId, String firstName, String lastName, String email, String login, String password, Timestamp updatedAt, Timestamp createdAt, Object avatar, String url, Integer departmentId) {
        super(connection, "user", "user_id");
        setNew(userId.intValue() <= 0);
//        if (userId.intValue() != 0) {
            this.userId = userId;
//        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.login = login;
        this.password = password;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.avatar = avatar;
        this.url = url;
        this.departmentId = departmentId;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        User user = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT user_id,first_name,last_name,email,login,password,updated_at,created_at,avatar,url,department_id FROM user WHERE user_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(getConnection());
                user.setUserId(new Integer(rs.getInt(1)));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setLogin(rs.getString(5));
                user.setPassword(rs.getString(6));
                user.setUpdatedAt(rs.getTimestamp(7));
                user.setCreatedAt(rs.getTimestamp(8));
                user.setAvatar(rs.getObject(9));
                user.setUrl(rs.getString(10));
                user.setDepartmentId(new Integer(rs.getInt(11)));
                user.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return user;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO user ("+(getUserId().intValue()!=0?"user_id,":"")+"first_name,last_name,email,login,password,updated_at,created_at,avatar,url,department_id) values("+(getUserId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getUserId().intValue()!=0) {
                 ps.setObject(++n, getUserId());
             }
             ps.setObject(++n, getFirstName());
             ps.setObject(++n, getLastName());
             ps.setObject(++n, getEmail());
             ps.setObject(++n, getLogin());
             ps.setObject(++n, getPassword());
             ps.setObject(++n, getUpdatedAt());
             ps.setObject(++n, getCreatedAt());
             ps.setObject(++n, getAvatar());
             ps.setObject(++n, getUrl());
             ps.setObject(++n, getDepartmentId());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getUserId().intValue()==0) {
             stmt = "SELECT max(user_id) FROM user";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setUserId(new Integer(rs.getInt(1)));
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
                    "UPDATE user " +
                    "SET first_name = ?, last_name = ?, email = ?, login = ?, password = ?, updated_at = ?, created_at = ?, avatar = ?, url = ?, department_id = ?" + 
                    " WHERE user_id = " + getUserId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getFirstName());
                ps.setObject(2, getLastName());
                ps.setObject(3, getEmail());
                ps.setObject(4, getLogin());
                ps.setObject(5, getPassword());
                ps.setObject(6, getUpdatedAt());
                ps.setObject(7, getCreatedAt());
                ps.setObject(8, getAvatar());
                ps.setObject(9, getUrl());
                ps.setObject(10, getDepartmentId());
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
        if (Invoice.exists(getConnection(),"created_by = " + getUserId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: invoice_user_fk");
        }
        if (Order.exists(getConnection(),"created_by = " + getUserId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: order_user_fk");
        }
        if (Quote.exists(getConnection(),"created_by = " + getUserId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: quote_user_fk");
        }
        if (Usersrole.exists(getConnection(),"user_id = " + getUserId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: usersrole_user_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM user " +
                "WHERE user_id = " + getUserId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setUserId(new Integer(-getUserId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getUserId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT user_id,first_name,last_name,email,login,password,updated_at,created_at,avatar,url,department_id FROM user " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new User(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getTimestamp(7),rs.getTimestamp(8),rs.getObject(9),rs.getString(10),new Integer(rs.getInt(11))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        User[] objects = new User[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            User user = (User) lst.get(i);
            objects[i] = user;
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
        String stmt = "SELECT user_id FROM user " +
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
    //    return getUserId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return userId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setUserId(id);
        setNew(prevIsNew);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) throws ForeignKeyViolationException {
        setWasChanged(this.userId != null && this.userId != userId);
        this.userId = userId;
        setNew(userId.intValue() == 0);
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.login != null && !this.login.equals(login));
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.password != null && !this.password.equals(password));
        this.password = password;
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

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.avatar != null && !this.avatar.equals(avatar));
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.url != null && !this.url.equals(url));
        this.url = url;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) throws SQLException, ForeignKeyViolationException {
        if (null != departmentId)
            departmentId = departmentId == 0 ? null : departmentId;
//        if (departmentId!=null && !Department.exists(getConnection(),"department_id = " + departmentId)) {
//            throw new ForeignKeyViolationException("Can't set department_id, foreign key violation: user_department_fk");
//        }
        setWasChanged(this.departmentId != null && !this.departmentId.equals(departmentId));
        this.departmentId = departmentId;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[11];
        columnValues[0] = getUserId();
        columnValues[1] = getFirstName();
        columnValues[2] = getLastName();
        columnValues[3] = getEmail();
        columnValues[4] = getLogin();
        columnValues[5] = getPassword();
        columnValues[6] = getUpdatedAt();
        columnValues[7] = getCreatedAt();
        columnValues[8] = getAvatar();
        columnValues[9] = getUrl();
        columnValues[10] = getDepartmentId();
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
            setUserId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setUserId(null);
        }
        setFirstName(flds[1]);
        setLastName(flds[2]);
        setEmail(flds[3]);
        setLogin(flds[4]);
        setPassword(flds[5]);
        setUpdatedAt(toTimeStamp(flds[6]));
        setCreatedAt(toTimeStamp(flds[7]));
        setAvatar(flds[8]);
        setUrl(flds[9]);
        try {
            setDepartmentId(Integer.parseInt(flds[10]));
        } catch(NumberFormatException ne) {
            setDepartmentId(null);
        }
    }
}
