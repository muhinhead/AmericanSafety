/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import com.as.Contact;
import com.as.Customer;
import com.as.util.Param4newContact;
import com.as.util.ResponseNewContact;
import com.as.util.ResponseNewCustomer;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Path("com.as.contact")
public class ContactFacadeREST extends AbstractFacade<Contact> {

    @PersistenceContext(unitName = "AmSafAPI1PU")
    private EntityManager em;

    public ContactFacadeREST() {
        super(Contact.class);
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseNewContact createContact(Param4newContact entity) {
        try {
            Customer customer = null;
            if (entity.getCustomerID() == null) {
                return new ResponseNewContact("CustomerID not specified!");
            } else {
                customer = (Customer) getEntityManager().createNamedQuery("Customer.findByCustomerId")
                        .setParameter("customerId", entity.getCustomerID()).getSingleResult();
            }
            if (entity.getContactLastName() == null) {
                return new ResponseNewContact("Contact Last Name not specified!");
            }
            if (entity.getContactEmail() == null) {
                return new ResponseNewContact("Contact e-mail not specified!");
            }
            Contact contact = new Contact();
            contact.setCustomerId(customer);
            contact.setFirstName(entity.getContactFirstName());
            contact.setLastName(entity.getContactLastName());
            contact.setEmail(entity.getContactEmail());
            contact.setPhone(entity.getContactPhone());
            contact.setTitle(entity.getContactTitle());
            String sid = createAndReturnID(contact);
            customer.setContactCollection(reloadContactCollection(customer));
            return new ResponseNewContact(new Integer(Integer.parseInt(sid)));
        } catch (Exception e) {
            if (e.getMessage().startsWith("getSingleResult() did not retrieve any entities")) {
                return new ResponseNewContact("Unknown CustomerID " + entity.getCustomerID());
            } else {
                e.printStackTrace();
                return new ResponseNewContact(e.getMessage());
            }
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Contact entity) {
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
    public Contact find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Contact> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Contact> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    private Collection<Contact> reloadContactCollection(Customer customer) {
        return (Collection<Contact>) getEntityManager().createNamedQuery("Contact.findByCustomerId")
                .setParameter("customerId", customer).getResultList();
    }

}
