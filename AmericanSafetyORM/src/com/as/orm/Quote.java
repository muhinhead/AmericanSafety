// Generated by com.xlend.orm.tools.dbgen.DbGenerator.class at Wed Oct 29 17:58:28 EET 2014
// generated file: do not modify
package com.as.orm;

import com.as.orm.dbobject.DbObject;
import com.as.orm.dbobject.ForeignKeyViolationException;
import com.as.orm.dbobject.Triggers;
import java.sql.*;
import java.util.ArrayList;

public class Quote extends DbObject implements IDocument {
    private static Triggers activeTriggers = null;
    private Integer quoteId = null;
    private String location = null;
    private String contractor = null;
    private Integer customerId = null;
    private Integer contactId = null;
    private String rigTankEq = null;
    private Double discount = null;
    private Double taxProc = null;
    private Integer taxId = null;
    private Integer poTypeId = null;
    private String poNumber = null;
    private Date dateIn = null;
    private Object signature = null;
    private Timestamp updatedAt = null;
    private Timestamp createdAt = null;
    private Integer createdBy = null;

    public Quote(Connection connection) {
        super(connection, "quote", "quote_id");
        setColumnNames(new String[]{"quote_id", "location", "contractor", "customer_id", "contact_id", "rig_tank_eq", "discount", "tax_proc", "tax_id", "po_type_id", "po_number", "date_in", "signature", "updated_at", "created_at", "created_by"});
    }

