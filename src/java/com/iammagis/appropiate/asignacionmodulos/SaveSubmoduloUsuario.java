/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.asignacionmodulos;

import com.iammagis.appropiate.beans.Modulo;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

/**
 *
 * @author sebastianarizmendy
 */
public class SaveSubmoduloUsuario extends org.apache.struts.action.Action {

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

        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        int idSubmodulo = Integer.parseInt(request.getParameter("idSubmodulo"));
        boolean estado = Boolean.parseBoolean(request.getParameter("state")); 

        EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
        SubmoduloJpaController submoduloJpaController = new SubmoduloJpaController(manager);
        UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);

        Usuario usuario = usuarioJpaController.findUsuario(idUsuario);
        ArrayList<Submodulo> submodulos = new ArrayList<>(usuario.getSubmoduloCollection());
        Submodulo submodulo = submoduloJpaController.findSubmodulo(idSubmodulo);
        ArrayList<Modulo> modulosUser = new ArrayList<>(usuario.getModuloCollection());
        if (estado) {
            //a guardar
            if (!modulosUser.contains(submodulo.getModuloIdmodulo())) {
                modulosUser.add(submodulo.getModuloIdmodulo());
            }
            usuario.setModuloCollection(modulosUser);
            submodulos.add(submodulo);
        } else {
            //eliminar
            submodulos.remove(submodulo);
        }
        usuario.setSubmoduloCollection(submodulos);
        usuarioJpaController.edit(usuario);
        JSONObject jSONObject = new JSONObject();
        response.setContentType("application/json");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(jSONObject);
        return null;
    }
}
