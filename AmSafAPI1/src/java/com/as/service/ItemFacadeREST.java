/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import com.as.Item;
import com.as.util.ParamMask;
import com.as.util.ResponseItemList;
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
@Path("com.as.item")
public class ItemFacadeREST extends AbstractFacade<Item> {
    @PersistenceContext(unitName = "AmSafAPI1PU")
    private EntityManager em;

    public ItemFacadeREST() {
        super(Item.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Item entity) {
        super.create(entity);
    }

    @POST
    @Path("/find")
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseItemList findStamps(ParamMask parms) {
        try {
            Query qry = getEntityManager().createNativeQuery(
                    "SELECT item_id FROM item"
                    + (parms.getMask() != null ? 
                            " WHERE concat(item_name,' ',item_description) LIKE '%" + parms.getMask() + "%'" : "")
                    + " LIMIT " + (parms.getOffset() != null ? parms.getOffset().toString() + "," : "")
                    + (parms.getLimit() != null ? parms.getLimit().toString() : "9999999999999999999"));
            List<Integer> itemIds = qry.getResultList();
            List<Item> itemsList = new ArrayList<Item>(itemIds.size());
            for (Integer stampsID : itemIds) {
                Item item = (Item) getEntityManager().createNamedQuery("Item.findByItemId")
                        .setParameter("itemId", stampsID).getSingleResult();
                itemsList.add(item);
            }
            return new ResponseItemList(itemsList, null);
        } catch (Exception e) {
            return new ResponseItemList(null, new String[]{e.getMessage()});
        }
    }
    
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Item entity) {
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
    public Item find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Item> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Item> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
