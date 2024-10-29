/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.jpa;

import com.iammagis.appropiate.beans.Archivo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sebastianarizmendy
 */
public class ArchivoJpaController implements Serializable {

    public ArchivoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Archivo archivo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CampanaComunicados campanaComunicadosIdcampana = archivo.getCampanaComunicadosIdcampana();
            if (campanaComunicadosIdcampana != null) {
                campanaComunicadosIdcampana = em.getReference(campanaComunicadosIdcampana.getClass(), campanaComunicadosIdcampana.getIdcampana());
                archivo.setCampanaComunicadosIdcampana(campanaComunicadosIdcampana);
            }
            Evento eventoIdevento = archivo.getEventoIdevento();
            if (eventoIdevento != null) {
                eventoIdevento = em.getReference(eventoIdevento.getClass(), eventoIdevento.getIdevento());
                archivo.setEventoIdevento(eventoIdevento);
            }
            Pqr pqrIdpqr = archivo.getPqrIdpqr();
            if (pqrIdpqr != null) {
                pqrIdpqr = em.getReference(pqrIdpqr.getClass(), pqrIdpqr.getIdpqr());
                archivo.setPqrIdpqr(pqrIdpqr);
            }
            em.persist(archivo);
            if (campanaComunicadosIdcampana != null) {
                campanaComunicadosIdcampana.getArchivoCollection().add(archivo);
                campanaComunicadosIdcampana = em.merge(campanaComunicadosIdcampana);
            }
            if (eventoIdevento != null) {
                eventoIdevento.getArchivoCollection().add(archivo);
                eventoIdevento = em.merge(eventoIdevento);
            }
            if (pqrIdpqr != null) {
                pqrIdpqr.getArchivoCollection().add(archivo);
                pqrIdpqr = em.merge(pqrIdpqr);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Archivo archivo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Archivo persistentArchivo = em.find(Archivo.class, archivo.getIdarchivo());
            CampanaComunicados campanaComunicadosIdcampanaOld = persistentArchivo.getCampanaComunicadosIdcampana();
            CampanaComunicados campanaComunicadosIdcampanaNew = archivo.getCampanaComunicadosIdcampana();
            Evento eventoIdeventoOld = persistentArchivo.getEventoIdevento();
            Evento eventoIdeventoNew = archivo.getEventoIdevento();
            Pqr pqrIdpqrOld = persistentArchivo.getPqrIdpqr();
            Pqr pqrIdpqrNew = archivo.getPqrIdpqr();
            if (campanaComunicadosIdcampanaNew != null) {
                campanaComunicadosIdcampanaNew = em.getReference(campanaComunicadosIdcampanaNew.getClass(), campanaComunicadosIdcampanaNew.getIdcampana());
                archivo.setCampanaComunicadosIdcampana(campanaComunicadosIdcampanaNew);
            }
            if (eventoIdeventoNew != null) {
                eventoIdeventoNew = em.getReference(eventoIdeventoNew.getClass(), eventoIdeventoNew.getIdevento());
                archivo.setEventoIdevento(eventoIdeventoNew);
            }
            if (pqrIdpqrNew != null) {
                pqrIdpqrNew = em.getReference(pqrIdpqrNew.getClass(), pqrIdpqrNew.getIdpqr());
                archivo.setPqrIdpqr(pqrIdpqrNew);
            }
            archivo = em.merge(archivo);
            if (campanaComunicadosIdcampanaOld != null && !campanaComunicadosIdcampanaOld.equals(campanaComunicadosIdcampanaNew)) {
                campanaComunicadosIdcampanaOld.getArchivoCollection().remove(archivo);
                campanaComunicadosIdcampanaOld = em.merge(campanaComunicadosIdcampanaOld);
            }
            if (campanaComunicadosIdcampanaNew != null && !campanaComunicadosIdcampanaNew.equals(campanaComunicadosIdcampanaOld)) {
                campanaComunicadosIdcampanaNew.getArchivoCollection().add(archivo);
                campanaComunicadosIdcampanaNew = em.merge(campanaComunicadosIdcampanaNew);
            }
            if (eventoIdeventoOld != null && !eventoIdeventoOld.equals(eventoIdeventoNew)) {
                eventoIdeventoOld.getArchivoCollection().remove(archivo);
                eventoIdeventoOld = em.merge(eventoIdeventoOld);
            }
            if (eventoIdeventoNew != null && !eventoIdeventoNew.equals(eventoIdeventoOld)) {
                eventoIdeventoNew.getArchivoCollection().add(archivo);
                eventoIdeventoNew = em.merge(eventoIdeventoNew);
            }
            if (pqrIdpqrOld != null && !pqrIdpqrOld.equals(pqrIdpqrNew)) {
                pqrIdpqrOld.getArchivoCollection().remove(archivo);
                pqrIdpqrOld = em.merge(pqrIdpqrOld);
            }
            if (pqrIdpqrNew != null && !pqrIdpqrNew.equals(pqrIdpqrOld)) {
                pqrIdpqrNew.getArchivoCollection().add(archivo);
                pqrIdpqrNew = em.merge(pqrIdpqrNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = archivo.getIdarchivo();
                if (findArchivo(id) == null) {
                    throw new NonexistentEntityException("The archivo with id " + id + " no longer exists.");
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
            Archivo archivo;
            try {
                archivo = em.getReference(Archivo.class, id);
                archivo.getIdarchivo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The archivo with id " + id + " no longer exists.", enfe);
            }
            CampanaComunicados campanaComunicadosIdcampana = archivo.getCampanaComunicadosIdcampana();
            if (campanaComunicadosIdcampana != null) {
                campanaComunicadosIdcampana.getArchivoCollection().remove(archivo);
                campanaComunicadosIdcampana = em.merge(campanaComunicadosIdcampana);
            }
            Evento eventoIdevento = archivo.getEventoIdevento();
            if (eventoIdevento != null) {
                eventoIdevento.getArchivoCollection().remove(archivo);
                eventoIdevento = em.merge(eventoIdevento);
            }
            Pqr pqrIdpqr = archivo.getPqrIdpqr();
            if (pqrIdpqr != null) {
                pqrIdpqr.getArchivoCollection().remove(archivo);
                pqrIdpqr = em.merge(pqrIdpqr);
            }
            em.remove(archivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Archivo> findArchivoEntities() {
        return findArchivoEntities(true, -1, -1);
    }

    public List<Archivo> findArchivoEntities(int maxResults, int firstResult) {
        return findArchivoEntities(false, maxResults, firstResult);
    }

    private List<Archivo> findArchivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Archivo.class));
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

    public Archivo findArchivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Archivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getArchivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Archivo> rt = cq.from(Archivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
