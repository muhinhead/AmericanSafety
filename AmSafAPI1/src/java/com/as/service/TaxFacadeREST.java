/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import com.as.Tax;
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
@Path("com.as.tax")
public class TaxFacadeREST extends AbstractFacade<Tax> {
    @PersistenceContext(unitName = "AmSafAPI1PU")
    private EntityManager em;

    public TaxFacadeREST() {
        super(Tax.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Tax entity) {
        super.create(entity);
    }

    @POST
    @Path("/find")
    @Consumes("application/json")
    @Produces("application/json")
    public Response findTax(ParamPage parms) {
        StringBuilder output = null;
        int code = 200;
        try {
            Query qry = getEntityManager().createNativeQuery("SELECT tax_id FROM tax "
                    + " LIMIT " + (parms.getOffset() != null ? parms.getOffset().toString() + "," : "")
                    + (parms.getLimit() != null ? parms.getLimit().toString() : "9999999999999999999"));
            List<Integer> taxIds = qry.getResultList();
            List<Tax> taxList = new ArrayList<Tax>(taxIds.size());
            output = new StringBuilder("{\"response\":[");
            boolean isFirst = true;
            for (Integer taxID : taxIds) {
                Tax tax = (Tax) getEntityManager().createNamedQuery("Tax.findByTaxId")
                        .setParameter("taxId", taxID).getSingleResult();
                output.append(isFirst?"":",").append("{\"taxId\":")
                        .append(tax.getTaxId().toString())
                        .append(",\"taxDescription\":\"")
                        .append(tax.getTaxDescription()).append("\"}");
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
    
    
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Tax entity) {
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
    public Tax find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Tax> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Tax> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
