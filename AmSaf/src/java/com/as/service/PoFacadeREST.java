/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import com.as.Po;
import com.as.util.ParamPage;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.ws.rs.core.Response;

/**
 *
 * @author nick
 */
@Stateless
@Path("com.as.po")
public class PoFacadeREST extends AbstractFacade<Po> {

    @PersistenceContext(unitName = "AmSafPU")
    private EntityManager em;

    public PoFacadeREST() {
        super(Po.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Po entity) {
        super.create(entity);
    }

    @POST
    @Path("/find")
    @Consumes("application/json")
    @Produces("application/json")
    public Response findPo(ParamPage parms) {
        StringBuilder output = null;
        int code = 200;
        try {
            Query qry = getEntityManager().createNativeQuery("SELECT po_id FROM po "
                    + " LIMIT " + (parms.getOffset() != null ? parms.getOffset().toString() + "," : "")
                    + (parms.getLimit() != null ? parms.getLimit().toString() : "9999999999999999999"));
            List<Integer> poIds = qry.getResultList();
            List<Po> poList = new ArrayList<Po>(poIds.size());
            output = new StringBuilder("{\"response\":[");
            boolean isFirst = true;
            for (Integer poID : poIds) {
                Po po = (Po) getEntityManager().createNamedQuery("Po.findByPoId")
                        .setParameter("poId", poID).getSingleResult();
                output.append(isFirst?"":",").append("{\"poId\":")
                        .append(po.getPoId().toString())
                        .append(",\"poDescription\":\"")
                        .append(po.getPoDescription()).append("\"}");
                isFirst = false;
            }
            output.append("]}");
        } catch (Exception ex) {
            Logger.getLogger(TaxFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            code = 500;
            output = new StringBuilder("{\"errorMsg\":[\"" + ex.getMessage() + "\"]}");
        }
        return Response.status(code).entity(output.toString()).build();        
    }
//    public ResponsePoList findPo(ParamPage parms) {
//        try {
//            Query qry = getEntityManager().createNativeQuery("SELECT po_id FROM po "
//                    + " LIMIT " + (parms.getOffset() != null ? parms.getOffset().toString() + "," : "")
//                    + (parms.getLimit() != null ? parms.getLimit().toString() : "9999999999999999999"));
//            List<Integer> poIds = qry.getResultList();
//            List<Po> poList = new ArrayList<>(poIds.size());
//            for (Integer poId : poIds) {
//                Po po = (Po) getEntityManager().createNamedQuery("Po.findByPoId")
//                        .setParameter("poId", poId).getSingleResult();
//                poList.add(po);
//            }
//            return new ResponsePoList(poList, null);
//        } catch (Exception e) {
//            return new ResponsePoList(null, new String[]{e.getMessage()});
//        }
//    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Po entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Po find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Po> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Po> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
