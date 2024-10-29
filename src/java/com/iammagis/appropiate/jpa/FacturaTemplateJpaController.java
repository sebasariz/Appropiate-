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
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Factura;
import com.iammagis.appropiate.beans.FacturaTemplate;
import com.iammagis.appropiate.jpa.exceptions.IllegalOrphanException;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sebasariz
 */
public class FacturaTemplateJpaController implements Serializable {

    public FacturaTemplateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaTemplate facturaTemplate) {
        if (facturaTemplate.getFacturaCollection() == null) {
            facturaTemplate.setFacturaCollection(new ArrayList<Factura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad entidadIdentidad = facturaTemplate.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad = em.getReference(entidadIdentidad.getClass(), entidadIdentidad.getIdentidad());
                facturaTemplate.setEntidadIdentidad(entidadIdentidad);
            }
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : facturaTemplate.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getIdfactura());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            facturaTemplate.setFacturaCollection(attachedFacturaCollection);
            em.persist(facturaTemplate);
            if (entidadIdentidad != null) {
                entidadIdentidad.getFacturaTemplateCollection().add(facturaTemplate);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            for (Factura facturaCollectionFactura : facturaTemplate.getFacturaCollection()) {
                FacturaTemplate oldFacturaTemplateIdfacturaTemplateOfFacturaCollectionFactura = facturaCollectionFactura.getFacturaTemplateIdfacturaTemplate();
                facturaCollectionFactura.setFacturaTemplateIdfacturaTemplate(facturaTemplate);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldFacturaTemplateIdfacturaTemplateOfFacturaCollectionFactura != null) {
                    oldFacturaTemplateIdfacturaTemplateOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldFacturaTemplateIdfacturaTemplateOfFacturaCollectionFactura = em.merge(oldFacturaTemplateIdfacturaTemplateOfFacturaCollectionFactura);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaTemplate facturaTemplate) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaTemplate persistentFacturaTemplate = em.find(FacturaTemplate.class, facturaTemplate.getIdfacturaTemplate());
            Entidad entidadIdentidadOld = persistentFacturaTemplate.getEntidadIdentidad();
            Entidad entidadIdentidadNew = facturaTemplate.getEntidadIdentidad();
            Collection<Factura> facturaCollectionOld = persistentFacturaTemplate.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = facturaTemplate.getFacturaCollection();
            List<String> illegalOrphanMessages = null;
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaCollectionOldFactura + " since its facturaTemplateIdfacturaTemplate field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (entidadIdentidadNew != null) {
                entidadIdentidadNew = em.getReference(entidadIdentidadNew.getClass(), entidadIdentidadNew.getIdentidad());
                facturaTemplate.setEntidadIdentidad(entidadIdentidadNew);
            }
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getIdfactura());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            facturaTemplate.setFacturaCollection(facturaCollectionNew);
            facturaTemplate = em.merge(facturaTemplate);
            if (entidadIdentidadOld != null && !entidadIdentidadOld.equals(entidadIdentidadNew)) {
                entidadIdentidadOld.getFacturaTemplateCollection().remove(facturaTemplate);
                entidadIdentidadOld = em.merge(entidadIdentidadOld);
            }
            if (entidadIdentidadNew != null && !entidadIdentidadNew.equals(entidadIdentidadOld)) {
                entidadIdentidadNew.getFacturaTemplateCollection().add(facturaTemplate);
                entidadIdentidadNew = em.merge(entidadIdentidadNew);
            }
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    FacturaTemplate oldFacturaTemplateIdfacturaTemplateOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getFacturaTemplateIdfacturaTemplate();
                    facturaCollectionNewFactura.setFacturaTemplateIdfacturaTemplate(facturaTemplate);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldFacturaTemplateIdfacturaTemplateOfFacturaCollectionNewFactura != null && !oldFacturaTemplateIdfacturaTemplateOfFacturaCollectionNewFactura.equals(facturaTemplate)) {
                        oldFacturaTemplateIdfacturaTemplateOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldFacturaTemplateIdfacturaTemplateOfFacturaCollectionNewFactura = em.merge(oldFacturaTemplateIdfacturaTemplateOfFacturaCollectionNewFactura);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturaTemplate.getIdfacturaTemplate();
                if (findFacturaTemplate(id) == null) {
                    throw new NonexistentEntityException("The facturaTemplate with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FacturaTemplate facturaTemplate;
            try {
                facturaTemplate = em.getReference(FacturaTemplate.class, id);
                facturaTemplate.getIdfacturaTemplate();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaTemplate with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Factura> facturaCollectionOrphanCheck = facturaTemplate.getFacturaCollection();
            for (Factura facturaCollectionOrphanCheckFactura : facturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FacturaTemplate (" + facturaTemplate + ") cannot be destroyed since the Factura " + facturaCollectionOrphanCheckFactura + " in its facturaCollection field has a non-nullable facturaTemplateIdfacturaTemplate field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Entidad entidadIdentidad = facturaTemplate.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad.getFacturaTemplateCollection().remove(facturaTemplate);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            em.remove(facturaTemplate);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaTemplate> findFacturaTemplateEntities() {
        return findFacturaTemplateEntities(true, -1, -1);
    }

    public List<FacturaTemplate> findFacturaTemplateEntities(int maxResults, int firstResult) {
        return findFacturaTemplateEntities(false, maxResults, firstResult);
    }

    private List<FacturaTemplate> findFacturaTemplateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturaTemplate.class));
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

    public FacturaTemplate findFacturaTemplate(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaTemplate.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaTemplateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturaTemplate> rt = cq.from(FacturaTemplate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
