/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.encuestas.resultado;

import com.iammagis.appropiate.beans.Pregunta;
import com.iammagis.appropiate.beans.Respuesta;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EncuestaJpaController;
import com.iammagis.appropiate.jpa.PreguntaJpaController;
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
public class ResumenResultado extends org.apache.struts.action.Action {

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

        //aqui vamos a mostrar los resultados de acuerdo al tipo de pregunta
//        1 SI/NO
//        2 Abierta
//        3 Escala
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        JSONObject jSONObject = new JSONObject();
        if (usuario != null) {
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            int id = Integer.parseInt(request.getParameter("id"));
            PreguntaJpaController preguntaJpaController = new PreguntaJpaController(manager);
            Pregunta pregunta = preguntaJpaController.findPregunta(id);
            ArrayList<Respuesta> respuestas = new ArrayList<>(pregunta.getRespuestaCollection());
            jSONObject.put("tipo", pregunta.getTipo());
            String table = "";
            if (null != pregunta.getTipo()) {
                switch (pregunta.getTipo()) {
                    case 1:
                        int si = 0;
                        int no = 0;
                        for (Respuesta respuesta : respuestas) {
                            boolean respuestaBoolean = Boolean.parseBoolean(respuesta.getRespuesta());
                            if (respuestaBoolean) {
                                si++;
                            } else {
                                no++;
                            }
                        }
                        //armamos la data
                        JSONArray arrayData = new JSONArray();
                        JSONObject jSONObjectSi = new JSONObject();
                        jSONObjectSi.put("label", "Si");
                        jSONObjectSi.put("data", si);
                        arrayData.put(jSONObjectSi);
                        JSONObject jSONObjectNo = new JSONObject();
                        jSONObjectNo.put("label", "No");
                        jSONObjectNo.put("data", no);
                        arrayData.put(jSONObjectNo);
                        jSONObject.put("data", arrayData);
                        //SI/NO
                        break;

                    case 2:
                        table = "<table class=\"table table-striped font-12\" id=\"datatable2\">"
                                + "<thead><tr><td>Nombre</td><td>Respuesta</td></tr></thead><tbody>";
                        for (Respuesta respuesta : respuestas) {
                            table += "<tr>"
                                    + "<td>" + respuesta.getUsuarioIdusuario().getNombre() + " " + respuesta.getUsuarioIdusuario().getApellidos() + "</td>"
                                    + "<td>" + respuesta.getRespuesta() + "</td>"
                                    + "</tr>";
                        }
                        table += "</tbody></table>";
                        jSONObject.put("data", table);
                        //Abierta
                        break;

                    case 3:
                        //Escala
                        JSONObject jSONObjectEscala = new JSONObject();
                        for (Respuesta respuesta : respuestas) {
                            if (jSONObjectEscala.has(respuesta.getRespuesta())) {
                                int valor = jSONObjectEscala.getInt(respuesta.getRespuesta());
                                valor++;
                                jSONObjectEscala.put(respuesta.getRespuesta(), valor);
                            } else {
                                jSONObjectEscala.put(respuesta.getRespuesta(), 1);
                            }
                        }
                        //
                        table = "<table class=\"table table-striped font-12\" id=\"datatable2\">"
                                + "<thead>"
                                + "<tr><td>Escala</td>"
                                + "<td>Numero respuestas</td>"
                                + "</tr>"
                                + "</thead>";
                        int escala = Integer.parseInt(pregunta.getEscala());
                        for (int i = 0; i < escala; i++) {
                            table += "<tr><td>" + i + "</td>";
                            if (jSONObjectEscala.has(i + "")) {
                                table += "<td>" + jSONObjectEscala.getInt(i + "") + "</td>";
                            } else {
                                table += "<td>" + 0 + "</td>";
                            }
                            table += "</tr>";
                        }
                        table += "</tbody></table>";
                        jSONObject.put("data", table);
                        //Abierta
                        break;

                    default:
                        break;
                }
            }

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
