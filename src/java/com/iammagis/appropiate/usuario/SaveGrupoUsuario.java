/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.usuario;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.GrupoInteres;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.GrupoInteresJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import com.iammagis.appropiate.support.GetDynamicTable;
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
import org.json.JSONObject;

/**
 *
 * @author sebasariz
 */
public class SaveGrupoUsuario extends org.apache.struts.action.Action {

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
            boolean state = Boolean.parseBoolean(request.getParameter("state"));
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));

//            System.out.println("id: " + id + " state:" + state + " idUsuario: " + idUsuario);
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
            Usuario usuarioGrupo = usuarioJpaController.findUsuario(idUsuario);

            GrupoInteresJpaController grupoInteresJpaController = new GrupoInteresJpaController(manager);
            ArrayList<GrupoInteres> grupoIntereses = new ArrayList<>(usuarioGrupo.getGrupoInteresCollection());

            GrupoInteres grupoInteres = grupoInteresJpaController.findGrupoInteres(id);
            if (state) {
                //agergarlo
                grupoIntereses.add(grupoInteres);
                System.out.println("agregando");
            } else {
                //eliminarlo
                System.out.println("eliminando");
                grupoIntereses.remove(grupoInteres);
            }
            usuarioGrupo.setGrupoInteresCollection(grupoIntereses);
            usuarioJpaController.edit(usuarioGrupo);

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
