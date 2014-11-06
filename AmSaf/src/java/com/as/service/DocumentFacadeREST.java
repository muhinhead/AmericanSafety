/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import com.as.Document;
import com.as.DocumentPK;
import com.as.IDocument;
import com.as.Invoice;
import com.as.Invoiceitem;
import com.as.Order1;
import com.as.Orderitem;
import com.as.Quote;
import com.as.Quoteitem;
import com.as.util.DocumentsParams;
import com.as.util.ParamDocItem;
import com.as.util.ParamDocument4Submit;
import com.as.util.ResponseDocumentList;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;

/**
 *
 * @author nick
 */
@Stateless
@Path("com.as.document")
public class DocumentFacadeREST extends AbstractFacade<Document> {
    @PersistenceContext(unitName = "AmSafPU")
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
            String sql;
            Query qry = getEntityManager().createNativeQuery(
                    sql = "SELECT concat(document_id,' ',doc_type) FROM document"
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
            List<String> rids = qry.getResultList();
            List<Document> doclist = new ArrayList<Document>(rids.size());
            for (String docIDtype : rids) {
                int p = docIDtype.indexOf(' ');
                Integer docID = Integer.parseInt(docIDtype.substring(0,p));
                String docType = docIDtype.substring(p+1);
                Document doc = (Document) getEntityManager()
                        .createNamedQuery("Document.findByDocumentIDandType")
                        .setParameter("documentID", docID).setParameter("docType", docType).getSingleResult();
                doclist.add(doc);
            }
            ResponseDocumentList rdl = new ResponseDocumentList(doclist, null);
            return rdl;
        } catch (Exception e) {
            return new ResponseDocumentList(null, new String[]{e.getMessage()});
        }
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createDoc(ParamDocument4Submit pars) {
        String output = null;
        int code = 200;
        try {
            Integer docID;
            IDocument doc = createDocument(pars);
            if (pars.getDocumentType().equalsIgnoreCase("quote")) {
                Quote quote = (Quote) doc;
                docID = QuoteFacadeREST.createAndReturnID(quote, getEntityManager());
                quote.setQuoteitemCollection(createQuoteItemsCollection(quote, pars.getItems()));
                output = "{\"quoteID\":\"" + String.valueOf(docID) + "\"}";
            } else if (pars.getDocumentType().equalsIgnoreCase("order")) {
                Order1 order = (Order1) doc;
                docID = Order1FacadeREST.createAndReturnId(order, getEntityManager());
                order.setOrderitemCollection(createOrderItemsCollection(order, pars.getItems()));
                output = "{\"orderID\":\"" + String.valueOf(docID) + "\"}";
            } else if (pars.getDocumentType().equalsIgnoreCase("invoice")) {
                Invoice invoice = (Invoice) doc;
                docID = InvoiceFacadeREST.createAndReturnId(invoice, getEntityManager());
                invoice.setInvoiceitemCollection(createInvoiceItemsCollection(invoice, pars.getItems()));
                output = "{\"invoiceID\":\"" + String.valueOf(docID) + "\"}";
            }
        } catch (Exception ex) {
            Logger.getLogger(DocumentFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            code = 500;
            output = "{\"errorMsg\":[\"" + ex.getMessage() + "\"]}";
        }
        return Response.status(code).entity(output).build();
    }
    
    @POST    
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    public Response submitDocument(@Context HttpServletRequest request) throws ParseException, IOException, ServletException {
        return createDoc(new ParamDocument4Submit(request));
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") String id, Document entity) {
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
 
    public IDocument createDocument(ParamDocument4Submit pd) {
        IDocument idoc = null;
        if (pd.getDocumentType().equalsIgnoreCase("quote")) {
            Quote quote = new Quote();
            idoc = quote;
        } else if (pd.getDocumentType().equalsIgnoreCase("order")) {
            Order1 order = new Order1();
            idoc = order;
        } else if (pd.getDocumentType().equalsIgnoreCase("invoice")) {
            Invoice invoice = new Invoice();
            idoc = invoice;
        } else {
            return null;
        }
        idoc.setAfeUww(pd.getAfeUww());
        idoc.setAprvrName(pd.getAprvrName());
        idoc.setCai(pd.getCai());
        idoc.setContactId(loadContactOnId(pd.getContactID()));
        idoc.setContractor(pd.getContractor());
        idoc.setCreatedBy(loadUserOnId(pd.getUserID()));
        idoc.setCustomerId(loadCustomerOnId(pd.getCustomerID()));
        idoc.setDateIn(pd.getDateIn());
        idoc.setDateOut(pd.getDateOut());
        idoc.setDateStr(pd.getDateStr());
        idoc.setDiscount(pd.getDiscount());
        idoc.setLocation(pd.getLocation());
        idoc.setPoNumber(pd.getPoNumber());
        idoc.setPoTypeId(loadPoTypeOnID(pd.getPoTypeID()));
        idoc.setRigTankEq(pd.getRigTankEquipment());
        idoc.setSignature(pd.getImageSignature());
        idoc.setTaxId(loadTaxOnID(pd.getTaxID()));
        idoc.setStampsId(loadStampsOnId(pd.getStampID()));
        return idoc;
    }

    private Collection<Quoteitem> createQuoteItemsCollection(Quote quote, Collection<ParamDocItem> items) {
        List<Quoteitem> quoteItems = new ArrayList<Quoteitem>(items.size());
        for (ParamDocItem itm : items) {
            Quoteitem qi = new Quoteitem();
            qi.setQty(itm.getQty());
            qi.setPrice(itm.getSum().divide(BigDecimal.valueOf(itm.getQty().longValue()),2,BigDecimal.ROUND_DOWN));
            qi.setQuoteId(quote);
            qi.setItemId(loadItemOnID(itm.getItemID()));
            QuoteitemFacadeREST.createAndReturnId(qi, getEntityManager());
            quoteItems.add(qi);
        }
        return quoteItems;
    }

    private Collection<Orderitem> createOrderItemsCollection(Order1 order, Collection<ParamDocItem> items) {
        List<Orderitem> orderItems = new ArrayList<Orderitem>(items.size());
        for (ParamDocItem itm : items) {
            Orderitem oi = new Orderitem();
            oi.setQty(itm.getQty());
            oi.setPrice(itm.getSum().divide(BigDecimal.valueOf(itm.getQty().longValue()),2,BigDecimal.ROUND_DOWN));
            oi.setOrderId(order);
            oi.setItemId(loadItemOnID(itm.getItemID()));
            OrderitemFacadeREST.createAndReturnId(oi, getEntityManager());
            orderItems.add(oi);
        }
        return orderItems;
    }

    private Collection<Invoiceitem> createInvoiceItemsCollection(Invoice invoice, Collection<ParamDocItem> items) {
        List<Invoiceitem> invoiceItems = new ArrayList<Invoiceitem>(items.size());
        for (ParamDocItem itm : items) {
            Invoiceitem ii = new Invoiceitem();
            ii.setQty(itm.getQty());
            ii.setPrice(itm.getSum().divide(BigDecimal.valueOf(itm.getQty().longValue()),2,BigDecimal.ROUND_DOWN));
            ii.setInvoiceId(invoice);
            ii.setItemId(loadItemOnID(itm.getItemID()));
            InvoiceitemFacadeREST.createAndReturnId(ii, getEntityManager());
            invoiceItems.add(ii);
        }
        return invoiceItems;
    }
    
}
