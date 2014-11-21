/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import com.as.Invoiceitem;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
@Path("com.as.invoiceitem")
public class InvoiceitemFacadeREST extends AbstractFacade<Invoiceitem> {
    @PersistenceContext(unitName = "AmSafAPI1PU")
    private EntityManager em;

    public InvoiceitemFacadeREST() {
        super(Invoiceitem.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Invoiceitem entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Invoiceitem entity) {
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
    public Invoiceitem find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Invoiceitem> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Invoiceitem> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    public static Integer createAndReturnId(Invoiceitem entity, EntityManager em) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Invoiceitem>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            StringBuilder sb = new StringBuilder();
            Iterator<ConstraintViolation<Invoiceitem>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<Invoiceitem> cv = iterator.next();
                String errElement = cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage();
                System.err.println(errElement);
                sb.append(errElement).append("\n");
            }
            throw (new ConstraintViolationException(sb.toString(), constraintViolations));
        } else {
            em.persist(entity);
        }
        BigInteger bi = (BigInteger) em.createNativeQuery("select last_id from last_inserted_id").getSingleResult();
        return bi.intValue();
    }
    
}
