/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.encuestas.resultado;

import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.Pregunta;
import com.iammagis.appropiate.beans.Respuesta;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EncuestaJpaController;
import com.iammagis.appropiate.jpa.PreguntaJpaController;
import com.iammagis.appropiate.jpa.RespuestaJpaController;
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
 * @author sebastianarizmendy
 */
public class LoadResultadoDetalle extends org.apache.struts.action.Action {

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
            int id = Integer.parseInt(request.getParameter("id"));
            EncuestaJpaController encuestaJpaController = new EncuestaJpaController(manager);
            Encuesta encuesta = encuestaJpaController.findEncuesta(id);

            ArrayList<Pregunta> preguntas = new ArrayList<>(encuesta.getPreguntaCollection());
            JSONArray jSONArrayPreguntas = new JSONArray();
            for (Pregunta pregunta : preguntas) {
                JSONObject jSONObjectPregunta = new JSONObject();
                jSONObjectPregunta.put("id", pregunta.getIdpregunta());
                jSONObjectPregunta.put("pregunta", pregunta.getNombre());
                String tipo = "";
                switch (pregunta.getTipo()) {
                    case 1:
                        tipo += "<td>Si/No</td>";
                        break;
                    case 2:
                        tipo += "<td>Abierta</td>";
                        break;
                    case 3:
                        tipo += "<td>Escala</td>";
                        break;
                    case 4:
                        tipo += "<td>Multiple</td>";
                        break;
                    default:
                        break;
                }
                jSONObjectPregunta.put("tipo", tipo);
                jSONObjectPregunta.put("respuestas", pregunta.getRespuestaCollection().size());
                jSONArrayPreguntas.put(jSONObjectPregunta);
            }
            jSONObject.put("preguntas", jSONArrayPreguntas);
            //total de invitados
            jSONObject.put("invitados", encuesta.getEntidadIdentidad().getUsuarioCollection().size());
            //respuestas parciales
            int parciales = encuestaJpaController.getPersonasRespondieron(id).size();
            jSONObject.put("parciales", parciales);
            
            RespuestaJpaController respuestaJpaController = new RespuestaJpaController(manager); 
            jSONObject.put("respuestas", respuestaJpaController.getRespuestasByEncuesta(id).size());
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
