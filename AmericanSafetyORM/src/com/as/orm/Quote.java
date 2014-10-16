// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Thu Oct 09 20:06:39 EEST 2014
// generated file: do not modify
package com.as.orm;

import com.as.orm.dbobject.DbObject;
import com.as.orm.dbobject.ForeignKeyViolationException;
import com.as.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Quote extends DbObject  {
    private static Triggers activeTriggers = null;
    private Integer quoteId = null;
    private String location = null;
    private String contactor = null;
    private Integer customerId = null;
    private String rigTankEq = null;
    private Double discount = null;
    private Double taxProc = null;
    private Integer poTypeId = null;
    private String poNumber = null;
    private Date dateIn = null;
    private Object signature = null;
    private Timestamp updatedAt = null;
    private Timestamp createdAt = null;
    private Integer createdBy = null;

    public Quote(Connection connection) {
        super(connection, "quote", "quote_id");
        setColumnNames(new String[]{"quote_id", "location", "contactor", "customer_id", "rig_tank_eq", "discount", "tax_proc", "po_type_id", "po_number", "date_in", "signature", "updated_at", "created_at", "created_by"});
    }

    public Quote(Connection connection, Integer quoteId, String location, String contactor, Integer customerId, String rigTankEq, Double discount, Double taxProc, Integer poTypeId, String poNumber, Date dateIn, Object signature, Timestamp updatedAt, Timestamp createdAt, Integer createdBy) {
        super(connection, "quote", "quote_id");
        setNew(quoteId.intValue() <= 0);
//        if (quoteId.intValue() != 0) {
            this.quoteId = quoteId;
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
        this.signature = signature;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public DbObject loadOnId(int id) throws SQLException, ForeignKeyViolationException {
        Quote quote = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT quote_id,location,contactor,customer_id,rig_tank_eq,discount,tax_proc,po_type_id,po_number,date_in,signature,updated_at,created_at,created_by FROM quote WHERE quote_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                quote = new Quote(getConnection());
                quote.setQuoteId(new Integer(rs.getInt(1)));
                quote.setLocation(rs.getString(2));
                quote.setContactor(rs.getString(3));
                quote.setCustomerId(new Integer(rs.getInt(4)));
                quote.setRigTankEq(rs.getString(5));
                quote.setDiscount(rs.getDouble(6));
                quote.setTaxProc(rs.getDouble(7));
                quote.setPoTypeId(new Integer(rs.getInt(8)));
                quote.setPoNumber(rs.getString(9));
                quote.setDateIn(rs.getDate(10));
                quote.setSignature(rs.getObject(11));
                quote.setUpdatedAt(rs.getTimestamp(12));
                quote.setCreatedAt(rs.getTimestamp(13));
                quote.setCreatedBy(new Integer(rs.getInt(14)));
                quote.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        return quote;
    }

    protected void insert() throws SQLException, ForeignKeyViolationException {
         if (getTriggers() != null) {
             getTriggers().beforeInsert(this);
         }
         PreparedStatement ps = null;
         String stmt =
                "INSERT INTO quote ("+(getQuoteId().intValue()!=0?"quote_id,":"")+"location,contactor,customer_id,rig_tank_eq,discount,tax_proc,po_type_id,po_number,date_in,signature,updated_at,created_at,created_by) values("+(getQuoteId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getQuoteId().intValue()!=0) {
                 ps.setObject(++n, getQuoteId());
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
             ps.setObject(++n, getSignature());
             ps.setObject(++n, getUpdatedAt());
             ps.setObject(++n, getCreatedAt());
             ps.setObject(++n, getCreatedBy());
             ps.execute();
         } finally {
             if (ps != null) ps.close();
         }
         ResultSet rs = null;
         if (getQuoteId().intValue()==0) {
             stmt = "SELECT max(quote_id) FROM quote";
             try {
                 ps = getConnection().prepareStatement(stmt);
                 rs = ps.executeQuery();
                 if (rs.next()) {
                     setQuoteId(new Integer(rs.getInt(1)));
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
                    "UPDATE quote " +
                    "SET location = ?, contactor = ?, customer_id = ?, rig_tank_eq = ?, discount = ?, tax_proc = ?, po_type_id = ?, po_number = ?, date_in = ?, signature = ?, updated_at = ?, created_at = ?, created_by = ?" + 
                    " WHERE quote_id = " + getQuoteId();
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
                ps.setObject(10, getSignature());
                ps.setObject(11, getUpdatedAt());
                ps.setObject(12, getCreatedAt());
                ps.setObject(13, getCreatedBy());
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
        if (Quoteitem.exists(getConnection(),"quote_id = " + getQuoteId())) {
            throw new ForeignKeyViolationException("Can't delete, foreign key violation: quoteitem_quote_fk");
        }
        if (getTriggers() != null) {
            getTriggers().beforeDelete(this);
        }
        PreparedStatement ps = null;
        String stmt =
                "DELETE FROM quote " +
                "WHERE quote_id = " + getQuoteId();
        try {
            ps = getConnection().prepareStatement(stmt);
            ps.execute();
        } finally {
            if (ps != null) ps.close();
        }
        setQuoteId(new Integer(-getQuoteId().intValue()));
        if (getTriggers() != null) {
            getTriggers().afterDelete(this);
        }
    }

    public boolean isDeleted() {
        return (getQuoteId().intValue() < 0);
    }

    public static DbObject[] load(Connection con,String whereCondition,String orderCondition) throws SQLException {
        ArrayList lst = new ArrayList();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT quote_id,location,contactor,customer_id,rig_tank_eq,discount,tax_proc,po_type_id,po_number,date_in,signature,updated_at,created_at,created_by FROM quote " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Quote(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),new Integer(rs.getInt(4)),rs.getString(5),rs.getDouble(6),rs.getDouble(7),new Integer(rs.getInt(8)),rs.getString(9),rs.getDate(10),rs.getObject(11),rs.getTimestamp(12),rs.getTimestamp(13),new Integer(rs.getInt(14))));
                dbObj.setNew(false);
            }
        } finally {
            try {
                if (rs != null) rs.close();
            } finally {
                if (ps != null) ps.close();
            }
        }
        Quote[] objects = new Quote[lst.size()];
        for (int i = 0; i < lst.size(); i++) {
            Quote quote = (Quote) lst.get(i);
            objects[i] = quote;
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
        String stmt = "SELECT quote_id FROM quote " +
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
    //    return getQuoteId() + getDelimiter();
    //}

    public Integer getPK_ID() {
        return quoteId;
    }

    public void setPK_ID(Integer id) throws ForeignKeyViolationException {
        boolean prevIsNew = isNew();
        setQuoteId(id);
        setNew(prevIsNew);
    }

    public Integer getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(Integer quoteId) throws ForeignKeyViolationException {
        setWasChanged(this.quoteId != null && this.quoteId != quoteId);
        this.quoteId = quoteId;
        setNew(quoteId.intValue() == 0);
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
            throw new ForeignKeyViolationException("Can't set customer_id, foreign key violation: quote_customer_fk");
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
            throw new ForeignKeyViolationException("Can't set po_type_id, foreign key violation: quote_po_fk");
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
            throw new ForeignKeyViolationException("Can't set created_by, foreign key violation: quote_user_fk");
        }
        setWasChanged(this.createdBy != null && !this.createdBy.equals(createdBy));
        this.createdBy = createdBy;
    }
    public Object[] getAsRow() {
        Object[] columnValues = new Object[14];
        columnValues[0] = getQuoteId();
        columnValues[1] = getLocation();
        columnValues[2] = getContactor();
        columnValues[3] = getCustomerId();
        columnValues[4] = getRigTankEq();
        columnValues[5] = getDiscount();
        columnValues[6] = getTaxProc();
        columnValues[7] = getPoTypeId();
        columnValues[8] = getPoNumber();
        columnValues[9] = getDateIn();
        columnValues[10] = getSignature();
        columnValues[11] = getUpdatedAt();
        columnValues[12] = getCreatedAt();
        columnValues[13] = getCreatedBy();
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
            setQuoteId(Integer.parseInt(flds[0]));
        } catch(NumberFormatException ne) {
            setQuoteId(null);
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
        setSignature(flds[10]);
        setUpdatedAt(toTimeStamp(flds[11]));
        setCreatedAt(toTimeStamp(flds[12]));
        try {
            setCreatedBy(Integer.parseInt(flds[13]));
        } catch(NumberFormatException ne) {
            setCreatedBy(null);
        }
    }
}