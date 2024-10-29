/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.asignacionmodulos;

import com.iammagis.appropiate.beans.Submodulo;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.SubmoduloJpaController;
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
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class LoadSubmodulosUsuario extends org.apache.struts.action.Action {

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

        HttpSession session = request.getSession();
        Usuario usuarioLog = (Usuario) session.getAttribute("usuario");
        request.setCharacterEncoding("utf-8");
        if (usuarioLog != null) {
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
            Usuario usuario = usuarioJpaController.findUsuario(idUsuario);

            if (usuario.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario() == 1) {
                //root  
            } else if (usuario.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario() == 2) {
                //Adinistrador
            }
            SubmoduloJpaController submoduloJpaController = new SubmoduloJpaController(manager);
            JSONArray datosCompletos = new JSONArray();
            ArrayList<Submodulo> submodulos = new ArrayList<>(submoduloJpaController.findSubmoduloEntities());
            ArrayList<Submodulo> submodulosUser = new ArrayList<>(usuario.getSubmoduloCollection());
            for (Submodulo subModulo : submodulos) {
                JSONArray data = new JSONArray();
                data.put(subModulo.getNombre());
                data.put(subModulo.getModuloIdmodulo().getNombre());
                String checked = "";
                for (Submodulo submoduloHasUsuario : submodulosUser) {
                    if (submoduloHasUsuario.getIdsubmodulo() == subModulo.getIdsubmodulo()) {
                        checked = "checked";
                    }
                }
                data.put("<td class=\"text-center\">"
                        + "<input type=\"checkbox\" " + checked
                        + " name=\"chk1\" id=\"chk1\" title=\"Activar/Desactivar modulo\" onchange=\"SaveSubmoduloUsuario(" + subModulo.getIdsubmodulo()
                        + ",this.checked)\" />"
                        + "</td>");
                datosCompletos.put(data);

            }
            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.print(datosCompletos);
        }
        return null;
    }
}
