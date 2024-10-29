/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iammagis.appropiate.beans.Factura;
import com.iammagis.appropiate.beans.Transaccion;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sebasariz
 */
public class TransaccionJpaController implements Serializable {

    public TransaccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Transaccion transaccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura facturaIdfactura = transaccion.getFacturaIdfactura();
            if (facturaIdfactura != null) {
                facturaIdfactura = em.getReference(facturaIdfactura.getClass(), facturaIdfactura.getIdfactura());
                transaccion.setFacturaIdfactura(facturaIdfactura);
            }
            em.persist(transaccion);
            if (facturaIdfactura != null) {
                facturaIdfactura.getTransaccionCollection().add(transaccion);
                facturaIdfactura = em.merge(facturaIdfactura);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Transaccion transaccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaccion persistentTransaccion = em.find(Transaccion.class, transaccion.getIdtransaccion());
            Factura facturaIdfacturaOld = persistentTransaccion.getFacturaIdfactura();
            Factura facturaIdfacturaNew = transaccion.getFacturaIdfactura();
            if (facturaIdfacturaNew != null) {
                facturaIdfacturaNew = em.getReference(facturaIdfacturaNew.getClass(), facturaIdfacturaNew.getIdfactura());
                transaccion.setFacturaIdfactura(facturaIdfacturaNew);
            }
            transaccion = em.merge(transaccion);
            if (facturaIdfacturaOld != null && !facturaIdfacturaOld.equals(facturaIdfacturaNew)) {
                facturaIdfacturaOld.getTransaccionCollection().remove(transaccion);
                facturaIdfacturaOld = em.merge(facturaIdfacturaOld);
            }
            if (facturaIdfacturaNew != null && !facturaIdfacturaNew.equals(facturaIdfacturaOld)) {
                facturaIdfacturaNew.getTransaccionCollection().add(transaccion);
                facturaIdfacturaNew = em.merge(facturaIdfacturaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = transaccion.getIdtransaccion();
                if (findTransaccion(id) == null) {
                    throw new NonexistentEntityException("The transaccion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaccion transaccion;
            try {
                transaccion = em.getReference(Transaccion.class, id);
                transaccion.getIdtransaccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transaccion with id " + id + " no longer exists.", enfe);
            }
            Factura facturaIdfactura = transaccion.getFacturaIdfactura();
            if (facturaIdfactura != null) {
                facturaIdfactura.getTransaccionCollection().remove(transaccion);
                facturaIdfactura = em.merge(facturaIdfactura);
            }
            em.remove(transaccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Transaccion> findTransaccionEntities() {
        return findTransaccionEntities(true, -1, -1);
    }

    public List<Transaccion> findTransaccionEntities(int maxResults, int firstResult) {
        return findTransaccionEntities(false, maxResults, firstResult);
    }

    private List<Transaccion> findTransaccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Transaccion.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Transaccion findTransaccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Transaccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransaccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Transaccion> rt = cq.from(Transaccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
