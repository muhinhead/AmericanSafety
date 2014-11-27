/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import com.as.Contact;
import com.as.Customer;
import com.as.util.Param4newContact;
import com.as.util.Param4newCustomer;
import com.as.util.ParamMask;
import com.as.util.ResponseCustomerList;
import com.as.util.ResponseNewCustomer;
import java.util.ArrayList;
import java.util.Collection;
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
@Path("com.as.customer")
public class CustomerFacadeREST extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "AmSafAPI1PU")
    private EntityManager em;

    public CustomerFacadeREST() {
        super(Customer.class);
    }

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseNewCustomer createCustomer(Param4newCustomer param) {
        Customer customer = new Customer();
        String sid = null;
        try {
            customer.setCustomerName(param.getCustomerName());
            customer.setCustomerAddress(param.getCustomerAddress());
            sid = createAndReturnID(customer);
            if (param.getContacts() != null) {
                for (Param4newContact cnt : param.getContacts()) {
                    Contact contact = new Contact();
                    contact.setCustomerId(customer);
                    contact.setTitle(cnt.getContactTitle());
                    contact.setFirstName(cnt.getContactFirstName());
                    contact.setLastName(cnt.getContactLastName());
                    contact.setEmail(cnt.getContactEmail());
                    contact.setPhone(cnt.getContactPhone());
                    cnt.setContactID(new Integer(Integer.parseInt(
                            createContactAndReturnID(getEntityManager(), contact))));
                }
                customer.setContactCollection(loadContactsForCustomer(customer));
            }
            return new ResponseNewCustomer(new Integer(Integer.parseInt(sid)),param.getContacts());
        } catch (Exception e) {
            if (customer != null && sid != null) {
                try {
                    getEntityManager().remove(getEntityManager().merge(customer));
                } catch (Exception ne) {
                }
            }
            e.printStackTrace();
            return new ResponseNewCustomer(e.getMessage());
        }
    }

    @POST
    @Path("/findonmask")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseCustomerList findOnMask(ParamMask mask) {
        try {
            Query qry = getEntityManager().createNativeQuery(
                    "SELECT customer_id FROM customer WHERE customer_name LIKE '" + mask.getMask() + "%' "
                    + " ORDER BY customer_name LIMIT " + (mask.getOffset() != null ? mask.getOffset().toString() + "," : "")
                    + (mask.getLimit() != null ? mask.getLimit().toString() : "9999999999999999999"));
            List<Integer> cids = qry.getResultList();
            List<Customer> clst = new ArrayList<Customer>(cids.size());
            for (Integer cid : cids) {
                Customer c = (Customer) getEntityManager().createNamedQuery("Customer.findByCustomerId")
                        .setParameter("customerId", cid).getSingleResult();
                c.setContactCollection(loadContactsForCustomer(c));
                clst.add(c);
            }
            return new ResponseCustomerList(clst, null);
        } catch (Exception e) {
            return new ResponseCustomerList(null, new String[]{e.getMessage()});
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Customer entity) {
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
    public Customer find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Customer> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Customer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

    private Collection<Contact> loadContactsForCustomer(Customer customer) {
        return getEntityManager().createNamedQuery("Contact.findByCustomerId")
                .setParameter("customerId", customer).getResultList();
    }

}
