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
import com.iammagis.appropiate.beans.Comentario;
import com.iammagis.appropiate.beans.Encuesta;
import java.util.ArrayList;
import java.util.Collection;
import com.iammagis.appropiate.beans.Pregunta;
import com.iammagis.appropiate.beans.GrupoInteres;
import com.iammagis.appropiate.beans.EncuestaHasUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.exceptions.IllegalOrphanException;
import com.iammagis.appropiate.jpa.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author sebasariz
 */
public class EncuestaJpaController implements Serializable {

    public EncuestaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Encuesta create(Encuesta encuesta) {
        if (encuesta.getComentarioCollection() == null) {
            encuesta.setComentarioCollection(new ArrayList<Comentario>());
        }
        if (encuesta.getPreguntaCollection() == null) {
            encuesta.setPreguntaCollection(new ArrayList<Pregunta>());
        }
        if (encuesta.getGrupoInteresCollection() == null) {
            encuesta.setGrupoInteresCollection(new ArrayList<GrupoInteres>());
        }
        if (encuesta.getEncuestaHasUsuarioCollection() == null) {
            encuesta.setEncuestaHasUsuarioCollection(new ArrayList<EncuestaHasUsuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Entidad entidadIdentidad = encuesta.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad = em.getReference(entidadIdentidad.getClass(), entidadIdentidad.getIdentidad());
                encuesta.setEntidadIdentidad(entidadIdentidad);
            }
            Collection<Comentario> attachedComentarioCollection = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionComentarioToAttach : encuesta.getComentarioCollection()) {
                comentarioCollectionComentarioToAttach = em.getReference(comentarioCollectionComentarioToAttach.getClass(), comentarioCollectionComentarioToAttach.getIdcomentarioPqr());
                attachedComentarioCollection.add(comentarioCollectionComentarioToAttach);
            }
            encuesta.setComentarioCollection(attachedComentarioCollection);
            Collection<Pregunta> attachedPreguntaCollection = new ArrayList<Pregunta>();
            for (Pregunta preguntaCollectionPreguntaToAttach : encuesta.getPreguntaCollection()) {
                preguntaCollectionPreguntaToAttach = em.getReference(preguntaCollectionPreguntaToAttach.getClass(), preguntaCollectionPreguntaToAttach.getIdpregunta());
                attachedPreguntaCollection.add(preguntaCollectionPreguntaToAttach);
            }
            encuesta.setPreguntaCollection(attachedPreguntaCollection);
            Collection<GrupoInteres> attachedGrupoInteresCollection = new ArrayList<GrupoInteres>();
            for (GrupoInteres grupoInteresCollectionGrupoInteresToAttach : encuesta.getGrupoInteresCollection()) {
                grupoInteresCollectionGrupoInteresToAttach = em.getReference(grupoInteresCollectionGrupoInteresToAttach.getClass(), grupoInteresCollectionGrupoInteresToAttach.getIdgrupoInteres());
                attachedGrupoInteresCollection.add(grupoInteresCollectionGrupoInteresToAttach);
            }
            encuesta.setGrupoInteresCollection(attachedGrupoInteresCollection);
            Collection<EncuestaHasUsuario> attachedEncuestaHasUsuarioCollection = new ArrayList<EncuestaHasUsuario>();
            for (EncuestaHasUsuario encuestaHasUsuarioCollectionEncuestaHasUsuarioToAttach : encuesta.getEncuestaHasUsuarioCollection()) {
                encuestaHasUsuarioCollectionEncuestaHasUsuarioToAttach = em.getReference(encuestaHasUsuarioCollectionEncuestaHasUsuarioToAttach.getClass(), encuestaHasUsuarioCollectionEncuestaHasUsuarioToAttach.getEncuestaHasUsuarioPK());
                attachedEncuestaHasUsuarioCollection.add(encuestaHasUsuarioCollectionEncuestaHasUsuarioToAttach);
            }
            encuesta.setEncuestaHasUsuarioCollection(attachedEncuestaHasUsuarioCollection);
            em.persist(encuesta);
            if (entidadIdentidad != null) {
                entidadIdentidad.getEncuestaCollection().add(encuesta);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            for (Comentario comentarioCollectionComentario : encuesta.getComentarioCollection()) {
                Encuesta oldEncuestaIdencuestaOfComentarioCollectionComentario = comentarioCollectionComentario.getEncuestaIdencuesta();
                comentarioCollectionComentario.setEncuestaIdencuesta(encuesta);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
                if (oldEncuestaIdencuestaOfComentarioCollectionComentario != null) {
                    oldEncuestaIdencuestaOfComentarioCollectionComentario.getComentarioCollection().remove(comentarioCollectionComentario);
                    oldEncuestaIdencuestaOfComentarioCollectionComentario = em.merge(oldEncuestaIdencuestaOfComentarioCollectionComentario);
                }
            }
            for (Pregunta preguntaCollectionPregunta : encuesta.getPreguntaCollection()) {
                Encuesta oldEncuestaIdencuestaOfPreguntaCollectionPregunta = preguntaCollectionPregunta.getEncuestaIdencuesta();
                preguntaCollectionPregunta.setEncuestaIdencuesta(encuesta);
                preguntaCollectionPregunta = em.merge(preguntaCollectionPregunta);
                if (oldEncuestaIdencuestaOfPreguntaCollectionPregunta != null) {
                    oldEncuestaIdencuestaOfPreguntaCollectionPregunta.getPreguntaCollection().remove(preguntaCollectionPregunta);
                    oldEncuestaIdencuestaOfPreguntaCollectionPregunta = em.merge(oldEncuestaIdencuestaOfPreguntaCollectionPregunta);
                }
            }
            for (GrupoInteres grupoInteresCollectionGrupoInteres : encuesta.getGrupoInteresCollection()) {
                grupoInteresCollectionGrupoInteres.getEncuestaCollection().add(encuesta);
                grupoInteresCollectionGrupoInteres = em.merge(grupoInteresCollectionGrupoInteres);
            }
            for (EncuestaHasUsuario encuestaHasUsuarioCollectionEncuestaHasUsuario : encuesta.getEncuestaHasUsuarioCollection()) {
                Encuesta oldEncuestaOfEncuestaHasUsuarioCollectionEncuestaHasUsuario = encuestaHasUsuarioCollectionEncuestaHasUsuario.getEncuesta();
                encuestaHasUsuarioCollectionEncuestaHasUsuario.setEncuesta(encuesta);
                encuestaHasUsuarioCollectionEncuestaHasUsuario = em.merge(encuestaHasUsuarioCollectionEncuestaHasUsuario);
                if (oldEncuestaOfEncuestaHasUsuarioCollectionEncuestaHasUsuario != null) {
                    oldEncuestaOfEncuestaHasUsuarioCollectionEncuestaHasUsuario.getEncuestaHasUsuarioCollection().remove(encuestaHasUsuarioCollectionEncuestaHasUsuario);
                    oldEncuestaOfEncuestaHasUsuarioCollectionEncuestaHasUsuario = em.merge(oldEncuestaOfEncuestaHasUsuarioCollectionEncuestaHasUsuario);
                }
            }
            em.getTransaction().commit();
            return encuesta;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Encuesta edit(Encuesta encuesta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Encuesta persistentEncuesta = em.find(Encuesta.class, encuesta.getIdencuesta());
            Entidad entidadIdentidadOld = persistentEncuesta.getEntidadIdentidad();
            Entidad entidadIdentidadNew = encuesta.getEntidadIdentidad();
            Collection<Comentario> comentarioCollectionOld = persistentEncuesta.getComentarioCollection();
            Collection<Comentario> comentarioCollectionNew = encuesta.getComentarioCollection();
            Collection<Pregunta> preguntaCollectionOld = persistentEncuesta.getPreguntaCollection();
            Collection<Pregunta> preguntaCollectionNew = encuesta.getPreguntaCollection();
            Collection<GrupoInteres> grupoInteresCollectionOld = persistentEncuesta.getGrupoInteresCollection();
            Collection<GrupoInteres> grupoInteresCollectionNew = encuesta.getGrupoInteresCollection();
            Collection<EncuestaHasUsuario> encuestaHasUsuarioCollectionOld = persistentEncuesta.getEncuestaHasUsuarioCollection();
            Collection<EncuestaHasUsuario> encuestaHasUsuarioCollectionNew = encuesta.getEncuestaHasUsuarioCollection();
            List<String> illegalOrphanMessages = null;
            for (Pregunta preguntaCollectionOldPregunta : preguntaCollectionOld) {
                if (!preguntaCollectionNew.contains(preguntaCollectionOldPregunta)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pregunta " + preguntaCollectionOldPregunta + " since its encuestaIdencuesta field is not nullable.");
                }
            }
            for (EncuestaHasUsuario encuestaHasUsuarioCollectionOldEncuestaHasUsuario : encuestaHasUsuarioCollectionOld) {
                if (!encuestaHasUsuarioCollectionNew.contains(encuestaHasUsuarioCollectionOldEncuestaHasUsuario)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EncuestaHasUsuario " + encuestaHasUsuarioCollectionOldEncuestaHasUsuario + " since its encuesta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (entidadIdentidadNew != null) {
                entidadIdentidadNew = em.getReference(entidadIdentidadNew.getClass(), entidadIdentidadNew.getIdentidad());
                encuesta.setEntidadIdentidad(entidadIdentidadNew);
            }
            Collection<Comentario> attachedComentarioCollectionNew = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionNewComentarioToAttach : comentarioCollectionNew) {
                comentarioCollectionNewComentarioToAttach = em.getReference(comentarioCollectionNewComentarioToAttach.getClass(), comentarioCollectionNewComentarioToAttach.getIdcomentarioPqr());
                attachedComentarioCollectionNew.add(comentarioCollectionNewComentarioToAttach);
            }
            comentarioCollectionNew = attachedComentarioCollectionNew;
            encuesta.setComentarioCollection(comentarioCollectionNew);
            Collection<Pregunta> attachedPreguntaCollectionNew = new ArrayList<Pregunta>();
            for (Pregunta preguntaCollectionNewPreguntaToAttach : preguntaCollectionNew) {
                preguntaCollectionNewPreguntaToAttach = em.getReference(preguntaCollectionNewPreguntaToAttach.getClass(), preguntaCollectionNewPreguntaToAttach.getIdpregunta());
                attachedPreguntaCollectionNew.add(preguntaCollectionNewPreguntaToAttach);
            }
            preguntaCollectionNew = attachedPreguntaCollectionNew;
            encuesta.setPreguntaCollection(preguntaCollectionNew);
            Collection<GrupoInteres> attachedGrupoInteresCollectionNew = new ArrayList<GrupoInteres>();
            for (GrupoInteres grupoInteresCollectionNewGrupoInteresToAttach : grupoInteresCollectionNew) {
                grupoInteresCollectionNewGrupoInteresToAttach = em.getReference(grupoInteresCollectionNewGrupoInteresToAttach.getClass(), grupoInteresCollectionNewGrupoInteresToAttach.getIdgrupoInteres());
                attachedGrupoInteresCollectionNew.add(grupoInteresCollectionNewGrupoInteresToAttach);
            }
            grupoInteresCollectionNew = attachedGrupoInteresCollectionNew;
            encuesta.setGrupoInteresCollection(grupoInteresCollectionNew);
            Collection<EncuestaHasUsuario> attachedEncuestaHasUsuarioCollectionNew = new ArrayList<EncuestaHasUsuario>();
            for (EncuestaHasUsuario encuestaHasUsuarioCollectionNewEncuestaHasUsuarioToAttach : encuestaHasUsuarioCollectionNew) {
                encuestaHasUsuarioCollectionNewEncuestaHasUsuarioToAttach = em.getReference(encuestaHasUsuarioCollectionNewEncuestaHasUsuarioToAttach.getClass(), encuestaHasUsuarioCollectionNewEncuestaHasUsuarioToAttach.getEncuestaHasUsuarioPK());
                attachedEncuestaHasUsuarioCollectionNew.add(encuestaHasUsuarioCollectionNewEncuestaHasUsuarioToAttach);
            }
            encuestaHasUsuarioCollectionNew = attachedEncuestaHasUsuarioCollectionNew;
            encuesta.setEncuestaHasUsuarioCollection(encuestaHasUsuarioCollectionNew);
            encuesta = em.merge(encuesta);
            if (entidadIdentidadOld != null && !entidadIdentidadOld.equals(entidadIdentidadNew)) {
                entidadIdentidadOld.getEncuestaCollection().remove(encuesta);
                entidadIdentidadOld = em.merge(entidadIdentidadOld);
            }
            if (entidadIdentidadNew != null && !entidadIdentidadNew.equals(entidadIdentidadOld)) {
                entidadIdentidadNew.getEncuestaCollection().add(encuesta);
                entidadIdentidadNew = em.merge(entidadIdentidadNew);
            }
            for (Comentario comentarioCollectionOldComentario : comentarioCollectionOld) {
                if (!comentarioCollectionNew.contains(comentarioCollectionOldComentario)) {
                    comentarioCollectionOldComentario.setEncuestaIdencuesta(null);
                    comentarioCollectionOldComentario = em.merge(comentarioCollectionOldComentario);
                }
            }
            for (Comentario comentarioCollectionNewComentario : comentarioCollectionNew) {
                if (!comentarioCollectionOld.contains(comentarioCollectionNewComentario)) {
                    Encuesta oldEncuestaIdencuestaOfComentarioCollectionNewComentario = comentarioCollectionNewComentario.getEncuestaIdencuesta();
                    comentarioCollectionNewComentario.setEncuestaIdencuesta(encuesta);
                    comentarioCollectionNewComentario = em.merge(comentarioCollectionNewComentario);
                    if (oldEncuestaIdencuestaOfComentarioCollectionNewComentario != null && !oldEncuestaIdencuestaOfComentarioCollectionNewComentario.equals(encuesta)) {
                        oldEncuestaIdencuestaOfComentarioCollectionNewComentario.getComentarioCollection().remove(comentarioCollectionNewComentario);
                        oldEncuestaIdencuestaOfComentarioCollectionNewComentario = em.merge(oldEncuestaIdencuestaOfComentarioCollectionNewComentario);
                    }
                }
            }
            for (Pregunta preguntaCollectionNewPregunta : preguntaCollectionNew) {
                if (!preguntaCollectionOld.contains(preguntaCollectionNewPregunta)) {
                    Encuesta oldEncuestaIdencuestaOfPreguntaCollectionNewPregunta = preguntaCollectionNewPregunta.getEncuestaIdencuesta();
                    preguntaCollectionNewPregunta.setEncuestaIdencuesta(encuesta);
                    preguntaCollectionNewPregunta = em.merge(preguntaCollectionNewPregunta);
                    if (oldEncuestaIdencuestaOfPreguntaCollectionNewPregunta != null && !oldEncuestaIdencuestaOfPreguntaCollectionNewPregunta.equals(encuesta)) {
                        oldEncuestaIdencuestaOfPreguntaCollectionNewPregunta.getPreguntaCollection().remove(preguntaCollectionNewPregunta);
                        oldEncuestaIdencuestaOfPreguntaCollectionNewPregunta = em.merge(oldEncuestaIdencuestaOfPreguntaCollectionNewPregunta);
                    }
                }
            }
            for (GrupoInteres grupoInteresCollectionOldGrupoInteres : grupoInteresCollectionOld) {
                if (!grupoInteresCollectionNew.contains(grupoInteresCollectionOldGrupoInteres)) {
                    grupoInteresCollectionOldGrupoInteres.getEncuestaCollection().remove(encuesta);
                    grupoInteresCollectionOldGrupoInteres = em.merge(grupoInteresCollectionOldGrupoInteres);
                }
            }
            for (GrupoInteres grupoInteresCollectionNewGrupoInteres : grupoInteresCollectionNew) {
                if (!grupoInteresCollectionOld.contains(grupoInteresCollectionNewGrupoInteres)) {
                    grupoInteresCollectionNewGrupoInteres.getEncuestaCollection().add(encuesta);
                    grupoInteresCollectionNewGrupoInteres = em.merge(grupoInteresCollectionNewGrupoInteres);
                }
            }
            for (EncuestaHasUsuario encuestaHasUsuarioCollectionNewEncuestaHasUsuario : encuestaHasUsuarioCollectionNew) {
                if (!encuestaHasUsuarioCollectionOld.contains(encuestaHasUsuarioCollectionNewEncuestaHasUsuario)) {
                    Encuesta oldEncuestaOfEncuestaHasUsuarioCollectionNewEncuestaHasUsuario = encuestaHasUsuarioCollectionNewEncuestaHasUsuario.getEncuesta();
                    encuestaHasUsuarioCollectionNewEncuestaHasUsuario.setEncuesta(encuesta);
                    encuestaHasUsuarioCollectionNewEncuestaHasUsuario = em.merge(encuestaHasUsuarioCollectionNewEncuestaHasUsuario);
                    if (oldEncuestaOfEncuestaHasUsuarioCollectionNewEncuestaHasUsuario != null && !oldEncuestaOfEncuestaHasUsuarioCollectionNewEncuestaHasUsuario.equals(encuesta)) {
                        oldEncuestaOfEncuestaHasUsuarioCollectionNewEncuestaHasUsuario.getEncuestaHasUsuarioCollection().remove(encuestaHasUsuarioCollectionNewEncuestaHasUsuario);
                        oldEncuestaOfEncuestaHasUsuarioCollectionNewEncuestaHasUsuario = em.merge(oldEncuestaOfEncuestaHasUsuarioCollectionNewEncuestaHasUsuario);
                    }
                }
            }
            em.getTransaction().commit();
            return encuesta;
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = encuesta.getIdencuesta();
                if (findEncuesta(id) == null) {
                    throw new NonexistentEntityException("The encuesta with id " + id + " no longer exists.");
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
            Encuesta encuesta;
            try {
                encuesta = em.getReference(Encuesta.class, id);
                encuesta.getIdencuesta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The encuesta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Pregunta> preguntaCollectionOrphanCheck = encuesta.getPreguntaCollection();
            for (Pregunta preguntaCollectionOrphanCheckPregunta : preguntaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Encuesta (" + encuesta + ") cannot be destroyed since the Pregunta " + preguntaCollectionOrphanCheckPregunta + " in its preguntaCollection field has a non-nullable encuestaIdencuesta field.");
            }
            Collection<EncuestaHasUsuario> encuestaHasUsuarioCollectionOrphanCheck = encuesta.getEncuestaHasUsuarioCollection();
            for (EncuestaHasUsuario encuestaHasUsuarioCollectionOrphanCheckEncuestaHasUsuario : encuestaHasUsuarioCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Encuesta (" + encuesta + ") cannot be destroyed since the EncuestaHasUsuario " + encuestaHasUsuarioCollectionOrphanCheckEncuestaHasUsuario + " in its encuestaHasUsuarioCollection field has a non-nullable encuesta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Entidad entidadIdentidad = encuesta.getEntidadIdentidad();
            if (entidadIdentidad != null) {
                entidadIdentidad.getEncuestaCollection().remove(encuesta);
                entidadIdentidad = em.merge(entidadIdentidad);
            }
            Collection<Comentario> comentarioCollection = encuesta.getComentarioCollection();
            for (Comentario comentarioCollectionComentario : comentarioCollection) {
                comentarioCollectionComentario.setEncuestaIdencuesta(null);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
            }
            Collection<GrupoInteres> grupoInteresCollection = encuesta.getGrupoInteresCollection();
            for (GrupoInteres grupoInteresCollectionGrupoInteres : grupoInteresCollection) {
                grupoInteresCollectionGrupoInteres.getEncuestaCollection().remove(encuesta);
                grupoInteresCollectionGrupoInteres = em.merge(grupoInteresCollectionGrupoInteres);
            }
            em.remove(encuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Encuesta> findEncuestaEntities() {
        return findEncuestaEntities(true, -1, -1);
    }

    public List<Encuesta> findEncuestaEntities(int maxResults, int firstResult) {
        return findEncuestaEntities(false, maxResults, firstResult);
    }

    private List<Encuesta> findEncuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Encuesta.class));
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

    public Encuesta findEncuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Encuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getEncuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Encuesta> rt = cq.from(Encuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public ArrayList<Encuesta> getEncuestasLogin() {
        EntityManager em = getEntityManager();
        String queryString = "SELECT * FROM `encuesta` WHERE `estado` = 1";
        Query query = em.createNativeQuery(queryString, Encuesta.class);
        ArrayList<Encuesta> encuestas = new ArrayList<>(query.getResultList());
        return encuestas;
    }

    public ArrayList<Usuario> getPersonasRespondieron(int id) {
        EntityManager em = getEntityManager();
        String queryString = "SELECT DISTINCT `idusuario`,`usuario`.`nombre`,`apellidos`,`correo`,`pass`,`tipo_usuario_idtipo_usuario`,`usuario`.`imagen`,`sessionToken` FROM `pregunta`,`respuesta`,`usuario` WHERE `idpregunta` = "+id+" AND `usuario_idusuario` = `idusuario`";
//        System.out.println("queryString: "+queryString);
        Query query = em.createNativeQuery(queryString, Usuario.class);
        ArrayList<Usuario> usuarios = new ArrayList<>(query.getResultList()); 
        return usuarios;
    }
}
