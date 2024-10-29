/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.EventoHasUsuario;
import com.iammagis.appropiate.beans.EventoHasUsuarioPK;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EventoHasUsuarioJpaController;
import com.iammagis.appropiate.jpa.EventoJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
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
public class CreateMeInteresa extends org.apache.struts.action.Action {

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
            int estado = Integer.parseInt(request.getParameter("estado"));
            int idEvento = Integer.parseInt(request.getParameter("id"));
            EventoJpaController eventoJpaController = new EventoJpaController(manager);
            Evento evento = eventoJpaController.findEvento(idEvento);
            EventoHasUsuarioPK eventoHasUsuarioPK = new EventoHasUsuarioPK();
            eventoHasUsuarioPK.setEventoIdevento(idEvento);
            eventoHasUsuarioPK.setUsuarioIdusuario(usuario.getIdusuario());
            EventoHasUsuarioJpaController eventoHasUsuarioJpaController = new EventoHasUsuarioJpaController(manager);
            EventoHasUsuario eventoHasUsuario = eventoHasUsuarioJpaController.findEventoHasUsuario(eventoHasUsuarioPK);
            if (estado != 3) {
                if (eventoHasUsuario == null) {
                    eventoHasUsuario = new EventoHasUsuario();
                    eventoHasUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
                    eventoHasUsuario.setUsuario(usuario);
                    eventoHasUsuario.setRespuesta(estado);
                    eventoHasUsuario.setEvento(evento);
                    eventoHasUsuario.setEventoHasUsuarioPK(eventoHasUsuarioPK);
                    eventoHasUsuarioJpaController.create(eventoHasUsuario);
                } else {
                    eventoHasUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
                    eventoHasUsuario.setUsuario(usuario);
                    eventoHasUsuario.setRespuesta(estado);
                    eventoHasUsuario.setEvento(evento);
                    eventoHasUsuario.setEventoHasUsuarioPK(eventoHasUsuarioPK);
                    eventoHasUsuarioJpaController.edit(eventoHasUsuario);
                }
            } else {
                if (eventoHasUsuario != null) {
                    eventoHasUsuarioJpaController.destroy(eventoHasUsuarioPK);
                }
            }

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Modificando intension evento: " + evento.getNombre());
            logUsuario.setTipo(20);
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
