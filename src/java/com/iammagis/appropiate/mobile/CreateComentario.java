/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.CampanaComunicados;
import com.iammagis.appropiate.beans.Comentario;
import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.CampanaComunicadosJpaController;
import com.iammagis.appropiate.jpa.ComentarioJpaController;
import com.iammagis.appropiate.jpa.EncuestaJpaController;
import com.iammagis.appropiate.jpa.EventoJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.PqrJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import java.io.PrintWriter;
import java.math.BigInteger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class CreateComentario extends org.apache.struts.action.Action {

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
            String comentarioString = request.getParameter("comentario");
            EventoJpaController eventoJpaController = new EventoJpaController(manager);
            CampanaComunicadosJpaController campanaComunicadosJpaController = new CampanaComunicadosJpaController(manager);
            EncuestaJpaController encuestaJpaController = new EncuestaJpaController(manager);
            PqrJpaController pqrJpaController = new PqrJpaController(manager);

            Comentario comentario = new Comentario();
            comentario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            comentario.setComentario(comentarioString);
            comentario.setUsuarioIdusuario(usuario);

            switch (tipocomentario) {
                case 1:
                    //eventos
                    Evento evento = eventoJpaController.findEvento(id);
                    comentario.setEventoIdevento(evento);
                    break;
                case 2:
                    //campañas
                    CampanaComunicados campanaComunicados = campanaComunicadosJpaController.findCampanaComunicados(id);
                    comentario.setCampanaComunicadosIdcampana(campanaComunicados);
                    break;
                case 3:
                    //Encuestas
                    Encuesta encuesta = encuestaJpaController.findEncuesta(id);
                    comentario.setEncuestaIdencuesta(encuesta);
                    break;
                case 4:
                    //pqrs
                    Pqr pqr = pqrJpaController.findPqr(id);
                    comentario.setPqrIdpqr(pqr);
                    break;

            }
            ComentarioJpaController comentarioJpaController = new ComentarioJpaController(manager);
            comentarioJpaController.create(comentario);
            
            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Nuevo comentario");
            logUsuario.setTipo(19);
            logUsuarioJpaController.create(logUsuario);
            jSONObject.put("code", 300);
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
