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
import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.Pregunta;
import com.iammagis.appropiate.beans.Respuesta;
import com.iammagis.appropiate.jpa.exceptions.IllegalOrphanException;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sebastianarizmendy
 */
public class PreguntaJpaController implements Serializable {

    public PreguntaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pregunta pregunta) {
        if (pregunta.getRespuestaCollection() == null) {
            pregunta.setRespuestaCollection(new ArrayList<Respuesta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encuesta encuestaIdencuesta = pregunta.getEncuestaIdencuesta();
            if (encuestaIdencuesta != null) {
                encuestaIdencuesta = em.getReference(encuestaIdencuesta.getClass(), encuestaIdencuesta.getIdencuesta());
                pregunta.setEncuestaIdencuesta(encuestaIdencuesta);
            }
            Collection<Respuesta> attachedRespuestaCollection = new ArrayList<Respuesta>();
            for (Respuesta respuestaCollectionRespuestaToAttach : pregunta.getRespuestaCollection()) {
                respuestaCollectionRespuestaToAttach = em.getReference(respuestaCollectionRespuestaToAttach.getClass(), respuestaCollectionRespuestaToAttach.getIdrespuesta());
                attachedRespuestaCollection.add(respuestaCollectionRespuestaToAttach);
            }
            pregunta.setRespuestaCollection(attachedRespuestaCollection);
            em.persist(pregunta);
            if (encuestaIdencuesta != null) {
                encuestaIdencuesta.getPreguntaCollection().add(pregunta);
                encuestaIdencuesta = em.merge(encuestaIdencuesta);
            }
            for (Respuesta respuestaCollectionRespuesta : pregunta.getRespuestaCollection()) {
                Pregunta oldPreguntaIdpreguntaOfRespuestaCollectionRespuesta = respuestaCollectionRespuesta.getPreguntaIdpregunta();
                respuestaCollectionRespuesta.setPreguntaIdpregunta(pregunta);
                respuestaCollectionRespuesta = em.merge(respuestaCollectionRespuesta);
                if (oldPreguntaIdpreguntaOfRespuestaCollectionRespuesta != null) {
                    oldPreguntaIdpreguntaOfRespuestaCollectionRespuesta.getRespuestaCollection().remove(respuestaCollectionRespuesta);
                    oldPreguntaIdpreguntaOfRespuestaCollectionRespuesta = em.merge(oldPreguntaIdpreguntaOfRespuestaCollectionRespuesta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pregunta pregunta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pregunta persistentPregunta = em.find(Pregunta.class, pregunta.getIdpregunta());
            Encuesta encuestaIdencuestaOld = persistentPregunta.getEncuestaIdencuesta();
            Encuesta encuestaIdencuestaNew = pregunta.getEncuestaIdencuesta();
            Collection<Respuesta> respuestaCollectionOld = persistentPregunta.getRespuestaCollection();
            Collection<Respuesta> respuestaCollectionNew = pregunta.getRespuestaCollection();
            List<String> illegalOrphanMessages = null;
            for (Respuesta respuestaCollectionOldRespuesta : respuestaCollectionOld) {
                if (!respuestaCollectionNew.contains(respuestaCollectionOldRespuesta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Respuesta " + respuestaCollectionOldRespuesta + " since its preguntaIdpregunta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (encuestaIdencuestaNew != null) {
                encuestaIdencuestaNew = em.getReference(encuestaIdencuestaNew.getClass(), encuestaIdencuestaNew.getIdencuesta());
                pregunta.setEncuestaIdencuesta(encuestaIdencuestaNew);
            }
            Collection<Respuesta> attachedRespuestaCollectionNew = new ArrayList<Respuesta>();
            for (Respuesta respuestaCollectionNewRespuestaToAttach : respuestaCollectionNew) {
                respuestaCollectionNewRespuestaToAttach = em.getReference(respuestaCollectionNewRespuestaToAttach.getClass(), respuestaCollectionNewRespuestaToAttach.getIdrespuesta());
                attachedRespuestaCollectionNew.add(respuestaCollectionNewRespuestaToAttach);
            }
            respuestaCollectionNew = attachedRespuestaCollectionNew;
            pregunta.setRespuestaCollection(respuestaCollectionNew);
            pregunta = em.merge(pregunta);
            if (encuestaIdencuestaOld != null && !encuestaIdencuestaOld.equals(encuestaIdencuestaNew)) {
                encuestaIdencuestaOld.getPreguntaCollection().remove(pregunta);
                encuestaIdencuestaOld = em.merge(encuestaIdencuestaOld);
            }
            if (encuestaIdencuestaNew != null && !encuestaIdencuestaNew.equals(encuestaIdencuestaOld)) {
                encuestaIdencuestaNew.getPreguntaCollection().add(pregunta);
                encuestaIdencuestaNew = em.merge(encuestaIdencuestaNew);
            }
            for (Respuesta respuestaCollectionNewRespuesta : respuestaCollectionNew) {
                if (!respuestaCollectionOld.contains(respuestaCollectionNewRespuesta)) {
                    Pregunta oldPreguntaIdpreguntaOfRespuestaCollectionNewRespuesta = respuestaCollectionNewRespuesta.getPreguntaIdpregunta();
                    respuestaCollectionNewRespuesta.setPreguntaIdpregunta(pregunta);
                    respuestaCollectionNewRespuesta = em.merge(respuestaCollectionNewRespuesta);
                    if (oldPreguntaIdpreguntaOfRespuestaCollectionNewRespuesta != null && !oldPreguntaIdpreguntaOfRespuestaCollectionNewRespuesta.equals(pregunta)) {
                        oldPreguntaIdpreguntaOfRespuestaCollectionNewRespuesta.getRespuestaCollection().remove(respuestaCollectionNewRespuesta);
                        oldPreguntaIdpreguntaOfRespuestaCollectionNewRespuesta = em.merge(oldPreguntaIdpreguntaOfRespuestaCollectionNewRespuesta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pregunta.getIdpregunta();
                if (findPregunta(id) == null) {
                    throw new NonexistentEntityException("The pregunta with id " + id + " no longer exists.");
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
            Pregunta pregunta;
            try {
                pregunta = em.getReference(Pregunta.class, id);
                pregunta.getIdpregunta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pregunta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Respuesta> respuestaCollectionOrphanCheck = pregunta.getRespuestaCollection();
            for (Respuesta respuestaCollectionOrphanCheckRespuesta : respuestaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pregunta (" + pregunta + ") cannot be destroyed since the Respuesta " + respuestaCollectionOrphanCheckRespuesta + " in its respuestaCollection field has a non-nullable preguntaIdpregunta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Encuesta encuestaIdencuesta = pregunta.getEncuestaIdencuesta();
            if (encuestaIdencuesta != null) {
                encuestaIdencuesta.getPreguntaCollection().remove(pregunta);
                encuestaIdencuesta = em.merge(encuestaIdencuesta);
            }
            em.remove(pregunta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pregunta> findPreguntaEntities() {
        return findPreguntaEntities(true, -1, -1);
    }

    public List<Pregunta> findPreguntaEntities(int maxResults, int firstResult) {
        return findPreguntaEntities(false, maxResults, firstResult);
    }

    private List<Pregunta> findPreguntaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pregunta.class));
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

    public Pregunta findPregunta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pregunta.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreguntaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pregunta> rt = cq.from(Pregunta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
