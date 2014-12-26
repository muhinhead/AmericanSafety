/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import com.as.Department;
import com.as.Document;
import com.as.DocumentPK;
import com.as.IDocument;
import com.as.Invoice;
import com.as.Invoiceitem;
import com.as.Item;
import com.as.Order1;
import com.as.Orderitem;
import com.as.Quote;
import com.as.Quoteitem;
import com.as.User;
import com.as.Usersrole;
import com.as.util.DocumentsParams;
import com.as.util.ParamDocItem;
import com.as.util.ParamDocument4Submit;
import com.as.util.ResponseDocumentList;
import com.as.util.ResponseProxyDocument;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    @PersistenceContext(unitName = "AmSafAPI1PU")
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
        java.util.List<String> documentId = map.get("documentId");
        if (documentId != null && !documentId.isEmpty()) {
            key.setDocumentID(new java.lang.Integer(documentId.get(0)));
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
//    public Response findDocuments(DocumentsParams parms) {
        String output = null;
        int code = 200;
        try {
            String sql;
            Query qry = getEntityManager().createNativeQuery(
                    sql = "SELECT concat(document_id,' ',doc_type) FROM document"
                    + " WHERE 1=1 "
                    + (parms.getDepartmentID() == null ? ""
                            : " AND created_by in (select user_id from user where department_id=" + parms.getDepartmentID() + ")")
                    + (parms.getUserID() == null ? "" : " AND created_by=" + parms.getUserID())
                    + (parms.getPoNumber() == null ? "" : " AND po_number='" + parms.getPoNumber() + "'")
                    + (parms.getPo() == null ? createPoNullCondition(parms.getIsPo())
                            : " AND po_type_id in (" + createIntList(parms.getPo()) + ")")
                    + (parms.getDocumentType() == null || parms.getDocumentType().length == 0 ? "" : " AND doc_type in ('" + createTypeList(parms.getDocumentType()) + "')")
                    + (parms.getDocumentID() == null || parms.getDocumentID().length == 0 ? "" : " AND document_id in (" + createIntList(parms.getDocumentID()) + ")")
                    + (parms.getStartFirstRangeTime() == null ? "" : " AND date_in>='" + dateFormat.format(parms.getStartFirstRangeTime()) + "'")
                    + (parms.getStartSecondRangeTime() == null ? "" : " AND date_in<='" + dateFormat.format(parms.getStartSecondRangeTime()) + "'")
                    + (parms.getFinishFirstRangeTime() == null ? "" : " AND date_out>='" + dateFormat.format(parms.getFinishFirstRangeTime()))
                    + (parms.getFinishSecondRangeTime() == null ? "" : " AND (date_out<='" + dateFormat.format(parms.getFinishSecondRangeTime()) + "' OR doc_type='quote')")
                    + (parms.getCustomerID() == null ? "" : " AND customer_id=" + parms.getCustomerID())
                    + " LIMIT " + (parms.getOffset() != null ? parms.getOffset().toString() + "," : "")
                    + (parms.getLimit() != null ? parms.getLimit().toString() : "9999999999999999999"));
//            System.out.println("!!!"+sql);            
            List<String> rids = qry.getResultList();
            List<IDocument> doclist = new ArrayList<IDocument>(rids.size());
            for (String docIDtype : rids) {
                int p = docIDtype.indexOf(' ');
                Integer uniqDocID = Integer.parseInt(docIDtype.substring(0, p));
                String docType = docIDtype.substring(p + 1);
                Integer docID = (Integer) em.createNativeQuery(
                        "select document_id from document_ids where document_type='" + docType + "' and id=" + uniqDocID).getSingleResult();
                IDocument doc = null;
                if (docType.equals("order")) {
                    doc = (IDocument) getEntityManager()
                            .createNamedQuery("Order1.findByOrderId")
                            .setParameter("orderId", docID).getSingleResult();
                } else if (docType.equals("quote")) {
                    doc = (IDocument) getEntityManager()
                            .createNamedQuery("Quote.findByQuoteId")
                            .setParameter("quoteId", docID).getSingleResult();
                } else {
                    doc = (IDocument) getEntityManager()
                            .createNamedQuery("Invoice.findByInvoiceId")
                            .setParameter("invoiceId", docID).getSingleResult();
                }
                //System.out.println("!!original:"+doc.getDocumentId()+" new:"+uniqDocID);
                doclist.add(new ResponseProxyDocument(doc,uniqDocID));
            }
            ResponseDocumentList rdl = new ResponseDocumentList(doclist, null);
            return rdl;
        } catch (Exception e) {
            return new ResponseDocumentList(null, new String[]{e.getMessage()});
        }
//        return Response.status(code).entity(output).build();

    }

    //private
    
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response createDoc(ParamDocument4Submit pars) {
        String output = null;
        int code = 200;
        try {
            Integer docID;
            Integer uniqID;
            IDocument doc = createDocument(pars);
            if (pars.getDocumentType().equalsIgnoreCase("quote")) {
                Quote quote = (Quote) doc;
                docID = QuoteFacadeREST.createAndReturnID(quote, getEntityManager());
                quote.setQuoteitemCollection(createQuoteItemsCollection(quote, pars.getItems()));
                uniqID = (Integer) em.createNativeQuery(
                        "select id from document_ids where document_type='quote' and document_id=" + docID.toString()).getSingleResult();
                output = "{\"documentID\":" + uniqID.toString() + ",\"quoteID\":\"" + String.valueOf(docID) + "\"}";
            } else if (pars.getDocumentType().equalsIgnoreCase("order")) {
                Order1 order = (Order1) doc;
                docID = Order1FacadeREST.createAndReturnId(order, getEntityManager());
                order.setOrderitemCollection(createOrderItemsCollection(order, pars.getItems()));
                uniqID = (Integer) em.createNativeQuery(
                        "select id from document_ids where document_type='order' and document_id=" + docID.toString()).getSingleResult();
                output = "{\"documentID\":" + uniqID.toString() + ",\"orderID\":\"" + String.valueOf(docID) + "\"}";
            } else if (pars.getDocumentType().equalsIgnoreCase("invoice")) {
                Invoice invoice = (Invoice) doc;
                docID = InvoiceFacadeREST.createAndReturnId(invoice, getEntityManager());
                invoice.setInvoiceitemCollection(createInvoiceItemsCollection(invoice, pars.getItems()));
                uniqID = (Integer) em.createNativeQuery(
                        "select id from document_ids where document_type='invoice' and document_id=" + docID.toString()).getSingleResult();
                output = "{\"documentID\":" + uniqID.toString() + ",\"invoiceID\":\"" + String.valueOf(docID) + "\"}";
                if (!notifyAccountManagers(pars.getUserID(), invoice)) {
                    Logger.getLogger(DocumentFacadeREST.class.getName()).log(Level.SEVERE, null,
                            "Warning! No account managers found to notify from userID=" + pars.getUserID());
                }
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
    @Produces({"application/xml", "application/json"})
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
        idoc.setContact(loadContactOnId(pd.getContactID()));
        idoc.setContractor(pd.getContractor());
        idoc.setCreatedBy(loadUserOnId(pd.getUserID()));
        idoc.setCustomer(loadCustomerOnId(pd.getCustomerID()));
        idoc.setDateIn(pd.getDateIn());
        idoc.setDateOut(pd.getDateOut());
        idoc.setDateStr(pd.getDateStr());
        idoc.setDiscount(pd.getDiscount());
        idoc.setLocation(pd.getLocation());
        idoc.setPoNumber(pd.getPoNumber());
        idoc.setPoType(loadPoTypeOnID(pd.getPoTypeID()));
        idoc.setRigTankEq(pd.getRigTankEquipment());
        idoc.setSignature(pd.getImageSignature());
        idoc.setTax(loadTaxOnID(pd.getTaxID()));
        idoc.setStamp(loadStampsOnId(pd.getStampID()));
        return idoc;
    }

    private Collection<Quoteitem> createQuoteItemsCollection(Quote quote, Collection<ParamDocItem> items) {
        List<Quoteitem> quoteItems = new ArrayList<Quoteitem>(items.size());
        for (ParamDocItem itm : items) {
            Quoteitem qi = new Quoteitem();
            qi.setQty(itm.getQty());
            qi.setPrice(itm.getSum().divide(BigDecimal.valueOf(itm.getQty().longValue()), 2, BigDecimal.ROUND_DOWN));
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
            oi.setPrice(itm.getSum().divide(BigDecimal.valueOf(itm.getQty().longValue()), 2, BigDecimal.ROUND_DOWN));
            oi.setOrderId(order);
            Item item;
            oi.setItemId(item = loadItemOnID(itm.getItemID()));
            OrderitemFacadeREST.createAndReturnId(oi, getEntityManager());
            orderItems.add(oi);
            // set last price for the product item
            item.setLastPrice(oi.getPrice());
            getEntityManager().merge(item);
        }
        return orderItems;
    }

    private Collection<Invoiceitem> createInvoiceItemsCollection(Invoice invoice, Collection<ParamDocItem> items) {
        List<Invoiceitem> invoiceItems = new ArrayList<Invoiceitem>(items.size());
        for (ParamDocItem itm : items) {
            Invoiceitem ii = new Invoiceitem();
            ii.setQty(itm.getQty());
            ii.setPrice(itm.getSum().divide(BigDecimal.valueOf(itm.getQty().longValue()), 2, BigDecimal.ROUND_DOWN));
            ii.setInvoiceId(invoice);
            ii.setItemId(loadItemOnID(itm.getItemID()));
            InvoiceitemFacadeREST.createAndReturnId(ii, getEntityManager());
            invoiceItems.add(ii);
        }
        return invoiceItems;
    }

    private String createTypeList(String[] documentType) {
        StringBuilder sb = new StringBuilder();
        for (String docType : documentType) {
            if (sb.length() > 0) {
                sb.append("','");
            }
            sb.append(docType);
        }
        return sb.toString();
    }

    private String createIntList(String[] poType) {
        StringBuilder sb = new StringBuilder();
        for (String docType : poType) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(docType);
        }
        return sb.toString();
    }

    private String createIntList(Integer[] ids) {
        StringBuilder sb = new StringBuilder();
        for (Integer id : ids) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(id.toString());
        }
        return sb.toString();
    }

    private boolean notifyAccountManagers(Integer userID, Invoice invoice) {
        boolean sent = false;
        User user = (User) getEntityManager()
                .createNamedQuery("User.findByUserId")
                .setParameter("userId", userID).getSingleResult();
        if (user != null) {
            Department dpt = user.getDepartmentId();
            for (User mngr : dpt.getUserCollection()) {
                Collection<Usersrole> rolesCollection = mngr.getUsersroleCollection();
                for (Usersrole ur : rolesCollection) {
                    if (ur.getRoleId().getRoleName().equalsIgnoreCase("account manager")) {
                        sendEmail(mngr.getEmail(), "Notification on Invoice submission for " + mngr.getLogin(),
                                "Attention please!\n\n" + user.getFirstName()
                                + " " + user.getLastName() + " (" + user.getLogin() + ") has submitted Invoice# "
                                + invoice.getInvoiceId() + " for client "
                                + invoice.getCustomer().getCustomerName()
                                + " in the amount of $" + invoice.getSubtotal()
                                + " " + (invoice.getDateIn() != null ? " started on " + invoice.getDateIn().toString() : "")
                                + " " + (invoice.getDateOut() != null ? " and completed on " + invoice.getDateOut().toString() : "")
                                + " " + invoice.getPoType().getPoDescription() + "# " + invoice.getPoNumber() + "\n\n"
                                + "Please don't answer this email since it was sent by robot at "
                                + Calendar.getInstance().getTime().toString(), null
                        );
                        sent = true;
                    }
                }
            }
        }
        return sent;
    }

    private String createPoNullCondition(String[] isPo) {
        if (isPo != null) {
//            if (isPo.length == 1) {
//                if (isPo[0].equalsIgnoreCase("YES") || isPo[0].equalsIgnoreCase("TRUE")) {
//                    return " AND po_type_id IS NOT NULL";
//                } else if (isPo[0].equalsIgnoreCase("NO") || isPo[0].equalsIgnoreCase("FALSE")) {
//                    return " AND po_type_id IS NULL";
//                }
//            } else {
            Boolean notNull = null;
            Boolean isNull = null;
            for (String s : isPo) {
                if (s.equalsIgnoreCase("YES") || s.equalsIgnoreCase("TRUE")) {
                    notNull = Boolean.TRUE;
                } else if (s.equalsIgnoreCase("NO") || s.equalsIgnoreCase("FALSE")) {
                    isNull = Boolean.TRUE;
                }
            }
            if (notNull == null && isNull != null) {
                return " AND po_type_id IS NULL";
            } else if (isNull == null && notNull != null) {
                return " AND po_type_id IS NOT NULL";
            }
//            }
        }
        return "";
    }

}
