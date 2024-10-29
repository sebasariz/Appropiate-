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
import com.iammagis.appropiate.beans.GrupoInteres;
import com.iammagis.appropiate.beans.Usuario;
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
public class GrupoInteresJpaController implements Serializable {

    public GrupoInteresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GrupoInteres grupoInteres) {
        if (grupoInteres.getUsuarioCollection() == null) {
            grupoInteres.setUsuarioCollection(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad entidadIdentidad = grupoInteres.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad = em.getReference(entidadIdentidad.getClass(), entidadIdentidad.getIdentidad());
                grupoInteres.setEntidadIdentidad(entidadIdentidad);
            }
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : grupoInteres.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdusuario());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            grupoInteres.setUsuarioCollection(attachedUsuarioCollection);
            em.persist(grupoInteres);
            if (entidadIdentidad != null) {
                entidadIdentidad.getGrupoInteresCollection().add(grupoInteres);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            for (Usuario usuarioCollectionUsuario : grupoInteres.getUsuarioCollection()) {
                usuarioCollectionUsuario.getGrupoInteresCollection().add(grupoInteres);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GrupoInteres grupoInteres) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GrupoInteres persistentGrupoInteres = em.find(GrupoInteres.class, grupoInteres.getIdgrupoInteres());
            Entidad entidadIdentidadOld = persistentGrupoInteres.getEntidadIdentidad();
            Entidad entidadIdentidadNew = grupoInteres.getEntidadIdentidad();
            Collection<Usuario> usuarioCollectionOld = persistentGrupoInteres.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = grupoInteres.getUsuarioCollection();
            if (entidadIdentidadNew != null) {
                entidadIdentidadNew = em.getReference(entidadIdentidadNew.getClass(), entidadIdentidadNew.getIdentidad());
                grupoInteres.setEntidadIdentidad(entidadIdentidadNew);
            }
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getIdusuario());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            grupoInteres.setUsuarioCollection(usuarioCollectionNew);
            grupoInteres = em.merge(grupoInteres);
            if (entidadIdentidadOld != null && !entidadIdentidadOld.equals(entidadIdentidadNew)) {
                entidadIdentidadOld.getGrupoInteresCollection().remove(grupoInteres);
                entidadIdentidadOld = em.merge(entidadIdentidadOld);
            }
            if (entidadIdentidadNew != null && !entidadIdentidadNew.equals(entidadIdentidadOld)) {
                entidadIdentidadNew.getGrupoInteresCollection().add(grupoInteres);
                entidadIdentidadNew = em.merge(entidadIdentidadNew);
            }
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    usuarioCollectionOldUsuario.getGrupoInteresCollection().remove(grupoInteres);
                    usuarioCollectionOldUsuario = em.merge(usuarioCollectionOldUsuario);
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    usuarioCollectionNewUsuario.getGrupoInteresCollection().add(grupoInteres);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = grupoInteres.getIdgrupoInteres();
                if (findGrupoInteres(id) == null) {
                    throw new NonexistentEntityException("The grupoInteres with id " + id + " no longer exists.");
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
            GrupoInteres grupoInteres;
            try {
                grupoInteres = em.getReference(GrupoInteres.class, id);
                grupoInteres.getIdgrupoInteres();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The grupoInteres with id " + id + " no longer exists.", enfe);
            }
            Entidad entidadIdentidad = grupoInteres.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad.getGrupoInteresCollection().remove(grupoInteres);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            Collection<Usuario> usuarioCollection = grupoInteres.getUsuarioCollection();
            for (Usuario usuarioCollectionUsuario : usuarioCollection) {
                usuarioCollectionUsuario.getGrupoInteresCollection().remove(grupoInteres);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }
            em.remove(grupoInteres);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GrupoInteres> findGrupoInteresEntities() {
        return findGrupoInteresEntities(true, -1, -1);
    }

    public List<GrupoInteres> findGrupoInteresEntities(int maxResults, int firstResult) {
        return findGrupoInteresEntities(false, maxResults, firstResult);
    }

    private List<GrupoInteres> findGrupoInteresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GrupoInteres.class));
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

    public GrupoInteres findGrupoInteres(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GrupoInteres.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrupoInteresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GrupoInteres> rt = cq.from(GrupoInteres.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
