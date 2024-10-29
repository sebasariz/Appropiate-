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
import com.iammagis.appropiate.beans.Pregunta;
import com.iammagis.appropiate.beans.Respuesta;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author sebastianarizmendy
 */
public class RespuestaJpaController implements Serializable {

    public RespuestaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Respuesta respuesta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pregunta preguntaIdpregunta = respuesta.getPreguntaIdpregunta();
            if (preguntaIdpregunta != null) {
                preguntaIdpregunta = em.getReference(preguntaIdpregunta.getClass(), preguntaIdpregunta.getIdpregunta());
                respuesta.setPreguntaIdpregunta(preguntaIdpregunta);
            }
            Usuario usuarioIdusuario = respuesta.getUsuarioIdusuario();
            if (usuarioIdusuario != null) {
                usuarioIdusuario = em.getReference(usuarioIdusuario.getClass(), usuarioIdusuario.getIdusuario());
                respuesta.setUsuarioIdusuario(usuarioIdusuario);
            }
            em.persist(respuesta);
            if (preguntaIdpregunta != null) {
                preguntaIdpregunta.getRespuestaCollection().add(respuesta);
                preguntaIdpregunta = em.merge(preguntaIdpregunta);
            }
            if (usuarioIdusuario != null) {
                usuarioIdusuario.getRespuestaCollection().add(respuesta);
                usuarioIdusuario = em.merge(usuarioIdusuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Respuesta respuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Respuesta persistentRespuesta = em.find(Respuesta.class, respuesta.getIdrespuesta());
            Pregunta preguntaIdpreguntaOld = persistentRespuesta.getPreguntaIdpregunta();
            Pregunta preguntaIdpreguntaNew = respuesta.getPreguntaIdpregunta();
            Usuario usuarioIdusuarioOld = persistentRespuesta.getUsuarioIdusuario();
            Usuario usuarioIdusuarioNew = respuesta.getUsuarioIdusuario();
            if (preguntaIdpreguntaNew != null) {
                preguntaIdpreguntaNew = em.getReference(preguntaIdpreguntaNew.getClass(), preguntaIdpreguntaNew.getIdpregunta());
                respuesta.setPreguntaIdpregunta(preguntaIdpreguntaNew);
            }
            if (usuarioIdusuarioNew != null) {
                usuarioIdusuarioNew = em.getReference(usuarioIdusuarioNew.getClass(), usuarioIdusuarioNew.getIdusuario());
                respuesta.setUsuarioIdusuario(usuarioIdusuarioNew);
            }
            respuesta = em.merge(respuesta);
            if (preguntaIdpreguntaOld != null && !preguntaIdpreguntaOld.equals(preguntaIdpreguntaNew)) {
                preguntaIdpreguntaOld.getRespuestaCollection().remove(respuesta);
                preguntaIdpreguntaOld = em.merge(preguntaIdpreguntaOld);
            }
            if (preguntaIdpreguntaNew != null && !preguntaIdpreguntaNew.equals(preguntaIdpreguntaOld)) {
                preguntaIdpreguntaNew.getRespuestaCollection().add(respuesta);
                preguntaIdpreguntaNew = em.merge(preguntaIdpreguntaNew);
            }
            if (usuarioIdusuarioOld != null && !usuarioIdusuarioOld.equals(usuarioIdusuarioNew)) {
                usuarioIdusuarioOld.getRespuestaCollection().remove(respuesta);
                usuarioIdusuarioOld = em.merge(usuarioIdusuarioOld);
            }
            if (usuarioIdusuarioNew != null && !usuarioIdusuarioNew.equals(usuarioIdusuarioOld)) {
                usuarioIdusuarioNew.getRespuestaCollection().add(respuesta);
                usuarioIdusuarioNew = em.merge(usuarioIdusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = respuesta.getIdrespuesta();
                if (findRespuesta(id) == null) {
                    throw new NonexistentEntityException("The respuesta with id " + id + " no longer exists.");
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
            Respuesta respuesta;
            try {
                respuesta = em.getReference(Respuesta.class, id);
                respuesta.getIdrespuesta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The respuesta with id " + id + " no longer exists.", enfe);
            }
            Pregunta preguntaIdpregunta = respuesta.getPreguntaIdpregunta();
            if (preguntaIdpregunta != null) {
                preguntaIdpregunta.getRespuestaCollection().remove(respuesta);
                preguntaIdpregunta = em.merge(preguntaIdpregunta);
            }
            Usuario usuarioIdusuario = respuesta.getUsuarioIdusuario();
            if (usuarioIdusuario != null) {
                usuarioIdusuario.getRespuestaCollection().remove(respuesta);
                usuarioIdusuario = em.merge(usuarioIdusuario);
            }
            em.remove(respuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Respuesta> findRespuestaEntities() {
        return findRespuestaEntities(true, -1, -1);
    }

    public List<Respuesta> findRespuestaEntities(int maxResults, int firstResult) {
        return findRespuestaEntities(false, maxResults, firstResult);
    }

    private List<Respuesta> findRespuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Respuesta.class));
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

    public Respuesta findRespuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Respuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRespuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Respuesta> rt = cq.from(Respuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Respuesta findRespuestaByUserAndPregunta(Integer idpregunta, Integer idusuario) {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `respuesta` WHERE `pregunta_idpregunta` = " + idpregunta + " AND `usuario_idusuario` =" + idusuario;
        Query query = em.createNativeQuery(queryString, Respuesta.class);
        Respuesta respuesta = null;
        try {
            respuesta = (Respuesta) query.getSingleResult();
        } catch (NoResultException e) {
//            e.printStackTrace();
        }
        return respuesta;
    }

    public Collection getRespuestasByEncuesta(int id) {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `respuesta`,`pregunta` WHERE `pregunta_idpregunta` = `idpregunta` AND `encuesta_idencuesta`=" + id;
        Query query = em.createNativeQuery(queryString, Respuesta.class);
        ArrayList<Respuesta> respuestas = new ArrayList<>();

        try {
            respuestas = new ArrayList<Respuesta>(query.getResultList());
        } catch (NoResultException e) {
//            e.printStackTrace();
        }
        return respuestas;
    }

}
