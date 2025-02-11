/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.jpa;

import com.iammagis.appropiate.beans.Modulo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iammagis.appropiate.beans.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import com.iammagis.appropiate.beans.Submodulo;
import com.iammagis.appropiate.jpa.exceptions.IllegalOrphanException;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sebastianarizmendy
 */
public class ModuloJpaController implements Serializable {

    public ModuloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Modulo modulo) {
        if (modulo.getUsuarioCollection() == null) {
            modulo.setUsuarioCollection(new ArrayList<Usuario>());
        }
        if (modulo.getSubmoduloCollection() == null) {
            modulo.setSubmoduloCollection(new ArrayList<Submodulo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Usuario> attachedUsuarioCollection = new ArrayList<Usuario>();
            for (Usuario usuarioCollectionUsuarioToAttach : modulo.getUsuarioCollection()) {
                usuarioCollectionUsuarioToAttach = em.getReference(usuarioCollectionUsuarioToAttach.getClass(), usuarioCollectionUsuarioToAttach.getIdusuario());
                attachedUsuarioCollection.add(usuarioCollectionUsuarioToAttach);
            }
            modulo.setUsuarioCollection(attachedUsuarioCollection);
            Collection<Submodulo> attachedSubmoduloCollection = new ArrayList<Submodulo>();
            for (Submodulo submoduloCollectionSubmoduloToAttach : modulo.getSubmoduloCollection()) {
                submoduloCollectionSubmoduloToAttach = em.getReference(submoduloCollectionSubmoduloToAttach.getClass(), submoduloCollectionSubmoduloToAttach.getIdsubmodulo());
                attachedSubmoduloCollection.add(submoduloCollectionSubmoduloToAttach);
            }
            modulo.setSubmoduloCollection(attachedSubmoduloCollection);
            em.persist(modulo);
            for (Usuario usuarioCollectionUsuario : modulo.getUsuarioCollection()) {
                usuarioCollectionUsuario.getModuloCollection().add(modulo);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }
            for (Submodulo submoduloCollectionSubmodulo : modulo.getSubmoduloCollection()) {
                Modulo oldModuloIdmoduloOfSubmoduloCollectionSubmodulo = submoduloCollectionSubmodulo.getModuloIdmodulo();
                submoduloCollectionSubmodulo.setModuloIdmodulo(modulo);
                submoduloCollectionSubmodulo = em.merge(submoduloCollectionSubmodulo);
                if (oldModuloIdmoduloOfSubmoduloCollectionSubmodulo != null) {
                    oldModuloIdmoduloOfSubmoduloCollectionSubmodulo.getSubmoduloCollection().remove(submoduloCollectionSubmodulo);
                    oldModuloIdmoduloOfSubmoduloCollectionSubmodulo = em.merge(oldModuloIdmoduloOfSubmoduloCollectionSubmodulo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Modulo modulo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Modulo persistentModulo = em.find(Modulo.class, modulo.getIdmodulo());
            Collection<Usuario> usuarioCollectionOld = persistentModulo.getUsuarioCollection();
            Collection<Usuario> usuarioCollectionNew = modulo.getUsuarioCollection();
            Collection<Submodulo> submoduloCollectionOld = persistentModulo.getSubmoduloCollection();
            Collection<Submodulo> submoduloCollectionNew = modulo.getSubmoduloCollection();
            List<String> illegalOrphanMessages = null;
            for (Submodulo submoduloCollectionOldSubmodulo : submoduloCollectionOld) {
                if (!submoduloCollectionNew.contains(submoduloCollectionOldSubmodulo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Submodulo " + submoduloCollectionOldSubmodulo + " since its moduloIdmodulo field is not nullable.");
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
            modulo.setUsuarioCollection(usuarioCollectionNew);
            Collection<Submodulo> attachedSubmoduloCollectionNew = new ArrayList<Submodulo>();
            for (Submodulo submoduloCollectionNewSubmoduloToAttach : submoduloCollectionNew) {
                submoduloCollectionNewSubmoduloToAttach = em.getReference(submoduloCollectionNewSubmoduloToAttach.getClass(), submoduloCollectionNewSubmoduloToAttach.getIdsubmodulo());
                attachedSubmoduloCollectionNew.add(submoduloCollectionNewSubmoduloToAttach);
            }
            submoduloCollectionNew = attachedSubmoduloCollectionNew;
            modulo.setSubmoduloCollection(submoduloCollectionNew);
            modulo = em.merge(modulo);
            for (Usuario usuarioCollectionOldUsuario : usuarioCollectionOld) {
                if (!usuarioCollectionNew.contains(usuarioCollectionOldUsuario)) {
                    usuarioCollectionOldUsuario.getModuloCollection().remove(modulo);
                    usuarioCollectionOldUsuario = em.merge(usuarioCollectionOldUsuario);
                }
            }
            for (Usuario usuarioCollectionNewUsuario : usuarioCollectionNew) {
                if (!usuarioCollectionOld.contains(usuarioCollectionNewUsuario)) {
                    usuarioCollectionNewUsuario.getModuloCollection().add(modulo);
                    usuarioCollectionNewUsuario = em.merge(usuarioCollectionNewUsuario);
                }
            }
            for (Submodulo submoduloCollectionNewSubmodulo : submoduloCollectionNew) {
                if (!submoduloCollectionOld.contains(submoduloCollectionNewSubmodulo)) {
                    Modulo oldModuloIdmoduloOfSubmoduloCollectionNewSubmodulo = submoduloCollectionNewSubmodulo.getModuloIdmodulo();
                    submoduloCollectionNewSubmodulo.setModuloIdmodulo(modulo);
                    submoduloCollectionNewSubmodulo = em.merge(submoduloCollectionNewSubmodulo);
                    if (oldModuloIdmoduloOfSubmoduloCollectionNewSubmodulo != null && !oldModuloIdmoduloOfSubmoduloCollectionNewSubmodulo.equals(modulo)) {
                        oldModuloIdmoduloOfSubmoduloCollectionNewSubmodulo.getSubmoduloCollection().remove(submoduloCollectionNewSubmodulo);
                        oldModuloIdmoduloOfSubmoduloCollectionNewSubmodulo = em.merge(oldModuloIdmoduloOfSubmoduloCollectionNewSubmodulo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = modulo.getIdmodulo();
                if (findModulo(id) == null) {
                    throw new NonexistentEntityException("The modulo with id " + id + " no longer exists.");
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
            Modulo modulo;
            try {
                modulo = em.getReference(Modulo.class, id);
                modulo.getIdmodulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The modulo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Submodulo> submoduloCollectionOrphanCheck = modulo.getSubmoduloCollection();
            for (Submodulo submoduloCollectionOrphanCheckSubmodulo : submoduloCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Modulo (" + modulo + ") cannot be destroyed since the Submodulo " + submoduloCollectionOrphanCheckSubmodulo + " in its submoduloCollection field has a non-nullable moduloIdmodulo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Usuario> usuarioCollection = modulo.getUsuarioCollection();
            for (Usuario usuarioCollectionUsuario : usuarioCollection) {
                usuarioCollectionUsuario.getModuloCollection().remove(modulo);
                usuarioCollectionUsuario = em.merge(usuarioCollectionUsuario);
            }
            em.remove(modulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Modulo> findModuloEntities() {
        return findModuloEntities(true, -1, -1);
    }

    public List<Modulo> findModuloEntities(int maxResults, int firstResult) {
        return findModuloEntities(false, maxResults, firstResult);
    }

    private List<Modulo> findModuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Modulo.class));
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

    public Modulo findModulo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Modulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getModuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Modulo> rt = cq.from(Modulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
