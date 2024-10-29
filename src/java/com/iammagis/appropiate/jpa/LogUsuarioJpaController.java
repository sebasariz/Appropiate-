/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.jpa;

import com.iammagis.appropiate.beans.LogUsuario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sebasariz
 */
public class LogUsuarioJpaController implements Serializable {

    public LogUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LogUsuario logUsuario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuarioIdusuario = logUsuario.getUsuarioIdusuario();
            if (usuarioIdusuario != null) {
                usuarioIdusuario = em.getReference(usuarioIdusuario.getClass(), usuarioIdusuario.getIdusuario());
                logUsuario.setUsuarioIdusuario(usuarioIdusuario);
            }
            em.persist(logUsuario);
            if (usuarioIdusuario != null) {
                usuarioIdusuario.getLogUsuarioCollection().add(logUsuario);
                usuarioIdusuario = em.merge(usuarioIdusuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LogUsuario logUsuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            LogUsuario persistentLogUsuario = em.find(LogUsuario.class, logUsuario.getIdlogUsuario());
            Usuario usuarioIdusuarioOld = persistentLogUsuario.getUsuarioIdusuario();
            Usuario usuarioIdusuarioNew = logUsuario.getUsuarioIdusuario();
            if (usuarioIdusuarioNew != null) {
                usuarioIdusuarioNew = em.getReference(usuarioIdusuarioNew.getClass(), usuarioIdusuarioNew.getIdusuario());
                logUsuario.setUsuarioIdusuario(usuarioIdusuarioNew);
            }
            logUsuario = em.merge(logUsuario);
            if (usuarioIdusuarioOld != null && !usuarioIdusuarioOld.equals(usuarioIdusuarioNew)) {
                usuarioIdusuarioOld.getLogUsuarioCollection().remove(logUsuario);
                usuarioIdusuarioOld = em.merge(usuarioIdusuarioOld);
            }
            if (usuarioIdusuarioNew != null && !usuarioIdusuarioNew.equals(usuarioIdusuarioOld)) {
                usuarioIdusuarioNew.getLogUsuarioCollection().add(logUsuario);
                usuarioIdusuarioNew = em.merge(usuarioIdusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = logUsuario.getIdlogUsuario();
                if (findLogUsuario(id) == null) {
                    throw new NonexistentEntityException("The logUsuario with id " + id + " no longer exists.");
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
            LogUsuario logUsuario;
            try {
                logUsuario = em.getReference(LogUsuario.class, id);
                logUsuario.getIdlogUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logUsuario with id " + id + " no longer exists.", enfe);
            }
            Usuario usuarioIdusuario = logUsuario.getUsuarioIdusuario();
            if (usuarioIdusuario != null) {
                usuarioIdusuario.getLogUsuarioCollection().remove(logUsuario);
                usuarioIdusuario = em.merge(usuarioIdusuario);
            }
            em.remove(logUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LogUsuario> findLogUsuarioEntities() {
        return findLogUsuarioEntities(true, -1, -1);
    }

    public List<LogUsuario> findLogUsuarioEntities(int maxResults, int firstResult) {
        return findLogUsuarioEntities(false, maxResults, firstResult);
    }

    private List<LogUsuario> findLogUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LogUsuario.class));
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

    public LogUsuario findLogUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LogUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LogUsuario> rt = cq.from(LogUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public ArrayList<LogUsuario> getLogsFromEntidad(Integer identidad) {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `log_usuario`,`entidad_has_usuario` WHERE `log_usuario`.`usuario_idusuario` = `entidad_has_usuario`.`usuario_idusuario` AND `entidad_identidad` =" + identidad+" ORDER BY `log_usuario`.`fecha` DESC LIMIT 100";
        Query query = em.createNativeQuery(queryString, LogUsuario.class);
        ArrayList<LogUsuario> logUsers = new ArrayList<>();
        try {
            logUsers = new ArrayList<>(query.getResultList());
        } catch (Exception e) {
        }
        return logUsers;
    }

    public ArrayList<LogUsuario> getLogsFromEntidadRoot() {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `log_usuario`,`usuario` WHERE `log_usuario`.`usuario_idusuario` = `usuario`.`idusuario` AND (`tipo_usuario_idtipo_usuario`= 1 OR `tipo_usuario_idtipo_usuario`= 2) ORDER BY `log_usuario`.`fecha` DESC LIMIT 100";
        Query query = em.createNativeQuery(queryString, LogUsuario.class);
        ArrayList<LogUsuario> logUsers = new ArrayList<>();
        try {
            logUsers = new ArrayList<>(query.getResultList());
        } catch (Exception e) {
        }
        return logUsers;
    }
    
}
