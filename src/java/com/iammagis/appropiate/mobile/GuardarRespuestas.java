/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Pregunta;
import com.iammagis.appropiate.beans.Respuesta;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.jpa.PqrJpaController;
import com.iammagis.appropiate.jpa.PreguntaJpaController;
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
public class GuardarRespuestas extends org.apache.struts.action.Action {

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
            PreguntaJpaController preguntaJpaController = new PreguntaJpaController(manager);
            RespuestaJpaController respuestaJpaController = new RespuestaJpaController(manager);
            String jsonArrayString = request.getParameter("json");
            JSONArray jSONArray = new JSONArray(jsonArrayString);
            Encuesta encuesta = null;
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObjectRespuesta = jSONArray.getJSONObject(i);
                Respuesta respuesta = respuestaJpaController.findRespuestaByUserAndPregunta(jSONObjectRespuesta.getInt("id"), usuario.getIdusuario());
                if (respuesta == null) {
                    Pregunta pregunta = preguntaJpaController.findPregunta(jSONObjectRespuesta.getInt("id"));
                    respuesta = new Respuesta();
                    respuesta.setPreguntaIdpregunta(pregunta);
                    respuesta.setUsuarioIdusuario(usuario);
                    respuesta.setRespuesta(jSONObjectRespuesta.getString("respuesta"));
                    respuestaJpaController.create(respuesta);

                    encuesta = pregunta.getEncuestaIdencuesta();
                } else {
                    respuesta.setRespuesta(jSONObjectRespuesta.getString("respuesta"));
                    respuestaJpaController.edit(respuesta);

                    encuesta = respuesta.getPreguntaIdpregunta().getEncuestaIdencuesta();
                }
            }
            String encuestaNombre = "";
            if (encuesta != null) {
                encuestaNombre = encuesta.getNombre();
            }
            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Guardando respuestas de encuesta: " + encuestaNombre);
            logUsuario.setTipo(23);
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
