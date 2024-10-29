/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.encuestas;

import com.iammagis.appropiate.beans.Encuesta;
import com.iammagis.appropiate.beans.Evento;
import com.iammagis.appropiate.beans.Pregunta;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EncuestaJpaController;
import com.iammagis.appropiate.jpa.EventoJpaController;
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
public class LoadEncuestaJson extends org.apache.struts.action.Action {

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
            jSONObject.put("id", encuesta.getIdencuesta());
            jSONObject.put("nombre", encuesta.getNombre());
            jSONObject.put("img", "img/" + encuesta.getImagen());
            jSONObject.put("idEntidad", encuesta.getEntidadIdentidad().getIdentidad());
            jSONObject.put("paras", new JSONArray(encuesta.getIdparaString()));
            
            
            
            ArrayList<Pregunta> preguntas = new ArrayList<>(encuesta.getPreguntaCollection());
            JSONArray array = new JSONArray();
            for (Pregunta pregunta : preguntas) {
                JSONArray arrayPregunta = new JSONArray();
                String tipo = "";
                if (null != pregunta.getTipo()) {
                    switch (pregunta.getTipo()) {
                        case 1:
                            tipo = "Si/No";
                            break;
                        case 2:
                            tipo = "Abierta";
                            break;
                        case 3:
                            tipo = "Escala";
                            break;
                        case 4:
                            tipo = "Multiple";
                            break;
                        default:
                            break;
                    }
                }
                arrayPregunta.put(pregunta.getNombre());
                arrayPregunta.put(tipo);
                arrayPregunta.put(pregunta.getEscala());
                arrayPregunta.put("<i class=\"fa fa-remove fa-2x\" title=\"Eliminar pregunta\"></i>");
                array.put(arrayPregunta);
            }
            jSONObject.put("grid", array);

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
