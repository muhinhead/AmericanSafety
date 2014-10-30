package com.as.service;

import com.as.Document;
import com.as.DocumentPK;
import com.as.util.DocumentsParams;
import com.as.util.ResponseDocument;
import com.as.util.ResponseDocumentList;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author Nick Mukhin
 */
@Stateless
@Path("com.as.document")
public class DocumentFacadeREST extends AbstractFacade<Document> {

    @PersistenceContext(unitName = "AmericanSafetyAPIPU")
    private EntityManager em;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private DocumentPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;documentId=documentIdValue;docType=docTypeValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        com.as.DocumentPK key = new com.as.DocumentPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> documentID = map.get("documentID");
        if (documentID != null && !documentID.isEmpty()) {
            key.setDocumentID(new java.lang.Integer(documentID.get(0)));
        }
        java.util.List<String> docType = map.get("docType");
        if (docType != null && !docType.isEmpty()) {
            key.setDocType(docType.get(0));
        }
        return key;
    }

    public DocumentFacadeREST() {
        super(Document.class);
    }

    @POST
    @Path("/find")
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseDocumentList findDocuments(DocumentsParams parms) {
        try {
            if (parms.getDocumentType() != null
                    && !parms.getDocumentType().equals("order")
                    && !parms.getDocumentType().equals("invoice")
                    && !parms.getDocumentType().equals("quote")) {
                return new ResponseDocumentList(null, new String[]{
                    "Wrong document type '" + parms.getDocumentType() + "'! Specify 'order' or 'invoice' or 'quote'"});
            }
            Query qry = getEntityManager().createNativeQuery(
                    "SELECT document_id FROM document"
                    + " WHERE 1=1 "
                    + (parms.getUserID() == null ? "" : " AND created_by=" + parms.getUserID())
                    + (parms.getDocumentType() == null ? "" : " AND doc_type='" + parms.getDocumentType() + "'")
                    + (parms.getStartFirstRangeTime() == null ? "" : " AND date_in>='" + dateFormat.format(parms.getStartFirstRangeTime()) + "'")
                    + (parms.getStartSecondRangeTime() == null ? "" : " AND date_in<='" + dateFormat.format(parms.getStartSecondRangeTime()) + "'")
                    + (parms.getFinishFirstRangeTime() == null ? "" : " AND date_out>='" + dateFormat.format(parms.getFinishFirstRangeTime()))
                    + (parms.getFinishSecondRangeTime() == null ? "" : " AND (date_out<='" + dateFormat.format(parms.getFinishSecondRangeTime()) + "' OR doc_type='quote')")
                    + (parms.getCustomerID() == null ? "" : " AND customer_id=" + parms.getCustomerID())
                    + " LIMIT " + (parms.getOffset() != null ? parms.getOffset().toString() + "," : "")
                    + (parms.getLimit() != null ? parms.getLimit().toString() : "9999999999999999999"));
            List<Integer> rids = qry.getResultList();
            List<Document> doclist = new ArrayList<Document>(rids.size());
            return new ResponseDocumentList(doclist, null);
        } catch (Exception e) {
            return new ResponseDocumentList(null, new String[]{e.getMessage()});
        }
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Document entity) {
        super.create(entity);
    }
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, Document entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        com.as.DocumentPK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces("application/json")
    public Document find(@PathParam("id") PathSegment id) {
        com.as.DocumentPK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Document> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Document> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
