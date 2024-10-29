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
import com.iammagis.appropiate.beans.TipoUsuario;
import com.iammagis.appropiate.beans.Modulo;
import java.util.ArrayList;
import java.util.Collection;
import com.iammagis.appropiate.beans.Submodulo;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.EventoHasUsuario;
import com.iammagis.appropiate.beans.CampanaComunicadosHasUsuario;
import com.iammagis.appropiate.beans.Respuesta;
import com.iammagis.appropiate.beans.Comentario;
import com.iammagis.appropiate.beans.Reserva;
import com.iammagis.appropiate.beans.Factura;
import com.iammagis.appropiate.beans.GrupoInteres;
import com.iammagis.appropiate.beans.EncuestaHasUsuario;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.exceptions.IllegalOrphanException;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 *
 * @author sebasariz
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Usuario create(Usuario usuario) {
        if (usuario.getModuloCollection() == null) {
            usuario.setModuloCollection(new ArrayList<Modulo>());
        }
        if (usuario.getSubmoduloCollection() == null) {
            usuario.setSubmoduloCollection(new ArrayList<Submodulo>());
        }
        if (usuario.getEntidadCollection() == null) {
            usuario.setEntidadCollection(new ArrayList<Entidad>());
        }
        if (usuario.getPqrCollection() == null) {
            usuario.setPqrCollection(new ArrayList<Pqr>());
        }
        if (usuario.getEventoHasUsuarioCollection() == null) {
            usuario.setEventoHasUsuarioCollection(new ArrayList<EventoHasUsuario>());
        }
        if (usuario.getCampanaComunicadosHasUsuarioCollection() == null) {
            usuario.setCampanaComunicadosHasUsuarioCollection(new ArrayList<CampanaComunicadosHasUsuario>());
        }
        if (usuario.getRespuestaCollection() == null) {
            usuario.setRespuestaCollection(new ArrayList<Respuesta>());
        }
        if (usuario.getComentarioCollection() == null) {
            usuario.setComentarioCollection(new ArrayList<Comentario>());
        }
        if (usuario.getReservaCollection() == null) {
            usuario.setReservaCollection(new ArrayList<Reserva>());
        }
        if (usuario.getFacturaCollection() == null) {
            usuario.setFacturaCollection(new ArrayList<Factura>());
        }
        if (usuario.getGrupoInteresCollection() == null) {
            usuario.setGrupoInteresCollection(new ArrayList<GrupoInteres>());
        }
        if (usuario.getEncuestaHasUsuarioCollection() == null) {
            usuario.setEncuestaHasUsuarioCollection(new ArrayList<EncuestaHasUsuario>());
        }
        if (usuario.getLogUsuarioCollection() == null) {
            usuario.setLogUsuarioCollection(new ArrayList<LogUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoUsuario tipoUsuarioIdtipoUsuario = usuario.getTipoUsuarioIdtipoUsuario();
            if (tipoUsuarioIdtipoUsuario != null) {
                tipoUsuarioIdtipoUsuario = em.getReference(tipoUsuarioIdtipoUsuario.getClass(), tipoUsuarioIdtipoUsuario.getIdtipoUsuario());
                usuario.setTipoUsuarioIdtipoUsuario(tipoUsuarioIdtipoUsuario);
            }
            Collection<Modulo> attachedModuloCollection = new ArrayList<Modulo>();
            for (Modulo moduloCollectionModuloToAttach : usuario.getModuloCollection()) {
                moduloCollectionModuloToAttach = em.getReference(moduloCollectionModuloToAttach.getClass(), moduloCollectionModuloToAttach.getIdmodulo());
                attachedModuloCollection.add(moduloCollectionModuloToAttach);
            }
            usuario.setModuloCollection(attachedModuloCollection);
            Collection<Submodulo> attachedSubmoduloCollection = new ArrayList<Submodulo>();
            for (Submodulo submoduloCollectionSubmoduloToAttach : usuario.getSubmoduloCollection()) {
                submoduloCollectionSubmoduloToAttach = em.getReference(submoduloCollectionSubmoduloToAttach.getClass(), submoduloCollectionSubmoduloToAttach.getIdsubmodulo());
                attachedSubmoduloCollection.add(submoduloCollectionSubmoduloToAttach);
            }
            usuario.setSubmoduloCollection(attachedSubmoduloCollection);
            Collection<Entidad> attachedEntidadCollection = new ArrayList<Entidad>();
            for (Entidad entidadCollectionEntidadToAttach : usuario.getEntidadCollection()) {
                entidadCollectionEntidadToAttach = em.getReference(entidadCollectionEntidadToAttach.getClass(), entidadCollectionEntidadToAttach.getIdentidad());
                attachedEntidadCollection.add(entidadCollectionEntidadToAttach);
            }
            usuario.setEntidadCollection(attachedEntidadCollection);
            Collection<Pqr> attachedPqrCollection = new ArrayList<Pqr>();
            for (Pqr pqrCollectionPqrToAttach : usuario.getPqrCollection()) {
                pqrCollectionPqrToAttach = em.getReference(pqrCollectionPqrToAttach.getClass(), pqrCollectionPqrToAttach.getIdpqr());
                attachedPqrCollection.add(pqrCollectionPqrToAttach);
            }
            usuario.setPqrCollection(attachedPqrCollection);
            Collection<EventoHasUsuario> attachedEventoHasUsuarioCollection = new ArrayList<EventoHasUsuario>();
            for (EventoHasUsuario eventoHasUsuarioCollectionEventoHasUsuarioToAttach : usuario.getEventoHasUsuarioCollection()) {
                eventoHasUsuarioCollectionEventoHasUsuarioToAttach = em.getReference(eventoHasUsuarioCollectionEventoHasUsuarioToAttach.getClass(), eventoHasUsuarioCollectionEventoHasUsuarioToAttach.getEventoHasUsuarioPK());
                attachedEventoHasUsuarioCollection.add(eventoHasUsuarioCollectionEventoHasUsuarioToAttach);
            }
            usuario.setEventoHasUsuarioCollection(attachedEventoHasUsuarioCollection);
            Collection<CampanaComunicadosHasUsuario> attachedCampanaComunicadosHasUsuarioCollection = new ArrayList<CampanaComunicadosHasUsuario>();
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuarioToAttach : usuario.getCampanaComunicadosHasUsuarioCollection()) {
                campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuarioToAttach = em.getReference(campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuarioToAttach.getClass(), campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuarioToAttach.getCampanaComunicadosHasUsuarioPK());
                attachedCampanaComunicadosHasUsuarioCollection.add(campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuarioToAttach);
            }
            usuario.setCampanaComunicadosHasUsuarioCollection(attachedCampanaComunicadosHasUsuarioCollection);
            Collection<Respuesta> attachedRespuestaCollection = new ArrayList<Respuesta>();
            for (Respuesta respuestaCollectionRespuestaToAttach : usuario.getRespuestaCollection()) {
                respuestaCollectionRespuestaToAttach = em.getReference(respuestaCollectionRespuestaToAttach.getClass(), respuestaCollectionRespuestaToAttach.getIdrespuesta());
                attachedRespuestaCollection.add(respuestaCollectionRespuestaToAttach);
            }
            usuario.setRespuestaCollection(attachedRespuestaCollection);
            Collection<Comentario> attachedComentarioCollection = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionComentarioToAttach : usuario.getComentarioCollection()) {
                comentarioCollectionComentarioToAttach = em.getReference(comentarioCollectionComentarioToAttach.getClass(), comentarioCollectionComentarioToAttach.getIdcomentarioPqr());
                attachedComentarioCollection.add(comentarioCollectionComentarioToAttach);
            }
            usuario.setComentarioCollection(attachedComentarioCollection);
            Collection<Reserva> attachedReservaCollection = new ArrayList<Reserva>();
            for (Reserva reservaCollectionReservaToAttach : usuario.getReservaCollection()) {
                reservaCollectionReservaToAttach = em.getReference(reservaCollectionReservaToAttach.getClass(), reservaCollectionReservaToAttach.getIdreserva());
                attachedReservaCollection.add(reservaCollectionReservaToAttach);
            }
            usuario.setReservaCollection(attachedReservaCollection);
            Collection<Factura> attachedFacturaCollection = new ArrayList<Factura>();
            for (Factura facturaCollectionFacturaToAttach : usuario.getFacturaCollection()) {
                facturaCollectionFacturaToAttach = em.getReference(facturaCollectionFacturaToAttach.getClass(), facturaCollectionFacturaToAttach.getIdfactura());
                attachedFacturaCollection.add(facturaCollectionFacturaToAttach);
            }
            usuario.setFacturaCollection(attachedFacturaCollection);
            Collection<GrupoInteres> attachedGrupoInteresCollection = new ArrayList<GrupoInteres>();
            for (GrupoInteres grupoInteresCollectionGrupoInteresToAttach : usuario.getGrupoInteresCollection()) {
                grupoInteresCollectionGrupoInteresToAttach = em.getReference(grupoInteresCollectionGrupoInteresToAttach.getClass(), grupoInteresCollectionGrupoInteresToAttach.getIdgrupoInteres());
                attachedGrupoInteresCollection.add(grupoInteresCollectionGrupoInteresToAttach);
            }
            usuario.setGrupoInteresCollection(attachedGrupoInteresCollection);
            Collection<EncuestaHasUsuario> attachedEncuestaHasUsuarioCollection = new ArrayList<EncuestaHasUsuario>();
            for (EncuestaHasUsuario encuestaHasUsuarioCollectionEncuestaHasUsuarioToAttach : usuario.getEncuestaHasUsuarioCollection()) {
                encuestaHasUsuarioCollectionEncuestaHasUsuarioToAttach = em.getReference(encuestaHasUsuarioCollectionEncuestaHasUsuarioToAttach.getClass(), encuestaHasUsuarioCollectionEncuestaHasUsuarioToAttach.getEncuestaHasUsuarioPK());
                attachedEncuestaHasUsuarioCollection.add(encuestaHasUsuarioCollectionEncuestaHasUsuarioToAttach);
            }
            usuario.setEncuestaHasUsuarioCollection(attachedEncuestaHasUsuarioCollection);
            Collection<LogUsuario> attachedLogUsuarioCollection = new ArrayList<LogUsuario>();
            for (LogUsuario logUsuarioCollectionLogUsuarioToAttach : usuario.getLogUsuarioCollection()) {
                logUsuarioCollectionLogUsuarioToAttach = em.getReference(logUsuarioCollectionLogUsuarioToAttach.getClass(), logUsuarioCollectionLogUsuarioToAttach.getIdlogUsuario());
                attachedLogUsuarioCollection.add(logUsuarioCollectionLogUsuarioToAttach);
            }
            usuario.setLogUsuarioCollection(attachedLogUsuarioCollection);
            em.persist(usuario);
            if (tipoUsuarioIdtipoUsuario != null) {
                tipoUsuarioIdtipoUsuario.getUsuarioCollection().add(usuario);
                tipoUsuarioIdtipoUsuario = em.merge(tipoUsuarioIdtipoUsuario);
            }
            for (Modulo moduloCollectionModulo : usuario.getModuloCollection()) {
                moduloCollectionModulo.getUsuarioCollection().add(usuario);
                moduloCollectionModulo = em.merge(moduloCollectionModulo);
            }
            for (Submodulo submoduloCollectionSubmodulo : usuario.getSubmoduloCollection()) {
                submoduloCollectionSubmodulo.getUsuarioCollection().add(usuario);
                submoduloCollectionSubmodulo = em.merge(submoduloCollectionSubmodulo);
            }
            for (Entidad entidadCollectionEntidad : usuario.getEntidadCollection()) {
                entidadCollectionEntidad.getUsuarioCollection().add(usuario);
                entidadCollectionEntidad = em.merge(entidadCollectionEntidad);
            }
            for (Pqr pqrCollectionPqr : usuario.getPqrCollection()) {
                Usuario oldUsuarioIdusuarioOfPqrCollectionPqr = pqrCollectionPqr.getUsuarioIdusuario();
                pqrCollectionPqr.setUsuarioIdusuario(usuario);
                pqrCollectionPqr = em.merge(pqrCollectionPqr);
                if (oldUsuarioIdusuarioOfPqrCollectionPqr != null) {
                    oldUsuarioIdusuarioOfPqrCollectionPqr.getPqrCollection().remove(pqrCollectionPqr);
                    oldUsuarioIdusuarioOfPqrCollectionPqr = em.merge(oldUsuarioIdusuarioOfPqrCollectionPqr);
                }
            }
            for (EventoHasUsuario eventoHasUsuarioCollectionEventoHasUsuario : usuario.getEventoHasUsuarioCollection()) {
                Usuario oldUsuarioOfEventoHasUsuarioCollectionEventoHasUsuario = eventoHasUsuarioCollectionEventoHasUsuario.getUsuario();
                eventoHasUsuarioCollectionEventoHasUsuario.setUsuario(usuario);
                eventoHasUsuarioCollectionEventoHasUsuario = em.merge(eventoHasUsuarioCollectionEventoHasUsuario);
                if (oldUsuarioOfEventoHasUsuarioCollectionEventoHasUsuario != null) {
                    oldUsuarioOfEventoHasUsuarioCollectionEventoHasUsuario.getEventoHasUsuarioCollection().remove(eventoHasUsuarioCollectionEventoHasUsuario);
                    oldUsuarioOfEventoHasUsuarioCollectionEventoHasUsuario = em.merge(oldUsuarioOfEventoHasUsuarioCollectionEventoHasUsuario);
                }
            }
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario : usuario.getCampanaComunicadosHasUsuarioCollection()) {
                Usuario oldUsuarioOfCampanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario = campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario.getUsuario();
                campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario.setUsuario(usuario);
                campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario = em.merge(campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario);
                if (oldUsuarioOfCampanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario != null) {
                    oldUsuarioOfCampanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioCollection().remove(campanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario);
                    oldUsuarioOfCampanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario = em.merge(oldUsuarioOfCampanaComunicadosHasUsuarioCollectionCampanaComunicadosHasUsuario);
                }
            }
            for (Respuesta respuestaCollectionRespuesta : usuario.getRespuestaCollection()) {
                Usuario oldUsuarioIdusuarioOfRespuestaCollectionRespuesta = respuestaCollectionRespuesta.getUsuarioIdusuario();
                respuestaCollectionRespuesta.setUsuarioIdusuario(usuario);
                respuestaCollectionRespuesta = em.merge(respuestaCollectionRespuesta);
                if (oldUsuarioIdusuarioOfRespuestaCollectionRespuesta != null) {
                    oldUsuarioIdusuarioOfRespuestaCollectionRespuesta.getRespuestaCollection().remove(respuestaCollectionRespuesta);
                    oldUsuarioIdusuarioOfRespuestaCollectionRespuesta = em.merge(oldUsuarioIdusuarioOfRespuestaCollectionRespuesta);
                }
            }
            for (Comentario comentarioCollectionComentario : usuario.getComentarioCollection()) {
                Usuario oldUsuarioIdusuarioOfComentarioCollectionComentario = comentarioCollectionComentario.getUsuarioIdusuario();
                comentarioCollectionComentario.setUsuarioIdusuario(usuario);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
                if (oldUsuarioIdusuarioOfComentarioCollectionComentario != null) {
                    oldUsuarioIdusuarioOfComentarioCollectionComentario.getComentarioCollection().remove(comentarioCollectionComentario);
                    oldUsuarioIdusuarioOfComentarioCollectionComentario = em.merge(oldUsuarioIdusuarioOfComentarioCollectionComentario);
                }
            }
            for (Reserva reservaCollectionReserva : usuario.getReservaCollection()) {
                Usuario oldUsuarioIdusuarioOfReservaCollectionReserva = reservaCollectionReserva.getUsuarioIdusuario();
                reservaCollectionReserva.setUsuarioIdusuario(usuario);
                reservaCollectionReserva = em.merge(reservaCollectionReserva);
                if (oldUsuarioIdusuarioOfReservaCollectionReserva != null) {
                    oldUsuarioIdusuarioOfReservaCollectionReserva.getReservaCollection().remove(reservaCollectionReserva);
                    oldUsuarioIdusuarioOfReservaCollectionReserva = em.merge(oldUsuarioIdusuarioOfReservaCollectionReserva);
                }
            }
            for (Factura facturaCollectionFactura : usuario.getFacturaCollection()) {
                Usuario oldUsuarioIdusuarioOfFacturaCollectionFactura = facturaCollectionFactura.getUsuarioIdusuario();
                facturaCollectionFactura.setUsuarioIdusuario(usuario);
                facturaCollectionFactura = em.merge(facturaCollectionFactura);
                if (oldUsuarioIdusuarioOfFacturaCollectionFactura != null) {
                    oldUsuarioIdusuarioOfFacturaCollectionFactura.getFacturaCollection().remove(facturaCollectionFactura);
                    oldUsuarioIdusuarioOfFacturaCollectionFactura = em.merge(oldUsuarioIdusuarioOfFacturaCollectionFactura);
                }
            }
            for (GrupoInteres grupoInteresCollectionGrupoInteres : usuario.getGrupoInteresCollection()) {
                grupoInteresCollectionGrupoInteres.getUsuarioCollection().add(usuario);
                grupoInteresCollectionGrupoInteres = em.merge(grupoInteresCollectionGrupoInteres);
            }
            for (EncuestaHasUsuario encuestaHasUsuarioCollectionEncuestaHasUsuario : usuario.getEncuestaHasUsuarioCollection()) {
                Usuario oldUsuarioOfEncuestaHasUsuarioCollectionEncuestaHasUsuario = encuestaHasUsuarioCollectionEncuestaHasUsuario.getUsuario();
                encuestaHasUsuarioCollectionEncuestaHasUsuario.setUsuario(usuario);
                encuestaHasUsuarioCollectionEncuestaHasUsuario = em.merge(encuestaHasUsuarioCollectionEncuestaHasUsuario);
                if (oldUsuarioOfEncuestaHasUsuarioCollectionEncuestaHasUsuario != null) {
                    oldUsuarioOfEncuestaHasUsuarioCollectionEncuestaHasUsuario.getEncuestaHasUsuarioCollection().remove(encuestaHasUsuarioCollectionEncuestaHasUsuario);
                    oldUsuarioOfEncuestaHasUsuarioCollectionEncuestaHasUsuario = em.merge(oldUsuarioOfEncuestaHasUsuarioCollectionEncuestaHasUsuario);
                }
            }
            for (LogUsuario logUsuarioCollectionLogUsuario : usuario.getLogUsuarioCollection()) {
                Usuario oldUsuarioIdusuarioOfLogUsuarioCollectionLogUsuario = logUsuarioCollectionLogUsuario.getUsuarioIdusuario();
                logUsuarioCollectionLogUsuario.setUsuarioIdusuario(usuario);
                logUsuarioCollectionLogUsuario = em.merge(logUsuarioCollectionLogUsuario);
                if (oldUsuarioIdusuarioOfLogUsuarioCollectionLogUsuario != null) {
                    oldUsuarioIdusuarioOfLogUsuarioCollectionLogUsuario.getLogUsuarioCollection().remove(logUsuarioCollectionLogUsuario);
                    oldUsuarioIdusuarioOfLogUsuarioCollectionLogUsuario = em.merge(oldUsuarioIdusuarioOfLogUsuarioCollectionLogUsuario);
                }
            }
            em.getTransaction().commit();
            return usuario;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Usuario edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdusuario());
            TipoUsuario tipoUsuarioIdtipoUsuarioOld = persistentUsuario.getTipoUsuarioIdtipoUsuario();
            TipoUsuario tipoUsuarioIdtipoUsuarioNew = usuario.getTipoUsuarioIdtipoUsuario();
            Collection<Modulo> moduloCollectionOld = persistentUsuario.getModuloCollection();
            Collection<Modulo> moduloCollectionNew = usuario.getModuloCollection();
            Collection<Submodulo> submoduloCollectionOld = persistentUsuario.getSubmoduloCollection();
            Collection<Submodulo> submoduloCollectionNew = usuario.getSubmoduloCollection();
            Collection<Entidad> entidadCollectionOld = persistentUsuario.getEntidadCollection();
            Collection<Entidad> entidadCollectionNew = usuario.getEntidadCollection();
            Collection<Pqr> pqrCollectionOld = persistentUsuario.getPqrCollection();
            Collection<Pqr> pqrCollectionNew = usuario.getPqrCollection();
            Collection<EventoHasUsuario> eventoHasUsuarioCollectionOld = persistentUsuario.getEventoHasUsuarioCollection();
            Collection<EventoHasUsuario> eventoHasUsuarioCollectionNew = usuario.getEventoHasUsuarioCollection();
            Collection<CampanaComunicadosHasUsuario> campanaComunicadosHasUsuarioCollectionOld = persistentUsuario.getCampanaComunicadosHasUsuarioCollection();
            Collection<CampanaComunicadosHasUsuario> campanaComunicadosHasUsuarioCollectionNew = usuario.getCampanaComunicadosHasUsuarioCollection();
            Collection<Respuesta> respuestaCollectionOld = persistentUsuario.getRespuestaCollection();
            Collection<Respuesta> respuestaCollectionNew = usuario.getRespuestaCollection();
            Collection<Comentario> comentarioCollectionOld = persistentUsuario.getComentarioCollection();
            Collection<Comentario> comentarioCollectionNew = usuario.getComentarioCollection();
            Collection<Reserva> reservaCollectionOld = persistentUsuario.getReservaCollection();
            Collection<Reserva> reservaCollectionNew = usuario.getReservaCollection();
            Collection<Factura> facturaCollectionOld = persistentUsuario.getFacturaCollection();
            Collection<Factura> facturaCollectionNew = usuario.getFacturaCollection();
            Collection<GrupoInteres> grupoInteresCollectionOld = persistentUsuario.getGrupoInteresCollection();
            Collection<GrupoInteres> grupoInteresCollectionNew = usuario.getGrupoInteresCollection();
            Collection<EncuestaHasUsuario> encuestaHasUsuarioCollectionOld = persistentUsuario.getEncuestaHasUsuarioCollection();
            Collection<EncuestaHasUsuario> encuestaHasUsuarioCollectionNew = usuario.getEncuestaHasUsuarioCollection();
            Collection<LogUsuario> logUsuarioCollectionOld = persistentUsuario.getLogUsuarioCollection();
            Collection<LogUsuario> logUsuarioCollectionNew = usuario.getLogUsuarioCollection();
            List<String> illegalOrphanMessages = null;
            for (Pqr pqrCollectionOldPqr : pqrCollectionOld) {
                if (!pqrCollectionNew.contains(pqrCollectionOldPqr)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pqr " + pqrCollectionOldPqr + " since its usuarioIdusuario field is not nullable.");
                }
            }
            for (EventoHasUsuario eventoHasUsuarioCollectionOldEventoHasUsuario : eventoHasUsuarioCollectionOld) {
                if (!eventoHasUsuarioCollectionNew.contains(eventoHasUsuarioCollectionOldEventoHasUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventoHasUsuario " + eventoHasUsuarioCollectionOldEventoHasUsuario + " since its usuario field is not nullable.");
                }
            }
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuarioCollectionOldCampanaComunicadosHasUsuario : campanaComunicadosHasUsuarioCollectionOld) {
                if (!campanaComunicadosHasUsuarioCollectionNew.contains(campanaComunicadosHasUsuarioCollectionOldCampanaComunicadosHasUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CampanaComunicadosHasUsuario " + campanaComunicadosHasUsuarioCollectionOldCampanaComunicadosHasUsuario + " since its usuario field is not nullable.");
                }
            }
            for (Respuesta respuestaCollectionOldRespuesta : respuestaCollectionOld) {
                if (!respuestaCollectionNew.contains(respuestaCollectionOldRespuesta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Respuesta " + respuestaCollectionOldRespuesta + " since its usuarioIdusuario field is not nullable.");
                }
            }
            for (Comentario comentarioCollectionOldComentario : comentarioCollectionOld) {
                if (!comentarioCollectionNew.contains(comentarioCollectionOldComentario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comentario " + comentarioCollectionOldComentario + " since its usuarioIdusuario field is not nullable.");
                }
            }
            for (Reserva reservaCollectionOldReserva : reservaCollectionOld) {
                if (!reservaCollectionNew.contains(reservaCollectionOldReserva)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reserva " + reservaCollectionOldReserva + " since its usuarioIdusuario field is not nullable.");
                }
            }
            for (Factura facturaCollectionOldFactura : facturaCollectionOld) {
                if (!facturaCollectionNew.contains(facturaCollectionOldFactura)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Factura " + facturaCollectionOldFactura + " since its usuarioIdusuario field is not nullable.");
                }
            }
            for (EncuestaHasUsuario encuestaHasUsuarioCollectionOldEncuestaHasUsuario : encuestaHasUsuarioCollectionOld) {
                if (!encuestaHasUsuarioCollectionNew.contains(encuestaHasUsuarioCollectionOldEncuestaHasUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EncuestaHasUsuario " + encuestaHasUsuarioCollectionOldEncuestaHasUsuario + " since its usuario field is not nullable.");
                }
            }
            for (LogUsuario logUsuarioCollectionOldLogUsuario : logUsuarioCollectionOld) {
                if (!logUsuarioCollectionNew.contains(logUsuarioCollectionOldLogUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain LogUsuario " + logUsuarioCollectionOldLogUsuario + " since its usuarioIdusuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tipoUsuarioIdtipoUsuarioNew != null) {
                tipoUsuarioIdtipoUsuarioNew = em.getReference(tipoUsuarioIdtipoUsuarioNew.getClass(), tipoUsuarioIdtipoUsuarioNew.getIdtipoUsuario());
                usuario.setTipoUsuarioIdtipoUsuario(tipoUsuarioIdtipoUsuarioNew);
            }
            Collection<Modulo> attachedModuloCollectionNew = new ArrayList<Modulo>();
            for (Modulo moduloCollectionNewModuloToAttach : moduloCollectionNew) {
                moduloCollectionNewModuloToAttach = em.getReference(moduloCollectionNewModuloToAttach.getClass(), moduloCollectionNewModuloToAttach.getIdmodulo());
                attachedModuloCollectionNew.add(moduloCollectionNewModuloToAttach);
            }
            moduloCollectionNew = attachedModuloCollectionNew;
            usuario.setModuloCollection(moduloCollectionNew);
            Collection<Submodulo> attachedSubmoduloCollectionNew = new ArrayList<Submodulo>();
            for (Submodulo submoduloCollectionNewSubmoduloToAttach : submoduloCollectionNew) {
                submoduloCollectionNewSubmoduloToAttach = em.getReference(submoduloCollectionNewSubmoduloToAttach.getClass(), submoduloCollectionNewSubmoduloToAttach.getIdsubmodulo());
                attachedSubmoduloCollectionNew.add(submoduloCollectionNewSubmoduloToAttach);
            }
            submoduloCollectionNew = attachedSubmoduloCollectionNew;
            usuario.setSubmoduloCollection(submoduloCollectionNew);
            Collection<Entidad> attachedEntidadCollectionNew = new ArrayList<Entidad>();
            for (Entidad entidadCollectionNewEntidadToAttach : entidadCollectionNew) {
                entidadCollectionNewEntidadToAttach = em.getReference(entidadCollectionNewEntidadToAttach.getClass(), entidadCollectionNewEntidadToAttach.getIdentidad());
                attachedEntidadCollectionNew.add(entidadCollectionNewEntidadToAttach);
            }
            entidadCollectionNew = attachedEntidadCollectionNew;
            usuario.setEntidadCollection(entidadCollectionNew);
            Collection<Pqr> attachedPqrCollectionNew = new ArrayList<Pqr>();
            for (Pqr pqrCollectionNewPqrToAttach : pqrCollectionNew) {
                pqrCollectionNewPqrToAttach = em.getReference(pqrCollectionNewPqrToAttach.getClass(), pqrCollectionNewPqrToAttach.getIdpqr());
                attachedPqrCollectionNew.add(pqrCollectionNewPqrToAttach);
            }
            pqrCollectionNew = attachedPqrCollectionNew;
            usuario.setPqrCollection(pqrCollectionNew);
            Collection<EventoHasUsuario> attachedEventoHasUsuarioCollectionNew = new ArrayList<EventoHasUsuario>();
            for (EventoHasUsuario eventoHasUsuarioCollectionNewEventoHasUsuarioToAttach : eventoHasUsuarioCollectionNew) {
                eventoHasUsuarioCollectionNewEventoHasUsuarioToAttach = em.getReference(eventoHasUsuarioCollectionNewEventoHasUsuarioToAttach.getClass(), eventoHasUsuarioCollectionNewEventoHasUsuarioToAttach.getEventoHasUsuarioPK());
                attachedEventoHasUsuarioCollectionNew.add(eventoHasUsuarioCollectionNewEventoHasUsuarioToAttach);
            }
            eventoHasUsuarioCollectionNew = attachedEventoHasUsuarioCollectionNew;
            usuario.setEventoHasUsuarioCollection(eventoHasUsuarioCollectionNew);
            Collection<CampanaComunicadosHasUsuario> attachedCampanaComunicadosHasUsuarioCollectionNew = new ArrayList<CampanaComunicadosHasUsuario>();
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuarioToAttach : campanaComunicadosHasUsuarioCollectionNew) {
                campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuarioToAttach = em.getReference(campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuarioToAttach.getClass(), campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuarioToAttach.getCampanaComunicadosHasUsuarioPK());
                attachedCampanaComunicadosHasUsuarioCollectionNew.add(campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuarioToAttach);
            }
            campanaComunicadosHasUsuarioCollectionNew = attachedCampanaComunicadosHasUsuarioCollectionNew;
            usuario.setCampanaComunicadosHasUsuarioCollection(campanaComunicadosHasUsuarioCollectionNew);
            Collection<Respuesta> attachedRespuestaCollectionNew = new ArrayList<Respuesta>();
            for (Respuesta respuestaCollectionNewRespuestaToAttach : respuestaCollectionNew) {
                respuestaCollectionNewRespuestaToAttach = em.getReference(respuestaCollectionNewRespuestaToAttach.getClass(), respuestaCollectionNewRespuestaToAttach.getIdrespuesta());
                attachedRespuestaCollectionNew.add(respuestaCollectionNewRespuestaToAttach);
            }
            respuestaCollectionNew = attachedRespuestaCollectionNew;
            usuario.setRespuestaCollection(respuestaCollectionNew);
            Collection<Comentario> attachedComentarioCollectionNew = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionNewComentarioToAttach : comentarioCollectionNew) {
                comentarioCollectionNewComentarioToAttach = em.getReference(comentarioCollectionNewComentarioToAttach.getClass(), comentarioCollectionNewComentarioToAttach.getIdcomentarioPqr());
                attachedComentarioCollectionNew.add(comentarioCollectionNewComentarioToAttach);
            }
            comentarioCollectionNew = attachedComentarioCollectionNew;
            usuario.setComentarioCollection(comentarioCollectionNew);
            Collection<Reserva> attachedReservaCollectionNew = new ArrayList<Reserva>();
            for (Reserva reservaCollectionNewReservaToAttach : reservaCollectionNew) {
                reservaCollectionNewReservaToAttach = em.getReference(reservaCollectionNewReservaToAttach.getClass(), reservaCollectionNewReservaToAttach.getIdreserva());
                attachedReservaCollectionNew.add(reservaCollectionNewReservaToAttach);
            }
            reservaCollectionNew = attachedReservaCollectionNew;
            usuario.setReservaCollection(reservaCollectionNew);
            Collection<Factura> attachedFacturaCollectionNew = new ArrayList<Factura>();
            for (Factura facturaCollectionNewFacturaToAttach : facturaCollectionNew) {
                facturaCollectionNewFacturaToAttach = em.getReference(facturaCollectionNewFacturaToAttach.getClass(), facturaCollectionNewFacturaToAttach.getIdfactura());
                attachedFacturaCollectionNew.add(facturaCollectionNewFacturaToAttach);
            }
            facturaCollectionNew = attachedFacturaCollectionNew;
            usuario.setFacturaCollection(facturaCollectionNew);
            Collection<GrupoInteres> attachedGrupoInteresCollectionNew = new ArrayList<GrupoInteres>();
            for (GrupoInteres grupoInteresCollectionNewGrupoInteresToAttach : grupoInteresCollectionNew) {
                grupoInteresCollectionNewGrupoInteresToAttach = em.getReference(grupoInteresCollectionNewGrupoInteresToAttach.getClass(), grupoInteresCollectionNewGrupoInteresToAttach.getIdgrupoInteres());
                attachedGrupoInteresCollectionNew.add(grupoInteresCollectionNewGrupoInteresToAttach);
            }
            grupoInteresCollectionNew = attachedGrupoInteresCollectionNew;
            usuario.setGrupoInteresCollection(grupoInteresCollectionNew);
            Collection<EncuestaHasUsuario> attachedEncuestaHasUsuarioCollectionNew = new ArrayList<EncuestaHasUsuario>();
            for (EncuestaHasUsuario encuestaHasUsuarioCollectionNewEncuestaHasUsuarioToAttach : encuestaHasUsuarioCollectionNew) {
                encuestaHasUsuarioCollectionNewEncuestaHasUsuarioToAttach = em.getReference(encuestaHasUsuarioCollectionNewEncuestaHasUsuarioToAttach.getClass(), encuestaHasUsuarioCollectionNewEncuestaHasUsuarioToAttach.getEncuestaHasUsuarioPK());
                attachedEncuestaHasUsuarioCollectionNew.add(encuestaHasUsuarioCollectionNewEncuestaHasUsuarioToAttach);
            }
            encuestaHasUsuarioCollectionNew = attachedEncuestaHasUsuarioCollectionNew;
            usuario.setEncuestaHasUsuarioCollection(encuestaHasUsuarioCollectionNew);
            Collection<LogUsuario> attachedLogUsuarioCollectionNew = new ArrayList<LogUsuario>();
            for (LogUsuario logUsuarioCollectionNewLogUsuarioToAttach : logUsuarioCollectionNew) {
                logUsuarioCollectionNewLogUsuarioToAttach = em.getReference(logUsuarioCollectionNewLogUsuarioToAttach.getClass(), logUsuarioCollectionNewLogUsuarioToAttach.getIdlogUsuario());
                attachedLogUsuarioCollectionNew.add(logUsuarioCollectionNewLogUsuarioToAttach);
            }
            logUsuarioCollectionNew = attachedLogUsuarioCollectionNew;
            usuario.setLogUsuarioCollection(logUsuarioCollectionNew);
            usuario = em.merge(usuario);
            if (tipoUsuarioIdtipoUsuarioOld != null && !tipoUsuarioIdtipoUsuarioOld.equals(tipoUsuarioIdtipoUsuarioNew)) {
                tipoUsuarioIdtipoUsuarioOld.getUsuarioCollection().remove(usuario);
                tipoUsuarioIdtipoUsuarioOld = em.merge(tipoUsuarioIdtipoUsuarioOld);
            }
            if (tipoUsuarioIdtipoUsuarioNew != null && !tipoUsuarioIdtipoUsuarioNew.equals(tipoUsuarioIdtipoUsuarioOld)) {
                tipoUsuarioIdtipoUsuarioNew.getUsuarioCollection().add(usuario);
                tipoUsuarioIdtipoUsuarioNew = em.merge(tipoUsuarioIdtipoUsuarioNew);
            }
            for (Modulo moduloCollectionOldModulo : moduloCollectionOld) {
                if (!moduloCollectionNew.contains(moduloCollectionOldModulo)) {
                    moduloCollectionOldModulo.getUsuarioCollection().remove(usuario);
                    moduloCollectionOldModulo = em.merge(moduloCollectionOldModulo);
                }
            }
            for (Modulo moduloCollectionNewModulo : moduloCollectionNew) {
                if (!moduloCollectionOld.contains(moduloCollectionNewModulo)) {
                    moduloCollectionNewModulo.getUsuarioCollection().add(usuario);
                    moduloCollectionNewModulo = em.merge(moduloCollectionNewModulo);
                }
            }
            for (Submodulo submoduloCollectionOldSubmodulo : submoduloCollectionOld) {
                if (!submoduloCollectionNew.contains(submoduloCollectionOldSubmodulo)) {
                    submoduloCollectionOldSubmodulo.getUsuarioCollection().remove(usuario);
                    submoduloCollectionOldSubmodulo = em.merge(submoduloCollectionOldSubmodulo);
                }
            }
            for (Submodulo submoduloCollectionNewSubmodulo : submoduloCollectionNew) {
                if (!submoduloCollectionOld.contains(submoduloCollectionNewSubmodulo)) {
                    submoduloCollectionNewSubmodulo.getUsuarioCollection().add(usuario);
                    submoduloCollectionNewSubmodulo = em.merge(submoduloCollectionNewSubmodulo);
                }
            }
            for (Entidad entidadCollectionOldEntidad : entidadCollectionOld) {
                if (!entidadCollectionNew.contains(entidadCollectionOldEntidad)) {
                    entidadCollectionOldEntidad.getUsuarioCollection().remove(usuario);
                    entidadCollectionOldEntidad = em.merge(entidadCollectionOldEntidad);
                }
            }
            for (Entidad entidadCollectionNewEntidad : entidadCollectionNew) {
                if (!entidadCollectionOld.contains(entidadCollectionNewEntidad)) {
                    entidadCollectionNewEntidad.getUsuarioCollection().add(usuario);
                    entidadCollectionNewEntidad = em.merge(entidadCollectionNewEntidad);
                }
            }
            for (Pqr pqrCollectionNewPqr : pqrCollectionNew) {
                if (!pqrCollectionOld.contains(pqrCollectionNewPqr)) {
                    Usuario oldUsuarioIdusuarioOfPqrCollectionNewPqr = pqrCollectionNewPqr.getUsuarioIdusuario();
                    pqrCollectionNewPqr.setUsuarioIdusuario(usuario);
                    pqrCollectionNewPqr = em.merge(pqrCollectionNewPqr);
                    if (oldUsuarioIdusuarioOfPqrCollectionNewPqr != null && !oldUsuarioIdusuarioOfPqrCollectionNewPqr.equals(usuario)) {
                        oldUsuarioIdusuarioOfPqrCollectionNewPqr.getPqrCollection().remove(pqrCollectionNewPqr);
                        oldUsuarioIdusuarioOfPqrCollectionNewPqr = em.merge(oldUsuarioIdusuarioOfPqrCollectionNewPqr);
                    }
                }
            }
            for (EventoHasUsuario eventoHasUsuarioCollectionNewEventoHasUsuario : eventoHasUsuarioCollectionNew) {
                if (!eventoHasUsuarioCollectionOld.contains(eventoHasUsuarioCollectionNewEventoHasUsuario)) {
                    Usuario oldUsuarioOfEventoHasUsuarioCollectionNewEventoHasUsuario = eventoHasUsuarioCollectionNewEventoHasUsuario.getUsuario();
                    eventoHasUsuarioCollectionNewEventoHasUsuario.setUsuario(usuario);
                    eventoHasUsuarioCollectionNewEventoHasUsuario = em.merge(eventoHasUsuarioCollectionNewEventoHasUsuario);
                    if (oldUsuarioOfEventoHasUsuarioCollectionNewEventoHasUsuario != null && !oldUsuarioOfEventoHasUsuarioCollectionNewEventoHasUsuario.equals(usuario)) {
                        oldUsuarioOfEventoHasUsuarioCollectionNewEventoHasUsuario.getEventoHasUsuarioCollection().remove(eventoHasUsuarioCollectionNewEventoHasUsuario);
                        oldUsuarioOfEventoHasUsuarioCollectionNewEventoHasUsuario = em.merge(oldUsuarioOfEventoHasUsuarioCollectionNewEventoHasUsuario);
                    }
                }
            }
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario : campanaComunicadosHasUsuarioCollectionNew) {
                if (!campanaComunicadosHasUsuarioCollectionOld.contains(campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario)) {
                    Usuario oldUsuarioOfCampanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario = campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario.getUsuario();
                    campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario.setUsuario(usuario);
                    campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario = em.merge(campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario);
                    if (oldUsuarioOfCampanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario != null && !oldUsuarioOfCampanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario.equals(usuario)) {
                        oldUsuarioOfCampanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario.getCampanaComunicadosHasUsuarioCollection().remove(campanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario);
                        oldUsuarioOfCampanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario = em.merge(oldUsuarioOfCampanaComunicadosHasUsuarioCollectionNewCampanaComunicadosHasUsuario);
                    }
                }
            }
            for (Respuesta respuestaCollectionNewRespuesta : respuestaCollectionNew) {
                if (!respuestaCollectionOld.contains(respuestaCollectionNewRespuesta)) {
                    Usuario oldUsuarioIdusuarioOfRespuestaCollectionNewRespuesta = respuestaCollectionNewRespuesta.getUsuarioIdusuario();
                    respuestaCollectionNewRespuesta.setUsuarioIdusuario(usuario);
                    respuestaCollectionNewRespuesta = em.merge(respuestaCollectionNewRespuesta);
                    if (oldUsuarioIdusuarioOfRespuestaCollectionNewRespuesta != null && !oldUsuarioIdusuarioOfRespuestaCollectionNewRespuesta.equals(usuario)) {
                        oldUsuarioIdusuarioOfRespuestaCollectionNewRespuesta.getRespuestaCollection().remove(respuestaCollectionNewRespuesta);
                        oldUsuarioIdusuarioOfRespuestaCollectionNewRespuesta = em.merge(oldUsuarioIdusuarioOfRespuestaCollectionNewRespuesta);
                    }
                }
            }
            for (Comentario comentarioCollectionNewComentario : comentarioCollectionNew) {
                if (!comentarioCollectionOld.contains(comentarioCollectionNewComentario)) {
                    Usuario oldUsuarioIdusuarioOfComentarioCollectionNewComentario = comentarioCollectionNewComentario.getUsuarioIdusuario();
                    comentarioCollectionNewComentario.setUsuarioIdusuario(usuario);
                    comentarioCollectionNewComentario = em.merge(comentarioCollectionNewComentario);
                    if (oldUsuarioIdusuarioOfComentarioCollectionNewComentario != null && !oldUsuarioIdusuarioOfComentarioCollectionNewComentario.equals(usuario)) {
                        oldUsuarioIdusuarioOfComentarioCollectionNewComentario.getComentarioCollection().remove(comentarioCollectionNewComentario);
                        oldUsuarioIdusuarioOfComentarioCollectionNewComentario = em.merge(oldUsuarioIdusuarioOfComentarioCollectionNewComentario);
                    }
                }
            }
            for (Reserva reservaCollectionNewReserva : reservaCollectionNew) {
                if (!reservaCollectionOld.contains(reservaCollectionNewReserva)) {
                    Usuario oldUsuarioIdusuarioOfReservaCollectionNewReserva = reservaCollectionNewReserva.getUsuarioIdusuario();
                    reservaCollectionNewReserva.setUsuarioIdusuario(usuario);
                    reservaCollectionNewReserva = em.merge(reservaCollectionNewReserva);
                    if (oldUsuarioIdusuarioOfReservaCollectionNewReserva != null && !oldUsuarioIdusuarioOfReservaCollectionNewReserva.equals(usuario)) {
                        oldUsuarioIdusuarioOfReservaCollectionNewReserva.getReservaCollection().remove(reservaCollectionNewReserva);
                        oldUsuarioIdusuarioOfReservaCollectionNewReserva = em.merge(oldUsuarioIdusuarioOfReservaCollectionNewReserva);
                    }
                }
            }
            for (Factura facturaCollectionNewFactura : facturaCollectionNew) {
                if (!facturaCollectionOld.contains(facturaCollectionNewFactura)) {
                    Usuario oldUsuarioIdusuarioOfFacturaCollectionNewFactura = facturaCollectionNewFactura.getUsuarioIdusuario();
                    facturaCollectionNewFactura.setUsuarioIdusuario(usuario);
                    facturaCollectionNewFactura = em.merge(facturaCollectionNewFactura);
                    if (oldUsuarioIdusuarioOfFacturaCollectionNewFactura != null && !oldUsuarioIdusuarioOfFacturaCollectionNewFactura.equals(usuario)) {
                        oldUsuarioIdusuarioOfFacturaCollectionNewFactura.getFacturaCollection().remove(facturaCollectionNewFactura);
                        oldUsuarioIdusuarioOfFacturaCollectionNewFactura = em.merge(oldUsuarioIdusuarioOfFacturaCollectionNewFactura);
                    }
                }
            }
            for (GrupoInteres grupoInteresCollectionOldGrupoInteres : grupoInteresCollectionOld) {
                if (!grupoInteresCollectionNew.contains(grupoInteresCollectionOldGrupoInteres)) {
                    grupoInteresCollectionOldGrupoInteres.getUsuarioCollection().remove(usuario);
                    grupoInteresCollectionOldGrupoInteres = em.merge(grupoInteresCollectionOldGrupoInteres);
                }
            }
            for (GrupoInteres grupoInteresCollectionNewGrupoInteres : grupoInteresCollectionNew) {
                if (!grupoInteresCollectionOld.contains(grupoInteresCollectionNewGrupoInteres)) {
                    grupoInteresCollectionNewGrupoInteres.getUsuarioCollection().add(usuario);
                    grupoInteresCollectionNewGrupoInteres = em.merge(grupoInteresCollectionNewGrupoInteres);
                }
            }
            for (EncuestaHasUsuario encuestaHasUsuarioCollectionNewEncuestaHasUsuario : encuestaHasUsuarioCollectionNew) {
                if (!encuestaHasUsuarioCollectionOld.contains(encuestaHasUsuarioCollectionNewEncuestaHasUsuario)) {
                    Usuario oldUsuarioOfEncuestaHasUsuarioCollectionNewEncuestaHasUsuario = encuestaHasUsuarioCollectionNewEncuestaHasUsuario.getUsuario();
                    encuestaHasUsuarioCollectionNewEncuestaHasUsuario.setUsuario(usuario);
                    encuestaHasUsuarioCollectionNewEncuestaHasUsuario = em.merge(encuestaHasUsuarioCollectionNewEncuestaHasUsuario);
                    if (oldUsuarioOfEncuestaHasUsuarioCollectionNewEncuestaHasUsuario != null && !oldUsuarioOfEncuestaHasUsuarioCollectionNewEncuestaHasUsuario.equals(usuario)) {
                        oldUsuarioOfEncuestaHasUsuarioCollectionNewEncuestaHasUsuario.getEncuestaHasUsuarioCollection().remove(encuestaHasUsuarioCollectionNewEncuestaHasUsuario);
                        oldUsuarioOfEncuestaHasUsuarioCollectionNewEncuestaHasUsuario = em.merge(oldUsuarioOfEncuestaHasUsuarioCollectionNewEncuestaHasUsuario);
                    }
                }
            }
            for (LogUsuario logUsuarioCollectionNewLogUsuario : logUsuarioCollectionNew) {
                if (!logUsuarioCollectionOld.contains(logUsuarioCollectionNewLogUsuario)) {
                    Usuario oldUsuarioIdusuarioOfLogUsuarioCollectionNewLogUsuario = logUsuarioCollectionNewLogUsuario.getUsuarioIdusuario();
                    logUsuarioCollectionNewLogUsuario.setUsuarioIdusuario(usuario);
                    logUsuarioCollectionNewLogUsuario = em.merge(logUsuarioCollectionNewLogUsuario);
                    if (oldUsuarioIdusuarioOfLogUsuarioCollectionNewLogUsuario != null && !oldUsuarioIdusuarioOfLogUsuarioCollectionNewLogUsuario.equals(usuario)) {
                        oldUsuarioIdusuarioOfLogUsuarioCollectionNewLogUsuario.getLogUsuarioCollection().remove(logUsuarioCollectionNewLogUsuario);
                        oldUsuarioIdusuarioOfLogUsuarioCollectionNewLogUsuario = em.merge(oldUsuarioIdusuarioOfLogUsuarioCollectionNewLogUsuario);
                    }
                }
            }
            em.getTransaction().commit();
            return usuario;
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdusuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdusuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Pqr> pqrCollectionOrphanCheck = usuario.getPqrCollection();
            for (Pqr pqrCollectionOrphanCheckPqr : pqrCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Pqr " + pqrCollectionOrphanCheckPqr + " in its pqrCollection field has a non-nullable usuarioIdusuario field.");
            }
            Collection<EventoHasUsuario> eventoHasUsuarioCollectionOrphanCheck = usuario.getEventoHasUsuarioCollection();
            for (EventoHasUsuario eventoHasUsuarioCollectionOrphanCheckEventoHasUsuario : eventoHasUsuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the EventoHasUsuario " + eventoHasUsuarioCollectionOrphanCheckEventoHasUsuario + " in its eventoHasUsuarioCollection field has a non-nullable usuario field.");
            }
            Collection<CampanaComunicadosHasUsuario> campanaComunicadosHasUsuarioCollectionOrphanCheck = usuario.getCampanaComunicadosHasUsuarioCollection();
            for (CampanaComunicadosHasUsuario campanaComunicadosHasUsuarioCollectionOrphanCheckCampanaComunicadosHasUsuario : campanaComunicadosHasUsuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the CampanaComunicadosHasUsuario " + campanaComunicadosHasUsuarioCollectionOrphanCheckCampanaComunicadosHasUsuario + " in its campanaComunicadosHasUsuarioCollection field has a non-nullable usuario field.");
            }
            Collection<Respuesta> respuestaCollectionOrphanCheck = usuario.getRespuestaCollection();
            for (Respuesta respuestaCollectionOrphanCheckRespuesta : respuestaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Respuesta " + respuestaCollectionOrphanCheckRespuesta + " in its respuestaCollection field has a non-nullable usuarioIdusuario field.");
            }
            Collection<Comentario> comentarioCollectionOrphanCheck = usuario.getComentarioCollection();
            for (Comentario comentarioCollectionOrphanCheckComentario : comentarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Comentario " + comentarioCollectionOrphanCheckComentario + " in its comentarioCollection field has a non-nullable usuarioIdusuario field.");
            }
            Collection<Reserva> reservaCollectionOrphanCheck = usuario.getReservaCollection();
            for (Reserva reservaCollectionOrphanCheckReserva : reservaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Reserva " + reservaCollectionOrphanCheckReserva + " in its reservaCollection field has a non-nullable usuarioIdusuario field.");
            }
            Collection<Factura> facturaCollectionOrphanCheck = usuario.getFacturaCollection();
            for (Factura facturaCollectionOrphanCheckFactura : facturaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the Factura " + facturaCollectionOrphanCheckFactura + " in its facturaCollection field has a non-nullable usuarioIdusuario field.");
            }
            Collection<EncuestaHasUsuario> encuestaHasUsuarioCollectionOrphanCheck = usuario.getEncuestaHasUsuarioCollection();
            for (EncuestaHasUsuario encuestaHasUsuarioCollectionOrphanCheckEncuestaHasUsuario : encuestaHasUsuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the EncuestaHasUsuario " + encuestaHasUsuarioCollectionOrphanCheckEncuestaHasUsuario + " in its encuestaHasUsuarioCollection field has a non-nullable usuario field.");
            }
            Collection<LogUsuario> logUsuarioCollectionOrphanCheck = usuario.getLogUsuarioCollection();
            for (LogUsuario logUsuarioCollectionOrphanCheckLogUsuario : logUsuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the LogUsuario " + logUsuarioCollectionOrphanCheckLogUsuario + " in its logUsuarioCollection field has a non-nullable usuarioIdusuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TipoUsuario tipoUsuarioIdtipoUsuario = usuario.getTipoUsuarioIdtipoUsuario();
            if (tipoUsuarioIdtipoUsuario != null) {
                tipoUsuarioIdtipoUsuario.getUsuarioCollection().remove(usuario);
                tipoUsuarioIdtipoUsuario = em.merge(tipoUsuarioIdtipoUsuario);
            }
            Collection<Modulo> moduloCollection = usuario.getModuloCollection();
            for (Modulo moduloCollectionModulo : moduloCollection) {
                moduloCollectionModulo.getUsuarioCollection().remove(usuario);
                moduloCollectionModulo = em.merge(moduloCollectionModulo);
            }
            Collection<Submodulo> submoduloCollection = usuario.getSubmoduloCollection();
            for (Submodulo submoduloCollectionSubmodulo : submoduloCollection) {
                submoduloCollectionSubmodulo.getUsuarioCollection().remove(usuario);
                submoduloCollectionSubmodulo = em.merge(submoduloCollectionSubmodulo);
            }
            Collection<Entidad> entidadCollection = usuario.getEntidadCollection();
            for (Entidad entidadCollectionEntidad : entidadCollection) {
                entidadCollectionEntidad.getUsuarioCollection().remove(usuario);
                entidadCollectionEntidad = em.merge(entidadCollectionEntidad);
            }
            Collection<GrupoInteres> grupoInteresCollection = usuario.getGrupoInteresCollection();
            for (GrupoInteres grupoInteresCollectionGrupoInteres : grupoInteresCollection) {
                grupoInteresCollectionGrupoInteres.getUsuarioCollection().remove(usuario);
                grupoInteresCollectionGrupoInteres = em.merge(grupoInteresCollectionGrupoInteres);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public Usuario login(Usuario usuario) throws UnsupportedEncodingException {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Usuario.login");
        query.setParameter("correo", usuario.getCorreo());
        query.setParameter("pass", usuario.getPass());
        Usuario usuarioRetrun = null;
        try {
            usuarioRetrun = (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
        }
        return usuarioRetrun;
    }

    public Usuario emailExist(Usuario usuario) throws UnsupportedEncodingException {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Usuario.findByCorreo");
        query.setParameter("correo", usuario.getCorreo());
        Usuario usuarioRetrun = null;
        try {
            usuarioRetrun = (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
        }
        return usuarioRetrun;
    }

    public Usuario sessionTokenExist(Usuario usuario) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Usuario.findBySessionToken");
//        query.setParameter("sessionToken", usuario.get());
        Usuario usuarioRetrun = null;
        try {
            usuarioRetrun = (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
        }
        return usuarioRetrun;
    }

    public ArrayList<Usuario> getUsuariosWithToken() {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `usuario` WHERE `Usuario.deviceToken` IS NOT NULL";
        Query query = em.createNativeQuery(queryString, Usuario.class);
        ArrayList<Usuario> result = new ArrayList<>(query.getResultList());
        return result;
    }

    public Usuario getUsuarioWithToken(String token) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Usuario.sessionToken");
        query.setParameter("sessionToken", token);
        Usuario usuarioRetrun = null;
        try {
            usuarioRetrun = (Usuario) query.getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        return usuarioRetrun;
    }

    public ArrayList<Usuario> getUsuariosByTipo(int i) {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `usuario` WHERE `tipo_usuario_idtipo_usuario` =" + i;
        Query query = em.createNativeQuery(queryString, Usuario.class);
        ArrayList<Usuario> result = new ArrayList<>(query.getResultList());
        return result;
    }
}
