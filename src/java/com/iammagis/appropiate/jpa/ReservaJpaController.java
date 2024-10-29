/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.jpa;

import com.iammagis.appropiate.beans.Evento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.iammagis.appropiate.beans.Locacion;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Reserva;
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
public class ReservaJpaController implements Serializable {

    public ReservaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Reserva create(Reserva reserva) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locacion locacionIdlocacion = reserva.getLocacionIdlocacion();
            if (locacionIdlocacion != null) {
                locacionIdlocacion = em.getReference(locacionIdlocacion.getClass(), locacionIdlocacion.getIdlocacion());
                reserva.setLocacionIdlocacion(locacionIdlocacion);
            }
            Usuario usuarioIdusuario = reserva.getUsuarioIdusuario();
            if (usuarioIdusuario != null) {
                usuarioIdusuario = em.getReference(usuarioIdusuario.getClass(), usuarioIdusuario.getIdusuario());
                reserva.setUsuarioIdusuario(usuarioIdusuario);
            }
            em.persist(reserva);
            if (locacionIdlocacion != null) {
                locacionIdlocacion.getReservaCollection().add(reserva);
                locacionIdlocacion = em.merge(locacionIdlocacion);
            }
            if (usuarioIdusuario != null) {
                usuarioIdusuario.getReservaCollection().add(reserva);
                usuarioIdusuario = em.merge(usuarioIdusuario);
            }
            em.getTransaction().commit();
            return reserva;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reserva reserva) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reserva persistentReserva = em.find(Reserva.class, reserva.getIdreserva());
            Locacion locacionIdlocacionOld = persistentReserva.getLocacionIdlocacion();
            Locacion locacionIdlocacionNew = reserva.getLocacionIdlocacion();
            Usuario usuarioIdusuarioOld = persistentReserva.getUsuarioIdusuario();
            Usuario usuarioIdusuarioNew = reserva.getUsuarioIdusuario();
            if (locacionIdlocacionNew != null) {
                locacionIdlocacionNew = em.getReference(locacionIdlocacionNew.getClass(), locacionIdlocacionNew.getIdlocacion());
                reserva.setLocacionIdlocacion(locacionIdlocacionNew);
            }
            if (usuarioIdusuarioNew != null) {
                usuarioIdusuarioNew = em.getReference(usuarioIdusuarioNew.getClass(), usuarioIdusuarioNew.getIdusuario());
                reserva.setUsuarioIdusuario(usuarioIdusuarioNew);
            }
            reserva = em.merge(reserva);
            if (locacionIdlocacionOld != null && !locacionIdlocacionOld.equals(locacionIdlocacionNew)) {
                locacionIdlocacionOld.getReservaCollection().remove(reserva);
                locacionIdlocacionOld = em.merge(locacionIdlocacionOld);
            }
            if (locacionIdlocacionNew != null && !locacionIdlocacionNew.equals(locacionIdlocacionOld)) {
                locacionIdlocacionNew.getReservaCollection().add(reserva);
                locacionIdlocacionNew = em.merge(locacionIdlocacionNew);
            }
            if (usuarioIdusuarioOld != null && !usuarioIdusuarioOld.equals(usuarioIdusuarioNew)) {
                usuarioIdusuarioOld.getReservaCollection().remove(reserva);
                usuarioIdusuarioOld = em.merge(usuarioIdusuarioOld);
            }
            if (usuarioIdusuarioNew != null && !usuarioIdusuarioNew.equals(usuarioIdusuarioOld)) {
                usuarioIdusuarioNew.getReservaCollection().add(reserva);
                usuarioIdusuarioNew = em.merge(usuarioIdusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reserva.getIdreserva();
                if (findReserva(id) == null) {
                    throw new NonexistentEntityException("The reserva with id " + id + " no longer exists.");
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
            Reserva reserva;
            try {
                reserva = em.getReference(Reserva.class, id);
                reserva.getIdreserva();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reserva with id " + id + " no longer exists.", enfe);
            }
            Locacion locacionIdlocacion = reserva.getLocacionIdlocacion();
            if (locacionIdlocacion != null) {
                locacionIdlocacion.getReservaCollection().remove(reserva);
                locacionIdlocacion = em.merge(locacionIdlocacion);
            }
            Usuario usuarioIdusuario = reserva.getUsuarioIdusuario();
            if (usuarioIdusuario != null) {
                usuarioIdusuario.getReservaCollection().remove(reserva);
                usuarioIdusuario = em.merge(usuarioIdusuario);
            }
            em.remove(reserva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reserva> findReservaEntities() {
        return findReservaEntities(true, -1, -1);
    }

    public List<Reserva> findReservaEntities(int maxResults, int firstResult) {
        return findReservaEntities(false, maxResults, firstResult);
    }

    private List<Reserva> findReservaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reserva.class));
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

    public Reserva findReserva(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reserva.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reserva> rt = cq.from(Reserva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public ArrayList<Reserva> getEventosFromFechaAndEntidad(long timeInMillis, long timeInMillis0, Integer identidad) {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `reserva`,`locacion` WHERE `fecha` > " + timeInMillis + " AND `fecha` < "
                + timeInMillis0 + " AND `locacion_idlocacion` = `idlocacion` AND `entidad_identidad` =" + identidad;
        Query query = em.createNativeQuery(queryString, Reserva.class);
        ArrayList<Reserva> reservas = new ArrayList<>(query.getResultList());
        return reservas;
    }

    public ArrayList<Reserva> getReservasPendientes(Integer identidad) {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `reserva` WHERE `estado` = 1";
        Query query = em.createNativeQuery(queryString, Reserva.class);
        ArrayList<Reserva> reservas = new ArrayList<>(query.getResultList());
        return reservas;
    }

}
