/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.jpa;

import com.iammagis.appropiate.beans.Actividad;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sebasariz
 */
public class ActividadJpaController implements Serializable {

    public ActividadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Actividad create(Actividad actividad) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad entidadIdentidad = actividad.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad = em.getReference(entidadIdentidad.getClass(), entidadIdentidad.getIdentidad());
                actividad.setEntidadIdentidad(entidadIdentidad);
            }
            em.persist(actividad);
            if (entidadIdentidad != null) {
                entidadIdentidad.getActividadCollection().add(actividad);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            em.getTransaction().commit();
            return actividad;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Actividad actividad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actividad persistentActividad = em.find(Actividad.class, actividad.getIdactividad());
            Entidad entidadIdentidadOld = persistentActividad.getEntidadIdentidad();
            Entidad entidadIdentidadNew = actividad.getEntidadIdentidad();
            if (entidadIdentidadNew != null) {
                entidadIdentidadNew = em.getReference(entidadIdentidadNew.getClass(), entidadIdentidadNew.getIdentidad());
                actividad.setEntidadIdentidad(entidadIdentidadNew);
            }
            actividad = em.merge(actividad);
            if (entidadIdentidadOld != null && !entidadIdentidadOld.equals(entidadIdentidadNew)) {
                entidadIdentidadOld.getActividadCollection().remove(actividad);
                entidadIdentidadOld = em.merge(entidadIdentidadOld);
            }
            if (entidadIdentidadNew != null && !entidadIdentidadNew.equals(entidadIdentidadOld)) {
                entidadIdentidadNew.getActividadCollection().add(actividad);
                entidadIdentidadNew = em.merge(entidadIdentidadNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actividad.getIdactividad();
                if (findActividad(id) == null) {
                    throw new NonexistentEntityException("The actividad with id " + id + " no longer exists.");
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
            Actividad actividad;
            try {
                actividad = em.getReference(Actividad.class, id);
                actividad.getIdactividad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actividad with id " + id + " no longer exists.", enfe);
            }
            Entidad entidadIdentidad = actividad.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad.getActividadCollection().remove(actividad);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            em.remove(actividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Actividad> findActividadEntities() {
        return findActividadEntities(true, -1, -1);
    }

    public List<Actividad> findActividadEntities(int maxResults, int firstResult) {
        return findActividadEntities(false, maxResults, firstResult);
    }

    private List<Actividad> findActividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Actividad.class));
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

    public Actividad findActividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Actividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getActividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Actividad> rt = cq.from(Actividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
