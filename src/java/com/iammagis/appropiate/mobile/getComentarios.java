/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.Comentario;
import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.CampanaComunicadosJpaController;
import com.iammagis.appropiate.jpa.EncuestaJpaController;
import com.iammagis.appropiate.jpa.EventoJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.PqrJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class getComentarios extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        request.setCharacterEncoding("ISO-8859-15");
        response.setCharacterEncoding("ISO-8859-15");
        JSONObject jSONObject = new JSONObject();

        EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
        String token = request.getParameter("token");
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
        Usuario usuario = usuarioJpaController.getUsuarioWithToken(token);
        if (usuario != null) {
            int tipocomentario = Integer.parseInt(request.getParameter("tipo"));
            int id = Integer.parseInt(request.getParameter("id"));

            EventoJpaController eventoJpaController = new EventoJpaController(manager);
            CampanaComunicadosJpaController campanaComunicadosJpaController = new CampanaComunicadosJpaController(manager);
            EncuestaJpaController encuestaJpaController = new EncuestaJpaController(manager);
            PqrJpaController pqrJpaController = new PqrJpaController(manager);
            ArrayList<Comentario> comentarios = new ArrayList<>();
            JSONArray arrayComentarios = new JSONArray();
            switch (tipocomentario) {
                case 1:
                    //eventos
                    Evento evento = eventoJpaController.findEvento(id);
                    comentarios = new ArrayList<>(evento.getComentarioCollection());
                    jSONObject.put("nombre", evento.getNombre());
                    break;
                case 2:
                    //campañas
                    CampanaComunicados campanaComunicados = campanaComunicadosJpaController.findCampanaComunicados(id);
                    comentarios = new ArrayList<>(campanaComunicados.getComentarioCollection());
                    jSONObject.put("nombre", campanaComunicados.getNombre());
                    break;
                case 3:
                    //Encuestas
                    Encuesta encuesta = encuestaJpaController.findEncuesta(id);
                    comentarios = new ArrayList<>(encuesta.getComentarioCollection());
                    jSONObject.put("nombre", encuesta.getNombre());
                    break;
                case 4:
                    //pqrs
                    Pqr pqr = pqrJpaController.findPqr(id);
                    comentarios = new ArrayList<>(pqr.getComentarioCollection());
                    switch (pqr.getTipo()) {
                        case 1:
                            jSONObject.put("nombre", "Petición");
                            break;
                        case 2:
                            jSONObject.put("nombre", "Queja");
                            break;
                        case 3:
                            jSONObject.put("nombre", "Reclamo");
                            break;
                        case 4:
                            jSONObject.put("nombre", "Solicitud");
                            break;
                    }

                    JSONObject jSONObjectComentario = new JSONObject();
                    jSONObjectComentario.put("id", 0);
                    jSONObjectComentario.put("msg", pqr.getPqr());
                    jSONObjectComentario.put("fecha", pqr.getFecha().longValue());
                    jSONObjectComentario.put("usuario_nombre", pqr.getUsuarioIdusuario().getNombre());
                    jSONObjectComentario.put("usuario_apellidos", pqr.getUsuarioIdusuario().getApellidos());
                    arrayComentarios.put(jSONObjectComentario);
                    break;

            }

            for (Comentario comentario : comentarios) {
                JSONObject jSONObjectComentario = new JSONObject();
                jSONObjectComentario.put("id", comentario.getIdcomentarioPqr());
                jSONObjectComentario.put("msg", comentario.getComentario());
                jSONObjectComentario.put("fecha", comentario.getFecha().longValue());
                jSONObjectComentario.put("usuario_nombre", comentario.getUsuarioIdusuario().getNombre());
                jSONObjectComentario.put("usuario_apellidos", comentario.getUsuarioIdusuario().getApellidos());
                arrayComentarios.put(jSONObjectComentario);
            }

            jSONObject.put("comentarios", arrayComentarios);
//            System.out.println("jSONObject: " + jSONObject);
            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Leyendo comentarios.");
            logUsuario.setTipo(27);
            logUsuarioJpaController.create(logUsuario);
        } else {
            jSONObject.put("error", 400);
            jSONObject.put("msg", "Error token");
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
