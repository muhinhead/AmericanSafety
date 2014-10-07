package com.as.orm.dbobject;

import java.sql.SQLException;

/**
 *
 * @author Nick Mukhin
 */
public abstract class Triggers {
    public abstract void beforeInsert(DbObject dbObject) throws SQLException;
    public abstract void afterInsert(DbObject dbObject) throws SQLException;

    public abstract void beforeUpdate(DbObject dbObject) throws SQLException;
    public abstract void afterUpdate(DbObject dbObject) throws SQLException;

    public abstract void beforeDelete(DbObject dbObject) throws SQLException;
    public abstract void afterDelete(DbObject dbObject) throws SQLException;
}