    public Quote(Connection connection, Integer quoteId, String location, String contractor, Integer customerId, Integer contactId, String rigTankEq, Double discount, Double taxProc, Integer taxId, Integer poTypeId, String poNumber, Date dateIn, Object signature, Timestamp updatedAt, Timestamp createdAt, Integer createdBy) {
        super(connection, "quote", "quote_id");
        setNew(quoteId.intValue() <= 0);
//        if (quoteId.intValue() != 0) {
            this.quoteId = quoteId;
//        }
        this.location = location;
        this.contractor = contractor;
        this.customerId = customerId;
        this.contactId = contactId;
        this.rigTankEq = rigTankEq;
        this.discount = discount;
        this.taxProc = taxProc;
        this.taxId = taxId;
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
        String stmt = "SELECT quote_id,location,contractor,customer_id,contact_id,rig_tank_eq,discount,tax_proc,tax_id,po_type_id,po_number,date_in,signature,updated_at,created_at,created_by FROM quote WHERE quote_id=" + id;
        try {
            ps = getConnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                quote = new Quote(getConnection());
                quote.setQuoteId(new Integer(rs.getInt(1)));
                quote.setLocation(rs.getString(2));
                quote.setContractor(rs.getString(3));
                quote.setCustomerId(new Integer(rs.getInt(4)));
                quote.setContactId(new Integer(rs.getInt(5)));
                quote.setRigTankEq(rs.getString(6));
                quote.setDiscount(rs.getDouble(7));
                quote.setTaxProc(rs.getDouble(8));
                quote.setTaxId(new Integer(rs.getInt(9)));
                quote.setPoTypeId(new Integer(rs.getInt(10)));
                quote.setPoNumber(rs.getString(11));
                quote.setDateIn(rs.getDate(12));
                quote.setSignature(rs.getObject(13));
                quote.setUpdatedAt(rs.getTimestamp(14));
                quote.setCreatedAt(rs.getTimestamp(15));
                quote.setCreatedBy(new Integer(rs.getInt(16)));
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
                "INSERT INTO quote ("+(getQuoteId().intValue()!=0?"quote_id,":"")+"location,contractor,customer_id,contact_id,rig_tank_eq,discount,tax_proc,tax_id,po_type_id,po_number,date_in,signature,updated_at,created_at,created_by) values("+(getQuoteId().intValue()!=0?"?,":"")+"?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
         try {
             ps = getConnection().prepareStatement(stmt);
             int n = 0;
             if (getQuoteId().intValue()!=0) {
                 ps.setObject(++n, getQuoteId());
             }
             ps.setObject(++n, getLocation());
             ps.setObject(++n, getContractor());
             ps.setObject(++n, getCustomerId());
             ps.setObject(++n, getContactId());
             ps.setObject(++n, getRigTankEq());
             ps.setObject(++n, getDiscount());
             ps.setObject(++n, getTaxProc());
             ps.setObject(++n, getTaxId());
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
                    "SET location = ?, contractor = ?, customer_id = ?, contact_id = ?, rig_tank_eq = ?, discount = ?, tax_proc = ?, tax_id = ?, po_type_id = ?, po_number = ?, date_in = ?, signature = ?, updated_at = ?, created_at = ?, created_by = ?" + 
                    " WHERE quote_id = " + getQuoteId();
            try {
                ps = getConnection().prepareStatement(stmt);
                ps.setObject(1, getLocation());
                ps.setObject(2, getContractor());
                ps.setObject(3, getCustomerId());
                ps.setObject(4, getContactId());
                ps.setObject(5, getRigTankEq());
                ps.setObject(6, getDiscount());
                ps.setObject(7, getTaxProc());
                ps.setObject(8, getTaxId());
                ps.setObject(9, getPoTypeId());
                ps.setObject(10, getPoNumber());
                ps.setObject(11, getDateIn());
                ps.setObject(12, getSignature());
                ps.setObject(13, getUpdatedAt());
                ps.setObject(14, getCreatedAt());
                ps.setObject(15, getCreatedBy());
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
        String stmt = "SELECT quote_id,location,contractor,customer_id,contact_id,rig_tank_eq,discount,tax_proc,tax_id,po_type_id,po_number,date_in,signature,updated_at,created_at,created_by FROM quote " +
                ((whereCondition != null && whereCondition.length() > 0) ?
                " WHERE " + whereCondition : "") +
                ((orderCondition != null && orderCondition.length() > 0) ?
                " ORDER BY " + orderCondition : "");
        try {
            ps = con.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                DbObject dbObj;
                lst.add(dbObj=new Quote(con,new Integer(rs.getInt(1)),rs.getString(2),rs.getString(3),new Integer(rs.getInt(4)),new Integer(rs.getInt(5)),rs.getString(6),rs.getDouble(7),rs.getDouble(8),new Integer(rs.getInt(9)),new Integer(rs.getInt(10)),rs.getString(11),rs.getDate(12),rs.getObject(13),rs.getTimestamp(14),rs.getTimestamp(15),new Integer(rs.getInt(16))));
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

    public String getContractor() {
        return contractor;
    }

    public void setContractor(String contractor) throws SQLException, ForeignKeyViolationException {
        setWasChanged(this.contractor != null && !this.contractor.equals(contractor));
        this.contractor = contractor;
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

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) throws SQLException, ForeignKeyViolationException {
        if (null != contactId)
            contactId = contactId == 0 ? null : contactId;
        if (contactId!=null && !Contact.exists(getConnection(),"contact_id = " + contactId)) {
            throw new ForeignKeyViolationException("Can't set contact_id, foreign key violation: quote_contact_fk");
        }
        setWasChanged(this.contactId != null && !this.contactId.equals(contactId));
        this.contactId = contactId;
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

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) throws SQLException, ForeignKeyViolationException {
        if (taxId!=null && !Tax.exists(getConnection(),"tax_id = " + taxId)) {
            throw new ForeignKeyViolationException("Can't set tax_id, foreign key violation: quote_tax_fk");
        }
        setWasChanged(this.taxId != null && !this.taxId.equals(taxId));
        this.taxId = taxId;
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
        Object[] columnValues = new Object[16];
        columnValues[0] = getQuoteId();
        columnValues[1] = getLocation();
        columnValues[2] = getContractor();
        columnValues[3] = getCustomerId();
        columnValues[4] = getContactId();
        columnValues[5] = getRigTankEq();
        columnValues[6] = getDiscount();
        columnValues[7] = getTaxProc();
        columnValues[8] = getTaxId();
        columnValues[9] = getPoTypeId();
        columnValues[10] = getPoNumber();
        columnValues[11] = getDateIn();
        columnValues[12] = getSignature();
        columnValues[13] = getUpdatedAt();
        columnValues[14] = getCreatedAt();
        columnValues[15] = getCreatedBy();
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
        setContractor(flds[2]);
        try {
            setCustomerId(Integer.parseInt(flds[3]));
        } catch(NumberFormatException ne) {
            setCustomerId(null);
        }
        try {
            setContactId(Integer.parseInt(flds[4]));
        } catch(NumberFormatException ne) {
            setContactId(null);
        }
        setRigTankEq(flds[5]);
        try {
            setDiscount(Double.parseDouble(flds[6]));
        } catch(NumberFormatException ne) {
            setDiscount(null);
        }
        try {
            setTaxProc(Double.parseDouble(flds[7]));
        } catch(NumberFormatException ne) {
            setTaxProc(null);
        }
        try {
            setTaxId(Integer.parseInt(flds[8]));
        } catch(NumberFormatException ne) {
            setTaxId(null);
        }
        try {
            setPoTypeId(Integer.parseInt(flds[9]));
        } catch(NumberFormatException ne) {
            setPoTypeId(null);
        }
        setPoNumber(flds[10]);
        setDateIn(toDate(flds[11]));
        setSignature(flds[12]);
        setUpdatedAt(toTimeStamp(flds[13]));
        setCreatedAt(toTimeStamp(flds[14]));
        try {
            setCreatedBy(Integer.parseInt(flds[15]));
        } catch(NumberFormatException ne) {
            setCreatedBy(null);
        }
    }
}
