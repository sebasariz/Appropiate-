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
import com.iammagis.appropiate.beans.EncuestaHasUsuario;
import com.iammagis.appropiate.beans.EncuestaHasUsuarioPK;
import com.iammagis.appropiate.beans.Respuesta;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import com.iammagis.appropiate.jpa.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author sebasariz
 */
public class EncuestaHasUsuarioJpaController implements Serializable {

    public EncuestaHasUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EncuestaHasUsuario encuestaHasUsuario) throws PreexistingEntityException, Exception {
        if (encuestaHasUsuario.getEncuestaHasUsuarioPK() == null) {
            encuestaHasUsuario.setEncuestaHasUsuarioPK(new EncuestaHasUsuarioPK());
        }
        encuestaHasUsuario.getEncuestaHasUsuarioPK().setUsuarioIdusuario(encuestaHasUsuario.getUsuario().getIdusuario());
        encuestaHasUsuario.getEncuestaHasUsuarioPK().setEncuestaIdencuesta(encuestaHasUsuario.getEncuesta().getIdencuesta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encuesta encuesta = encuestaHasUsuario.getEncuesta();
            if (encuesta != null) {
                encuesta = em.getReference(encuesta.getClass(), encuesta.getIdencuesta());
                encuestaHasUsuario.setEncuesta(encuesta);
            }
            Usuario usuario = encuestaHasUsuario.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdusuario());
                encuestaHasUsuario.setUsuario(usuario);
            }
            em.persist(encuestaHasUsuario);
            if (encuesta != null) {
                encuesta.getEncuestaHasUsuarioCollection().add(encuestaHasUsuario);
                encuesta = em.merge(encuesta);
            }
            if (usuario != null) {
                usuario.getEncuestaHasUsuarioCollection().add(encuestaHasUsuario);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEncuestaHasUsuario(encuestaHasUsuario.getEncuestaHasUsuarioPK()) != null) {
                throw new PreexistingEntityException("EncuestaHasUsuario " + encuestaHasUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EncuestaHasUsuario encuestaHasUsuario) throws NonexistentEntityException, Exception {
        encuestaHasUsuario.getEncuestaHasUsuarioPK().setUsuarioIdusuario(encuestaHasUsuario.getUsuario().getIdusuario());
        encuestaHasUsuario.getEncuestaHasUsuarioPK().setEncuestaIdencuesta(encuestaHasUsuario.getEncuesta().getIdencuesta());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EncuestaHasUsuario persistentEncuestaHasUsuario = em.find(EncuestaHasUsuario.class, encuestaHasUsuario.getEncuestaHasUsuarioPK());
            Encuesta encuestaOld = persistentEncuestaHasUsuario.getEncuesta();
            Encuesta encuestaNew = encuestaHasUsuario.getEncuesta();
            Usuario usuarioOld = persistentEncuestaHasUsuario.getUsuario();
            Usuario usuarioNew = encuestaHasUsuario.getUsuario();
            if (encuestaNew != null) {
                encuestaNew = em.getReference(encuestaNew.getClass(), encuestaNew.getIdencuesta());
                encuestaHasUsuario.setEncuesta(encuestaNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdusuario());
                encuestaHasUsuario.setUsuario(usuarioNew);
            }
            encuestaHasUsuario = em.merge(encuestaHasUsuario);
            if (encuestaOld != null && !encuestaOld.equals(encuestaNew)) {
                encuestaOld.getEncuestaHasUsuarioCollection().remove(encuestaHasUsuario);
                encuestaOld = em.merge(encuestaOld);
            }
            if (encuestaNew != null && !encuestaNew.equals(encuestaOld)) {
                encuestaNew.getEncuestaHasUsuarioCollection().add(encuestaHasUsuario);
                encuestaNew = em.merge(encuestaNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getEncuestaHasUsuarioCollection().remove(encuestaHasUsuario);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getEncuestaHasUsuarioCollection().add(encuestaHasUsuario);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                EncuestaHasUsuarioPK id = encuestaHasUsuario.getEncuestaHasUsuarioPK();
                if (findEncuestaHasUsuario(id) == null) {
                    throw new NonexistentEntityException("The encuestaHasUsuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(EncuestaHasUsuarioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EncuestaHasUsuario encuestaHasUsuario;
            try {
                encuestaHasUsuario = em.getReference(EncuestaHasUsuario.class, id);
                encuestaHasUsuario.getEncuestaHasUsuarioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encuestaHasUsuario with id " + id + " no longer exists.", enfe);
            }
            Encuesta encuesta = encuestaHasUsuario.getEncuesta();
            if (encuesta != null) {
                encuesta.getEncuestaHasUsuarioCollection().remove(encuestaHasUsuario);
                encuesta = em.merge(encuesta);
            }
            Usuario usuario = encuestaHasUsuario.getUsuario();
            if (usuario != null) {
                usuario.getEncuestaHasUsuarioCollection().remove(encuestaHasUsuario);
                usuario = em.merge(usuario);
            }
            em.remove(encuestaHasUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EncuestaHasUsuario> findEncuestaHasUsuarioEntities() {
        return findEncuestaHasUsuarioEntities(true, -1, -1);
    }

    public List<EncuestaHasUsuario> findEncuestaHasUsuarioEntities(int maxResults, int firstResult) {
        return findEncuestaHasUsuarioEntities(false, maxResults, firstResult);
    }

    private List<EncuestaHasUsuario> findEncuestaHasUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EncuestaHasUsuario.class));
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

    public EncuestaHasUsuario findEncuestaHasUsuario(EncuestaHasUsuarioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EncuestaHasUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getEncuestaHasUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EncuestaHasUsuario> rt = cq.from(EncuestaHasUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public EncuestaHasUsuario findEncuestaHasUsuario(Integer idencuesta, Integer idusuario) {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `encuesta_has_usuario` WHERE `usuario_idusuario` = " + idusuario + " AND `encuesta_idencuesta`=" + idencuesta;
        Query query = em.createNativeQuery(queryString, EncuestaHasUsuario.class);
        EncuestaHasUsuario encuestaHasUsuario=null;
        try {
            encuestaHasUsuario = (EncuestaHasUsuario) query.getSingleResult();
        } catch (NoResultException e) {
//            e.printStackTrace();
        }
        return encuestaHasUsuario;
    }

}
