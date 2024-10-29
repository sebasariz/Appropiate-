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
import java.util.ArrayList;
import java.util.Collection;
import com.iammagis.appropiate.beans.CampanaComunicadosHasUsuario;
import com.iammagis.appropiate.jpa.exceptions.IllegalOrphanException;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 *
 * @author sebastianarizmendy
 */
public class CampanaComunicadosJpaController implements Serializable {

    public CampanaComunicadosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CampanaComunicados campanaComunicados) {
        if (campanaComunicados.getArchivoCollection() == null) {
            campanaComunicados.setArchivoCollection(new ArrayList<Archivo>());
        }
        if (campanaComunicados.getCampanaComunicadosHasUsuarioCollection() == null) {
            campanaComunicados.setCampanaComunicadosHasUsuarioCollection(new ArrayList<CampanaComunicadosHasUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad entidadIdentidad = campanaComunicados.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad = em.getReference(entidadIdentidad.getClass(), entidadIdentidad.getIdentidad());
                campanaComunicados.setEntidadIdentidad(entidadIdentidad);
            }
            Collection<Archivo> attachedArchivoCollection = new ArrayList<Archivo>();
            for (Archivo archivoCollectionArchivoToAttach : campanaComunicados.getArchivoCollection()) {
                archivoCollectionArchivoToAttach = em.getReference(archivoCollectionArchivoToAttach.getClass(), archivoCollectionArchivoToAttach.getIdarchivo());
                attachedArchivoCollection.add(archivoCollectionArchivoToAttach);
            }
            campanaComunicados.setArchivoCollection(attachedArchivoCollection);
            Collection<CampanaComunicadosHasUsuario> attachedCampanaComunicadosHasUsuarioCollection = new ArrayList<CampanaComunicadosHasUsuario>();
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuarioToAttach : campanaComunicados.getCampanaComunicadosHasUsuarioCollection()) {
                campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuarioToAttach = em.getReference(campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuarioToAttach.getClass(), campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuarioToAttach.getCampanaComunicadosHasUsuarioPK());
                attachedCampanaComunicadosHasUsuarioCollection.add(campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuarioToAttach);
            }
            campanaComunicados.setCampanaComunicadosHasUsuarioCollection(attachedCampanaComunicadosHasUsuarioCollection);
            em.persist(campanaComunicados);
            if (entidadIdentidad != null) {
                entidadIdentidad.getCampanaComunicadosCollection().add(campanaComunicados);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            for (Archivo archivoCollectionArchivo : campanaComunicados.getArchivoCollection()) {
                CampanaComunicados oldCampanaComunicadosIdcampanaOfArchivoCollectionArchivo = archivoCollectionArchivo.getCampanaComunicadosIdcampana();
                archivoCollectionArchivo.setCampanaComunicadosIdcampana(campanaComunicados);
                archivoCollectionArchivo = em.merge(archivoCollectionArchivo);
                if (oldCampanaComunicadosIdcampanaOfArchivoCollectionArchivo != null) {
                    oldCampanaComunicadosIdcampanaOfArchivoCollectionArchivo.getArchivoCollection().remove(archivoCollectionArchivo);
                    oldCampanaComunicadosIdcampanaOfArchivoCollectionArchivo = em.merge(oldCampanaComunicadosIdcampanaOfArchivoCollectionArchivo);
                }
            }
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario : campanaComunicados.getCampanaComunicadosHasUsuarioCollection()) {
                CampanaComunicados oldCampanaComunicadosOfCampanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario = campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario.getCampanaComunicados();
                campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario.setCampanaComunicados(campanaComunicados);
                campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario = em.merge(campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario);
                if (oldCampanaComunicadosOfCampanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario != null) {
                    oldCampanaComunicadosOfCampanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioCollection().remove(campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario);
                    oldCampanaComunicadosOfCampanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario = em.merge(oldCampanaComunicadosOfCampanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public CampanaComunicados edit(CampanaComunicados campanaComunicados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CampanaComunicados persistentCampanaComunicados = em.find(CampanaComunicados.class, campanaComunicados.getIdcampana());
            Entidad entidadIdentidadOld = persistentCampanaComunicados.getEntidadIdentidad();
            Entidad entidadIdentidadNew = campanaComunicados.getEntidadIdentidad();
            Collection<Archivo> archivoCollectionOld = persistentCampanaComunicados.getArchivoCollection();
            Collection<Archivo> archivoCollectionNew = campanaComunicados.getArchivoCollection();
            Collection<CampanaComunicadosHasUsuario> campanaComunicadosHasUsuarioCollectionOld = persistentCampanaComunicados.getCampanaComunicadosHasUsuarioCollection();
            Collection<CampanaComunicadosHasUsuario> campanaComunicadosHasUsuarioCollectionNew = campanaComunicados.getCampanaComunicadosHasUsuarioCollection();
            List<String> illegalOrphanMessages = null;
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuarioCollectionOldCampanaComunicadosHasUsuario : campanaComunicadosHasUsuarioCollectionOld) {
                if (!campanaComunicadosHasUsuarioCollectionNew.contains(campanaComunicadosHasUsuarioCollectionOldCampanaComunicadosHasUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CampanaComunicadosHasUsuario " + campanaComunicadosHasUsuarioCollectionOldCampanaComunicadosHasUsuario + " since its campanaComunicados field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (entidadIdentidadNew != null) {
                entidadIdentidadNew = em.getReference(entidadIdentidadNew.getClass(), entidadIdentidadNew.getIdentidad());
                campanaComunicados.setEntidadIdentidad(entidadIdentidadNew);
            }
            Collection<Archivo> attachedArchivoCollectionNew = new ArrayList<Archivo>();
            for (Archivo archivoCollectionNewArchivoToAttach : archivoCollectionNew) {
                archivoCollectionNewArchivoToAttach = em.getReference(archivoCollectionNewArchivoToAttach.getClass(), archivoCollectionNewArchivoToAttach.getIdarchivo());
                attachedArchivoCollectionNew.add(archivoCollectionNewArchivoToAttach);
            }
            archivoCollectionNew = attachedArchivoCollectionNew;
            campanaComunicados.setArchivoCollection(archivoCollectionNew);
            Collection<CampanaComunicadosHasUsuario> attachedCampanaComunicadosHasUsuarioCollectionNew = new ArrayList<CampanaComunicadosHasUsuario>();
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuarioToAttach : campanaComunicadosHasUsuarioCollectionNew) {
                campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuarioToAttach = em.getReference(campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuarioToAttach.getClass(), campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuarioToAttach.getCampanaComunicadosHasUsuarioPK());
                attachedCampanaComunicadosHasUsuarioCollectionNew.add(campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuarioToAttach);
            }
            campanaComunicadosHasUsuarioCollectionNew = attachedCampanaComunicadosHasUsuarioCollectionNew;
            campanaComunicados.setCampanaComunicadosHasUsuarioCollection(campanaComunicadosHasUsuarioCollectionNew);
            campanaComunicados = em.merge(campanaComunicados);
            if (entidadIdentidadOld != null && !entidadIdentidadOld.equals(entidadIdentidadNew)) {
                entidadIdentidadOld.getCampanaComunicadosCollection().remove(campanaComunicados);
                entidadIdentidadOld = em.merge(entidadIdentidadOld);
            }
            if (entidadIdentidadNew != null && !entidadIdentidadNew.equals(entidadIdentidadOld)) {
                entidadIdentidadNew.getCampanaComunicadosCollection().add(campanaComunicados);
                entidadIdentidadNew = em.merge(entidadIdentidadNew);
            }
            for (Archivo archivoCollectionOldArchivo : archivoCollectionOld) {
                if (!archivoCollectionNew.contains(archivoCollectionOldArchivo)) {
                    archivoCollectionOldArchivo.setCampanaComunicadosIdcampana(null);
                    archivoCollectionOldArchivo = em.merge(archivoCollectionOldArchivo);
                }
            }
            for (Archivo archivoCollectionNewArchivo : archivoCollectionNew) {
                if (!archivoCollectionOld.contains(archivoCollectionNewArchivo)) {
                    CampanaComunicados oldCampanaComunicadosIdcampanaOfArchivoCollectionNewArchivo = archivoCollectionNewArchivo.getCampanaComunicadosIdcampana();
                    archivoCollectionNewArchivo.setCampanaComunicadosIdcampana(campanaComunicados);
                    archivoCollectionNewArchivo = em.merge(archivoCollectionNewArchivo);
                    if (oldCampanaComunicadosIdcampanaOfArchivoCollectionNewArchivo != null && !oldCampanaComunicadosIdcampanaOfArchivoCollectionNewArchivo.equals(campanaComunicados)) {
                        oldCampanaComunicadosIdcampanaOfArchivoCollectionNewArchivo.getArchivoCollection().remove(archivoCollectionNewArchivo);
                        oldCampanaComunicadosIdcampanaOfArchivoCollectionNewArchivo = em.merge(oldCampanaComunicadosIdcampanaOfArchivoCollectionNewArchivo);
                    }
                }
            }
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario : campanaComunicadosHasUsuarioCollectionNew) {
                if (!campanaComunicadosHasUsuarioCollectionOld.contains(campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario)) {
                    CampanaComunicados oldCampanaComunicadosOfCampanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario = campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario.getCampanaComunicados();
                    campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario.setCampanaComunicados(campanaComunicados);
                    campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario = em.merge(campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario);
                    if (oldCampanaComunicadosOfCampanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario != null && !oldCampanaComunicadosOfCampanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario.equals(campanaComunicados)) {
                        oldCampanaComunicadosOfCampanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioCollection().remove(campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario);
                        oldCampanaComunicadosOfCampanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario = em.merge(oldCampanaComunicadosOfCampanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario);
                    }
                }
            }
            em.getTransaction().commit();
            return campanaComunicados;
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = campanaComunicados.getIdcampana();
                if (findCampanaComunicados(id) == null) {
                    throw new NonexistentEntityException("The campanaComunicados with id " + id + " no longer exists.");
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
            CampanaComunicados campanaComunicados;
            try {
                campanaComunicados = em.getReference(CampanaComunicados.class, id);
                campanaComunicados.getIdcampana();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The campanaComunicados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CampanaComunicadosHasUsuario> campanaComunicadosHasUsuarioCollectionOrphanCheck = campanaComunicados.getCampanaComunicadosHasUsuarioCollection();
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuarioCollectionOrphanCheckCampanaComunicadosHasUsuario : campanaComunicadosHasUsuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CampanaComunicados (" + campanaComunicados + ") cannot be destroyed since the CampanaComunicadosHasUsuario " + campanaComunicadosHasUsuarioCollectionOrphanCheckCampanaComunicadosHasUsuario + " in its campanaComunicadosHasUsuarioCollection field has a non-nullable campanaComunicados field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Entidad entidadIdentidad = campanaComunicados.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad.getCampanaComunicadosCollection().remove(campanaComunicados);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            Collection<Archivo> archivoCollection = campanaComunicados.getArchivoCollection();
            for (Archivo archivoCollectionArchivo : archivoCollection) {
                archivoCollectionArchivo.setCampanaComunicadosIdcampana(null);
                archivoCollectionArchivo = em.merge(archivoCollectionArchivo);
            }
            em.remove(campanaComunicados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CampanaComunicados> findCampanaComunicadosEntities() {
        return findCampanaComunicadosEntities(true, -1, -1);
    }

    public List<CampanaComunicados> findCampanaComunicadosEntities(int maxResults, int firstResult) {
        return findCampanaComunicadosEntities(false, maxResults, firstResult);
    }

    private List<CampanaComunicados> findCampanaComunicadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CampanaComunicados.class));
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

    public CampanaComunicados findCampanaComunicados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CampanaComunicados.class, id);
        } finally {
            em.close();
        }
    }

    public int getCampanaComunicadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CampanaComunicados> rt = cq.from(CampanaComunicados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

   
    
}
