package com.as.service;

import com.as.Login;
import com.as.User;
import com.as.util.LoginParams;
import com.as.util.ParamLoginName;
import com.as.util.ResponseLogin;
import com.as.util.ResponseOk;
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
import javax.ws.rs.core.Response;

/**
 *
 * @author Nick Mukhin
 */
@Stateless
@Path("com.as.login")
public class LoginFacadeREST extends AbstractFacade<Login> {

    @PersistenceContext(unitName = "AmericanSafetyAPIPU")
    private EntityManager em;

    public LoginFacadeREST() {
        super(Login.class);
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseLogin findByNameAndPassword(LoginParams entity) {
        String errMsg[] = null;
        Login login = null;
        try {
            login = (Login) em.createNamedQuery("Login.findByNameAndPassword")
                    .setParameter("username", entity.getUsername())
                    .setParameter("password", entity.getPassword())
                    .getSingleResult();
            if (login.getPassword() == null || login.getPassword().length() == 0) {
                login = null;
                errMsg = new String[]{"Unknow user or wrong password entered"};
            }
        } catch (Exception e) {
            if (e.getMessage().startsWith("getSingleResult() did not retrieve any entities")) {
                errMsg = new String[]{"Unknow user or wrong password entered"};
            } else {
                errMsg = new String[]{e.getMessage()};
            }
        }
        return new ResponseLogin(login, errMsg);
    }

    @POST
    @Path("forgotpwd")
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseOk forgotPwd(ParamLoginName par) {
        ResponseOk ok = new ResponseOk();
        try {
            User user = (User) getEntityManager().createNamedQuery("User.findByLogin")
                    .setParameter("login", par.getUsername()).getSingleResult();
            if (user != null) {
                user.setPassword(null);
                getEntityManager().merge(user);
                ok.setResult(true);
            }
        } catch (Exception e) {
            if (e.getMessage().indexOf("did not retrieve any entities") > 0) {
                ok.setErrorMsg(new String[]{"User named " + par.getUsername() + " not found"});
            } else {
                ok.setErrorMsg(new String[]{e.getMessage()});
            }
        }
        return ok;
    }

    @POST
    @Path("setpwd")
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseOk forgotPwd(LoginParams par) {
        ResponseOk ok = new ResponseOk();
        try {
            User user = (User) getEntityManager().createNamedQuery("User.findByLogin")
                    .setParameter("login", par.getUsername()).getSingleResult();
            if (user != null) {
                user.setPassword(par.getPassword());
                getEntityManager().merge(user);
                ok.setResult(true);
            }
        } catch (Exception e) {
            if (e.getMessage().indexOf("did not retrieve any entities") > 0) {
                ok.setErrorMsg(new String[]{"User named " + par.getUsername() + " not found"});
            } else {
                ok.setErrorMsg(new String[]{e.getMessage()});
            }
        }
        return ok;
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Login entity) {
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
    public Login find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Login> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Login> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
