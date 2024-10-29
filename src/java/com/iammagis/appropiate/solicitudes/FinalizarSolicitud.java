/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.solicitudes;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Pqr;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.PqrJpaController;
import com.iammagis.appropiate.support.FCM;
import com.iammagis.appropiate.support.GetDynamicTable;
import com.iammagis.appropiate.support.PropertiesAcces;
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
public class FinalizarSolicitud extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final PropertiesAcces propertiesAcces = new PropertiesAcces();

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
            pqr.setEstado(2);
            pqrJpaController.edit(pqr);

            //enviar notificacion
            ArrayList<String> tokensArray = new ArrayList<>();
            Usuario usuarioEntidad = pqr.getUsuarioIdusuario();
            if (usuarioEntidad.getDevicesTokens() != null) {
                String devicesTokens = usuarioEntidad.getDevicesTokens();
                String[] tokens = devicesTokens.split(",");
                for (String token : tokens) {
                    tokensArray.add(token);
                }
            }

            FCM.send_FCM_NotificationMulti(tokensArray, propertiesAcces.FCM_SERVER, "PQRS finalizado: " + pqr.getPqr(), 2/*eventos*/);
            ArrayList<Pqr> pqrs;
            JSONArray jsonArray = new JSONArray();
            switch (usuario.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario()) {
                case 1:
                    //Root
                    //obtenemos todos los usuarios por ser root
                    pqrs = pqrJpaController.getPQRActivos();
                    jsonArray = GetDynamicTable.getPQRS(pqrs);
                    break;
                case 2:
                    //ADMINISTRADOR
                    pqrs = new ArrayList<>();
                    for (Entidad entidad : usuario.getEntidadCollection()) {
                        ArrayList<Pqr> pqrsEntidad = pqrJpaController.getPQRSActivoByEntidad(entidad.getIdentidad());
                        pqrs.addAll(pqrsEntidad);
                    }
                    jsonArray = GetDynamicTable.getPQRS(pqrs);
                    break;
                default:
                    break;
            }

            jSONObject.put("grid", jsonArray);
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
