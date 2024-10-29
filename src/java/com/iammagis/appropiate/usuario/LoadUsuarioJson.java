/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.usuario;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
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
public class LoadUsuarioJson extends org.apache.struts.action.Action {

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
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
            Usuario usuarioResponse = usuarioJpaController.findUsuario(id);
            jSONObject.put("idusuario", usuarioResponse.getIdusuario());
            jSONObject.put("idTipoUsuario", usuarioResponse.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario());
            if (!usuarioResponse.getEntidadCollection().isEmpty()) {
                ArrayList<Entidad> arrayList = new ArrayList<>(usuarioResponse.getEntidadCollection());
                JSONArray select= new JSONArray();
                for (Entidad entidad : arrayList) {
                    select.put(entidad.getIdentidad());
                }
                jSONObject.put("idEntidad", select);
            } else {
                jSONObject.put("idEntidad", 0);
            }
            jSONObject.put("nombre", usuarioResponse.getNombre());
            jSONObject.put("apellidos", usuarioResponse.getApellidos());
            jSONObject.put("email", usuarioResponse.getCorreo());
            jSONObject.put("pass", usuarioResponse.getPass());
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
