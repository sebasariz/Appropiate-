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
import com.iammagis.appropiate.beans.Comentario;
import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 *
 * @author sebastianarizmendy
 */
public class ComentarioJpaController implements Serializable {

    public ComentarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comentario comentario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CampanaComunicados campanaComunicadosIdcampana = comentario.getCampanaComunicadosIdcampana();
            if (campanaComunicadosIdcampana != null) {
                campanaComunicadosIdcampana = em.getReference(campanaComunicadosIdcampana.getClass(), campanaComunicadosIdcampana.getIdcampana());
                comentario.setCampanaComunicadosIdcampana(campanaComunicadosIdcampana);
            }
            Encuesta encuestaIdencuesta = comentario.getEncuestaIdencuesta();
            if (encuestaIdencuesta != null) {
                encuestaIdencuesta = em.getReference(encuestaIdencuesta.getClass(), encuestaIdencuesta.getIdencuesta());
                comentario.setEncuestaIdencuesta(encuestaIdencuesta);
            }
            Evento eventoIdevento = comentario.getEventoIdevento();
            if (eventoIdevento != null) {
                eventoIdevento = em.getReference(eventoIdevento.getClass(), eventoIdevento.getIdevento());
                comentario.setEventoIdevento(eventoIdevento);
            }
            Pqr pqrIdpqr = comentario.getPqrIdpqr();
            if (pqrIdpqr != null) {
                pqrIdpqr = em.getReference(pqrIdpqr.getClass(), pqrIdpqr.getIdpqr());
                comentario.setPqrIdpqr(pqrIdpqr);
            }
            em.persist(comentario);
            if (campanaComunicadosIdcampana != null) {
                campanaComunicadosIdcampana.getComentarioCollection().add(comentario);
                campanaComunicadosIdcampana = em.merge(campanaComunicadosIdcampana);
            }
            if (encuestaIdencuesta != null) {
                encuestaIdencuesta.getComentarioCollection().add(comentario);
                encuestaIdencuesta = em.merge(encuestaIdencuesta);
            }
            if (eventoIdevento != null) {
                eventoIdevento.getComentarioCollection().add(comentario);
                eventoIdevento = em.merge(eventoIdevento);
            }
            if (pqrIdpqr != null) {
                pqrIdpqr.getComentarioCollection().add(comentario);
                pqrIdpqr = em.merge(pqrIdpqr);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comentario comentario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comentario persistentComentario = em.find(Comentario.class, comentario.getIdcomentarioPqr());
            CampanaComunicados campanaComunicadosIdcampanaOld = persistentComentario.getCampanaComunicadosIdcampana();
            CampanaComunicados campanaComunicadosIdcampanaNew = comentario.getCampanaComunicadosIdcampana();
            Encuesta encuestaIdencuestaOld = persistentComentario.getEncuestaIdencuesta();
            Encuesta encuestaIdencuestaNew = comentario.getEncuestaIdencuesta();
            Evento eventoIdeventoOld = persistentComentario.getEventoIdevento();
            Evento eventoIdeventoNew = comentario.getEventoIdevento();
            Pqr pqrIdpqrOld = persistentComentario.getPqrIdpqr();
            Pqr pqrIdpqrNew = comentario.getPqrIdpqr();
            if (campanaComunicadosIdcampanaNew != null) {
                campanaComunicadosIdcampanaNew = em.getReference(campanaComunicadosIdcampanaNew.getClass(), campanaComunicadosIdcampanaNew.getIdcampana());
                comentario.setCampanaComunicadosIdcampana(campanaComunicadosIdcampanaNew);
            }
            if (encuestaIdencuestaNew != null) {
                encuestaIdencuestaNew = em.getReference(encuestaIdencuestaNew.getClass(), encuestaIdencuestaNew.getIdencuesta());
                comentario.setEncuestaIdencuesta(encuestaIdencuestaNew);
            }
            if (eventoIdeventoNew != null) {
                eventoIdeventoNew = em.getReference(eventoIdeventoNew.getClass(), eventoIdeventoNew.getIdevento());
                comentario.setEventoIdevento(eventoIdeventoNew);
            }
            if (pqrIdpqrNew != null) {
                pqrIdpqrNew = em.getReference(pqrIdpqrNew.getClass(), pqrIdpqrNew.getIdpqr());
                comentario.setPqrIdpqr(pqrIdpqrNew);
            }
            comentario = em.merge(comentario);
            if (campanaComunicadosIdcampanaOld != null && !campanaComunicadosIdcampanaOld.equals(campanaComunicadosIdcampanaNew)) {
                campanaComunicadosIdcampanaOld.getComentarioCollection().remove(comentario);
                campanaComunicadosIdcampanaOld = em.merge(campanaComunicadosIdcampanaOld);
            }
            if (campanaComunicadosIdcampanaNew != null && !campanaComunicadosIdcampanaNew.equals(campanaComunicadosIdcampanaOld)) {
                campanaComunicadosIdcampanaNew.getComentarioCollection().add(comentario);
                campanaComunicadosIdcampanaNew = em.merge(campanaComunicadosIdcampanaNew);
            }
            if (encuestaIdencuestaOld != null && !encuestaIdencuestaOld.equals(encuestaIdencuestaNew)) {
                encuestaIdencuestaOld.getComentarioCollection().remove(comentario);
                encuestaIdencuestaOld = em.merge(encuestaIdencuestaOld);
            }
            if (encuestaIdencuestaNew != null && !encuestaIdencuestaNew.equals(encuestaIdencuestaOld)) {
                encuestaIdencuestaNew.getComentarioCollection().add(comentario);
                encuestaIdencuestaNew = em.merge(encuestaIdencuestaNew);
            }
            if (eventoIdeventoOld != null && !eventoIdeventoOld.equals(eventoIdeventoNew)) {
                eventoIdeventoOld.getComentarioCollection().remove(comentario);
                eventoIdeventoOld = em.merge(eventoIdeventoOld);
            }
            if (eventoIdeventoNew != null && !eventoIdeventoNew.equals(eventoIdeventoOld)) {
                eventoIdeventoNew.getComentarioCollection().add(comentario);
                eventoIdeventoNew = em.merge(eventoIdeventoNew);
            }
            if (pqrIdpqrOld != null && !pqrIdpqrOld.equals(pqrIdpqrNew)) {
                pqrIdpqrOld.getComentarioCollection().remove(comentario);
                pqrIdpqrOld = em.merge(pqrIdpqrOld);
            }
            if (pqrIdpqrNew != null && !pqrIdpqrNew.equals(pqrIdpqrOld)) {
                pqrIdpqrNew.getComentarioCollection().add(comentario);
                pqrIdpqrNew = em.merge(pqrIdpqrNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comentario.getIdcomentarioPqr();
                if (findComentario(id) == null) {
                    throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.");
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
            Comentario comentario;
            try {
                comentario = em.getReference(Comentario.class, id);
                comentario.getIdcomentarioPqr();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.", enfe);
            }
            CampanaComunicados campanaComunicadosIdcampana = comentario.getCampanaComunicadosIdcampana();
            if (campanaComunicadosIdcampana != null) {
                campanaComunicadosIdcampana.getComentarioCollection().remove(comentario);
                campanaComunicadosIdcampana = em.merge(campanaComunicadosIdcampana);
            }
            Encuesta encuestaIdencuesta = comentario.getEncuestaIdencuesta();
            if (encuestaIdencuesta != null) {
                encuestaIdencuesta.getComentarioCollection().remove(comentario);
                encuestaIdencuesta = em.merge(encuestaIdencuesta);
            }
            Evento eventoIdevento = comentario.getEventoIdevento();
            if (eventoIdevento != null) {
                eventoIdevento.getComentarioCollection().remove(comentario);
                eventoIdevento = em.merge(eventoIdevento);
            }
            Pqr pqrIdpqr = comentario.getPqrIdpqr();
            if (pqrIdpqr != null) {
                pqrIdpqr.getComentarioCollection().remove(comentario);
                pqrIdpqr = em.merge(pqrIdpqr);
            }
            em.remove(comentario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comentario> findComentarioEntities() {
        return findComentarioEntities(true, -1, -1);
    }

    public List<Comentario> findComentarioEntities(int maxResults, int firstResult) {
        return findComentarioEntities(false, maxResults, firstResult);
    }

    private List<Comentario> findComentarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comentario.class));
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

    public Comentario findComentario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comentario.class, id);
        } finally {
            em.close();
        }
    }

