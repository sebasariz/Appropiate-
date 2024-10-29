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
import com.iammagis.appropiate.beans.Modulo;
import com.iammagis.appropiate.beans.Submodulo;
import com.iammagis.appropiate.beans.Usuario;
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
public class SubmoduloJpaController implements Serializable {

    public SubmoduloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Submodulo submodulo) {
        if (submodulo.getUsuarioCollection() == null) {
            submodulo.setUsuarioCollection(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modulo moduloIdmodulo = submodulo.getModuloIdmodulo();
            if (moduloIdmodulo != null) {
                moduloIdmodulo = em.getReference(moduloIdmodulo.getClass(), moduloIdmodulo.getIdmodulo());
                submodulo.setModuloIdmodulo(moduloIdmodulo);
            }
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : submodulo.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdusuario());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            submodulo.setUsuarioCollection(attachedUsuarioCollection);
            em.persist(submodulo);
            if (moduloIdmodulo != null) {
                moduloIdmodulo.getSubmoduloCollection().add(submodulo);
                moduloIdmodulo = em.merge(moduloIdmodulo);
            }
            for (Usuario usuarioCollectionUsuario : submodulo.getUsuarioCollection()) {
                usuarioCollectionUsuario.getSubmoduloCollection().add(submodulo);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Submodulo submodulo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Submodulo persistentSubmodulo = em.find(Submodulo.class, submodulo.getIdsubmodulo());
            Modulo moduloIdmoduloOld = persistentSubmodulo.getModuloIdmodulo();
            Modulo moduloIdmoduloNew = submodulo.getModuloIdmodulo();
            Collection<Usuario> usuarioCollectionOld = persistentSubmodulo.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = submodulo.getUsuarioCollection();
            if (moduloIdmoduloNew != null) {
                moduloIdmoduloNew = em.getReference(moduloIdmoduloNew.getClass(), moduloIdmoduloNew.getIdmodulo());
                submodulo.setModuloIdmodulo(moduloIdmoduloNew);
            }
            Collection<Usuario> attachedUsuarioCollectionNew = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionNewUsuarioToAttach : usuarioCollectionNew) {
                usuarioCollectionNewUsuarioToAttach = em.getReference(usuarioCollectionNewUsuarioToAttach.getClass(), usuarioCollectionNewUsuarioToAttach.getIdusuario());
                attachedUsuarioCollectionNew.add(usuarioCollectionNewUsuarioToAttach);
            }
            usuarioCollectionNew = attachedUsuarioCollectionNew;
            submodulo.setUsuarioCollection(usuarioCollectionNew);
            submodulo = em.merge(submodulo);
            if (moduloIdmoduloOld != null && !moduloIdmoduloOld.equals(moduloIdmoduloNew)) {
                moduloIdmoduloOld.getSubmoduloCollection().remove(submodulo);
                moduloIdmoduloOld = em.merge(moduloIdmoduloOld);
            }
            if (moduloIdmoduloNew != null && !moduloIdmoduloNew.equals(moduloIdmoduloOld)) {
                moduloIdmoduloNew.getSubmoduloCollection().add(submodulo);
                moduloIdmoduloNew = em.merge(moduloIdmoduloNew);
            }
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    usuarioCollectionOldUsuario.getSubmoduloCollection().remove(submodulo);
                    usuarioCollectionOldUsuario = em.merge(usuarioCollectionOldUsuario);
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    usuarioCollectionNewUsuario.getSubmoduloCollection().add(submodulo);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = submodulo.getIdsubmodulo();
                if (findSubmodulo(id) == null) {
                    throw new NonexistentEntityException("The submodulo with id " + id + " no longer exists.");
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
            Submodulo submodulo;
            try {
                submodulo = em.getReference(Submodulo.class, id);
                submodulo.getIdsubmodulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The submodulo with id " + id + " no longer exists.", enfe);
            }
            Modulo moduloIdmodulo = submodulo.getModuloIdmodulo();
            if (moduloIdmodulo != null) {
                moduloIdmodulo.getSubmoduloCollection().remove(submodulo);
                moduloIdmodulo = em.merge(moduloIdmodulo);
            }
            Collection<Usuario> usuarioCollection = submodulo.getUsuarioCollection();
            for (Usuario usuarioCollectionUsuario : usuarioCollection) {
                usuarioCollectionUsuario.getSubmoduloCollection().remove(submodulo);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }
            em.remove(submodulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Submodulo> findSubmoduloEntities() {
        return findSubmoduloEntities(true, -1, -1);
    }

    public List<Submodulo> findSubmoduloEntities(int maxResults, int firstResult) {
        return findSubmoduloEntities(false, maxResults, firstResult);
    }

    private List<Submodulo> findSubmoduloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Submodulo.class));
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

    public Submodulo findSubmodulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Submodulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubmoduloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Submodulo> rt = cq.from(Submodulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
