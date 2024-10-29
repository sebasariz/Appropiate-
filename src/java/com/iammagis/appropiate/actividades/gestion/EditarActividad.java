/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.actividades.gestion;

import com.iammagis.appropiate.beans.Actividad;
import com.iammagis.appropiate.beans.LogUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.ActividadJpaController;
import com.iammagis.appropiate.jpa.LogUsuarioJpaController;
import com.iammagis.appropiate.support.Correo;
import java.io.PrintWriter;
import java.math.BigInteger;
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
import org.json.JSONObject;

/**
 *
 * @author sebasariz
 */
public class EditarActividad extends org.apache.struts.action.Action {

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

            int idActividad = Integer.parseInt(request.getParameter("id"));
            String actividad = request.getParameter("actividad");
            String responsable = request.getParameter("responsable");
            long fecha = Long.parseLong(request.getParameter("fecha")+43200000);
            String correo = request.getParameter("correo");

            ActividadJpaController actividadJpaController = new ActividadJpaController(manager);
            Actividad actividadObj = actividadJpaController.findActividad(idActividad);
            actividadObj.setActividad(actividad);
            actividadObj.setResponsable(responsable);
            actividadObj.setFecha(BigInteger.valueOf(fecha));
            actividadObj.setCorreoResponsable(correo);

            actividadJpaController.edit(actividadObj);

            LogUsuarioJpaController logUsuarioJpaController = new LogUsuarioJpaController(manager);
            LogUsuario logUsuario = new LogUsuario();
            logUsuario.setFecha(BigInteger.valueOf(System.currentTimeMillis()));
            logUsuario.setUsuarioIdusuario(usuario);
            logUsuario.setActividad("Editando actividad: " + actividadObj.getActividad());
            logUsuario.setTipo(4);
            logUsuarioJpaController.create(logUsuario);
            
            //Correo
            ArrayList<String> correos = new ArrayList<>();
            String template = Correo.templateActividad;
            template = template.replaceAll("$nombre", responsable);
            template = template.replaceAll("$descripcion", actividad);
            correos.add(correo);
            Correo correoSender = new Correo("Actividad asignada actualizada", template, correos);
            correoSender.start();
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
