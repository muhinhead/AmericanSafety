// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Oct 09 20:06:39 EEST 2014
// generated file: do not modify
package com.as.orm;

import com.as.orm.dbobject.DbObject;
import com.as.orm.dbobject.ForeignKeyViolationException;
import com.as.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Order extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer orderId = null;
    private String location = null;
    private String contactor = null;
    private Integer customerId = null;
    private String rigTankEq = null;
    private Double discount = null;
    private Double taxProc = null;
    private Integer poTypeId = null;
    private String poNumber = null;
    private Date dateIn = null;
    private Date dateOut = null;
    private Object signature = null;
    private Timestamp updatedAt = null;
    private Timestamp createdAt = null;
    private Integer createdBy = null;

    public Order(Connection connection) {
        super(connection, "order", "order_id");
        setColumnNames(new String[]{"order_id", "location", "contactor", "customer_id", "rig_tank_eq", "discount", "tax_proc", "po_type_id", "po_number", "date_in", "date_out", "signature", "updated_at", "created_at", "created_by"});
    }

    public Order(Connection connection, Integer orderId, String location, String contactor, Integer customerId, String rigTankEq, Double discount, Double taxProc, Integer poTypeId, String poNumber, Date dateIn, Date dateOut, Object signature, Timestamp updatedAt, Timestamp createdAt, Integer createdBy) {
        super(connection, "order", "order_id");
        setNew(orderId.intValue() <= 0);
//        if (orderId.intValue() != 0) {
            this.orderId = orderId;
//        }
        this.location = location;
        this.contactor = contactor;
        this.customerId = customerId;
        this.rigTankEq = rigTankEq;
        this.discount = discount;
        this.taxProc = taxProc;
        this.poTypeId = poTypeId;
        this.poNumber = poNumber;
        this.dateIn = dateIn;
        this.dateOut = dateOut;
        this.signature = signature;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Order order = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT order_id,location,contactor,customer_id,rig_tank_eq,discount,tax_proc,po_type_id,po_number,date_in,date_out,signature,updated_at,created_at,created_by FROM `order` WHERE order_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                order = new Order(getConnection());
                order.setOrderId(new Integer(rs.getInt(1)));
                order.setLocation(rs.getString(2));
                order.setContactor(rs.getString(3));
                order.setCustomerId(new Integer(rs.getInt(4)));
                order.setRigTankEq(rs.getString(5));
                order.setDiscount(rs.getDouble(6));
                order.setTaxProc(rs.getDouble(7));
                order.setPoTypeId(new Integer(rs.getInt(8)));
                order.setPoNumber(rs.getString(9));
                order.setDateIn(rs.getDate(10));
                order.setDateOut(rs.getDate(11));
                order.setSignature(rs.getObject(12));
                order.setUpdatedAt(rs.getTimestamp(13));
                order.setCreatedAt(rs.getTimestamp(14));
                order.setCreatedBy(new Integer(rs.getInt(15)));
                order.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return order;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO `order` ("+(getOrderId().intValue()!=0?"order_id,":"")+"location,contactor,customer_id,rig_tank_eq,discount,tax_proc,po_type_id,po_number,date_in,date_out,signature,updated_at,created_at,created_by) values("+(getOrderId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getOrderId().intValue()!=0) {
                 ps.setObject(++n, getOrderId());
             }
             ps.setObject(++n, getLocation());
             ps.setObject(++n, getContactor());
             ps.setObject(++n, getCustomerId());
             ps.setObject(++n, getRigTankEq());
             ps.setObject(++n, getDiscount());
             ps.setObject(++n, getTaxProc());
             ps.setObject(++n, getPoTypeId());
             ps.setObject(++n, getPoNumber());
             ps.setObject(++n, getDateIn());
             ps.setObject(++n, getDateOut());
             ps.setObject(++n, getSignature());
             ps.setObject(++n, getUpdatedAt());
             ps.setObject(++n, getCreatedAt());
             ps.setObject(++n, getCreatedBy());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getOrderId().intValue()==0) {
             stmt = "SELECT max(order_id) FROM `order`";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setOrderId(new Integer(rs.getInt(1)));
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
                    "UPDATE `order` " +
                    "SET location = ?, contactor = ?, customer_id = ?, rig_tank_eq = ?, discount = ?, tax_proc = ?, po_type_id = ?, po_number = ?, date_in = ?, date_out = ?, signature = ?, updated_at = ?, created_at = ?, created_by = ?" + 
                    " WHERE order_id = " + getOrderId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getLocation());
                ps.setObject(2, getContactor());
                ps.setObject(3, getCustomerId());
                ps.setObject(4, getRigTankEq());
                ps.setObject(5, getDiscount());
                ps.setObject(6, getTaxProc());
                ps.setObject(7, getPoTypeId());
                ps.setObject(8, getPoNumber());
                ps.setObject(9, getDateIn());
                ps.setObject(10, getDateOut());
                ps.setObject(11, getSignature());
                ps.setObject(12, getUpdatedAt());
                ps.setObject(13, getCreatedAt());
                ps.setObject(14, getCreatedBy());
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
        if (Orderitem.exists(getConnection(),"order_id = " + getOrderId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: orderitem_order_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM `order` " +
                "WHERE order_id = " + getOrderId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setOrderId(new Integer(-getOrderId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getOrderId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT order_id,location,contactor,customer_id,rig_tank_eq,discount,tax_proc,po_type_id,po_number,date_in,date_out,signature,updated_at,created_at,created_by FROM `order` " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Order(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),new Integer(rs.getInt(4)),rs.getString(5),rs.getDouble(6),rs.getDouble(7),new Integer(rs.getInt(8)),rs.getString(9),rs.getDate(10),rs.getDate(11),rs.getObject(12),rs.getTimestamp(13),rs.getTimestamp(14),new Integer(rs.getInt(15))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Order[] objects = new Order[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Order order = (Order) lst.get(i);
            objects[i] = order;
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
        String stmt = "SELECT order_id FROM `order` " +
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
    //    return getOrderId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return orderId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setOrderId(id);
        setNew(prevIsNew);
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) throws ForeignKeyViolationException {
        setWasChanged(this.orderId != null && this.orderId != orderId);
        this.orderId = orderId;
        setNew(orderId.intValue() == 0);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.location != null && !this.location.equals(location));
        this.location = location;
    }

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.contactor != null && !this.contactor.equals(contactor));
        this.contactor = contactor;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) throws SQLException, ForeignKeyViolationException {
        if (null != customerId)
            customerId = customerId == 0 ? null : customerId;
        if (customerId!=null && !Customer.exists(getConnection(),"customer_id = " + customerId)) {
            throw new ForeignKeyViolationException("Can't set customer_id, foreign key violation: order_customer_fk");
        }
        setWasChanged(this.customerId != null && !this.customerId.equals(customerId));
        this.customerId = customerId;
    }

    public String getRigTankEq() {
        return rigTankEq;
    }

    public void setRigTankEq(String rigTankEq) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.rigTankEq != null && !this.rigTankEq.equals(rigTankEq));
        this.rigTankEq = rigTankEq;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.discount != null && !this.discount.equals(discount));
        this.discount = discount;
    }

    public Double getTaxProc() {
        return taxProc;
    }

    public void setTaxProc(Double taxProc) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.taxProc != null && !this.taxProc.equals(taxProc));
        this.taxProc = taxProc;
    }

    public Integer getPoTypeId() {
        return poTypeId;
    }

    public void setPoTypeId(Integer poTypeId) throws SQLException, ForeignKeyViolationException {
        if (null != poTypeId)
            poTypeId = poTypeId == 0 ? null : poTypeId;
        if (poTypeId!=null && !Po.exists(getConnection(),"po_id = " + poTypeId)) {
            throw new ForeignKeyViolationException("Can't set po_type_id, foreign key violation: order_po_fk");
        }
        setWasChanged(this.poTypeId != null && !this.poTypeId.equals(poTypeId));
        this.poTypeId = poTypeId;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.poNumber != null && !this.poNumber.equals(poNumber));
        this.poNumber = poNumber;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.dateIn != null && !this.dateIn.equals(dateIn));
        this.dateIn = dateIn;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.dateOut != null && !this.dateOut.equals(dateOut));
        this.dateOut = dateOut;
    }

    public Object getSignature() {
        return signature;
    }

    public void setSignature(Object signature) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.signature != null && !this.signature.equals(signature));
        this.signature = signature;
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

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) throws SQLException, ForeignKeyViolationException {
        if (createdBy!=null && !User.exists(getConnection(),"user_id = " + createdBy)) {
            throw new ForeignKeyViolationException("Can't set created_by, foreign key violation: order_user_fk");
        }
        setWasChanged(this.createdBy != null && !this.createdBy.equals(createdBy));
        this.createdBy = createdBy;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[15];
        columnValues[0] = getOrderId();
        columnValues[1] = getLocation();
        columnValues[2] = getContactor();
        columnValues[3] = getCustomerId();
        columnValues[4] = getRigTankEq();
        columnValues[5] = getDiscount();
        columnValues[6] = getTaxProc();
        columnValues[7] = getPoTypeId();
        columnValues[8] = getPoNumber();
        columnValues[9] = getDateIn();
        columnValues[10] = getDateOut();
        columnValues[11] = getSignature();
        columnValues[12] = getUpdatedAt();
        columnValues[13] = getCreatedAt();
        columnValues[14] = getCreatedBy();
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
            setOrderId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setOrderId(null);
        }
        setLocation(flds[1]);
        setContactor(flds[2]);
        try {
            setCustomerId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setCustomerId(null);
        }
        setRigTankEq(flds[4]);
        try {
            setDiscount(Double.parseDouble(flds[5]));
        } catch(NumberFormatException ne) {
            setDiscount(null);
        }
        try {
            setTaxProc(Double.parseDouble(flds[6]));
        } catch(NumberFormatException ne) {
            setTaxProc(null);
        }
        try {
            setPoTypeId(Integer.parseInt(flds[7]));
        } catch(NumberFormatException ne) {
            setPoTypeId(null);
        }
        setPoNumber(flds[8]);
        setDateIn(toDate(flds[9]));
        setDateOut(toDate(flds[10]));
        setSignature(flds[11]);
        setUpdatedAt(toTimeStamp(flds[12]));
        setCreatedAt(toTimeStamp(flds[13]));
        try {
            setCreatedBy(Integer.parseInt(flds[14]));
        } catch(NumberFormatException ne) {
            setCreatedBy(null);
        }
    }
}