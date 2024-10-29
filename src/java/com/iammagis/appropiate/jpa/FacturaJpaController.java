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
import com.iammagis.appropiate.beans.Factura;
import com.iammagis.appropiate.beans.FacturaTemplate;
import com.iammagis.appropiate.beans.Reserva;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.beans.Transaccion;
import com.iammagis.appropiate.jpa.exceptions.IllegalOrphanException;
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
public class FacturaJpaController implements Serializable {

    public FacturaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Factura factura) {
        if (factura.getTransaccionCollection() == null) {
            factura.setTransaccionCollection(new ArrayList<Transaccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad entidadIdentidad = factura.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad = em.getReference(entidadIdentidad.getClass(), entidadIdentidad.getIdentidad());
                factura.setEntidadIdentidad(entidadIdentidad);
            }
            FacturaTemplate facturaTemplateIdfacturaTemplate = factura.getFacturaTemplateIdfacturaTemplate();
            if (facturaTemplateIdfacturaTemplate != null) {
                facturaTemplateIdfacturaTemplate = em.getReference(facturaTemplateIdfacturaTemplate.getClass(), facturaTemplateIdfacturaTemplate.getIdfacturaTemplate());
                factura.setFacturaTemplateIdfacturaTemplate(facturaTemplateIdfacturaTemplate);
            }
            Usuario usuarioIdusuario = factura.getUsuarioIdusuario();
            if (usuarioIdusuario != null) {
                usuarioIdusuario = em.getReference(usuarioIdusuario.getClass(), usuarioIdusuario.getIdusuario());
                factura.setUsuarioIdusuario(usuarioIdusuario);
            }
            Collection<Transaccion> attachedTransaccionCollection = new ArrayList<Transaccion>();
            for (Transaccion transaccionCollectionTransaccionToAttach : factura.getTransaccionCollection()) {
                transaccionCollectionTransaccionToAttach = em.getReference(transaccionCollectionTransaccionToAttach.getClass(), transaccionCollectionTransaccionToAttach.getIdtransaccion());
                attachedTransaccionCollection.add(transaccionCollectionTransaccionToAttach);
            }
            factura.setTransaccionCollection(attachedTransaccionCollection);
            em.persist(factura);
            if (entidadIdentidad != null) {
                entidadIdentidad.getFacturaCollection().add(factura);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            if (facturaTemplateIdfacturaTemplate != null) {
                facturaTemplateIdfacturaTemplate.getFacturaCollection().add(factura);
                facturaTemplateIdfacturaTemplate = em.merge(facturaTemplateIdfacturaTemplate);
            }
            if (usuarioIdusuario != null) {
                usuarioIdusuario.getFacturaCollection().add(factura);
                usuarioIdusuario = em.merge(usuarioIdusuario);
            }
            for (Transaccion transaccionCollectionTransaccion : factura.getTransaccionCollection()) {
                Factura oldFacturaIdfacturaOfTransaccionCollectionTransaccion = transaccionCollectionTransaccion.getFacturaIdfactura();
                transaccionCollectionTransaccion.setFacturaIdfactura(factura);
                transaccionCollectionTransaccion = em.merge(transaccionCollectionTransaccion);
                if (oldFacturaIdfacturaOfTransaccionCollectionTransaccion != null) {
                    oldFacturaIdfacturaOfTransaccionCollectionTransaccion.getTransaccionCollection().remove(transaccionCollectionTransaccion);
                    oldFacturaIdfacturaOfTransaccionCollectionTransaccion = em.merge(oldFacturaIdfacturaOfTransaccionCollectionTransaccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Factura factura) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Factura persistentFactura = em.find(Factura.class, factura.getIdfactura());
            Entidad entidadIdentidadOld = persistentFactura.getEntidadIdentidad();
            Entidad entidadIdentidadNew = factura.getEntidadIdentidad();
            FacturaTemplate facturaTemplateIdfacturaTemplateOld = persistentFactura.getFacturaTemplateIdfacturaTemplate();
            FacturaTemplate facturaTemplateIdfacturaTemplateNew = factura.getFacturaTemplateIdfacturaTemplate();
            Usuario usuarioIdusuarioOld = persistentFactura.getUsuarioIdusuario();
            Usuario usuarioIdusuarioNew = factura.getUsuarioIdusuario();
            Collection<Transaccion> transaccionCollectionOld = persistentFactura.getTransaccionCollection();
            Collection<Transaccion> transaccionCollectionNew = factura.getTransaccionCollection();
            List<String> illegalOrphanMessages = null;
            for (Transaccion transaccionCollectionOldTransaccion : transaccionCollectionOld) {
                if (!transaccionCollectionNew.contains(transaccionCollectionOldTransaccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Transaccion " + transaccionCollectionOldTransaccion + " since its facturaIdfactura field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (entidadIdentidadNew != null) {
                entidadIdentidadNew = em.getReference(entidadIdentidadNew.getClass(), entidadIdentidadNew.getIdentidad());
                factura.setEntidadIdentidad(entidadIdentidadNew);
            }
            if (facturaTemplateIdfacturaTemplateNew != null) {
                facturaTemplateIdfacturaTemplateNew = em.getReference(facturaTemplateIdfacturaTemplateNew.getClass(), facturaTemplateIdfacturaTemplateNew.getIdfacturaTemplate());
                factura.setFacturaTemplateIdfacturaTemplate(facturaTemplateIdfacturaTemplateNew);
            }
            if (usuarioIdusuarioNew != null) {
                usuarioIdusuarioNew = em.getReference(usuarioIdusuarioNew.getClass(), usuarioIdusuarioNew.getIdusuario());
                factura.setUsuarioIdusuario(usuarioIdusuarioNew);
            }
            Collection<Transaccion> attachedTransaccionCollectionNew = new ArrayList<Transaccion>();
            for (Transaccion transaccionCollectionNewTransaccionToAttach : transaccionCollectionNew) {
                transaccionCollectionNewTransaccionToAttach = em.getReference(transaccionCollectionNewTransaccionToAttach.getClass(), transaccionCollectionNewTransaccionToAttach.getIdtransaccion());
                attachedTransaccionCollectionNew.add(transaccionCollectionNewTransaccionToAttach);
            }
            transaccionCollectionNew = attachedTransaccionCollectionNew;
            factura.setTransaccionCollection(transaccionCollectionNew);
            factura = em.merge(factura);
            if (entidadIdentidadOld != null && !entidadIdentidadOld.equals(entidadIdentidadNew)) {
                entidadIdentidadOld.getFacturaCollection().remove(factura);
                entidadIdentidadOld = em.merge(entidadIdentidadOld);
            }
            if (entidadIdentidadNew != null && !entidadIdentidadNew.equals(entidadIdentidadOld)) {
                entidadIdentidadNew.getFacturaCollection().add(factura);
                entidadIdentidadNew = em.merge(entidadIdentidadNew);
            }
            if (facturaTemplateIdfacturaTemplateOld != null && !facturaTemplateIdfacturaTemplateOld.equals(facturaTemplateIdfacturaTemplateNew)) {
                facturaTemplateIdfacturaTemplateOld.getFacturaCollection().remove(factura);
                facturaTemplateIdfacturaTemplateOld = em.merge(facturaTemplateIdfacturaTemplateOld);
            }
            if (facturaTemplateIdfacturaTemplateNew != null && !facturaTemplateIdfacturaTemplateNew.equals(facturaTemplateIdfacturaTemplateOld)) {
                facturaTemplateIdfacturaTemplateNew.getFacturaCollection().add(factura);
                facturaTemplateIdfacturaTemplateNew = em.merge(facturaTemplateIdfacturaTemplateNew);
            }
            if (usuarioIdusuarioOld != null && !usuarioIdusuarioOld.equals(usuarioIdusuarioNew)) {
                usuarioIdusuarioOld.getFacturaCollection().remove(factura);
                usuarioIdusuarioOld = em.merge(usuarioIdusuarioOld);
            }
            if (usuarioIdusuarioNew != null && !usuarioIdusuarioNew.equals(usuarioIdusuarioOld)) {
                usuarioIdusuarioNew.getFacturaCollection().add(factura);
                usuarioIdusuarioNew = em.merge(usuarioIdusuarioNew);
            }
            for (Transaccion transaccionCollectionNewTransaccion : transaccionCollectionNew) {
                if (!transaccionCollectionOld.contains(transaccionCollectionNewTransaccion)) {
                    Factura oldFacturaIdfacturaOfTransaccionCollectionNewTransaccion = transaccionCollectionNewTransaccion.getFacturaIdfactura();
                    transaccionCollectionNewTransaccion.setFacturaIdfactura(factura);
                    transaccionCollectionNewTransaccion = em.merge(transaccionCollectionNewTransaccion);
                    if (oldFacturaIdfacturaOfTransaccionCollectionNewTransaccion != null && !oldFacturaIdfacturaOfTransaccionCollectionNewTransaccion.equals(factura)) {
                        oldFacturaIdfacturaOfTransaccionCollectionNewTransaccion.getTransaccionCollection().remove(transaccionCollectionNewTransaccion);
                        oldFacturaIdfacturaOfTransaccionCollectionNewTransaccion = em.merge(oldFacturaIdfacturaOfTransaccionCollectionNewTransaccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = factura.getIdfactura();
                if (findFactura(id) == null) {
                    throw new NonexistentEntityException("The factura with id " + id + " no longer exists.");
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
            Factura factura;
            try {
                factura = em.getReference(Factura.class, id);
                factura.getIdfactura();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The factura with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Transaccion> transaccionCollectionOrphanCheck = factura.getTransaccionCollection();
            for (Transaccion transaccionCollectionOrphanCheckTransaccion : transaccionCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Factura (" + factura + ") cannot be destroyed since the Transaccion " + transaccionCollectionOrphanCheckTransaccion + " in its transaccionCollection field has a non-nullable facturaIdfactura field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Entidad entidadIdentidad = factura.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad.getFacturaCollection().remove(factura);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            FacturaTemplate facturaTemplateIdfacturaTemplate = factura.getFacturaTemplateIdfacturaTemplate();
            if (facturaTemplateIdfacturaTemplate != null) {
                facturaTemplateIdfacturaTemplate.getFacturaCollection().remove(factura);
                facturaTemplateIdfacturaTemplate = em.merge(facturaTemplateIdfacturaTemplate);
            }
            Usuario usuarioIdusuario = factura.getUsuarioIdusuario();
            if (usuarioIdusuario != null) {
                usuarioIdusuario.getFacturaCollection().remove(factura);
                usuarioIdusuario = em.merge(usuarioIdusuario);
            }
            em.remove(factura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Factura> findFacturaEntities() {
        return findFacturaEntities(true, -1, -1);
    }

    public List<Factura> findFacturaEntities(int maxResults, int firstResult) {
        return findFacturaEntities(false, maxResults, firstResult);
    }

    private List<Factura> findFacturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Factura.class));
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

    public Factura findFactura(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Factura.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Factura> rt = cq.from(Factura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public ArrayList<Factura> getFacturasPendientes(Integer identidad) {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `factura` WHERE (`estado` = 1 OR `estado` = 3)";
        Query query = em.createNativeQuery(queryString, Factura.class);
        ArrayList<Factura> facturas = new ArrayList<>(query.getResultList());
        return facturas;
    }

}
