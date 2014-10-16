package com.as;

import com.as.orm.dbobject.DbObject;
import com.as.util.RecordEditPanel;

/**
 *
 * @author Nick Mukhin
 */
abstract class EditDocumentPanel extends RecordEditPanel {

    public EditDocumentPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        //TOOD
    }

    @Override
    public void loadData() {
        //TOOD
    }

    @Override
    public boolean save() throws Exception {
        //TOOD
        return true;
    }
}
