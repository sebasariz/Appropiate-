/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.solicitudes;

import com.iammagis.appropiate.beans.Comentario;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.PqrJpaController;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class LoadSolicitudJson extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

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
            PqrJpaController pqrJpaController = new PqrJpaController(manager);
            Pqr pqr = pqrJpaController.findPqr(id);

            jSONObject.put("id", pqr.getIdpqr());
            jSONObject.put("identidad", pqr.getEntidadIdentidad().getIdentidad());
            jSONObject.put("nombre", pqr.getUsuarioIdusuario().getNombre() + " " + pqr.getUsuarioIdusuario().getApellidos());
            jSONObject.put("informacion", pqr.getPqr());
            jSONObject.put("fecha", pqr.getFecha().longValue());
            ArrayList<Comentario> comentarios = new ArrayList<>(pqr.getComentarioCollection());
            String comentariosString = "";

            
//            comentariosString += "<div>" + pqr.getPqr() + "(" + simpleDateFormat.format(new Date(pqr.getFecha().longValue())) + ")" + "</div>";
//            System.out.println("comentario: "+comentariosString);
            for (Comentario comentario : comentarios) {
                comentariosString += "<div>" + comentario.getUsuarioIdusuario().getNombre()
                        + " " + comentario.getUsuarioIdusuario().getApellidos() + " ("
                        + simpleDateFormat.format(new Date(comentario.getFecha().longValue())) + "): "
                        + comentario.getComentario() + "</div>";
            }
            jSONObject.put("comentario", comentariosString);
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
