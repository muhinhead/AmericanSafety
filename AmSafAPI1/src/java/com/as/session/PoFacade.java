/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.session;

import com.as.Po;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nick
 */
@Stateless
public class PoFacade extends AbstractFacade<Po> {
    @PersistenceContext(unitName = "AmSafAPI1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PoFacade() {
        super(Po.class);
    }
    
}