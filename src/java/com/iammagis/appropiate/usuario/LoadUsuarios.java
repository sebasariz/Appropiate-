/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.appropiate.usuario;

import com.iammagis.appropiate.beans.Entidad;
import com.iammagis.appropiate.beans.TipoUsuario;
import com.iammagis.appropiate.beans.Usuario;
import com.iammagis.appropiate.jpa.EntidadJpaController;
import com.iammagis.appropiate.jpa.TipoUsuarioJpaController;
import com.iammagis.appropiate.jpa.UsuarioJpaController;
import com.iammagis.appropiate.support.GetDynamicTable;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.json.JSONArray;

/**
 *
 * @author sebastianarizmendy
 */
public class LoadUsuarios extends org.apache.struts.action.Action {

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
        HttpSession httpSession = request.getSession();
        Usuario usuario = (Usuario) httpSession.getAttribute("usuario");
        ActionErrors errores = new ActionErrors();
        JSONArray jSONArray = new JSONArray();
        if (usuario != null) {
            EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppropiatePU");
            UsuarioJpaController usuarioJpaController = new UsuarioJpaController(manager);
            EntidadJpaController entidadJpaController = new EntidadJpaController(manager);
            String content = "";
            ArrayList<Entidad> entidads = new ArrayList<>();
            ArrayList<Usuario> usuarios = new ArrayList<>();
            TipoUsuarioJpaController tipoUsuarioJpaController = new TipoUsuarioJpaController(manager);
            ArrayList<TipoUsuario> tipoUsuarios = new ArrayList<>(tipoUsuarioJpaController.findTipoUsuarioEntities());
            Entidad entidad = (Entidad) httpSession.getAttribute("entidadGlobal");
            entidad= entidadJpaController.findEntidad(entidad.getIdentidad());
            switch (usuario.getTipoUsuarioIdtipoUsuario().getIdtipoUsuario()) {
                case 1:
                    //Root
                    //obtenemos todos los usuarios por ser root
                    usuarios = usuarioJpaController.getUsuariosByTipo(1);
                    usuarios.addAll(entidad.getUsuarioCollection());
                    jSONArray = GetDynamicTable.getUsersTable(usuarios);
                    entidads = new ArrayList<>(entidadJpaController.findEntidadEntities());
                    break;
                case 2:
                    //ADMINISTRADOR
                    tipoUsuarios.remove(0);
                    usuarios = new ArrayList<>();
                    entidad = entidadJpaController.findEntidad(entidad.getIdentidad());
                    usuarios.addAll(entidad.getUsuarioCollection());

                    jSONArray = GetDynamicTable.getUsersTable(usuarios);
                    entidads = new ArrayList<>(usuario.getEntidadCollection());
                    break;
                default:
                    break;
            }
            request.setAttribute("entidades", entidads);
            request.setAttribute("grid", jSONArray);
            content = "/pages/usuarios/usuarios.jsp";
            request.setAttribute("contenido", content);

            request.setAttribute("tipos", tipoUsuarios);
        } else {
            errores.add("register", new ActionMessage("erros.timepoSesion"));
            saveErrors(request, errores);
            return (new ActionForward(mapping.getInput()));
        }
        return mapping.findForward(SUCCESS);
    }
}
