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
import com.iammagis.appropiate.beans.Locacion;
import com.iammagis.appropiate.beans.Reserva;
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
public class LocacionJpaController implements Serializable {

    public LocacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Locacion locacion) {
        if (locacion.getReservaCollection() == null) {
            locacion.setReservaCollection(new ArrayList<Reserva>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad entidadIdentidad = locacion.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad = em.getReference(entidadIdentidad.getClass(), entidadIdentidad.getIdentidad());
                locacion.setEntidadIdentidad(entidadIdentidad);
            }
            Collection<Reserva> attachedReservaCollection = new ArrayList<Reserva>();
            for (Reserva reservaCollectionReservaToAttach : locacion.getReservaCollection()) {
                reservaCollectionReservaToAttach = em.getReference(reservaCollectionReservaToAttach.getClass(), reservaCollectionReservaToAttach.getIdreserva());
                attachedReservaCollection.add(reservaCollectionReservaToAttach);
            }
            locacion.setReservaCollection(attachedReservaCollection);
            em.persist(locacion);
            if (entidadIdentidad != null) {
                entidadIdentidad.getLocacionCollection().add(locacion);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            for (Reserva reservaCollectionReserva : locacion.getReservaCollection()) {
                Locacion oldLocacionIdlocacionOfReservaCollectionReserva = reservaCollectionReserva.getLocacionIdlocacion();
                reservaCollectionReserva.setLocacionIdlocacion(locacion);
                reservaCollectionReserva = em.merge(reservaCollectionReserva);
                if (oldLocacionIdlocacionOfReservaCollectionReserva != null) {
                    oldLocacionIdlocacionOfReservaCollectionReserva.getReservaCollection().remove(reservaCollectionReserva);
                    oldLocacionIdlocacionOfReservaCollectionReserva = em.merge(oldLocacionIdlocacionOfReservaCollectionReserva);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Locacion locacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locacion persistentLocacion = em.find(Locacion.class, locacion.getIdlocacion());
            Entidad entidadIdentidadOld = persistentLocacion.getEntidadIdentidad();
            Entidad entidadIdentidadNew = locacion.getEntidadIdentidad();
            Collection<Reserva> reservaCollectionOld = persistentLocacion.getReservaCollection();
            Collection<Reserva> reservaCollectionNew = locacion.getReservaCollection();
            List<String> illegalOrphanMessages = null;
            for (Reserva reservaCollectionOldReserva : reservaCollectionOld) {
                if (!reservaCollectionNew.contains(reservaCollectionOldReserva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reserva " + reservaCollectionOldReserva + " since its locacionIdlocacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (entidadIdentidadNew != null) {
                entidadIdentidadNew = em.getReference(entidadIdentidadNew.getClass(), entidadIdentidadNew.getIdentidad());
                locacion.setEntidadIdentidad(entidadIdentidadNew);
            }
            Collection<Reserva> attachedReservaCollectionNew = new ArrayList<Reserva>();
            for (Reserva reservaCollectionNewReservaToAttach : reservaCollectionNew) {
                reservaCollectionNewReservaToAttach = em.getReference(reservaCollectionNewReservaToAttach.getClass(), reservaCollectionNewReservaToAttach.getIdreserva());
                attachedReservaCollectionNew.add(reservaCollectionNewReservaToAttach);
            }
            reservaCollectionNew = attachedReservaCollectionNew;
            locacion.setReservaCollection(reservaCollectionNew);
            locacion = em.merge(locacion);
            if (entidadIdentidadOld != null && !entidadIdentidadOld.equals(entidadIdentidadNew)) {
                entidadIdentidadOld.getLocacionCollection().remove(locacion);
                entidadIdentidadOld = em.merge(entidadIdentidadOld);
            }
            if (entidadIdentidadNew != null && !entidadIdentidadNew.equals(entidadIdentidadOld)) {
                entidadIdentidadNew.getLocacionCollection().add(locacion);
                entidadIdentidadNew = em.merge(entidadIdentidadNew);
            }
            for (Reserva reservaCollectionNewReserva : reservaCollectionNew) {
                if (!reservaCollectionOld.contains(reservaCollectionNewReserva)) {
                    Locacion oldLocacionIdlocacionOfReservaCollectionNewReserva = reservaCollectionNewReserva.getLocacionIdlocacion();
                    reservaCollectionNewReserva.setLocacionIdlocacion(locacion);
                    reservaCollectionNewReserva = em.merge(reservaCollectionNewReserva);
                    if (oldLocacionIdlocacionOfReservaCollectionNewReserva != null && !oldLocacionIdlocacionOfReservaCollectionNewReserva.equals(locacion)) {
                        oldLocacionIdlocacionOfReservaCollectionNewReserva.getReservaCollection().remove(reservaCollectionNewReserva);
                        oldLocacionIdlocacionOfReservaCollectionNewReserva = em.merge(oldLocacionIdlocacionOfReservaCollectionNewReserva);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = locacion.getIdlocacion();
                if (findLocacion(id) == null) {
                    throw new NonexistentEntityException("The locacion with id " + id + " no longer exists.");
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
            Locacion locacion;
            try {
                locacion = em.getReference(Locacion.class, id);
                locacion.getIdlocacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The locacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Reserva> reservaCollectionOrphanCheck = locacion.getReservaCollection();
            for (Reserva reservaCollectionOrphanCheckReserva : reservaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Locacion (" + locacion + ") cannot be destroyed since the Reserva " + reservaCollectionOrphanCheckReserva + " in its reservaCollection field has a non-nullable locacionIdlocacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Entidad entidadIdentidad = locacion.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad.getLocacionCollection().remove(locacion);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            em.remove(locacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Locacion> findLocacionEntities() {
        return findLocacionEntities(true, -1, -1);
    }

    public List<Locacion> findLocacionEntities(int maxResults, int firstResult) {
        return findLocacionEntities(false, maxResults, firstResult);
    }

    private List<Locacion> findLocacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Locacion.class));
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

    public Locacion findLocacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Locacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Locacion> rt = cq.from(Locacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
