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
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.beans.Archivo;
import java.util.ArrayList;
import java.util.Collection;
import com.iammagis.appropiate.beans.Comentario;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sebastianarizmendy
 */
public class PqrJpaController implements Serializable {

    public PqrJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pqr pqr) {
        if (pqr.getArchivoCollection() == null) {
            pqr.setArchivoCollection(new ArrayList<Archivo>());
        }
        if (pqr.getComentarioCollection() == null) {
            pqr.setComentarioCollection(new ArrayList<Comentario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad entidadIdentidad = pqr.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad = em.getReference(entidadIdentidad.getClass(), entidadIdentidad.getIdentidad());
                pqr.setEntidadIdentidad(entidadIdentidad);
            }
            Usuario usuarioIdusuario = pqr.getUsuarioIdusuario();
            if (usuarioIdusuario != null) {
                usuarioIdusuario = em.getReference(usuarioIdusuario.getClass(), usuarioIdusuario.getIdusuario());
                pqr.setUsuarioIdusuario(usuarioIdusuario);
            }
            Collection<Archivo> attachedArchivoCollection = new ArrayList<Archivo>();
            for (Archivo archivoCollectionArchivoToAttach : pqr.getArchivoCollection()) {
                archivoCollectionArchivoToAttach = em.getReference(archivoCollectionArchivoToAttach.getClass(), archivoCollectionArchivoToAttach.getIdarchivo());
                attachedArchivoCollection.add(archivoCollectionArchivoToAttach);
            }
            pqr.setArchivoCollection(attachedArchivoCollection);
            Collection<Comentario> attachedComentarioCollection = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionComentarioToAttach : pqr.getComentarioCollection()) {
                comentarioCollectionComentarioToAttach = em.getReference(comentarioCollectionComentarioToAttach.getClass(), comentarioCollectionComentarioToAttach.getIdcomentarioPqr());
                attachedComentarioCollection.add(comentarioCollectionComentarioToAttach);
            }
            pqr.setComentarioCollection(attachedComentarioCollection);
            em.persist(pqr);
            if (entidadIdentidad != null) {
                entidadIdentidad.getPqrCollection().add(pqr);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            if (usuarioIdusuario != null) {
                usuarioIdusuario.getPqrCollection().add(pqr);
                usuarioIdusuario = em.merge(usuarioIdusuario);
            }
            for (Archivo archivoCollectionArchivo : pqr.getArchivoCollection()) {
                Pqr oldPqrIdpqrOfArchivoCollectionArchivo = archivoCollectionArchivo.getPqrIdpqr();
                archivoCollectionArchivo.setPqrIdpqr(pqr);
                archivoCollectionArchivo = em.merge(archivoCollectionArchivo);
                if (oldPqrIdpqrOfArchivoCollectionArchivo != null) {
                    oldPqrIdpqrOfArchivoCollectionArchivo.getArchivoCollection().remove(archivoCollectionArchivo);
                    oldPqrIdpqrOfArchivoCollectionArchivo = em.merge(oldPqrIdpqrOfArchivoCollectionArchivo);
                }
            }
            for (Comentario comentarioCollectionComentario : pqr.getComentarioCollection()) {
                Pqr oldPqrIdpqrOfComentarioCollectionComentario = comentarioCollectionComentario.getPqrIdpqr();
                comentarioCollectionComentario.setPqrIdpqr(pqr);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
                if (oldPqrIdpqrOfComentarioCollectionComentario != null) {
                    oldPqrIdpqrOfComentarioCollectionComentario.getComentarioCollection().remove(comentarioCollectionComentario);
                    oldPqrIdpqrOfComentarioCollectionComentario = em.merge(oldPqrIdpqrOfComentarioCollectionComentario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pqr pqr) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pqr persistentPqr = em.find(Pqr.class, pqr.getIdpqr());
            Entidad entidadIdentidadOld = persistentPqr.getEntidadIdentidad();
            Entidad entidadIdentidadNew = pqr.getEntidadIdentidad();
            Usuario usuarioIdusuarioOld = persistentPqr.getUsuarioIdusuario();
            Usuario usuarioIdusuarioNew = pqr.getUsuarioIdusuario();
            Collection<Archivo> archivoCollectionOld = persistentPqr.getArchivoCollection();
            Collection<Archivo> archivoCollectionNew = pqr.getArchivoCollection();
            Collection<Comentario> comentarioCollectionOld = persistentPqr.getComentarioCollection();
            Collection<Comentario> comentarioCollectionNew = pqr.getComentarioCollection();
            if (entidadIdentidadNew != null) {
                entidadIdentidadNew = em.getReference(entidadIdentidadNew.getClass(), entidadIdentidadNew.getIdentidad());
                pqr.setEntidadIdentidad(entidadIdentidadNew);
            }
            if (usuarioIdusuarioNew != null) {
                usuarioIdusuarioNew = em.getReference(usuarioIdusuarioNew.getClass(), usuarioIdusuarioNew.getIdusuario());
                pqr.setUsuarioIdusuario(usuarioIdusuarioNew);
            }
            Collection<Archivo> attachedArchivoCollectionNew = new ArrayList<Archivo>();
            for (Archivo archivoCollectionNewArchivoToAttach : archivoCollectionNew) {
                archivoCollectionNewArchivoToAttach = em.getReference(archivoCollectionNewArchivoToAttach.getClass(), archivoCollectionNewArchivoToAttach.getIdarchivo());
                attachedArchivoCollectionNew.add(archivoCollectionNewArchivoToAttach);
            }
            archivoCollectionNew = attachedArchivoCollectionNew;
            pqr.setArchivoCollection(archivoCollectionNew);
            Collection<Comentario> attachedComentarioCollectionNew = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionNewComentarioToAttach : comentarioCollectionNew) {
                comentarioCollectionNewComentarioToAttach = em.getReference(comentarioCollectionNewComentarioToAttach.getClass(), comentarioCollectionNewComentarioToAttach.getIdcomentarioPqr());
                attachedComentarioCollectionNew.add(comentarioCollectionNewComentarioToAttach);
            }
            comentarioCollectionNew = attachedComentarioCollectionNew;
            pqr.setComentarioCollection(comentarioCollectionNew);
            pqr = em.merge(pqr);
            if (entidadIdentidadOld != null && !entidadIdentidadOld.equals(entidadIdentidadNew)) {
                entidadIdentidadOld.getPqrCollection().remove(pqr);
                entidadIdentidadOld = em.merge(entidadIdentidadOld);
            }
            if (entidadIdentidadNew != null && !entidadIdentidadNew.equals(entidadIdentidadOld)) {
                entidadIdentidadNew.getPqrCollection().add(pqr);
                entidadIdentidadNew = em.merge(entidadIdentidadNew);
            }
            if (usuarioIdusuarioOld != null && !usuarioIdusuarioOld.equals(usuarioIdusuarioNew)) {
                usuarioIdusuarioOld.getPqrCollection().remove(pqr);
                usuarioIdusuarioOld = em.merge(usuarioIdusuarioOld);
            }
            if (usuarioIdusuarioNew != null && !usuarioIdusuarioNew.equals(usuarioIdusuarioOld)) {
                usuarioIdusuarioNew.getPqrCollection().add(pqr);
                usuarioIdusuarioNew = em.merge(usuarioIdusuarioNew);
            }
            for (Archivo archivoCollectionOldArchivo : archivoCollectionOld) {
                if (!archivoCollectionNew.contains(archivoCollectionOldArchivo)) {
                    archivoCollectionOldArchivo.setPqrIdpqr(null);
                    archivoCollectionOldArchivo = em.merge(archivoCollectionOldArchivo);
                }
            }
            for (Archivo archivoCollectionNewArchivo : archivoCollectionNew) {
                if (!archivoCollectionOld.contains(archivoCollectionNewArchivo)) {
                    Pqr oldPqrIdpqrOfArchivoCollectionNewArchivo = archivoCollectionNewArchivo.getPqrIdpqr();
                    archivoCollectionNewArchivo.setPqrIdpqr(pqr);
                    archivoCollectionNewArchivo = em.merge(archivoCollectionNewArchivo);
                    if (oldPqrIdpqrOfArchivoCollectionNewArchivo != null && !oldPqrIdpqrOfArchivoCollectionNewArchivo.equals(pqr)) {
                        oldPqrIdpqrOfArchivoCollectionNewArchivo.getArchivoCollection().remove(archivoCollectionNewArchivo);
                        oldPqrIdpqrOfArchivoCollectionNewArchivo = em.merge(oldPqrIdpqrOfArchivoCollectionNewArchivo);
                    }
                }
            }
            for (Comentario comentarioCollectionOldComentario : comentarioCollectionOld) {
                if (!comentarioCollectionNew.contains(comentarioCollectionOldComentario)) {
                    comentarioCollectionOldComentario.setPqrIdpqr(null);
                    comentarioCollectionOldComentario = em.merge(comentarioCollectionOldComentario);
                }
            }
            for (Comentario comentarioCollectionNewComentario : comentarioCollectionNew) {
                if (!comentarioCollectionOld.contains(comentarioCollectionNewComentario)) {
                    Pqr oldPqrIdpqrOfComentarioCollectionNewComentario = comentarioCollectionNewComentario.getPqrIdpqr();
                    comentarioCollectionNewComentario.setPqrIdpqr(pqr);
                    comentarioCollectionNewComentario = em.merge(comentarioCollectionNewComentario);
                    if (oldPqrIdpqrOfComentarioCollectionNewComentario != null && !oldPqrIdpqrOfComentarioCollectionNewComentario.equals(pqr)) {
                        oldPqrIdpqrOfComentarioCollectionNewComentario.getComentarioCollection().remove(comentarioCollectionNewComentario);
                        oldPqrIdpqrOfComentarioCollectionNewComentario = em.merge(oldPqrIdpqrOfComentarioCollectionNewComentario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pqr.getIdpqr();
                if (findPqr(id) == null) {
                    throw new NonexistentEntityException("The pqr with id " + id + " no longer exists.");
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
            Pqr pqr;
            try {
                pqr = em.getReference(Pqr.class, id);
                pqr.getIdpqr();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pqr with id " + id + " no longer exists.", enfe);
            }
            Entidad entidadIdentidad = pqr.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad.getPqrCollection().remove(pqr);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            Usuario usuarioIdusuario = pqr.getUsuarioIdusuario();
            if (usuarioIdusuario != null) {
                usuarioIdusuario.getPqrCollection().remove(pqr);
                usuarioIdusuario = em.merge(usuarioIdusuario);
            }
            Collection<Archivo> archivoCollection = pqr.getArchivoCollection();
            for (Archivo archivoCollectionArchivo : archivoCollection) {
                archivoCollectionArchivo.setPqrIdpqr(null);
                archivoCollectionArchivo = em.merge(archivoCollectionArchivo);
            }
            Collection<Comentario> comentarioCollection = pqr.getComentarioCollection();
            for (Comentario comentarioCollectionComentario : comentarioCollection) {
                comentarioCollectionComentario.setPqrIdpqr(null);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
            }
            em.remove(pqr);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pqr> findPqrEntities() {
        return findPqrEntities(true, -1, -1);
    }

    public List<Pqr> findPqrEntities(int maxResults, int firstResult) {
        return findPqrEntities(false, maxResults, firstResult);
    }

    private List<Pqr> findPqrEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pqr.class));
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

    public Pqr findPqr(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pqr.class, id);
        } finally {
            em.close();
        }
    }

    public int getPqrCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pqr> rt = cq.from(Pqr.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public ArrayList<Pqr> getUltimos20() {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `pqr` WHERE 1 ORDER BY idpqr DESC LIMIT 20";
        Query query = em.createNativeQuery(queryString, Pqr.class);
        ArrayList<Pqr> pqrs = new ArrayList<>(query.getResultList());
        return pqrs;
    }

    public ArrayList<Pqr> getpqrsFrom(String campo) {
        String queryString = "SELECT DISTINCT idpqr,fecha,pqr,usuario_idusuariom,entidad_identidad"
                + ",estado,tipo FROM `pqr`,`usuario` WHERE "
                + "(`usuario_idusuario` = `idusuario` AND `correo` LIKE '%" + campo + "%')"
                + "OR (`usuario_idusuario` = `idusuario` AND `nombre` LIKE '%" + campo + "%')"
                + "OR (`usuario_idusuario` = `idusuario` AND `apellidos` LIKE '%" + campo + "%')"
                + "OR `pqr` LIKE '%" + campo + "%'";
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery(queryString, Pqr.class);
        ArrayList<Pqr> pqrs = new ArrayList<>(query.getResultList());
        return pqrs;
    } 

    public ArrayList<Pqr> getPQRSActivoByEntidad(Integer identidad) {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `pqr` WHERE `estado` != 2 AND `entidad_identidad` = " + identidad;
        Query query = em.createNativeQuery(queryString, Pqr.class);
        ArrayList<Pqr> pqrs = new ArrayList<>(query.getResultList());
        return pqrs;
    }

    public ArrayList<Pqr> getPQRActivos() {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `pqr` WHERE `estado` != 2";
        Query query = em.createNativeQuery(queryString, Pqr.class);
        ArrayList<Pqr> pqrs = new ArrayList<>(query.getResultList());
        return pqrs;
    }
}
