/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.session;

import com.as.Invoiceitem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nick
 */
@Stateless
public class InvoiceitemFacade extends AbstractFacade<Invoiceitem> {
    @PersistenceContext(unitName = "AmSafPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvoiceitemFacade() {
        super(Invoiceitem.class);
    }
    
}
