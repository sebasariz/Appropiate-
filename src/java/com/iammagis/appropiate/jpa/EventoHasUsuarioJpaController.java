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
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.EventoHasUsuario;
import com.iammagis.appropiate.beans.EventoHasUsuarioPK;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import com.iammagis.appropiate.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 *
 * @author sebastianarizmendy
 */
public class EventoHasUsuarioJpaController implements Serializable {

    public EventoHasUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EventoHasUsuario eventoHasUsuario) throws PreexistingEntityException, Exception {
        if (eventoHasUsuario.getEventoHasUsuarioPK() == null) {
            eventoHasUsuario.setEventoHasUsuarioPK(new EventoHasUsuarioPK());
        }
        eventoHasUsuario.getEventoHasUsuarioPK().setUsuarioIdusuario(eventoHasUsuario.getUsuario().getIdusuario());
        eventoHasUsuario.getEventoHasUsuarioPK().setEventoIdevento(eventoHasUsuario.getEvento().getIdevento());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento evento = eventoHasUsuario.getEvento();
            if (evento != null) {
                evento = em.getReference(evento.getClass(), evento.getIdevento());
                eventoHasUsuario.setEvento(evento);
            }
            Usuario usuario = eventoHasUsuario.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdusuario());
                eventoHasUsuario.setUsuario(usuario);
            }
            em.persist(eventoHasUsuario);
            if (evento != null) {
                evento.getEventoHasUsuarioCollection().add(eventoHasUsuario);
                evento = em.merge(evento);
            }
            if (usuario != null) {
                usuario.getEventoHasUsuarioCollection().add(eventoHasUsuario);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEventoHasUsuario(eventoHasUsuario.getEventoHasUsuarioPK()) != null) {
                throw new PreexistingEntityException("EventoHasUsuario " + eventoHasUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EventoHasUsuario eventoHasUsuario) throws NonexistentEntityException, Exception {
        eventoHasUsuario.getEventoHasUsuarioPK().setUsuarioIdusuario(eventoHasUsuario.getUsuario().getIdusuario());
        eventoHasUsuario.getEventoHasUsuarioPK().setEventoIdevento(eventoHasUsuario.getEvento().getIdevento());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventoHasUsuario persistentEventoHasUsuario = em.find(EventoHasUsuario.class, eventoHasUsuario.getEventoHasUsuarioPK());
            Evento eventoOld = persistentEventoHasUsuario.getEvento();
            Evento eventoNew = eventoHasUsuario.getEvento();
            Usuario usuarioOld = persistentEventoHasUsuario.getUsuario();
            Usuario usuarioNew = eventoHasUsuario.getUsuario();
            if (eventoNew != null) {
                eventoNew = em.getReference(eventoNew.getClass(), eventoNew.getIdevento());
                eventoHasUsuario.setEvento(eventoNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdusuario());
                eventoHasUsuario.setUsuario(usuarioNew);
            }
            eventoHasUsuario = em.merge(eventoHasUsuario);
            if (eventoOld != null && !eventoOld.equals(eventoNew)) {
                eventoOld.getEventoHasUsuarioCollection().remove(eventoHasUsuario);
                eventoOld = em.merge(eventoOld);
            }
            if (eventoNew != null && !eventoNew.equals(eventoOld)) {
                eventoNew.getEventoHasUsuarioCollection().add(eventoHasUsuario);
                eventoNew = em.merge(eventoNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getEventoHasUsuarioCollection().remove(eventoHasUsuario);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getEventoHasUsuarioCollection().add(eventoHasUsuario);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EventoHasUsuarioPK id = eventoHasUsuario.getEventoHasUsuarioPK();
                if (findEventoHasUsuario(id) == null) {
                    throw new NonexistentEntityException("The eventoHasUsuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EventoHasUsuarioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventoHasUsuario eventoHasUsuario;
            try {
                eventoHasUsuario = em.getReference(EventoHasUsuario.class, id);
                eventoHasUsuario.getEventoHasUsuarioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventoHasUsuario with id " + id + " no longer exists.", enfe);
            }
            Evento evento = eventoHasUsuario.getEvento();
            if (evento != null) {
                evento.getEventoHasUsuarioCollection().remove(eventoHasUsuario);
                evento = em.merge(evento);
            }
            Usuario usuario = eventoHasUsuario.getUsuario();
            if (usuario != null) {
                usuario.getEventoHasUsuarioCollection().remove(eventoHasUsuario);
                usuario = em.merge(usuario);
            }
            em.remove(eventoHasUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EventoHasUsuario> findEventoHasUsuarioEntities() {
        return findEventoHasUsuarioEntities(true, -1, -1);
    }

    public List<EventoHasUsuario> findEventoHasUsuarioEntities(int maxResults, int firstResult) {
        return findEventoHasUsuarioEntities(false, maxResults, firstResult);
    }

    private List<EventoHasUsuario> findEventoHasUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EventoHasUsuario.class));
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

    public EventoHasUsuario findEventoHasUsuario(EventoHasUsuarioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EventoHasUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventoHasUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EventoHasUsuario> rt = cq.from(EventoHasUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public void destroyFromEvento(int id) {
         EntityManager em = getEntityManager();
        String queryString = "DELETE FROM `evento_has_usuario` WHERE  `evento_idevento`=" + id;
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createNativeQuery(queryString);
        query.executeUpdate();
        tx.commit();
    }
    
}
