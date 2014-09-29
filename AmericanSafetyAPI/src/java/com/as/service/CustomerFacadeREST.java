package com.as.service;

import com.as.Customer;
import com.as.util.ParamMask;
import com.as.util.ResponseCustomerList;
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

/**
 *
 * @author Nick Mukhin
 */
@Stateless
@Path("com.as.customer")
public class CustomerFacadeREST extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "AmericanSafetyAPIPU")
    private EntityManager em;

    public CustomerFacadeREST() {
        super(Customer.class);
    }

    @POST
    @Override
    @Consumes("application/json")
    public void create(Customer entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes("application/json")
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
    @Produces("application/json")
    public Customer find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @POST
    @Path("/findonmask")
    @Produces("application/json")
    @Consumes("application/json")
    public ResponseCustomerList findOnMask(ParamMask mask) {
        try {
//            List<Customer> clst = (List<Customer>) getEntityManager()
//                    .createNamedQuery("Customer.findByNameMask")
//                    .setParameter("mask", mask.getMask() + "%")
//                    .getResultList();
            Query qry = getEntityManager().createNativeQuery(
                    "SELECT customer_id FROM customer WHERE customer_name LIKE '"+mask.getMask()+"%' "
                            + " ORDER BY customer_name LIMIT " + (mask.getOffset() != null ? mask.getOffset().toString() + "," : "")
                    + (mask.getLimit() != null ? mask.getLimit().toString() : "9999999999999999999"));
            List<Integer> cids = qry.getResultList();
            List<Customer> clst = new ArrayList<Customer>(cids.size());
            for (Integer cid : cids) {
                Customer c = (Customer)getEntityManager().createNamedQuery("Customer.findByCustomerID")
                        .setParameter("customerID", cid).getSingleResult();
                clst.add(c);
            }
            return new ResponseCustomerList(clst, null);
        } catch (Exception e) {
            return new ResponseCustomerList(null, new String[]{e.getMessage()});
        }
    }

    @GET
    @Override
    @Produces("application/json")
    public List<Customer> findAll() {
        return super.findAll();
    }
//    @GET
//    @Path("{from}/{to}")
//    @Produces("application/json")
//    public List<Customer> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }

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
