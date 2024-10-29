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
import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.CampanaComunicadosHasUsuario;
import com.iammagis.appropiate.beans.CampanaComunicadosHasUsuarioPK;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import com.iammagis.appropiate.jpa.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 *
 * @author sebastianarizmendy
 */
public class CampanaComunicadosHasUsuarioJpaController implements Serializable {

    public CampanaComunicadosHasUsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CampanaComunicadosHasUsuario campanaComunicadosHasUsuario) throws PreexistingEntityException, Exception {
        if (campanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioPK() == null) {
            campanaComunicadosHasUsuario.setCampanaComunicadosHasUsuarioPK(new CampanaComunicadosHasUsuarioPK());
        }
        campanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioPK().setCampanaComunicadosIdcampana(campanaComunicadosHasUsuario.getCampanaComunicados().getIdcampana());
        campanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioPK().setUsuarioIdusuario(campanaComunicadosHasUsuario.getUsuario().getIdusuario());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CampanaComunicados campanaComunicados = campanaComunicadosHasUsuario.getCampanaComunicados();
            if (campanaComunicados != null) {
                campanaComunicados = em.getReference(campanaComunicados.getClass(), campanaComunicados.getIdcampana());
                campanaComunicadosHasUsuario.setCampanaComunicados(campanaComunicados);
            }
            Usuario usuario = campanaComunicadosHasUsuario.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getIdusuario());
                campanaComunicadosHasUsuario.setUsuario(usuario);
            }
            em.persist(campanaComunicadosHasUsuario);
            if (campanaComunicados != null) {
                campanaComunicados.getCampanaComunicadosHasUsuarioCollection().add(campanaComunicadosHasUsuario);
                campanaComunicados = em.merge(campanaComunicados);
            }
            if (usuario != null) {
                usuario.getCampanaComunicadosHasUsuarioCollection().add(campanaComunicadosHasUsuario);
                usuario = em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCampanaComunicadosHasUsuario(campanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioPK()) != null) {
                throw new PreexistingEntityException("CampanaComunicadosHasUsuario " + campanaComunicadosHasUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CampanaComunicadosHasUsuario campanaComunicadosHasUsuario) throws NonexistentEntityException, Exception {
        campanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioPK().setCampanaComunicadosIdcampana(campanaComunicadosHasUsuario.getCampanaComunicados().getIdcampana());
        campanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioPK().setUsuarioIdusuario(campanaComunicadosHasUsuario.getUsuario().getIdusuario());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CampanaComunicadosHasUsuario persistentCampanaComunicadosHasUsuario = em.find(CampanaComunicadosHasUsuario.class, campanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioPK());
            CampanaComunicados campanaComunicadosOld = persistentCampanaComunicadosHasUsuario.getCampanaComunicados();
            CampanaComunicados campanaComunicadosNew = campanaComunicadosHasUsuario.getCampanaComunicados();
            Usuario usuarioOld = persistentCampanaComunicadosHasUsuario.getUsuario();
            Usuario usuarioNew = campanaComunicadosHasUsuario.getUsuario();
            if (campanaComunicadosNew != null) {
                campanaComunicadosNew = em.getReference(campanaComunicadosNew.getClass(), campanaComunicadosNew.getIdcampana());
                campanaComunicadosHasUsuario.setCampanaComunicados(campanaComunicadosNew);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getIdusuario());
                campanaComunicadosHasUsuario.setUsuario(usuarioNew);
            }
            campanaComunicadosHasUsuario = em.merge(campanaComunicadosHasUsuario);
            if (campanaComunicadosOld != null && !campanaComunicadosOld.equals(campanaComunicadosNew)) {
                campanaComunicadosOld.getCampanaComunicadosHasUsuarioCollection().remove(campanaComunicadosHasUsuario);
                campanaComunicadosOld = em.merge(campanaComunicadosOld);
            }
            if (campanaComunicadosNew != null && !campanaComunicadosNew.equals(campanaComunicadosOld)) {
                campanaComunicadosNew.getCampanaComunicadosHasUsuarioCollection().add(campanaComunicadosHasUsuario);
                campanaComunicadosNew = em.merge(campanaComunicadosNew);
            }
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getCampanaComunicadosHasUsuarioCollection().remove(campanaComunicadosHasUsuario);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getCampanaComunicadosHasUsuarioCollection().add(campanaComunicadosHasUsuario);
                usuarioNew = em.merge(usuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CampanaComunicadosHasUsuarioPK id = campanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioPK();
                if (findCampanaComunicadosHasUsuario(id) == null) {
                    throw new NonexistentEntityException("The campanaComunicadosHasUsuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CampanaComunicadosHasUsuarioPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CampanaComunicadosHasUsuario campanaComunicadosHasUsuario;
            try {
                campanaComunicadosHasUsuario = em.getReference(CampanaComunicadosHasUsuario.class, id);
                campanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The campanaComunicadosHasUsuario with id " + id + " no longer exists.", enfe);
            }
            CampanaComunicados campanaComunicados = campanaComunicadosHasUsuario.getCampanaComunicados();
            if (campanaComunicados != null) {
                campanaComunicados.getCampanaComunicadosHasUsuarioCollection().remove(campanaComunicadosHasUsuario);
                campanaComunicados = em.merge(campanaComunicados);
            }
            Usuario usuario = campanaComunicadosHasUsuario.getUsuario();
            if (usuario != null) {
                usuario.getCampanaComunicadosHasUsuarioCollection().remove(campanaComunicadosHasUsuario);
                usuario = em.merge(usuario);
            }
            em.remove(campanaComunicadosHasUsuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CampanaComunicadosHasUsuario> findCampanaComunicadosHasUsuarioEntities() {
        return findCampanaComunicadosHasUsuarioEntities(true, -1, -1);
    }

    public List<CampanaComunicadosHasUsuario> findCampanaComunicadosHasUsuarioEntities(int maxResults, int firstResult) {
        return findCampanaComunicadosHasUsuarioEntities(false, maxResults, firstResult);
    }

    private List<CampanaComunicadosHasUsuario> findCampanaComunicadosHasUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CampanaComunicadosHasUsuario.class));
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

    public CampanaComunicadosHasUsuario findCampanaComunicadosHasUsuario(CampanaComunicadosHasUsuarioPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CampanaComunicadosHasUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getCampanaComunicadosHasUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CampanaComunicadosHasUsuario> rt = cq.from(CampanaComunicadosHasUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public ArrayList<CampanaComunicados> getCampanasActivas() {

        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `campana_comunicados`";
        Query query = em.createNativeQuery(queryString, CampanaComunicados.class);
        ArrayList<CampanaComunicados> campanaComunicadoses = new ArrayList<>(query.getResultList());
        return campanaComunicadoses;

    }

    public void remofeFromCampana(int id) {
        EntityManager em = getEntityManager();
        String queryString = "DELETE FROM `campana_comunicados_has_usuario` WHERE `campana_comunicados_idcampana` =" + id;
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createNativeQuery(queryString);
        query.executeUpdate();
        tx.commit();
    }

}
