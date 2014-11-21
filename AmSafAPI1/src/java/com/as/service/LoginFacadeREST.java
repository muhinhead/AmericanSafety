/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import com.as.Login;
import com.as.User;
import com.as.util.LoginParams;
import com.as.util.ParamLoginName;
import com.as.util.ResponseLogin;
import com.as.util.ResponseOk;
import com.as.util.Utils;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
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

/**
 *
 * @author nick
 */
@Stateless
@Path("com.as.login")
public class LoginFacadeREST extends AbstractFacade<Login> {
    @PersistenceContext(unitName = "AmSafAPI1PU")
    private EntityManager em;

    public LoginFacadeREST() {
        super(Login.class);
    }

//    @POST
    @Path("/uploadavatar")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json")
    public ResponseOk uploadAvatar(@Context HttpServletRequest request) {
        ResponseOk ok = new ResponseOk();
        try {
            Map<String, String[]> vals = request.getParameterMap();
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Login login = (Login) getEntityManager().createNamedQuery("Login.findByNameAndPassword")
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
            
            if (login != null) {
                User user = (User) getEntityManager().createNamedQuery("User.findByUserId")
                        .setParameter("userId", login.getUserID()).getSingleResult();
                Collection<Part> parts = request.getParts();
                for (Part part : parts) {
                    if (part.getContentType() != null) {
                        user.setAvatar(Utils.createByteArray(part.getInputStream()));
                        getEntityManager().merge(user);
                        ok.setResult(true);
                    }
                }
            } else {
                ok.setResult(false);
            }
        } catch (Exception e) {
            if (e.getMessage().indexOf("did not retrieve any entities") > 0) {
                ok.setErrorMsg(new String[]{"User name " + request.getParameter("username") + " not found"});
            } else {
                ok.setErrorMsg(new String[]{e.getMessage()});
            }
        }
        return ok;
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
    @Path("forgetpwd")
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
                sendEmail(user.getEmail(), "Password discard",
                        "Dear " + user.getFirstName() + " " + user.getLastName() + ",\n\n"
                        + "Your password in AmericanSafety application was discarded");
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
    public ResponseOk setPwd(LoginParams par) {
        ResponseOk ok = new ResponseOk();
        try {
            User user = (User) getEntityManager().createNamedQuery("User.findByLogin")
                    .setParameter("login", par.getUsername()).getSingleResult();
            if (user != null) {
                user.setPassword(par.getPassword());
                getEntityManager().merge(user);
                ok.setResult(true);
                sendEmail(user.getEmail(), "Your new password",
                        "Dear " + user.getFirstName() + " " + user.getLastName() + ",\n\n"
                        + "Here is your new password in AmericanSafety application: " + par.getPassword());
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
