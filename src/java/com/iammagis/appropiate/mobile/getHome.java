/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.Comentario;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.ComentarioJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import com.iammagis.appropiate.support.comparators.ComentariosComparatorFecha;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
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
public class getHome extends org.apache.struts.action.Action {

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
            ComentarioJpaController comentarioJpaController = new ComentarioJpaController(manager);
            ArrayList<Entidad> entidads = new ArrayList<>(usuario.getEntidadCollection());

            ArrayList<Comentario> comentariosTotales = new ArrayList<>();
            for (Entidad entidad : entidads) {
                ArrayList<Comentario> comentarios = comentarioJpaController.getComentariosLast20(entidad.getIdentidad(), usuario.getIdusuario());
                comentariosTotales.addAll(comentarios);
            }
            Collections.sort(comentariosTotales, new ComentariosComparatorFecha());
            System.out.println("comentariosTotales: " + comentariosTotales.size());
            JSONArray jSONArrayComentarios = new JSONArray();
            for (Comentario comentario : comentariosTotales) {
                JSONObject jSONObjectComentario = new JSONObject();
                if (comentario.getEventoIdevento() != null) {
                    jSONObjectComentario.put("tipo", 1);
                    jSONObjectComentario.put("id", comentario.getEventoIdevento().getIdevento());
                } else if (comentario.getCampanaComunicadosIdcampana() != null) {
                    jSONObjectComentario.put("tipo", 2);
                    jSONObjectComentario.put("id", comentario.getCampanaComunicadosIdcampana().getIdcampana());
                } else if (comentario.getEncuestaIdencuesta() != null) {
                    jSONObjectComentario.put("tipo", 3);
                    jSONObjectComentario.put("id", comentario.getEncuestaIdencuesta().getIdencuesta());
                } else if (comentario.getPqrIdpqr() != null) {
                    jSONObjectComentario.put("tipo", 4);
                    jSONObjectComentario.put("id", comentario.getPqrIdpqr().getIdpqr());
                }
                
                jSONObjectComentario.put("msg", comentario.getComentario());
                jSONObjectComentario.put("fecha", comentario.getFecha().longValue());
                jSONObjectComentario.put("usuario_nombre", comentario.getUsuarioIdusuario().getNombre());
                jSONObjectComentario.put("usuario_apellidos", comentario.getUsuarioIdusuario().getApellidos());
                jSONArrayComentarios.put(jSONObjectComentario);
            }
            jSONObject.put("comentarios", jSONArrayComentarios);
            
            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Abriendo aplicación movil.");
            logUsuario.setTipo(31);
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
