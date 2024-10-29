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
import com.iammagis.appropiate.beans.Archivo;
import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.Evento;
import java.util.ArrayList;
import java.util.Collection;
import com.iammagis.appropiate.beans.EventoHasUsuario;
import com.iammagis.appropiate.jpa.exceptions.IllegalOrphanException;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sebastianarizmendy
 */
public class EventoJpaController implements Serializable {

    public EventoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Evento create(Evento evento) {
        if (evento.getArchivoCollection() == null) {
            evento.setArchivoCollection(new ArrayList<Archivo>());
        }
        if (evento.getEventoHasUsuarioCollection() == null) {
            evento.setEventoHasUsuarioCollection(new ArrayList<EventoHasUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad entidadIdentidad = evento.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad = em.getReference(entidadIdentidad.getClass(), entidadIdentidad.getIdentidad());
                evento.setEntidadIdentidad(entidadIdentidad);
            }
            Collection<Archivo> attachedArchivoCollection = new ArrayList<Archivo>();
            for (Archivo archivoCollectionArchivoToAttach : evento.getArchivoCollection()) {
                archivoCollectionArchivoToAttach = em.getReference(archivoCollectionArchivoToAttach.getClass(), archivoCollectionArchivoToAttach.getIdarchivo());
                attachedArchivoCollection.add(archivoCollectionArchivoToAttach);
            }
            evento.setArchivoCollection(attachedArchivoCollection);
            Collection<EventoHasUsuario> attachedEventoHasUsuarioCollection = new ArrayList<EventoHasUsuario>();
            for (EventoHasUsuario eventoHasUsuarioCollectionEventoHasUsuarioToAttach : evento.getEventoHasUsuarioCollection()) {
                eventoHasUsuarioCollectionEventoHasUsuarioToAttach = em.getReference(eventoHasUsuarioCollectionEventoHasUsuarioToAttach.getClass(), eventoHasUsuarioCollectionEventoHasUsuarioToAttach.getEventoHasUsuarioPK());
                attachedEventoHasUsuarioCollection.add(eventoHasUsuarioCollectionEventoHasUsuarioToAttach);
            }
            evento.setEventoHasUsuarioCollection(attachedEventoHasUsuarioCollection);
            em.persist(evento);
            if (entidadIdentidad != null) {
                entidadIdentidad.getEventoCollection().add(evento);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            for (Archivo archivoCollectionArchivo : evento.getArchivoCollection()) {
                Evento oldEventoIdeventoOfArchivoCollectionArchivo = archivoCollectionArchivo.getEventoIdevento();
                archivoCollectionArchivo.setEventoIdevento(evento);
                archivoCollectionArchivo = em.merge(archivoCollectionArchivo);
                if (oldEventoIdeventoOfArchivoCollectionArchivo != null) {
                    oldEventoIdeventoOfArchivoCollectionArchivo.getArchivoCollection().remove(archivoCollectionArchivo);
                    oldEventoIdeventoOfArchivoCollectionArchivo = em.merge(oldEventoIdeventoOfArchivoCollectionArchivo);
                }
            }
            for (EventoHasUsuario eventoHasUsuarioCollectionEventoHasUsuario : evento.getEventoHasUsuarioCollection()) {
                Evento oldEventoOfEventoHasUsuarioCollectionEventoHasUsuario = eventoHasUsuarioCollectionEventoHasUsuario.getEvento();
                eventoHasUsuarioCollectionEventoHasUsuario.setEvento(evento);
                eventoHasUsuarioCollectionEventoHasUsuario = em.merge(eventoHasUsuarioCollectionEventoHasUsuario);
                if (oldEventoOfEventoHasUsuarioCollectionEventoHasUsuario != null) {
                    oldEventoOfEventoHasUsuarioCollectionEventoHasUsuario.getEventoHasUsuarioCollection().remove(eventoHasUsuarioCollectionEventoHasUsuario);
                    oldEventoOfEventoHasUsuarioCollectionEventoHasUsuario = em.merge(oldEventoOfEventoHasUsuarioCollectionEventoHasUsuario);
                }
            }
            em.getTransaction().commit();
            return evento;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Evento edit(Evento evento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento persistentEvento = em.find(Evento.class, evento.getIdevento());
            Entidad entidadIdentidadOld = persistentEvento.getEntidadIdentidad();
            Entidad entidadIdentidadNew = evento.getEntidadIdentidad();
            Collection<Archivo> archivoCollectionOld = persistentEvento.getArchivoCollection();
            Collection<Archivo> archivoCollectionNew = evento.getArchivoCollection();
            Collection<EventoHasUsuario> eventoHasUsuarioCollectionOld = persistentEvento.getEventoHasUsuarioCollection();
            Collection<EventoHasUsuario> eventoHasUsuarioCollectionNew = evento.getEventoHasUsuarioCollection();
            List<String> illegalOrphanMessages = null;
            for (EventoHasUsuario eventoHasUsuarioCollectionOldEventoHasUsuario : eventoHasUsuarioCollectionOld) {
                if (!eventoHasUsuarioCollectionNew.contains(eventoHasUsuarioCollectionOldEventoHasUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventoHasUsuario " + eventoHasUsuarioCollectionOldEventoHasUsuario + " since its evento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (entidadIdentidadNew != null) {
                entidadIdentidadNew = em.getReference(entidadIdentidadNew.getClass(), entidadIdentidadNew.getIdentidad());
                evento.setEntidadIdentidad(entidadIdentidadNew);
            }
            Collection<Archivo> attachedArchivoCollectionNew = new ArrayList<Archivo>();
            for (Archivo archivoCollectionNewArchivoToAttach : archivoCollectionNew) {
                archivoCollectionNewArchivoToAttach = em.getReference(archivoCollectionNewArchivoToAttach.getClass(), archivoCollectionNewArchivoToAttach.getIdarchivo());
                attachedArchivoCollectionNew.add(archivoCollectionNewArchivoToAttach);
            }
            archivoCollectionNew = attachedArchivoCollectionNew;
            evento.setArchivoCollection(archivoCollectionNew);
            Collection<EventoHasUsuario> attachedEventoHasUsuarioCollectionNew = new ArrayList<EventoHasUsuario>();
            for (EventoHasUsuario eventoHasUsuarioCollectionNewEventoHasUsuarioToAttach : eventoHasUsuarioCollectionNew) {
                eventoHasUsuarioCollectionNewEventoHasUsuarioToAttach = em.getReference(eventoHasUsuarioCollectionNewEventoHasUsuarioToAttach.getClass(), eventoHasUsuarioCollectionNewEventoHasUsuarioToAttach.getEventoHasUsuarioPK());
                attachedEventoHasUsuarioCollectionNew.add(eventoHasUsuarioCollectionNewEventoHasUsuarioToAttach);
            }
            eventoHasUsuarioCollectionNew = attachedEventoHasUsuarioCollectionNew;
            evento.setEventoHasUsuarioCollection(eventoHasUsuarioCollectionNew);
            evento = em.merge(evento);
            if (entidadIdentidadOld != null && !entidadIdentidadOld.equals(entidadIdentidadNew)) {
                entidadIdentidadOld.getEventoCollection().remove(evento);
                entidadIdentidadOld = em.merge(entidadIdentidadOld);
            }
            if (entidadIdentidadNew != null && !entidadIdentidadNew.equals(entidadIdentidadOld)) {
                entidadIdentidadNew.getEventoCollection().add(evento);
                entidadIdentidadNew = em.merge(entidadIdentidadNew);
            }
            for (Archivo archivoCollectionOldArchivo : archivoCollectionOld) {
                if (!archivoCollectionNew.contains(archivoCollectionOldArchivo)) {
                    archivoCollectionOldArchivo.setEventoIdevento(null);
                    archivoCollectionOldArchivo = em.merge(archivoCollectionOldArchivo);
                }
            }
            for (Archivo archivoCollectionNewArchivo : archivoCollectionNew) {
                if (!archivoCollectionOld.contains(archivoCollectionNewArchivo)) {
                    Evento oldEventoIdeventoOfArchivoCollectionNewArchivo = archivoCollectionNewArchivo.getEventoIdevento();
                    archivoCollectionNewArchivo.setEventoIdevento(evento);
                    archivoCollectionNewArchivo = em.merge(archivoCollectionNewArchivo);
                    if (oldEventoIdeventoOfArchivoCollectionNewArchivo != null && !oldEventoIdeventoOfArchivoCollectionNewArchivo.equals(evento)) {
                        oldEventoIdeventoOfArchivoCollectionNewArchivo.getArchivoCollection().remove(archivoCollectionNewArchivo);
                        oldEventoIdeventoOfArchivoCollectionNewArchivo = em.merge(oldEventoIdeventoOfArchivoCollectionNewArchivo);
                    }
                }
            }
            for (EventoHasUsuario eventoHasUsuarioCollectionNewEventoHasUsuario : eventoHasUsuarioCollectionNew) {
                if (!eventoHasUsuarioCollectionOld.contains(eventoHasUsuarioCollectionNewEventoHasUsuario)) {
                    Evento oldEventoOfEventoHasUsuarioCollectionNewEventoHasUsuario = eventoHasUsuarioCollectionNewEventoHasUsuario.getEvento();
                    eventoHasUsuarioCollectionNewEventoHasUsuario.setEvento(evento);
                    eventoHasUsuarioCollectionNewEventoHasUsuario = em.merge(eventoHasUsuarioCollectionNewEventoHasUsuario);
                    if (oldEventoOfEventoHasUsuarioCollectionNewEventoHasUsuario != null && !oldEventoOfEventoHasUsuarioCollectionNewEventoHasUsuario.equals(evento)) {
                        oldEventoOfEventoHasUsuarioCollectionNewEventoHasUsuario.getEventoHasUsuarioCollection().remove(eventoHasUsuarioCollectionNewEventoHasUsuario);
                        oldEventoOfEventoHasUsuarioCollectionNewEventoHasUsuario = em.merge(oldEventoOfEventoHasUsuarioCollectionNewEventoHasUsuario);
                    }
                }
            }
            em.getTransaction().commit();
            return evento;
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evento.getIdevento();
                if (findEvento(id) == null) {
                    throw new NonexistentEntityException("The evento with id " + id + " no longer exists.");
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
            Evento evento;
            try {
                evento = em.getReference(Evento.class, id);
                evento.getIdevento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<EventoHasUsuario> eventoHasUsuarioCollectionOrphanCheck = evento.getEventoHasUsuarioCollection();
            for (EventoHasUsuario eventoHasUsuarioCollectionOrphanCheckEventoHasUsuario : eventoHasUsuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Evento (" + evento + ") cannot be destroyed since the EventoHasUsuario " + eventoHasUsuarioCollectionOrphanCheckEventoHasUsuario + " in its eventoHasUsuarioCollection field has a non-nullable evento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Entidad entidadIdentidad = evento.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad.getEventoCollection().remove(evento);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            Collection<Archivo> archivoCollection = evento.getArchivoCollection();
            for (Archivo archivoCollectionArchivo : archivoCollection) {
                archivoCollectionArchivo.setEventoIdevento(null);
                archivoCollectionArchivo = em.merge(archivoCollectionArchivo);
            }
            em.remove(evento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evento> findEventoEntities() {
        return findEventoEntities(true, -1, -1);
    }

    public List<Evento> findEventoEntities(int maxResults, int firstResult) {
        return findEventoEntities(false, maxResults, firstResult);
    }

    private List<Evento> findEventoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evento.class));
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

    public Evento findEvento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evento> rt = cq.from(Evento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public ArrayList<Evento> getEventosLogin() {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `evento` WHERE `estado` = 1";
        Query query = em.createNativeQuery(queryString, Evento.class);
        ArrayList<Evento> eventos = new ArrayList<>(query.getResultList());
        return eventos;
    }

   

}
