/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.as.service;

import com.as.Contact;
import com.as.Customer;
import com.as.Item;
import com.as.Po;
import com.as.Stamps;
import com.as.Tax;
import com.as.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author nick
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> cv = iterator.next();
                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
            }
        } else {
            getEntityManager().persist(entity);
        }
    }

    public String createAndReturnID(T entity) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if (constraintViolations.size() > 0) {
            StringBuilder sb = new StringBuilder();
            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation<T> cv = iterator.next();
                String errElement = cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage();
                System.err.println(errElement);
                sb.append(errElement).append("\n");
            }
            throw (new ConstraintViolationException(sb.toString(), constraintViolations));
        } else {
            getEntityManager().persist(entity);
        }
        return "0";
    }

    public String createList(List<T> entityList) {
        StringBuilder sb = new StringBuilder();
        for (T entity : entityList) {
            if (sb.length() > 0) {
                sb.append(",");
            }
            sb.append(createAndReturnID(entity));
        }
        return sb.toString();
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    protected byte[] createByteArray(InputStream inputStream) throws IOException {
        ArrayList<Byte> byteList = new ArrayList<Byte>(2048);
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            for (int i = 0; i < read; i++) {
                byteList.add(new Byte(bytes[i]));
            }
        }
        inputStream.close();
        byte[] arr = new byte[byteList.size()];
        for (int i = 0; i < byteList.size(); i++) {
            arr[i] = byteList.get(i).byteValue();
        }
        return arr;
    }

    protected void saveFile(byte[] bytes, String serverLocation, Class class4logger) {
        try {
            File file = new File(serverLocation);
            file.getParentFile().mkdirs();
            OutputStream outpuStream = new FileOutputStream(file);
            outpuStream = new FileOutputStream(new File(serverLocation));
            outpuStream.write(bytes, 0, bytes.length);
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            Logger.getLogger(class4logger.getName()).log(Level.SEVERE, null, e);
        }
    }

// save uploaded file to a defined location on the server
    protected void saveFile(InputStream uploadedInputStream, String serverLocation, Class class4logger) {
        try {
            File file = new File(serverLocation);
            file.getParentFile().mkdirs();
            OutputStream outpuStream = new FileOutputStream(file);
            int read = 0;
            byte[] bytes = new byte[1024];
            outpuStream = new FileOutputStream(new File(serverLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
            uploadedInputStream.close();
//            System.out.println("!!!File path:"+file.getAbsolutePath());
        } catch (IOException e) {
            Logger.getLogger(class4logger.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Contact loadContactOnId(Integer contactID) {
        try {
            return (Contact) getEntityManager().createNamedQuery("Contact.findByContactId").setParameter("contactId", contactID).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    protected Customer loadCustomerOnId(Integer customerID) {
        try {
            return (Customer) getEntityManager().createNamedQuery("Customer.findByCustomerId").setParameter("customerId", customerID).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    protected User loadUserOnId(Integer userID) {
        try {
            return (User) getEntityManager().createNamedQuery("User.findByUserId")
                    .setParameter("userId", userID).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    protected Po loadPoTypeOnID(Integer poID) {
        try {
            return (Po) getEntityManager().createNamedQuery("Po.findByPoId")
                    .setParameter("poId", poID).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    protected Tax loadTaxOnID(Integer taxID) {
        try {
            return (Tax) getEntityManager().createNamedQuery("Tax.findByTaxId")
                    .setParameter("taxId", taxID).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    protected Stamps loadStampsOnId(Integer stampID) {
        try {
            return (Stamps) getEntityManager().createNamedQuery("Stamps.findByStampsId")
                    .setParameter("stampsId", stampID).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    protected Item loadItemOnID(Integer itemID) {
        try {
            return (Item) getEntityManager().createNamedQuery("Item.findByItemId")
                    .setParameter("itemId", itemID).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    protected boolean sendEmail(String email, String subject, String body) {
        Properties mailProps = new Properties();
        String STARTTLS = "true";
        String AUTH = "true";
        String DEBUG = "true";
        String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";
        String SUBJECT = subject;
        String PORT = "465";
        String HOST = "smtp.yandex.ru";
        String FROM = "nm250660@yandex.ru";
        String PASSWORD = "ghbdtnnt";
        String USER = FROM;
        mailProps.put("mail.smtp.host", HOST);
        mailProps.put("mail.smtp.port", PORT);
        mailProps.put("mail.smtp.user", FROM);
        mailProps.put("mail.smtp.socketFactory.port", PORT);
        mailProps.put("mail.smtp.auth", AUTH);
        mailProps.put("mail.smtp.starttls.enable", STARTTLS);
        mailProps.put("mail.smtp.debug", DEBUG);
        mailProps.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
        mailProps.put("mail.smtp.socketFactory.fallback", "false");
        try {
            Session session = Session.getDefaultInstance(mailProps, null);
            MimeMessage message = new MimeMessage(session);
            message.setText(body);
            message.setSubject(SUBJECT);
            message.setFrom(new InternetAddress(FROM));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect(HOST, USER, PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
