/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import com.as.Stamps;
import com.as.util.ParamPage;
import com.as.util.ResponseStampsList;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author nick
 */
@Stateless
@Path("com.as.stamps")
public class StampsFacadeREST extends AbstractFacade<Stamps> {
    @PersistenceContext(unitName = "AmSafPU")
    private EntityManager em;

    public StampsFacadeREST() {
        super(Stamps.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Stamps entity) {
        super.create(entity);
    }

@POST
    @Path("/find")
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseStampsList findStamps(ParamPage parms) {
        try {
            Query qry = getEntityManager().createNativeQuery("SELECT stamps_id FROM stamps "
                    + " LIMIT " + (parms.getOffset() != null ? parms.getOffset().toString() + "," : "")
                    + (parms.getLimit() != null ? parms.getLimit().toString() : "9999999999999999999"));
            //System.out.println("!!sql:"+sql);
            List<Integer> stampsIds = qry.getResultList();
            
            List<Stamps> stampsList = new ArrayList<Stamps>(stampsIds.size());
            for (Integer stampsID : stampsIds) {
                Stamps stamps = (Stamps) getEntityManager().createNamedQuery("Stamps.findByStampsId")
                        .setParameter("stampsId", stampsID).getSingleResult();
                stampsList.add(stamps);
            }
            return new ResponseStampsList(stampsList, null);
        } catch (Exception e) {
            return new ResponseStampsList(null, new String[]{e.getMessage()});
        }
    }
        
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Stamps entity) {
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
    public Stamps find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Stamps> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Stamps> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
