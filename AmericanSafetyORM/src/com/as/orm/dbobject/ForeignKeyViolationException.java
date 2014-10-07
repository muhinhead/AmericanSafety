package com.as.orm.dbobject;

/**
 *
 * @author Nick Mukhin
 */
public class ForeignKeyViolationException extends Exception {
    public ForeignKeyViolationException(String s) {
        super(s);
    }
}
