/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.EncuestaHasUsuario;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Pregunta;
import com.iammagis.appropiate.beans.Respuesta;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EncuestaHasUsuarioJpaController;
import com.iammagis.appropiate.jpa.EncuestaJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.RespuestaJpaController;
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
public class getPreguntasEncuesta extends org.apache.struts.action.Action {

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
        String token = request.getParameter("token");
        EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
        Usuario usuario = usuarioJpaController.getUsuarioWithToken(token);
        JSONObject jSONObject = new JSONObject();
        
        if (usuario != null) {
            System.out.println("calling");
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("entando: " + token + " id: " + id);
            EncuestaJpaController encuestaJpaController = new EncuestaJpaController(manager);
            Encuesta encuesta = encuestaJpaController.findEncuesta(id);
            RespuestaJpaController respuestaJpaController = new RespuestaJpaController(manager);
            ArrayList<Pregunta> preguntas = new ArrayList<>(encuesta.getPreguntaCollection());
            System.out.println("preguntas: " + preguntas.size());
            JSONArray arrayPreguntas = new JSONArray();
            for (Pregunta pregunta : preguntas) {
                JSONObject jSONObjectPreguntas = new JSONObject();
                jSONObjectPreguntas.put("id", pregunta.getIdpregunta());
                jSONObjectPreguntas.put("pregunta", pregunta.getNombre());
                jSONObjectPreguntas.put("escala", pregunta.getEscala());
                jSONObjectPreguntas.put("tipo", pregunta.getTipo());

                Respuesta respuesta = respuestaJpaController.findRespuestaByUserAndPregunta(pregunta.getIdpregunta(), usuario.getIdusuario());
                if (respuesta != null) {
                    jSONObjectPreguntas.put("respuesta", respuesta.getRespuesta());
                }

                arrayPreguntas.put(jSONObjectPreguntas);

            }
            System.out.println("arrayPreguntas: " + arrayPreguntas);
            jSONObject.put("preguntas", arrayPreguntas);

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Ingresando a encuesta: " + encuesta.getNombre());
            logUsuario.setTipo(34);
            logUsuarioJpaController.create(logUsuario);

            EncuestaHasUsuarioJpaController encuestaHasUsuarioJpaController = new EncuestaHasUsuarioJpaController(manager);
            EncuestaHasUsuario encuestaHasUsuario = encuestaHasUsuarioJpaController.findEncuestaHasUsuario(encuesta.getIdencuesta(), usuario.getIdusuario());
            if (encuestaHasUsuario != null) {
                encuestaHasUsuario.setRespuesta(1);
                encuestaHasUsuarioJpaController.edit(encuestaHasUsuario);
            }
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