    public int getComentarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comentario> rt = cq.from(Comentario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public ArrayList<Comentario> getComentariosLast20(Integer identidad, int idUsuario) {
        EntityManager em = getEntityManager();
        String queryString = "SELECT DISTINCT `comentario`.`idcomentario_pqr`,`comentario`.`fecha`,"
                + "`comentario`.`comentario`,`comentario`.`pqr_idpqr`,`comentario`.`evento_idevento`,"
                + "`comentario`.`evento_idevento`,`comentario`.`encuesta_idencuesta`,`comentario`.`campana_comunicados_idcampana`"
                + ",`comentario`.`usuario_idusuario` FROM `comentario`,`evento`,`campana_comunicados`,`encuesta`"
                + ",`pqr` WHERE (`evento_idevento` = `idevento` AND `evento`.`entidad_identidad` = " + identidad + "  ) "
                + "OR (`campana_comunicados_idcampana` = `idcampana` AND `campana_comunicados`.`entidad_identidad` = " + identidad + ") "
                + "OR (`encuesta_idencuesta`= `idencuesta` AND `encuesta`.`entidad_identidad`=" + identidad + ") "
                + "OR (`pqr_idpqr`=`idpqr` AND `pqr`.`usuario_idusuario`=" + idUsuario + ") "
                + "ORDER BY `comentario`.`fecha` ASC LIMIT 20";

        System.out.println("queryString: " + queryString);
        Query query = em.createNativeQuery(queryString, Comentario.class);
        ArrayList<Comentario> result = new ArrayList<>(query.getResultList());
        return result;
    }

    public void removeFromCampana(int id) {
        EntityManager em = getEntityManager();
        String queryString = "DELETE FROM `comentario` WHERE `comentario`.`campana_comunicados_idcampana` = " + id;
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createNativeQuery(queryString);
        query.executeUpdate();
        tx.commit();
    }

    public void destroyFromEvento(int id) {
         EntityManager em = getEntityManager();
        String queryString = "DELETE FROM `comentario` WHERE  `evento_idevento`=" + id;
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createNativeQuery(queryString);
        query.executeUpdate();
        tx.commit();
    }

    public void removeFromEncuesta(int id) {
        EntityManager em = getEntityManager();
        String queryString = "DELETE FROM `comentario` WHERE  `encuesta_idencuesta`=" + id;
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Query query = em.createNativeQuery(queryString);
        query.executeUpdate();
        tx.commit();
    }

}
