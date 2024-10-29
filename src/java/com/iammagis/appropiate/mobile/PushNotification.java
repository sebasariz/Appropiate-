/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.mobile;

import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import java.io.PrintWriter;
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
public class PushNotification extends org.apache.struts.action.Action {

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
        String notification = request.getParameter("notification");
        String androidString = request.getParameter("android");
        String token = request.getParameter("token");
        System.out.println("token push: " + token);
        JSONObject jSONObject = new JSONObject();
        if (token != null && notification != null && androidString != null) {
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
            Usuario usuario = usuarioJpaController.getUsuarioWithToken(token);
            String devices = notification;
            System.out.println("notification: " + notification);
            if (usuario != null) {
                if (usuario.getDevicesTokens() != null && !usuario.getDevicesTokens().contains(devices)) {
                    devices = devices + "," + usuario.getDevicesTokens();
                    usuario.setDevicesTokens(devices);
                } else {
                    usuario.setDevicesTokens(devices);
                };
                usuarioJpaController.edit(usuario);
                jSONObject.put("code", 300);
            } else {
                jSONObject.put("code", 401);
                jSONObject.put("msg", "token no pertenece a usuario");
            }

        } else {
            jSONObject.put("code", 401);
            jSONObject.put("msg", "Parametro faltante");
        }

        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
