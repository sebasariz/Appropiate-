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
import com.iammagis.appropiate.beans.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.jpa.exceptions.IllegalOrphanException;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sebastianarizmendy
 */
public class EntidadJpaController implements Serializable {

    public EntidadJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Entidad entidad) {
        if (entidad.getUsuarioCollection() == null) {
            entidad.setUsuarioCollection(new ArrayList<Usuario>());
        }
        if (entidad.getEncuestaCollection() == null) {
            entidad.setEncuestaCollection(new ArrayList<Encuesta>());
        }
        if (entidad.getCampanaComunicadosCollection() == null) {
            entidad.setCampanaComunicadosCollection(new ArrayList<CampanaComunicados>());
        }
        if (entidad.getPqrCollection() == null) {
            entidad.setPqrCollection(new ArrayList<Pqr>());
        }
        if (entidad.getEventoCollection() == null) {
            entidad.setEventoCollection(new ArrayList<Evento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : entidad.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdusuario());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            entidad.setUsuarioCollection(attachedUsuarioCollection);
            Collection<Encuesta> attachedEncuestaCollection = new ArrayList<Encuesta>();
            for (Encuesta encuestaCollectionEncuestaToAttach : entidad.getEncuestaCollection()) {
                encuestaCollectionEncuestaToAttach = em.getReference(encuestaCollectionEncuestaToAttach.getClass(), encuestaCollectionEncuestaToAttach.getIdencuesta());
                attachedEncuestaCollection.add(encuestaCollectionEncuestaToAttach);
            }
            entidad.setEncuestaCollection(attachedEncuestaCollection);
            Collection<CampanaComunicados> attachedCampanaComunicadosCollection = new ArrayList<CampanaComunicados>();
            for (CampanaComunicados campanaComunicadosCollectionCampanaComunicadosToAttach : entidad.getCampanaComunicadosCollection()) {
                campanaComunicadosCollectionCampanaComunicadosToAttach = em.getReference(campanaComunicadosCollectionCampanaComunicadosToAttach.getClass(), campanaComunicadosCollectionCampanaComunicadosToAttach.getIdcampana());
                attachedCampanaComunicadosCollection.add(campanaComunicadosCollectionCampanaComunicadosToAttach);
            }
            entidad.setCampanaComunicadosCollection(attachedCampanaComunicadosCollection);
            Collection<Pqr> attachedPqrCollection = new ArrayList<Pqr>();
            for (Pqr pqrCollectionPqrToAttach : entidad.getPqrCollection()) {
                pqrCollectionPqrToAttach = em.getReference(pqrCollectionPqrToAttach.getClass(), pqrCollectionPqrToAttach.getIdpqr());
                attachedPqrCollection.add(pqrCollectionPqrToAttach);
            }
            entidad.setPqrCollection(attachedPqrCollection);
            Collection<Evento> attachedEventoCollection = new ArrayList<Evento>();
            for (Evento eventoCollectionEventoToAttach : entidad.getEventoCollection()) {
                eventoCollectionEventoToAttach = em.getReference(eventoCollectionEventoToAttach.getClass(), eventoCollectionEventoToAttach.getIdevento());
                attachedEventoCollection.add(eventoCollectionEventoToAttach);
            }
            entidad.setEventoCollection(attachedEventoCollection);
            em.persist(entidad);
            for (Usuario usuarioCollectionUsuario : entidad.getUsuarioCollection()) {
                usuarioCollectionUsuario.getEntidadCollection().add(entidad);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }
            for (Encuesta encuestaCollectionEncuesta : entidad.getEncuestaCollection()) {
                Entidad oldEntidadIdentidadOfEncuestaCollectionEncuesta = encuestaCollectionEncuesta.getEntidadIdentidad();
                encuestaCollectionEncuesta.setEntidadIdentidad(entidad);
                encuestaCollectionEncuesta = em.merge(encuestaCollectionEncuesta);
                if (oldEntidadIdentidadOfEncuestaCollectionEncuesta != null) {
                    oldEntidadIdentidadOfEncuestaCollectionEncuesta.getEncuestaCollection().remove(encuestaCollectionEncuesta);
                    oldEntidadIdentidadOfEncuestaCollectionEncuesta = em.merge(oldEntidadIdentidadOfEncuestaCollectionEncuesta);
                }
            }
            for (CampanaComunicados campanaComunicadosCollectionCampanaComunicados : entidad.getCampanaComunicadosCollection()) {
                Entidad oldEntidadIdentidadOfCampanaComunicadosCollectionCampanaComunicados = campanaComunicadosCollectionCampanaComunicados.getEntidadIdentidad();
                campanaComunicadosCollectionCampanaComunicados.setEntidadIdentidad(entidad);
                campanaComunicadosCollectionCampanaComunicados = em.merge(campanaComunicadosCollectionCampanaComunicados);
                if (oldEntidadIdentidadOfCampanaComunicadosCollectionCampanaComunicados != null) {
                    oldEntidadIdentidadOfCampanaComunicadosCollectionCampanaComunicados.getCampanaComunicadosCollection().remove(campanaComunicadosCollectionCampanaComunicados);
                    oldEntidadIdentidadOfCampanaComunicadosCollectionCampanaComunicados = em.merge(oldEntidadIdentidadOfCampanaComunicadosCollectionCampanaComunicados);
                }
            }
            for (Pqr pqrCollectionPqr : entidad.getPqrCollection()) {
                Entidad oldEntidadIdentidadOfPqrCollectionPqr = pqrCollectionPqr.getEntidadIdentidad();
                pqrCollectionPqr.setEntidadIdentidad(entidad);
                pqrCollectionPqr = em.merge(pqrCollectionPqr);
                if (oldEntidadIdentidadOfPqrCollectionPqr != null) {
                    oldEntidadIdentidadOfPqrCollectionPqr.getPqrCollection().remove(pqrCollectionPqr);
                    oldEntidadIdentidadOfPqrCollectionPqr = em.merge(oldEntidadIdentidadOfPqrCollectionPqr);
                }
            }
            for (Evento eventoCollectionEvento : entidad.getEventoCollection()) {
                Entidad oldEntidadIdentidadOfEventoCollectionEvento = eventoCollectionEvento.getEntidadIdentidad();
                eventoCollectionEvento.setEntidadIdentidad(entidad);
                eventoCollectionEvento = em.merge(eventoCollectionEvento);
                if (oldEntidadIdentidadOfEventoCollectionEvento != null) {
                    oldEntidadIdentidadOfEventoCollectionEvento.getEventoCollection().remove(eventoCollectionEvento);
                    oldEntidadIdentidadOfEventoCollectionEvento = em.merge(oldEntidadIdentidadOfEventoCollectionEvento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Entidad entidad) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad persistentEntidad = em.find(Entidad.class, entidad.getIdentidad());
            Collection<Usuario> usuarioCollectionOld = persistentEntidad.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = entidad.getUsuarioCollection();
            Collection<Encuesta> encuestaCollectionOld = persistentEntidad.getEncuestaCollection();
            Collection<Encuesta> encuestaCollectionNew = entidad.getEncuestaCollection();
            Collection<CampanaComunicados> campanaComunicadosCollectionOld = persistentEntidad.getCampanaComunicadosCollection();
            Collection<CampanaComunicados> campanaComunicadosCollectionNew = entidad.getCampanaComunicadosCollection();
            Collection<Pqr> pqrCollectionOld = persistentEntidad.getPqrCollection();
            Collection<Pqr> pqrCollectionNew = entidad.getPqrCollection();
            Collection<Evento> eventoCollectionOld = persistentEntidad.getEventoCollection();
            Collection<Evento> eventoCollectionNew = entidad.getEventoCollection();
            List<String> illegalOrphanMessages = null;
            for (Encuesta encuestaCollectionOldEncuesta : encuestaCollectionOld) {
                if (!encuestaCollectionNew.contains(encuestaCollectionOldEncuesta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Encuesta " + encuestaCollectionOldEncuesta + " since its entidadIdentidad field is not nullable.");
                }
            }
            for (CampanaComunicados campanaComunicadosCollectionOldCampanaComunicados : campanaComunicadosCollectionOld) {
                if (!campanaComunicadosCollectionNew.contains(campanaComunicadosCollectionOldCampanaComunicados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CampanaComunicados " + campanaComunicadosCollectionOldCampanaComunicados + " since its entidadIdentidad field is not nullable.");
                }
            }
            for (Pqr pqrCollectionOldPqr : pqrCollectionOld) {
                if (!pqrCollectionNew.contains(pqrCollectionOldPqr)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pqr " + pqrCollectionOldPqr + " since its entidadIdentidad field is not nullable.");
                }
            }
            for (Evento eventoCollectionOldEvento : eventoCollectionOld) {
                if (!eventoCollectionNew.contains(eventoCollectionOldEvento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evento " + eventoCollectionOldEvento + " since its entidadIdentidad field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getIdusuario());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            entidad.setUsuarioCollection(usuarioCollectionNew);
            Collection<Encuesta> attachedEncuestaCollectionNew = new ArrayList<Encuesta>();
            for (Encuesta encuestaCollectionNewEncuestaToAttach : encuestaCollectionNew) {
                encuestaCollectionNewEncuestaToAttach = em.getReference(encuestaCollectionNewEncuestaToAttach.getClass(), encuestaCollectionNewEncuestaToAttach.getIdencuesta());
                attachedEncuestaCollectionNew.add(encuestaCollectionNewEncuestaToAttach);
            }
            encuestaCollectionNew = attachedEncuestaCollectionNew;
            entidad.setEncuestaCollection(encuestaCollectionNew);
            Collection<CampanaComunicados> attachedCampanaComunicadosCollectionNew = new ArrayList<CampanaComunicados>();
            for (CampanaComunicados campanaComunicadosCollectionNewCampanaComunicadosToAttach : campanaComunicadosCollectionNew) {
                campanaComunicadosCollectionNewCampanaComunicadosToAttach = em.getReference(campanaComunicadosCollectionNewCampanaComunicadosToAttach.getClass(), campanaComunicadosCollectionNewCampanaComunicadosToAttach.getIdcampana());
                attachedCampanaComunicadosCollectionNew.add(campanaComunicadosCollectionNewCampanaComunicadosToAttach);
            }
            campanaComunicadosCollectionNew = attachedCampanaComunicadosCollectionNew;
            entidad.setCampanaComunicadosCollection(campanaComunicadosCollectionNew);
            Collection<Pqr> attachedPqrCollectionNew = new ArrayList<Pqr>();
            for (Pqr pqrCollectionNewPqrToAttach : pqrCollectionNew) {
                pqrCollectionNewPqrToAttach = em.getReference(pqrCollectionNewPqrToAttach.getClass(), pqrCollectionNewPqrToAttach.getIdpqr());
                attachedPqrCollectionNew.add(pqrCollectionNewPqrToAttach);
            }
            pqrCollectionNew = attachedPqrCollectionNew;
            entidad.setPqrCollection(pqrCollectionNew);
            Collection<Evento> attachedEventoCollectionNew = new ArrayList<Evento>();
            for (Evento eventoCollectionNewEventoToAttach : eventoCollectionNew) {
                eventoCollectionNewEventoToAttach = em.getReference(eventoCollectionNewEventoToAttach.getClass(), eventoCollectionNewEventoToAttach.getIdevento());
                attachedEventoCollectionNew.add(eventoCollectionNewEventoToAttach);
            }
            eventoCollectionNew = attachedEventoCollectionNew;
            entidad.setEventoCollection(eventoCollectionNew);
            entidad = em.merge(entidad);
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    usuarioCollectionOldUsuario.getEntidadCollection().remove(entidad);
                    usuarioCollectionOldUsuario = em.merge(usuarioCollectionOldUsuario);
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    usuarioCollectionNewUsuario.getEntidadCollection().add(entidad);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                }
            }
            for (Encuesta encuestaCollectionNewEncuesta : encuestaCollectionNew) {
                if (!encuestaCollectionOld.contains(encuestaCollectionNewEncuesta)) {
                    Entidad oldEntidadIdentidadOfEncuestaCollectionNewEncuesta = encuestaCollectionNewEncuesta.getEntidadIdentidad();
                    encuestaCollectionNewEncuesta.setEntidadIdentidad(entidad);
                    encuestaCollectionNewEncuesta = em.merge(encuestaCollectionNewEncuesta);
                    if (oldEntidadIdentidadOfEncuestaCollectionNewEncuesta != null && !oldEntidadIdentidadOfEncuestaCollectionNewEncuesta.equals(entidad)) {
                        oldEntidadIdentidadOfEncuestaCollectionNewEncuesta.getEncuestaCollection().remove(encuestaCollectionNewEncuesta);
                        oldEntidadIdentidadOfEncuestaCollectionNewEncuesta = em.merge(oldEntidadIdentidadOfEncuestaCollectionNewEncuesta);
                    }
                }
            }
            for (CampanaComunicados campanaComunicadosCollectionNewCampanaComunicados : campanaComunicadosCollectionNew) {
                if (!campanaComunicadosCollectionOld.contains(campanaComunicadosCollectionNewCampanaComunicados)) {
                    Entidad oldEntidadIdentidadOfCampanaComunicadosCollectionNewCampanaComunicados = campanaComunicadosCollectionNewCampanaComunicados.getEntidadIdentidad();
                    campanaComunicadosCollectionNewCampanaComunicados.setEntidadIdentidad(entidad);
                    campanaComunicadosCollectionNewCampanaComunicados = em.merge(campanaComunicadosCollectionNewCampanaComunicados);
                    if (oldEntidadIdentidadOfCampanaComunicadosCollectionNewCampanaComunicados != null && !oldEntidadIdentidadOfCampanaComunicadosCollectionNewCampanaComunicados.equals(entidad)) {
                        oldEntidadIdentidadOfCampanaComunicadosCollectionNewCampanaComunicados.getCampanaComunicadosCollection().remove(campanaComunicadosCollectionNewCampanaComunicados);
                        oldEntidadIdentidadOfCampanaComunicadosCollectionNewCampanaComunicados = em.merge(oldEntidadIdentidadOfCampanaComunicadosCollectionNewCampanaComunicados);
                    }
                }
            }
            for (Pqr pqrCollectionNewPqr : pqrCollectionNew) {
                if (!pqrCollectionOld.contains(pqrCollectionNewPqr)) {
                    Entidad oldEntidadIdentidadOfPqrCollectionNewPqr = pqrCollectionNewPqr.getEntidadIdentidad();
                    pqrCollectionNewPqr.setEntidadIdentidad(entidad);
                    pqrCollectionNewPqr = em.merge(pqrCollectionNewPqr);
                    if (oldEntidadIdentidadOfPqrCollectionNewPqr != null && !oldEntidadIdentidadOfPqrCollectionNewPqr.equals(entidad)) {
                        oldEntidadIdentidadOfPqrCollectionNewPqr.getPqrCollection().remove(pqrCollectionNewPqr);
                        oldEntidadIdentidadOfPqrCollectionNewPqr = em.merge(oldEntidadIdentidadOfPqrCollectionNewPqr);
                    }
                }
            }
            for (Evento eventoCollectionNewEvento : eventoCollectionNew) {
                if (!eventoCollectionOld.contains(eventoCollectionNewEvento)) {
                    Entidad oldEntidadIdentidadOfEventoCollectionNewEvento = eventoCollectionNewEvento.getEntidadIdentidad();
                    eventoCollectionNewEvento.setEntidadIdentidad(entidad);
                    eventoCollectionNewEvento = em.merge(eventoCollectionNewEvento);
                    if (oldEntidadIdentidadOfEventoCollectionNewEvento != null && !oldEntidadIdentidadOfEventoCollectionNewEvento.equals(entidad)) {
                        oldEntidadIdentidadOfEventoCollectionNewEvento.getEventoCollection().remove(eventoCollectionNewEvento);
                        oldEntidadIdentidadOfEventoCollectionNewEvento = em.merge(oldEntidadIdentidadOfEventoCollectionNewEvento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = entidad.getIdentidad();
                if (findEntidad(id) == null) {
                    throw new NonexistentEntityException("The entidad with id " + id + " no longer exists.");
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
            Entidad entidad;
            try {
                entidad = em.getReference(Entidad.class, id);
                entidad.getIdentidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The entidad with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Encuesta> encuestaCollectionOrphanCheck = entidad.getEncuestaCollection();
            for (Encuesta encuestaCollectionOrphanCheckEncuesta : encuestaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entidad (" + entidad + ") cannot be destroyed since the Encuesta " + encuestaCollectionOrphanCheckEncuesta + " in its encuestaCollection field has a non-nullable entidadIdentidad field.");
            }
            Collection<CampanaComunicados> campanaComunicadosCollectionOrphanCheck = entidad.getCampanaComunicadosCollection();
            for (CampanaComunicados campanaComunicadosCollectionOrphanCheckCampanaComunicados : campanaComunicadosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entidad (" + entidad + ") cannot be destroyed since the CampanaComunicados " + campanaComunicadosCollectionOrphanCheckCampanaComunicados + " in its campanaComunicadosCollection field has a non-nullable entidadIdentidad field.");
            }
            Collection<Pqr> pqrCollectionOrphanCheck = entidad.getPqrCollection();
            for (Pqr pqrCollectionOrphanCheckPqr : pqrCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entidad (" + entidad + ") cannot be destroyed since the Pqr " + pqrCollectionOrphanCheckPqr + " in its pqrCollection field has a non-nullable entidadIdentidad field.");
            }
            Collection<Evento> eventoCollectionOrphanCheck = entidad.getEventoCollection();
            for (Evento eventoCollectionOrphanCheckEvento : eventoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Entidad (" + entidad + ") cannot be destroyed since the Evento " + eventoCollectionOrphanCheckEvento + " in its eventoCollection field has a non-nullable entidadIdentidad field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Usuario> usuarioCollection = entidad.getUsuarioCollection();
            for (Usuario usuarioCollectionUsuario : usuarioCollection) {
                usuarioCollectionUsuario.getEntidadCollection().remove(entidad);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }
            em.remove(entidad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Entidad> findEntidadEntities() {
        return findEntidadEntities(true, -1, -1);
    }

    public List<Entidad> findEntidadEntities(int maxResults, int firstResult) {
        return findEntidadEntities(false, maxResults, firstResult);
    }

    private List<Entidad> findEntidadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Entidad.class));
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

    public Entidad findEntidad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Entidad.class, id);
        } finally {
            em.close();
        }
    }

    public int getEntidadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Entidad> rt = cq.from(Entidad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Entidad getByToken(String id) {
        String queryString = "SELECT * FROM `entidad` WHERE  `identificador` = '" + id + "'";

        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery(queryString, Entidad.class);
        Entidad entidad = (Entidad) query.getSingleResult();
        return entidad;
    }

    
}
