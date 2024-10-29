/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.comentario;

import com.iammagis.appropiate.beans.Actividad;
import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.Comentario;
import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.ActividadJpaController;
import com.iammagis.appropiate.jpa.CampanaComunicadosJpaController;
import com.iammagis.appropiate.jpa.EncuestaJpaController;
import com.iammagis.appropiate.jpa.EventoJpaController;
import com.iammagis.appropiate.jpa.PqrJpaController;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sebasariz
 */
public class LoadComentarios extends org.apache.struts.action.Action {

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

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        JSONObject jSONObject = new JSONObject();
        if (usuario != null) {
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            int evento = Integer.parseInt(request.getParameter("tipo"));
            int id = Integer.parseInt(request.getParameter("id"));
            JSONArray jSONArray = new JSONArray();
            ArrayList<Comentario> comentarios = new ArrayList<>();
            EncuestaJpaController encuestaJpaController = new EncuestaJpaController(manager);
            switch (evento) {
                case 1:
                    Encuesta encuesta = encuestaJpaController.findEncuesta(id);
                    comentarios = new ArrayList<>(encuesta.getComentarioCollection());
                    break;
                case 2:
                    PqrJpaController pqrJpaController = new PqrJpaController(manager);
                    Pqr pqr = pqrJpaController.findPqr(id);
                    JSONObject jSONObjectComentario = new JSONObject();
                    jSONObjectComentario.put("id", pqr.getIdpqr());
                    jSONObjectComentario.put("comentario", pqr.getPqr());
                    jSONObjectComentario.put("fecha", pqr.getFecha().longValue());
                    jSONObjectComentario.put("usuario", pqr.getUsuarioIdusuario().getNombre() + " " + pqr.getUsuarioIdusuario().getApellidos());

                    jSONArray.put(jSONObjectComentario);
                    comentarios = new ArrayList<>(pqr.getComentarioCollection());
                    break;
                case 3:
                    EventoJpaController eventoJpaController = new EventoJpaController(manager);
                    Evento eventos = eventoJpaController.findEvento(id);
                    comentarios = new ArrayList<>(eventos.getComentarioCollection());
                    break;
                case 4:
                    CampanaComunicadosJpaController campanaComunicadosJpaController = new CampanaComunicadosJpaController(manager);
                    CampanaComunicados campanaComunicados = campanaComunicadosJpaController.findCampanaComunicados(id);
                    comentarios = new ArrayList<>(campanaComunicados.getComentarioCollection());
                    break;
                case 5:
                    ActividadJpaController actividadJpaController = new ActividadJpaController(manager);
                    Actividad actividad = actividadJpaController.findActividad(id);
                    comentarios = new ArrayList<>(actividad.getComentarioCollection());
                    break;
                default:
                    break;
            }

            for (Comentario comentario : comentarios) {
                JSONObject jSONObjectComentario = new JSONObject();
                jSONObjectComentario.put("id", comentario.getIdcomentarioPqr());
                jSONObjectComentario.put("comentario", comentario.getComentario());
                jSONObjectComentario.put("fecha", comentario.getFecha().longValue());
                jSONObjectComentario.put("usuario", comentario.getUsuarioIdusuario().getNombre() + " " + comentario.getUsuarioIdusuario().getApellidos());
                if (usuario.getIdusuario() != comentario.getUsuarioIdusuario().getIdusuario()) {
                    jSONObjectComentario.put("site", "right2 chat-main");
                }
                jSONArray.put(jSONObjectComentario);
            }
            jSONObject.put("grid", jSONArray);
//            System.out.println("jSONObject: " + jSONObject);
        } else {
            MessageResources messages = MessageResources.getMessageResources("com.iammagis.appropiate.ApplicationResource");
            String message = messages.getMessage(request.getLocale(), "erros.timepoSesion");
            jSONObject.put("error", message);
        }
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }

}
